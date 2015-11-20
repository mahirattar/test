package ma.kapisoft.gjurd.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import ma.kapisoft.gjurd.service.CompteUtilisateurService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EmailUtil {
	private static final Log log = LogFactory.getLog(EmailUtil.class);
	
	private static final String PROTOCOL="mail.transport.protocol";
	private static final String HOST="mail.smtp.host";
	private static final String PORT="mail.smtp.port";
	private static final String AUTH="mail.smtp.auth";
	private static final String STARTTLS_ENABLE="mail.smtp.starttls.enable";
	private static final String SSL_TRUST="mail.smtp.ssl.trust";

	private static final String PROTOCOL_VALUE="smtp";
	private static final String HOST_VALUE="smtp.gmail.com";
	private static final String PORT_VALUE="587";
	private static final String AUTH_VALUE="true";
	private static final String STARTTLS_ENABLE_VALUE="true";
	private static final String SSL_TRUST_VALUE="smtp.gmail.com";
	
	private static final String EMAIL_FROM="gjurdique@gmail.com";
	private static final String PASSWORD_FROM="mahirkhalid998811";
	
		
	
	private   Properties properties;
	private Authenticator authenticator;
	public EmailUtil() {
		initProperties();
		initAuthenticator();
		
		
	}

	private void initAuthenticator()
	{
		authenticator = new Authenticator() {
		    @Override
		    protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication(EMAIL_FROM, PASSWORD_FROM);
		    }
		};
	}
	
	private void initProperties()
	{
		  properties = new Properties();
		  properties.put(PROTOCOL, PROTOCOL_VALUE);
		  properties.put(HOST, HOST_VALUE);
		  properties.put(PORT, PORT_VALUE);
		  properties.put(AUTH, AUTH_VALUE);
		  properties.put(STARTTLS_ENABLE, STARTTLS_ENABLE_VALUE);
		  properties.put(SSL_TRUST, SSL_TRUST_VALUE);
	}
	
	
	public void sendMail(String mailto,String subject,String msg) throws AddressException, MessagingException
	{
		Session session = Session.getDefaultInstance(properties, authenticator);
	     
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         //message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO,
	                                  new InternetAddress(mailto));

	         // Set Subject: header field
	         message.setSubject(subject);

	         // Now set the actual message
	         message.setText(msg, "utf-8", "html");

	         // Send message
	         Transport.send(message);
	         log.trace("Sent message successfully....");
	     
	   
	}
}
