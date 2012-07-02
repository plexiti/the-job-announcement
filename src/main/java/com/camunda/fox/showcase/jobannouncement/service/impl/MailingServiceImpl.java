package com.camunda.fox.showcase.jobannouncement.service.impl;

import java.util.logging.Logger;

import javax.inject.Named;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;

import com.camunda.fox.showcase.jobannouncement.service.PostingService;

@Named("mailingService")
public class MailingServiceImpl implements PostingService<Email> {

	private static Logger log = Logger.getLogger(MailingServiceImpl.class.getName());

	@Override
	public String post(Email message) {
		
		try {
			message.setSmtpPort(587);
		    message.setAuthenticator(new DefaultAuthenticator("thejobannouncer@gmail.com", "3cWaN2jGhEbAAT"));
		    message.setDebug(false);
		    message.setHostName("smtp.gmail.com");
		    message.setFrom("The Job Announcer <thejobannouncer@gmail.com>");
		    message.setTLS(true);
		    message.send();
			log.info("Mailed Notification with subject '" + message.getSubject() + "'.");
		} catch (EmailException e) {
		    log.info("Failed to mail Notification with subject '" + message.getSubject() + "'.");
			log.throwing(getClass().getName(), "post", e);
		}
		return null;
	}

}
