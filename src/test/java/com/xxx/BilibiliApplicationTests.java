package com.xxx;

import com.xxx.utils.RSAUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class BilibiliApplicationTests {


    @Value("${publicKey}")
    private String publicKey;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${privateKey}")
    private String privateKey;
    @Test
    void contextLoads() {
        System.out.println(RSAUtil.encode("123456", publicKey));
        System.out.println(RSAUtil.decode("I+oUe1w5vHU2MQO0+ANW2TVt2Q2C2vkXZwbu8jF1UbdlbeE+Bwi0uI45D9li2Gqj+hac1Xoev2a8QgqNTo1syVQ87FW97tncv01dEqLMJ9G741UrLDKBHDvvQ8WmkYngiIzq6nCdwB0QD0u55B/IGCpoIjrOwPNmFFMPT2qabfw=", privateKey));
    }

    @Test
    public void test01(){
        System.out.println(passwordEncoder.encode("123456"));
        System.out.println(passwordEncoder.matches("123456", "$2a$10$9jCL35lY/x6sr3tFBGWN3.DUgbu8jMoN9OdDQjEL7pUCMnFo0uBqC"));
    }

}
