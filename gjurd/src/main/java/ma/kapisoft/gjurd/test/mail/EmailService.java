package ma.kapisoft.gjurd.test.mail;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.kapisoft.gjurd.util.EmailUtil;

@Service
@Transactional(readOnly = true)
public class EmailService implements IEmailService {

	public EmailService() {
		// TODO Auto-generated constructor stub
	}

	public void sendMail(String to, String subject, String msg) {
       EmailUtil email=new EmailUtil();
       try {
		email.sendMail(to, subject, msg);
	} catch (AddressException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (MessagingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	}

}
