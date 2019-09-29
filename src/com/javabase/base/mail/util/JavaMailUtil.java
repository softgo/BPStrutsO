package com.javabase.base.mail.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;

/**
 * 邮件发送测试
 * @author 
 *
 */
public class JavaMailUtil {
	
	private static Logger log = Logger.getLogger(JavaMailUtil.class);

	/**
	 * 发送邮件(发邮件,一定要有邮件服务器)
	 * 
	 * @param server
	 *            邮件服务器(smtp.163.com)
	 * @param from
	 * 			  发送者
	 * @param pass
	 * 			  发送密码
	 * @param port
	 * 			  端口
	 * @param user
	 * 		      用户名
	 * @param pwd
	 *			  密码 
	 * @param mailTo
	 *            接收人
	 * @param mailType
	 *            发送类型(0--普通，1--抄送，2---暗送)
	 * @param subject
	 *            主题
	 * @param enclosure
	 *            附件
	 * @param content
	 *            内容
	 * @param hasSure
	 *            是否有附件（如果有附件,那么附件要有全路径）
	 * @return
	 */
	private static String sendMail(String server, String from, String pass, String port,String user, String pwd, String[] mailTo, int mailType,
			String subject, String[] enclosure, String content, boolean hasSure) {
		
		String revalue="true"; 
		try {
			//设置文件发送的属性
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.transport.protoco1", "smtp");
			props.put("mail.smtp.host", server);
			props.put("mail.smtp.port", port);
			//获得邮件的session
			Session session = Session.getDefaultInstance(props, null);
			//邮件消息
			Message msg = new MimeMessage(session);
			//设置发送者
			msg.setFrom(new InternetAddress(from,pass));
			javax.mail.Message.RecipientType type;
			switch (mailType) { // 设置邮件类型
			case 0:
				type = Message.RecipientType.TO;
				break;
			case 1:
				type = Message.RecipientType.CC;
				break;
			case 2:
				type = Message.RecipientType.BCC;
				break;
			default:
				type = Message.RecipientType.TO;
				break;
			}
			for (String element : mailTo) { // 循环加入收件人
				if (element == null || "".equals(element)) {
					break;
				}
				msg.addRecipient(type, new InternetAddress(element));
			}
			 //设置邮件主题
			msg.setSubject(subject);
			Multipart part = new MimeMultipart();
			BodyPart body = new MimeBodyPart();
			//文件头设置
			body.setContent(content ,"text/html;charset=UTF-8");
			part.addBodyPart(body);
			//判断是否有附件
			if (hasSure) { // 如果有附件
				for (String element : enclosure) {
					String fileName = element.substring(
							element.indexOf("//") + 1, element.length());
					BASE64Encoder base64 = new BASE64Encoder();
					String gbkName = base64.encode(fileName.getBytes()); // 获取转码后的文件名
					body = new MimeBodyPart();
					FileDataSource source = new FileDataSource(element);
					body.setDataHandler(new DataHandler(source));
					body.setFileName("=?GBK?B?" + gbkName + "?=");
					part.addBodyPart(body);
				}
			}
			msg.setSentDate(new Date());
			msg.setContent(part);
			Transport transport = session.getTransport("smtp");

			// 分别使用两种方式登录邮箱,邮箱为用户名和独立用户名
			try {
				transport.connect(server, user, pwd); // 登录邮箱
			} catch (MessagingException e1) {
				revalue="false";
				log.debug("登录邮箱服务器" + server + "异常"+e1.getMessage());
				// throw new Exception("登录邮箱服务器" + server + "失败", e1);
				return revalue;
			}

			// 发送邮件
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			if (log.isDebugEnabled()) {
				for (String string : mailTo) {
					log.debug("成功发送邮件到：" + string);
				}
			}
		} catch (Exception ex) {
			revalue="false";
			log.error("发送邮件异常"+ex.getMessage());
			try {
				throw new Exception("发送邮件失败,请联系管理员");
			} catch (Exception e) {
				revalue="false";
			}
		}
		return revalue;
	}
	
	/**
	 * 发送不带附件的邮件
	 * 
	 * @param server
	 * 			邮件服务器(mail.163.com)
	 * @param from
	 * 			邮件发送者
	 * @param pass
	 * 			邮件发送密码
	 * @param port
	 * 			邮件发送端口
	 * @param user
	 * 			用户名
	 * @param pwd
	 * 			用户密码
	 * @param mailTo
	 * 			接收人
	 * @param mailType
	 *          发送类型(0--普通，1--抄送，2---暗送)
	 * @param subject
	 *          主题
	 * @param content
	 * 			邮件内容
	 * @return
	 */
	public static boolean sendMailOutA(String server, String from, String pass, String port,String user, String pwd, String[] mailTo, int mailType,
			String subject, String content) {
		String result = sendMail(server, from, pass, port, user, pwd, mailTo, mailType, subject,null, content, false);
		if(result != null && result.equals("true")){
			System.out.println("发送成功!");
			return true;
		}else{
			System.out.println("发送失败!");
			return false;
		}
	}
	
	public static void sendMailOutB(String server, String from, String nick, String port,String user, String pwd, String[] mailTo, int mailType,
			String subject, String content) {
		String result = sendMail(server, from, nick, port, user, pwd, mailTo, mailType, subject,null, content, false);
		if(result!=null && result.equals("true")){
		   System.out.println("发送成功!");
		}else{
			System.out.println("发送失败!");
		}
	}

	/**
	 * 发送带附件的邮件
	 * 
	 * @param server
	 *          邮件服务器(mail.163.com)
	 * @param from
	 * 			邮件发送者
	 * @param pass
	 * 			邮件发送密码
	 * @param port
	 * 			邮件发送端口
	 * @param user
	 * 			用户名
	 * @param pwd
	 * 			用户密码
	 * @param mailTo
	 * 			接收人
	 * @param mailType
	 *          发送类型(0--普通，1--抄送，2---暗送)
	 * @param subject
	 * 			主题
	 * @param enclosure
	 * 			附件(跟附件的完整路径)
	 * @param content
	 * 			邮件内容
	 */
	public static void sendMailAffixA(String server, String from, String pass, String port,String user, String pwd, String[] mailTo, int mailType,
			String subject, String[] enclosure, String content) {
		//发送邮件	
		String result=sendMail(server, from, pass, port, user, pwd, mailTo, mailType, subject,enclosure, content, true);
		if(result!=null && result.equals("true")){
			System.out.println("发送成功!");
		}else{
			System.out.println("发送失败!");
		}
	}
	
	public static boolean sendMailAffixB(String server, String from, String pass, String port,String user, String pwd, String[] mailTo, int mailType,
			String subject, String[] enclosure, String content) {
		//发送邮件	
		String result=sendMail(server, from, pass, port, user, pwd, mailTo, mailType, subject,enclosure, content, true);
		if(result!=null && result.equals("true")){
			System.out.println("发送成功!");
		   return true;
		}else{
			System.out.println("发送失败!");
			return false;
		}
	}
	
	
}
