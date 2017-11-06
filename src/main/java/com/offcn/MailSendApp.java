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
		mail.setSubject("���ǲ����ʼ�");
		mail.setContent("�ʼ�����");
		//sendPureMail(mail);
		try {
			sendMailWithAttachment();
			//sendMailHtmlWithAttachment();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
	//��ͨ�ı�Email
    public static void sendPureMail(Mail mail) {
        SimpleMailMessage message = new SimpleMailMessage();
 
        message.setFrom(mail.getFrom());
        message.setTo(mail.getTo());
        message.setSubject(mail.getSubject());
        message.setText(mail.getContent());
        mailSender.send(message);
    }
 
    //�����������Email
    public static void sendMailWithAttachment() throws MessagingException {
 
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
 
        helper.setFrom("18034320772@163.com");
        helper.setTo("1399429387@qq.com");
        helper.setSubject("�����Ǳ���(�����������!");
        helper.setText("����������(��������");
 
        //�����������������λ��λ��java-->resourcesĿ¼)���ɸ�����Ҫ��ӻ��޸�
        ClassPathResource image = new ClassPathResource("/kl.jpg");
       
        helper.addAttachment("Coupon.png", image);
        
 
        mailSender.send(message);
    }
 
    //��������HTML��ʽ��Email
    public static void sendMailHtmlWithAttachment() throws MessagingException {
 
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true,"GBK"); //�����������
 
        helper.setFrom("18034320772@163.com");
        helper.setTo("1399429387@qq.com");
        helper.setSubject("�����Ǳ���(Html��������!");
        //����META�����������
        helper.setText("<html><META http-equiv=Content-Type content='text/html; charset=GBK'><body><img src='cid:Coupon'>" +
                "<h4>" + "��������" + " says...</h4>" +
                "<i>" + "��������2" + "</i>" +
                "</body></html>", true);
 
        //ͼƬǶ�뵽html�ļ���
        ClassPathResource couponImage = new ClassPathResource("/kl.jpg");
        helper.addInline("Coupon", couponImage);
 
        //ͼƬ��Ϊ��������
        ClassPathResource couponImage2 = new ClassPathResource("/kl.jpg");
        helper.addAttachment("Coupon.jpg", couponImage2);
 
 
        mailSender.send(message);
    }
}
