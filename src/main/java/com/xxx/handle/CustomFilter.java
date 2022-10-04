package com.xxx.handle;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import java.util.Collection;

@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {

    AntPathMatcher antPathMatcher = new AntPathMatcher();

//    @Autowired
//    private MenuService menuService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String url = ((FilterInvocation) object).getRequestUrl();
//        List<Menu> menus = menuService.getAllMenuAndMenuRole();
//
//        for (Menu menu:menus){
//            if (menu.getUrl()!=null && antPathMatcher.match(menu.getUrl(),url)){
//                String[] strings = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
//                return SecurityConfig.createList(strings);
//            }
//        }
        //没匹配，分配为Login角色
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
