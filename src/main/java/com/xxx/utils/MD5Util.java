package com.xxx.utils;

import org.springframework.util.DigestUtils;

/**
 * @desc: 基于apache.commons.codec封装摘要运算、编码解码工具类
 * @auth: cao_wencao
 * @date: 2020-05-09 17:59
 */
public class MD5Util {
    
    /**
     * 加密方法
     * @param str
     * @return
     */
    public static String md5(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }
    
    /**
     * 将用户输入的明文密码与随机盐进行拼装后再进行MD5加密
     * @param sourceStr
     * @return
     */

    public static String encode(String sourceStr,String salt) {
        String str = ""+salt.charAt(0)+salt.charAt(2) + sourceStr +salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

}
