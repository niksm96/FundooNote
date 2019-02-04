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

	public void sendEmail(String toEmail, String subject, String body) {
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			// set message headers
			msg.setFrom("mundargi95@gmail.com");

			msg.setSubject(subject);

			msg.setText(body);

			msg.setSentDate(new Date());

			msg.setTo("anmolvid135@gmail.com");
			System.out.println("Message is ready");
	        mailSender.send(msg);

			System.out.println("EMail Sent Successfully!!");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}