package org.o7planning.load_ds_wl.action;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class EmailAction extends ActionSupport {
	private String from = "zadoraemonzu@gmail.com";
	private String password = "gauin1123581321";
	private String to;
	private String subject;
	private String body;
	private String type;

	static Properties properties = new Properties();
	static {
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");
	}

	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();

		setTo(request.getParameter("to"));
		setType(request.getParameter("type"));
		System.out.print("email" + to);
		System.out.print("emailType" + type);

		if (type.equals("pending")) {
			setSubject("System Notifcation: Embargo list upload is pending for approval");
			setBody("Dear users, AML System would like to inform you that the Embargo list is pending for approval. Please kindly check.Regards,");
		} else if (type.equals("success")) {
			setSubject("System Notifcation: Embargo list upload was approved");
			setBody("Dear users, AML System would like to inform you that the Embargo list was approved. Regards");
		} else {
			setSubject("System Notifcation: Embargo list upload was rejected");
			setBody("Dear users, AML System would like to inform you that the Embargo list was rejected. Please kindly check.Regards,");
		}
		String ret = SUCCESS;
		try {
			Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(from, password);
				}
			});

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(body);
			Transport.send(message);
		} catch (Exception e) {
			ret = ERROR;
			e.printStackTrace();
		}
		return ret;
	}
	
	

	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public static Properties getProperties() {
		return properties;
	}

	public static void setProperties(Properties properties) {
		EmailAction.properties = properties;
	}
}
