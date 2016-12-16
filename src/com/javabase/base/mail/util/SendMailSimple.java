package com.javabase.base.mail.util;

import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SendMailSimple {
	
	private MimeMessage mimeMsg; // MIME邮件对象

	private Session session; // 邮件会话对象

	private Properties props; // 系统属性

	private String username = ""; // smtp认证用户名和密码

	private String password = "";

	private Multipart mp;   // Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象

	//constructor
	public SendMailSimple(){
		createMimeMessage();
	}

	public SendMailSimple(String smtp,String port) {
		setSmtpHost(smtp,port);
		createMimeMessage();
	}

	public void setSmtpHost(String hostName,String port) {
		if (props == null)
		props = System.getProperties(); // 获得系统属性对象
		props.put("mail.smtp.host", hostName); // 设置SMTP主机
		props.put("mail.smtp.port", port); // 设置SMTP端口
	}

	public boolean createMimeMessage() {
		try {
			System.out.println("准备获取邮件会话对象！");
			session = Session.getDefaultInstance(props, null); // 获得邮件会话对象
		} catch (Exception e) {
			System.err.println("获取邮件会话对象时发生错误！" + e);
			return false;
		}
		System.out.println("准备创建MIME邮件对象！");
		try {
			mimeMsg = new MimeMessage(session); // 创建MIME邮件对象
			mp = new MimeMultipart();
			return true;
		} catch (Exception e) {
			System.err.println("创建MIME邮件对象失败！" + e);
			return false;
		}
	}

	public void setNeedAuth(boolean need) {
		System.out.println("设置smtp身份认证：mail.smtp.auth   =   " + need);
		if (props == null)
			props = System.getProperties();
		if (need) {
			props.put("mail.smtp.auth", "true");
		} else {
			props.put("mail.smtp.auth", "false");
		}
	}

	public void setNamePass(String name, String pass) {
		username = name;
		password = pass;
	}

	//set the subject item.("设置邮件主题")
	public boolean setSubject(String mailSubject) {
		try {
			mimeMsg.setSubject(mailSubject,"gb2312");
			return true;
		} catch (Exception e) {
			System.err.println("设置邮件主题发生错误！");
			return false;
		}
	}

	
	public boolean setBody(String mailBody) {
		try {
			BodyPart bp = new MimeBodyPart();
			bp.setContent("<meta   http-equiv=Content-Type   content=text/html;   charset=gb2312>" + mailBody, "text/html;charset=gb2312");
			mp.addBodyPart(bp);
			return true;
		} catch (Exception e) {
			System.err.println("设置邮件正文时发生错误！" + e);
			return false;
		}
	}

	public boolean addFileAffix(String filename) {
		System.out.println("增加邮件附件：" + filename);
		try {
			BodyPart bp = new MimeBodyPart();
			FileDataSource fileds = new FileDataSource(filename);
			bp.setDataHandler(new DataHandler(fileds));
			bp.setFileName(fileds.getName());
			mp.addBodyPart(bp);
			return true;
		} catch (Exception e) {
			System.err.println("增加邮件附件：" + filename + "发生错误！" + e);
			return false;
		}
	}

	public boolean setFrom(String from) {
		System.out.println("设置发信人！");
		try {
			mimeMsg.setFrom(new InternetAddress(from)); // 设置发信人
			return true;
		} catch (Exception e) {
			System.err.println("设置发送人邮件地址失败！" + e);
			return false;
		}
	}

	public boolean setFrom(String from,String nick) {
		System.out.println("设置发信人！");
		try {
			mimeMsg.setFrom(new InternetAddress(from,nick)); // 设置发信人，昵称
			return true;
		} catch (Exception e) {
			System.err.println("设置发送人邮件地址失败！" + e);
			return false;
		}
	}

	public boolean setTo(String[] to) {
		if (to == null)
			return false;
		try {
			for (String element : to) { // 循环加入收件人
				if (element == null || "".equals(element)) {
					continue;
				}
				mimeMsg.addRecipient(Message.RecipientType.TO, new InternetAddress(element));
			}
			return true;
		} catch (Exception e) {
			System.err.println("设置收件人邮件地址失败！" + e);
			return false;
		}
	}

	/**
	 * send it .
	 * @return
	 */
	public boolean sendOut() {
		try {
			mimeMsg.setSentDate(new Date());
			mimeMsg.setContent(mp);
			mimeMsg.saveChanges();
			Session mailSession = Session.getInstance(props, null);
			Transport transport = mailSession.getTransport("smtp");
			transport.connect((String) props.get("mail.smtp.host"), username,password);
			transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
			transport.close();
			return true;
		} catch (Exception e) {
			System.err.println("邮件发送失败！" + e);
			return false;
		}
	}

	/**
	 * sendMail
	 * @param host
	 * 
	 * @param port
	 * 
	 * @param title
	 * 
	 * @param content
	 * 
	 * @param from
	 * 
	 * @param to
	 * 
	 * @param pass
	 * 
	 * @param nick
	 * 
	 * @return
	 */
	public static boolean sendMail(String host,String port,String title,String content,String from,String[] to,String pass,String nick) {
		SendMailSimple themail = new SendMailSimple(host,port);
		themail.setNeedAuth(true);// smtp是否需要认证
		themail.setSubject(title);
		themail.setBody(content);
		themail.setTo(to);
		themail.setFrom(from,nick);
		themail.setNamePass(from, pass);
		return themail.sendOut();
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String mailbody = "邮件内容";
		SendMailSimple themail = new SendMailSimple("mail.demo.com","25");
		themail.setNeedAuth(true);// smtp是否需要认证
		themail.setSubject("邮件标题！");
		themail.setBody(mailbody);
		themail.setTo(new String[]{"suping@demo.com"});
		themail.setFrom("suping@demo.com","天行网安");
		themail.addFileAffix("F:\\country.xml");//增加发送附件
		themail.setNamePass("suping@demo.com", "suping123");
		boolean result = themail.sendOut();
		
		//boolean result = sendMail("mail.topwalk.com","25","邮件标题！","邮件内容","suping@topwalk.com",new String[]{"suping@topwalk.com"},"suping123","天行");
		if (result) {
			System.out.println("发送邮件成功!");
		}else {
			System.out.println("发送邮件失败!");
		}
	}
}

