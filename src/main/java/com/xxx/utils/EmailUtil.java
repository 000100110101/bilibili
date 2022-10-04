package com.xxx.utils;

import com.xxx.entity.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class EmailUtil {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public EmailUtil(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /*
    * @param to 收件邮箱
    * @param code 验证码
    * */
    public void sendEmailCode(String to,String code) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String today = simpleDateFormat.format(new Date());
        String encrypted = to.substring(0,5) + "*****";
        MimeMessageHelper minehelper = new MimeMessageHelper(message, true);
        //谁发
        minehelper.setFrom(from);
        //谁要接收
        minehelper.setTo(to);
        //邮件主题
        minehelper.setSubject("安全验证");
        //邮件内容   true 表示带有附件或html
        //验证码模板
        String str = "<div style=\"background:#fff;border:1px solid #ccc;margin:2%%;padding:0 30px\">\n" +
                "<div style=\"line-height:40px;height:40px\">&nbsp;</div>\n" +
                "<p style=\"margin:0;padding:0;font-size:14px;line-height:30px;color:#333;font-family:arial,sans-serif;font-weight:bold\">亲爱的用户：</p>\n" +
                "<div style=\"line-height:20px;height:20px\">&nbsp;</div>\n" +
                "<p style=\"margin:0;padding:0;line-height:30px;font-size:14px;color:#333;font-family:'宋体',arial,sans-serif\">您好！感谢您使用通讯录备份，您的账号(%s)正在进行邮箱验证，本次请求的验证码为：</p>\n" +
                "<p style=\"margin:0;padding:0;line-height:30px;font-size:14px;color:#333;font-family:'宋体',arial,sans-serif\"><b style=\"font-size:18px;color:#f90\">%s</b><span style=\"margin:0;padding:0;margin-left:10px;line-height:30px;font-size:14px;color:#979797;font-family:'宋体',arial,sans-serif\">(为了保障您帐号的安全性，请在5分钟内完成验证。)</span></p>\n" +
                "<div style=\"line-height:80px;height:80px\">&nbsp;</div>\n" +
                "<p style=\"margin:0;padding:0;line-height:30px;font-size:14px;color:#333;font-family:'宋体',arial,sans-serif\">通讯录备份团队</p>\n" +
                "<p style=\"margin:0;padding:0;line-height:30px;font-size:14px;color:#333;font-family:'宋体',arial,sans-serif\">%s</p>\n" +
                "<div style=\"line-height:20px;height:20px\">&nbsp;</div>\n" +
                "</div>";
        minehelper.setText(String.format(str,encrypted,code,today), true);
        mailSender.send(message);
    }

    /*
    * @param EmailObj
    * 公告
    * */
    public void sendNotification(Email obj) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String today = simpleDateFormat.format(new Date());
        MimeMessageHelper minehelper = new MimeMessageHelper(message, true);
        //谁发
        minehelper.setFrom(from);
        //谁要接收
        minehelper.setTo(obj.getTo());
        //邮件主题
        minehelper.setSubject(obj.getSubject());
        //邮件内容   true 表示带有附件或html
        //通知模板
        String str1 = "<div style=\"background:#fff;border:1px solid #ccc;margin:2%%;padding:0 30px\">\n" +
                "<div style=\"line-height:40px;height:40px\">&nbsp;</div>\n" +
                "<p style=\"margin:0;padding:0;font-size:14px;line-height:30px;color:#333;font-family:arial,sans-serif;font-weight:bold\">亲爱的用户：</p>\n" +
                "<div style=\"line-height:20px;height:20px\">&nbsp;</div>\n" +
                "<p style=\"margin:0;padding:0;line-height:30px;font-size:14px;color:#333;font-family:'宋体',arial,sans-serif\">%s</p>\n" +
                "<div style=\"line-height:80px;height:80px\">&nbsp;</div>\n" +
                "<p style=\"margin:0;padding:0;line-height:30px;font-size:14px;color:#333;font-family:'宋体',arial,sans-serif\">人人外卖团队</p>\n" +
                "<p style=\"margin:0;padding:0;line-height:30px;font-size:14px;color:#333;font-family:'宋体',arial,sans-serif\">%s</p>\n" +
                "<div style=\"line-height:20px;height:20px\">&nbsp;</div>\n" +
                "</div>";
        minehelper.setText(String.format(str1,obj.getContent(),today), true);
        mailSender.send(message);
    }
}
