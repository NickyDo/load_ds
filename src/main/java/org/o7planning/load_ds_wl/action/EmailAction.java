package org.o7planning.load_ds_wl.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
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
	private String from = " ";
	private String password = " ";
	private String to;
	private String subject;
	private String body;
	private String type;
	private String username = " ";
	private File fileResult;
	private String typeResult;
	InputStream inputStream;

	public String execute() {
		System.out.println("run email" + to + type);

		try {
			Properties propconfig = new Properties();
			File configFile = new File("/app/setup/tonbeller/sironKYC/client/0001/data/input/config.properties");
			FileReader reader = new FileReader(configFile);
			propconfig.load(reader);
			// String dc1 = prop.getProperty("DC1");

			from = propconfig.getProperty("usernameMail");
			password = propconfig.getProperty("passwordMail");
			System.out.println("usernameMail" + from);
			System.out.println("pass" + password);

			String keyHost = propconfig.getProperty("keyHostMail");
			String valueHost = propconfig.getProperty("valueHostMail");
			username = propconfig.getProperty("username");
			System.out.println("username" + username);

			String port = propconfig.getProperty("port");

			String tail = propconfig.getProperty("tail");

			Properties propsmail = new Properties();
			System.out.println("keyhost" + keyHost);
			System.out.println("keyhost" + valueHost);
			System.out.println("keyhost" + port);

			propsmail.put("mail.smtp.socketFactory.port", port);
			propsmail.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			propsmail.put("mail.smtp.auth", "true");
			propsmail.put("mail.smtp.starttls.enable", "false");
			propsmail.put(keyHost, valueHost);
			propsmail.put("mail.smtp.port", port);
			HttpServletRequest request = ServletActionContext.getRequest();
			if (request.getParameter("to") != null) {
				setTo(request.getParameter("to"));
			}
			if (request.getParameter("type") != null) {
				setType(request.getParameter("type"));
			} else {
				setType("delete");
			}

			System.out.print("email" + to);
			System.out.print("emailType" + type);
			System.out.print("tail" + tail);

			if (type != null) {
				System.out.print("start pending");

			}
			if (type.equals("pending")) {
				String receiv = request.getParameter("to") + tail;
				System.out.print("receiv" + receiv);

				setTo(receiv);
				setSubject("System Notifcation: Embargo list upload is pending for approval");
				setBody("Dear users, AML System would like to inform you that the Embargo list is pending for approval. Please kindly check.Regards,");
			} else if (type.equals("success")) {
				System.out.print("load_success");

				setSubject("System Notifcation: Embargo list upload was approved");
				setBody("Dear users, AML System would like to inform you that the Embargo list was approved. Regards");
			} else if (type.equals("delete")) {
				System.out.print("delete step1");
				setSubject("System Notifcation: Embargo list upload was rejected");
				setBody("Dear users, AML System would like to inform you that the Embargo list was rejected. Please kindly check.Regards,");
			} else {
				if (typeResult != null) {
					if (typeResult.equals("load_error")) {
						System.out.print("load_error");
						setSubject("System Notifcation: SironKYC Error during Embargo list processing");
						setBody("Dear users, During Embargo list processing, the system encountered an error Please check the log file for more detail.");
					}
				}
			}

			System.out.print("oooo");

			String ret = SUCCESS;

			System.out.print("ooo1");

			try {
				System.out.println("start");

				Session session = Session.getDefaultInstance(propsmail, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});
				System.out.println("step1");

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(from));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
				message.setSubject(subject);
				message.setText(body);
				if (typeResult != null) {
					System.out.println("send file attach");
					if (typeResult.equals("load_error")) {
						String file = "/app/setup/tonbeller/sironKYC/client/0001/data/log/tool/" + fileResult.getName();
						System.out.println("send file" + file);

						String fileName = fileResult.getName();

						DataSource source = new FileDataSource(file);
						message.setDataHandler(new DataHandler(source));
						message.setFileName(fileName);
					}

				}
				System.out.println("step2");

				Transport.send(message);
				System.out.println("step3");
				return ret;

			} catch (Exception e) {
				System.out.print("error" + e);
				ret = ERROR;
				e.printStackTrace();

				return ret;
			}
		} catch (Exception ex) {
			return "error";
		}

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

	public File getFileResult() {
		return fileResult;
	}

	public void setFileResult(File fileResult) {
		this.fileResult = fileResult;
	}

	public String getTypeResult() {
		return typeResult;
	}

	public void setTypeResult(String typeResult) {
		this.typeResult = typeResult;
	}

}
