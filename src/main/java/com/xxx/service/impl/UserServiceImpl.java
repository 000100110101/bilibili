package com.xxx.service.impl;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xxx.constants.RedisConstants;
import com.xxx.entity.Result;
import com.xxx.entity.User;
import com.xxx.entity.UserInfo;
import com.xxx.handle.CustomizeException;
import com.xxx.mapper.UserMapper;
import com.xxx.service.UserInfoService;
import com.xxx.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.utils.RSAUtil;
import com.xxx.utils.SecurityContextUtil;
import com.xxx.utils.TokenUtil;
import com.xxx.utils.ValidUtil;
import com.xxx.dto.UserDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.xxx.constants.RedisConstants.USER_REGISTER_CAPTCHA;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author JINMING
 * @since 2022-09-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Value("${privateKey}")
    private String privateKey;

    private final UserInfoService userInfoService;

    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String,String> redisTemplate;

    private final UserDetailsService userDetailsService;

    public UserServiceImpl(PasswordEncoder passwordEncoder, RedisTemplate<String, String> redisTemplate, UserDetailsService userDetailsService, UserInfoService userInfoService) {
        this.passwordEncoder = passwordEncoder;
        this.redisTemplate = redisTemplate;
        this.userDetailsService = userDetailsService;
        this.userInfoService = userInfoService;
    }

    @Override
    public User getUSerByUsername(String username) {
        return getOne(new LambdaQueryWrapper<User>().eq(User::getEmail,username));
    }

    @Transactional
    @Override
    public Result insert(@Valid UserDto userDto, BindingResult result) {
        //参数校验
        FieldError fieldError = ValidUtil.paramValid(result);
        if (fieldError != null){
            return Result.error(fieldError.getField()+":"+fieldError.getDefaultMessage());
        }

        String code = redisTemplate.opsForValue().get(USER_REGISTER_CAPTCHA + userDto.getUuid() + ":" + userDto.getName());
        if (code == null || code.equalsIgnoreCase(userDto.getCode())){
            return Result.error("验证码错误");
        }
        //是否注册
        User user = getUSerByUsername(userDto.getEmail());
        if (user != null)
            return Result.error("该邮箱已注册");

        //解密
        String pwd = RSAUtil.decode(userDto.getPwd(), privateKey);
        //判断密码规格是否合适
        if (pwd == null || pwd.length() < 6 || pwd.length() > 12)
            return Result.error("密码长度应为6到12位");
        //判断昵称规格是否合适
        String name = userDto.getName();
        if (name == null || name.length() < 2 || name.length() > 8)
            return Result.error("昵称长度应为2到8位");
        user = new User(name, userDto.getEmail(), passwordEncoder.encode(pwd));
        if (save(user)){
            UserInfo userInfo = new UserInfo();
            userInfo.setUid(user.getId());
            if (userInfoService.save(userInfo)){
                return Result.success();
            }else {
                throw new CustomizeException("添加失败",500);
            }
        }
        return Result.error();
    }

    @Override
    public Result delete(Integer id) {
        if (removeById(id))
            return Result.success();
        return Result.error();
    }

    @Override
    public Result update(User user) {
        if (!StringUtils.isBlank(user.getPwd()))
            user.setPwd(passwordEncoder.encode(user.getPassword()));
        if (updateById(user))
            return Result.success();
        return Result.error();
    }

    @Override
    public Result info(Integer id) {
        User user = getById(id);
        user.setPwd(null);
        user.setUserInfo(userInfoService.getById(id));
        return Result.success().put("info",user);
    }

    @Override
    public Result info() {
        User user = SecurityContextUtil.getLoginUser();
        return info(user.getId());
    }

    @Override
    public Result login(UserDto userDto, BindingResult result, HttpServletRequest request) {
        //参数校验
        FieldError fieldError = ValidUtil.paramValid(result);
        if (fieldError != null){
            return Result.error(fieldError.getField()+":"+fieldError.getDefaultMessage());
        }

        String pwd = RSAUtil.decode(userDto.getPwd(), privateKey);
        if (pwd.length() < 6 || pwd.length() > 12)
            return Result.error("密码长度应为6到12位");
        String tokenKey = RedisConstants.USER_LOGIN_CAPTCHA + userDto.getUuid();
        String code = redisTemplate.opsForValue().get(tokenKey);
        if(StringUtils.isBlank(userDto.getCode()) || StringUtils.isBlank(code) || code.equalsIgnoreCase(userDto.getCode()))
            return Result.error("验证码错误");

        UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getEmail());
        if (userDetails == null || !passwordEncoder.matches(pwd,userDetails.getPassword())){
            return Result.error("用户名或密码错误!");
        }

        if (!userDetails.isEnabled()){
            return Result.error("用户已被冻结!");
        }

        //添加到上下文中
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //用完后删除redis中的key
        redisTemplate.delete(tokenKey);
        String username = userDetails.getUsername();
        String token = TokenUtil.getLoginToken(username);
        String refreshToken = TokenUtil.getRefreshToken(username);

        baseMapper.removeToken(username);
        baseMapper.insertToken(username,refreshToken);

        return Result.success().put("token",token).put("refreshToken",refreshToken);
    }

    @Override
    public Result getToken(String token) {
        try {
            String username = TokenUtil.getUsername(token);
            //TODO 查询数据库
            String refreshToken = baseMapper.selectByToken(token);
            if (StringUtils.isEmpty(refreshToken))
                return Result.error("token已失效");
            //生成新token
            String loginToken = TokenUtil.getLoginToken(username);
            return Result.success().put("token",loginToken);
        }catch (JWTVerificationException e){
            return Result.error("token已失效");
        }
    }

    @Override
    public Result logout() {
        User loginUser = SecurityContextUtil.getLoginUser();
        String email = loginUser.getEmail();
        baseMapper.removeToken(email);
        return Result.success();
    }

}
