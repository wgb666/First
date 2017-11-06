package com.offcn;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailSendApp {
	private static JavaMailSenderImpl mailSender=null;
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-mail.xml");
		mailSender=(JavaMailSenderImpl) context.getBean("mailSender");
		Mail mail=new Mail();
		mail.setFrom("18034320772@163.com");
		mail.setTo("1399429387@qq.com");
		mail.setSubject("这是测试邮件");
		mail.setContent("邮件内容");
		//sendPureMail(mail);
		try {
			sendMailWithAttachment();
			//sendMailHtmlWithAttachment();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
	//普通文本Email
    public static void sendPureMail(Mail mail) {
        SimpleMailMessage message = new SimpleMailMessage();
 
        message.setFrom(mail.getFrom());
        message.setTo(mail.getTo());
        message.setSubject(mail.getSubject());
        message.setText(mail.getContent());
        mailSender.send(message);
    }
 
    //带多个附件的Email
    public static void sendMailWithAttachment() throws MessagingException {
 
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
 
        helper.setFrom("18034320772@163.com");
        helper.setTo("1399429387@qq.com");
        helper.setSubject("这里是标题(带多个附件）!");
        helper.setText("这里是内容(带附件）");
 
        //添加两个附件（附件位置位于java-->resources目录)，可根据需要添加或修改
        ClassPathResource image = new ClassPathResource("/kl.jpg");
       
        helper.addAttachment("Coupon.png", image);
        
 
        mailSender.send(message);
    }
 
    //带附件的HTML格式的Email
    public static void sendMailHtmlWithAttachment() throws MessagingException {
 
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true,"GBK"); //解决乱码问题
 
        helper.setFrom("18034320772@163.com");
        helper.setTo("1399429387@qq.com");
        helper.setSubject("这里是标题(Html带附件）!");
        //设置META解决乱码问题
        helper.setText("<html><META http-equiv=Content-Type content='text/html; charset=GBK'><body><img src='cid:Coupon'>" +
                "<h4>" + "测试乱码" + " says...</h4>" +
                "<i>" + "测试乱码2" + "</i>" +
                "</body></html>", true);
 
        //图片嵌入到html文件中
        ClassPathResource couponImage = new ClassPathResource("/kl.jpg");
        helper.addInline("Coupon", couponImage);
 
        //图片作为附件发送
        ClassPathResource couponImage2 = new ClassPathResource("/kl.jpg");
        helper.addAttachment("Coupon.jpg", couponImage2);
 
 
        mailSender.send(message);
    }
}
