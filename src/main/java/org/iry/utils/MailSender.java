package org.iry.utils;

import java.util.Arrays;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * This class is an implementation of Email.
 * 
 * @author vpatil
 */
@Component
public class MailSender {

	private static final Logger LOGGER = Logger.getLogger(MailSender.class);

	private static final String EMAIL_SMTP_HOST = "mail.smtp.host";
	private static final String EMAIL_SMTP_PORT = "mail.smtp.port";
	private static final String EMAIL_SMTP_AUTH = "mail.smtp.auth";
	private static final String EMAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";

	private String mailSmtpHost;
	private String mailSmtpPort;
	private String mailSmtpAuth;
	private String mailSmtpStarttlsEnable;
	private String mailSmtpFromAddress;
	private String mailSmtpFromPassword;
	private String mailSupportAddress;
	private Properties props = null;

	/**
	 * Method to initialize <code>Properties</code> object.All the values for property is loaded from
	 * cc-config.properties file using Spring.
	 */
	public void init() {
		props = System.getProperties();
		props.put(EMAIL_SMTP_AUTH, mailSmtpAuth);
		props.put(EMAIL_SMTP_STARTTLS_ENABLE, mailSmtpStarttlsEnable);
		props.put(EMAIL_SMTP_HOST, mailSmtpHost);
		props.put(EMAIL_SMTP_PORT, mailSmtpPort);
	}

	/**
	 * Method to send the email.
	 * 
	 * @param to - to emails.
	 * @param subject - subject of email.
	 * @param body - body of email.
	 */
	public void sendMail(final String[] to, final String subject, final String body) {
		final Session session = getMailSession();
		Message message = null;
		try {
			message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailSmtpFromAddress));
			final InternetAddress add[] = new InternetAddress[to.length];
			for (int i = 0; i < to.length; i++) {
				add[i] = new InternetAddress(to[i]);
			}
			message.setRecipients(Message.RecipientType.TO, add);
			message.setSubject(subject);
			message.setText(body);
			Transport.send(message);
		} catch (MessagingException e) {
			int i = 1;
			while (i <= 3) {
				i++;
				try {
					Thread.sleep(10000);
					Transport.send(message);
					break;
				} catch (Exception e1) {
					LOGGER.error("Exception thrown againg while resending emil to : " + Arrays.toString(to)
							+ " . Re attempt no is : " + i + "." + e1.getLocalizedMessage(), e1);
				}
			}
			LOGGER.error(
				"Exception thrown while sending emil to : " + Arrays.toString(to) + " ." + e.getLocalizedMessage(), e);
		}
	}

	/**
	 * Method to send the email.
	 * 
	 * @param to - to emails.
	 * @param cc - cc emails.
	 * @param subject - subject of email.
	 * @param body - body of email.
	 */
	public void sendMail(final String[] to, final String[] cc, final String subject, final String body) {
		final Session session = getMailSession();
		Message message = null;
		try {
			message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailSmtpFromAddress));
			final InternetAddress add[] = new InternetAddress[to.length];
			for (int i = 0; i < to.length; i++) {
				add[i] = new InternetAddress(to[i]);
			}

			final InternetAddress ccMails[] = new InternetAddress[cc.length];
			for (int i = 0; i < cc.length; i++) {
				ccMails[i] = new InternetAddress(cc[i]);
			}
			message.setRecipients(Message.RecipientType.TO, add);
			message.setRecipients(Message.RecipientType.CC, ccMails);
			message.setSubject(subject);
			message.setText(body);
			Transport.send(message);
		} catch (MessagingException e) {
			int i = 1;
			while (i <= 3) {
				i++;
				try {
					Thread.sleep(10000);
					Transport.send(message);
					break;
				} catch (Exception e1) {
					LOGGER.error("Exception thrown againg while resending emil to : " + Arrays.toString(to)
							+ " . Re attempt no is : " + i + "." + e1.getLocalizedMessage(), e1);
				}
			}
			LOGGER.error(
				"Exception thrown while sending emil to : " + Arrays.toString(to) + " ." + e.getLocalizedMessage(), e);
		}
	}

	/**
	 * This method get the Mail Session by using system properties.
	 * 
	 * @param host Host name.
	 * @param port - SMTP port number
	 * @return mail session.
	 */
	private Session getMailSession() {
		Session session = null;
		if (StringUtils.equalsIgnoreCase("true", mailSmtpAuth)) {
			session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(mailSmtpFromAddress, new StringEncrypter().decrypt(mailSmtpFromPassword));
				}
			});
		} else {
			session = Session.getDefaultInstance(props, null);
		}
		return session;
	}
	
	public void sendHtmlMail(final String[] to, final String subject, final String body) {
		final Session session = getMailSession();
		try {
			final Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailSmtpFromAddress));
			final InternetAddress add[] = new InternetAddress[to.length];
			for (int i = 0; i < to.length; i++) {
				add[i] = new InternetAddress(to[i]);
			}
			message.setRecipients(Message.RecipientType.TO, add);
			message.setSubject(subject);
			message.setContent(body, "text/html; charset=utf-8");
			Transport.send(message);
		} catch (MessagingException e) {
			LOGGER.error("Exception thrown while sending emil to : " + to + " ." + e.getLocalizedMessage(), e);
		}
	}

	public String getMailSmtpHost() {
		return mailSmtpHost;
	}
	public void setMailSmtpHost(String mailSmtpHost) {
		this.mailSmtpHost = mailSmtpHost;
	}
	public String getMailSmtpPort() {
		return mailSmtpPort;
	}
	public void setMailSmtpPort(String mailSmtpPort) {
		this.mailSmtpPort = mailSmtpPort;
	}
	public String getMailSmtpAuth() {
		return mailSmtpAuth;
	}
	public void setMailSmtpAuth(String mailSmtpAuth) {
		this.mailSmtpAuth = mailSmtpAuth;
	}
	public String getMailSmtpStarttlsEnable() {
		return mailSmtpStarttlsEnable;
	}
	public void setMailSmtpStarttlsEnable(String mailSmtpStarttlsEnable) {
		this.mailSmtpStarttlsEnable = mailSmtpStarttlsEnable;
	}
	public String getMailSmtpFromAddress() {
		return mailSmtpFromAddress;
	}
	public void setMailSmtpFromAddress(String mailSmtpFromAddress) {
		this.mailSmtpFromAddress = mailSmtpFromAddress;
	}
	public String getMailSmtpFromPassword() {
		return mailSmtpFromPassword;
	}
	public void setMailSmtpFromPassword(String mailSmtpFromPassword) {
		this.mailSmtpFromPassword = mailSmtpFromPassword;
	}
	public String getMailSupportAddress() {
		return mailSupportAddress;
	}
	public void setMailSupportAddress(String mailSupportAddress) {
		this.mailSupportAddress = mailSupportAddress;
	}

}