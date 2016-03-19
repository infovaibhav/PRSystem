package org.iry.utils;

import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;


/**
 * This class is an implementation of Email.
 * 
 * @author vpatil
 */
@Component
@Service("mailSender")
@PropertySource(value = { "classpath:prs-config.properties" })
public class MailSender {

	private static final Logger LOGGER = Logger.getLogger(MailSender.class);

	private static final String EMAIL_SMTP_HOST = "mail.smtp.host";
	private static final String EMAIL_SMTP_PORT = "mail.smtp.port";
	private static final String EMAIL_SMTP_AUTH = "mail.smtp.auth";
	private static final String EMAIL_SMTP_USER = "mail.smtp.user";
	private static final String EMAIL_SMTP_PASSWORD = "mail.smtp.password";
	private static final String EMAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";

    @Autowired
    private Environment environment;

	private String mailSmtpHost;
	private String mailSmtpPort;
	private String mailSmtpAuth;
	private String mailSmtpStarttlsEnable;
	private String mailSmtpUser;
	private String mailSmtpPassword;
	private Properties props = null;

	/**
	 * Method to initialize <code>Properties</code> object.All the values for property is loaded from
	 * prs-config.properties file using Spring.
	 */
	@PostConstruct
	public void init() {
		this.mailSmtpHost = environment.getRequiredProperty(EMAIL_SMTP_HOST);
		this.mailSmtpPort = environment.getRequiredProperty(EMAIL_SMTP_PORT);
		this.mailSmtpAuth = environment.getRequiredProperty(EMAIL_SMTP_AUTH);
		this.mailSmtpUser = environment.getRequiredProperty(EMAIL_SMTP_USER);
		this.mailSmtpPassword = environment.getRequiredProperty(EMAIL_SMTP_PASSWORD);
		this.mailSmtpStarttlsEnable = environment.getRequiredProperty(EMAIL_SMTP_STARTTLS_ENABLE);
		
		props = System.getProperties();
		props.put(EMAIL_SMTP_HOST, mailSmtpHost);
		props.put(EMAIL_SMTP_PORT, mailSmtpPort);
		props.put(EMAIL_SMTP_AUTH, mailSmtpAuth);
		props.put(EMAIL_SMTP_USER, mailSmtpUser);
		props.put(EMAIL_SMTP_PASSWORD, mailSmtpPassword);
		props.put(EMAIL_SMTP_STARTTLS_ENABLE, mailSmtpStarttlsEnable);
	}

	/**
	 * Method to send the email.
	 * 
	 * @param to - to emails.
	 * @param cc - cc emails.
	 * @param subject - subject of email.
	 * @param body - body of email.
	 * @param attachment - attachment of email.
	 */
	public boolean sendMail( List<String> to, List<String> cc, String subject, String body, byte[] attachment, String fileName) {
		Message message = null;
		StopWatch watch = new StopWatch();
		watch.start();
		try {
			LOGGER.info("Sending email notification #Subject- " + subject + " #To- " + to.toString());
			Session session = getMailSession();
			message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailSmtpUser));
			final InternetAddress add[] = new InternetAddress[to.size()];
			for (int i = 0; i < to.size(); i++) {
				add[i] = new InternetAddress(to.get(i));
			}
			message.setRecipients(Message.RecipientType.TO, add);
			
			if( cc != null && cc.size() > 0 ) {
				final InternetAddress ccMails[] = new InternetAddress[cc.size()];
				for (int i = 0; i < cc.size(); i++) {
					ccMails[i] = new InternetAddress(cc.get(i));
				}
				message.setRecipients(Message.RecipientType.CC, ccMails);
			}
			
			message.setSubject(subject);
			
			MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);
            messageBodyPart.setContent(body, "text/html; charset=utf-8");
  
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            
			
			message.setContent(multipart, "text/html; charset=utf-8");
			
			if( attachment != null && fileName != null ) {
		        ByteArrayDataSource ds = new ByteArrayDataSource(attachment, "application/pdf");
				MimeBodyPart attachmentPart = new MimeBodyPart();
				attachmentPart.setDataHandler(new DataHandler(ds));
				attachmentPart.setFileName(fileName);
		        multipart.addBodyPart(attachmentPart);
			}
			
			Transport.send(message);
			
			LOGGER.debug("Email notificatin sent for #Subject- " + subject + " #To- " + to.toString() + " in #Seconds- " + watch.getTotalTimeMillis());
			
			return true;
		} catch (MessagingException e) {
			int i = 1;
			while (i <= 3) {
				i++;
				try {
					Thread.sleep(10000);
					Transport.send(message);
					LOGGER.debug("Email notificatin sent for #Subject- " + subject + " #To- " + to.toString() + " in #Seconds- " + watch.getTotalTimeSeconds() + " #Attempt- " + i );
					return true;
				} catch (Exception e1) {
					LOGGER.error("Exception thrown againg while resending emil to : " + to.toString()
							+ " . Re attempt no is : " + i + "." + e1.getLocalizedMessage(), e1);
				}
			}
			LOGGER.error("MessagingException thrown while sending emil to : " + to.toString() + " ." + e.getLocalizedMessage(), e);
		} catch ( Exception e ) {
			LOGGER.error("Exception thrown while sending emil to : " + to.toString() + " ." + e.getLocalizedMessage(), e);
		}
		return false;
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
					return new PasswordAuthentication(mailSmtpUser, new StringEncrypter().decrypt(mailSmtpPassword));
				}
			});
		} else {
			session = Session.getDefaultInstance(props, null);
		}
		return session;
	}
	
}