package com.xxx.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.xxx.entity.Result;
import com.xxx.handle.CustomizeException;
import com.xxx.utils.EmailUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.xxx.constants.RedisConstants.*;

@Api(tags = "获取验证码")
@RequestMapping("captcha")
@RestController
public class CaptchaController {

    private final DefaultKaptcha defaultKaptcha;

    private final RedisTemplate<String,Object> redisTemplate;

    private final EmailUtil emailUtil;

    public CaptchaController(DefaultKaptcha defaultKaptcha, RedisTemplate<String,Object> redisTemplate, EmailUtil emailUtil) {
        this.defaultKaptcha = defaultKaptcha;
        this.redisTemplate = redisTemplate;
        this.emailUtil = emailUtil;
    }

    @ApiOperation(value = "注册验证码")
    @PostMapping(value = "/register")
    public Result registerCaptcha(String email){
        String uuid = UUID.randomUUID().toString();
        String text = RandomStringUtils.randomNumeric(4);
            try {
                emailUtil.sendEmailCode(email, text);
            } catch (MessagingException e) {
                throw new CustomizeException("邮件发送失败",500);
            }
        redisTemplate.opsForValue().set(USER_REGISTER_CAPTCHA + uuid + ":" + email, text,USER_LOGIN_CAPTCHA_TIME, TimeUnit.MINUTES);
        return Result.success().put("uuid", uuid);
    }


    @ApiOperation(value = "图片验证码")
    @GetMapping(value = "/img")
    public Result captcha(){
        String uuid = UUID.randomUUID().toString();
        //获取验证码文本内容
        String text = defaultKaptcha.createText();
        //将验证码放到redis中
        redisTemplate.opsForValue().set(USER_LOGIN_CAPTCHA+uuid,text,USER_LOGIN_CAPTCHA_TIME, TimeUnit.MINUTES);
        //根据文本内容创建图形验证码
        BufferedImage image = defaultKaptcha.createImage(text);
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "jpg", os);
        }
        catch (IOException e)
        {
            return Result.error(e.getMessage());
        }
        //data:image/jpeg;base64,
        return Result.success().put("uuid", uuid).put("img", Base64.encode(os.toByteArray()));
    }
}