package com.bridgelabz.utility;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {
	
	@Autowired
	private MailSender mailSender;

	public void sendEmail(String toEmail, String subject, String activationUrl) {
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			// set message headers
			msg.setFrom("mundargi95@gmail.com");
			msg.setTo("mundargi95@gmail.com");
			msg.setSubject("Verification Email");
			String message = "Please click on the link below to verify email Id /n/n " +activationUrl;
			msg.setText(message);
			msg.setSentDate(new Date());
	        mailSender.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}