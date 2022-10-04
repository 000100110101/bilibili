package com.xxx.config;

import com.xxx.entity.User;

import com.xxx.handle.*;
import com.xxx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

//    @Autowired
//    private RoleService roleService;

    @Autowired
    private CustomFilter customFilter;

    @Autowired
    private CustomUrlDecisionManage customUrlDecisionManage;

    @Autowired
    private ResultAuthorizationEntryPoint resultAuthorizationEntryPoint;

    @Autowired
    private ResultAccessDeniedHandler resultAccessDeniedHandler;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            User user = userService.getUSerByUsername(username);
            if (user == null)
                throw new UsernameNotFoundException("用户名或密码错误");
//TODO            user.setRoles(roleService.getRoles(user.getUid()));
            return user;
        };

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/user/login","/captcha/**","/user/token",
                "/doc.html","/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**","/css/**",
                "/js/**","/index.html",
                "/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf()
                .disable()
                .cors()
                .and()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //都需要授权
                .anyRequest()
                .authenticated()
                //动态权限
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(customUrlDecisionManage);
                        object.setSecurityMetadataSource(customFilter);
                        return object;
                    }
                })
                .and()
                .headers()
                .cacheControl();

        //添加jwt登录授权过滤器
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        //添加自定义未授权和未登录
        http.exceptionHandling()
                .accessDeniedHandler(resultAccessDeniedHandler)
                .authenticationEntryPoint(resultAuthorizationEntryPoint);
    }
}
