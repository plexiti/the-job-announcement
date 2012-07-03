package com.camunda.fox.showcase.jobannouncement.service.impl;

import static com.camunda.fox.showcase.jobannouncement.process.ProcessConstants.*;

import java.util.Date;
import java.util.List;
import java.util.Stack;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.camunda.fox.showcase.jobannouncement.model.FacebookPosting;
import com.camunda.fox.showcase.jobannouncement.model.JobAnnouncement;
import com.camunda.fox.showcase.jobannouncement.service.JobAnnouncementService;
import com.camunda.fox.showcase.jobannouncement.service.PostingService;
import com.plexiti.activiti.service.ProcessAwareJpaEntityServiceImpl;

@Stateless
@Named("jobAnnouncementService")
public class JobAnnouncementServiceImpl extends ProcessAwareJpaEntityServiceImpl<JobAnnouncement> implements JobAnnouncementService {

	private static final long serialVersionUID = -876313470772353619L;
	
	@Inject PostingService<FacebookPosting> facebookPostingService;
	@Inject PostingService<String> twitterPostingService;
	@Inject PostingService<Email> mailingService;

	@SuppressWarnings("cdi-ambiguous-dependency")
	@Inject IdentityService identityService;
	
	@Override
	protected String processKey() {
		return JOBANNOUNCEMENT_PROCESS;
	}

	@SuppressWarnings("cdi-ambiguous-dependency")
	@Override
	public List<JobAnnouncement> listPublished() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<JobAnnouncement> criteria = builder.createQuery(JobAnnouncement.class);
		Root<JobAnnouncement> root = criteria.from(JobAnnouncement.class);
		criteria.select(root).where(builder.isNotNull(root.get("published")));
		return entityManager.createQuery(criteria).getResultList();
	}

	@Override
	public List<JobAnnouncement> listInvolved(String involvedUser) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<JobAnnouncement> criteria = builder.createQuery(JobAnnouncement.class);
		Root<JobAnnouncement> root = criteria.from(JobAnnouncement.class);
		criteria.select(root).where(builder.and(builder.isNull(root.get("published")), builder.or(builder.equal(root.get("requester"), involvedUser), builder.equal(root.get("editor"), involvedUser))));
		Stack<JobAnnouncement> entities = new Stack<JobAnnouncement>();
		for (JobAnnouncement announcement: entityManager.createQuery(criteria).getResultList()) {
			entities.push(associate(announcement));			
		}
		return entities;
	}	

	@Override // #{jobAnnouncementService.postToWebsite(jobAnnouncementId)}
	public void postToWebsite(Long jobAnnouncementId) {
		JobAnnouncement announcement = find(jobAnnouncementId);
		announcement.setPublished(new Date());
		log.info("Job Announcement #[" + announcement.getId() + "] published to Website.");
	}

	@Override // #{jobAnnouncementService.postToTwitter(jobAnnouncementId)}
	public void postToTwitter(Long jobAnnouncementId) {
		JobAnnouncement announcement = find(jobAnnouncementId);
		String twitterUrl = twitterPostingService.post(announcement.getTwitterMessageWithLink());
		announcement.setTwitterUrl(twitterUrl);
		log.info("Job Announcement #[" + announcement.getId() + "] posted to Twitter.");
	}

	@Override // #{jobAnnouncementService.postToFacebook(jobAnnouncementId)}
	public void postToFacebook(Long jobAnnouncementId) {
		JobAnnouncement announcement = find(jobAnnouncementId);
		User requester = identityService.createUserQuery().userId(announcement.getRequester()).singleResult();
		String facebookUrl = facebookPostingService.post(
			new FacebookPosting()
				.setMessage(announcement.getFacebookPost())
				.setLink(announcement.getWebsiteUrl())
				.setName(announcement.getJobTitle() + " gesucht!")
				.setDescription("Unser " + (announcement.getRequester().equals("gonzo") ? "Geschäftsführer" : "Chef der Personalabteilung") + " " + requester.getFirstName() + " " + requester.getLastName() + " eine neue Mitarbeiter/-in! Sind Sie interessiert?")
				.setPicture("http://martin.schimak.at/wp-content/uploads/2012/07/" + announcement.getRequester() + ".png")
			); // TODO MessageBundle abfragen, Picture URLs besser setzen.
		announcement.setFacebookUrl(facebookUrl);
		log.info("Job Announcement #[" + announcement.getId() + "] posted to Facebook.");
	}

	@Override // #{jobAnnouncementService.notifyAboutPostings(jobAnnouncementId)}
	public void notifyAboutPostings(Long jobAnnouncementId) {
		JobAnnouncement announcement = find(jobAnnouncementId);
		Email message = new SimpleEmail();
		try {
			message.setSubject("Job Announcement #" + announcement.getId() + " published.");
			User requester = identityService.createUserQuery().userId(announcement.getRequester()).singleResult();
			User editor = identityService.createUserQuery().userId(announcement.getEditor()).singleResult();
			message.addTo("thejobannouncer+" + requester.getId() + "@gmail.com", requester.getFirstName() + " " + requester.getLastName());
			message.addTo("thejobannouncer+" + editor.getId() + "@gmail.com", editor.getFirstName() + " " + editor.getLastName());
			StringBuffer subject = new StringBuffer("News: Your job announcement #[").append(announcement.getId()).append("] has been posted to your Website");
			if (announcement.getTwitterUrl() != null) {
				subject.append(announcement.getFacebookUrl() != null ? ", " : " and ");
				subject.append("Twitter");				
			}
			if (announcement.getFacebookUrl() != null)
				subject.append(" and Facebook");
			subject.append("!");
			message.setSubject(subject.toString());
			String nl = System.getProperty("line.separator");

			StringBuffer text = new StringBuffer("Dear ").append(requester.getFirstName()).append(" ").append(requester.getLastName()).append(",").append(nl);
			text.append("dear ").append(editor.getFirstName()).append(" ").append(editor.getLastName()).append(",").append(nl).append(nl);

			text.append("Your job announcement #[").append(announcement.getId()).append("] to search for a '" + announcement.getJobTitle() + "' has just been posted!").append(nl).append(nl);
			text.append("Website: http://the-job-announcement.com:8080/job-announcement-fox/view.jsf?id=" + announcement.getId()).append(nl);
			if (announcement.getTwitterUrl() != null) {
				text.append("Twitter: " + announcement.getTwitterUrl()).append(nl);
			} else if (announcement.getTwitterMessage() != null) {
				text.append("Twitter: Tried, but failed.").append(nl);				
			}
			if (announcement.getFacebookUrl() != null) {
				text.append("Facebook: " + announcement.getFacebookUrl()).append(nl);
			} else if (announcement.getFacebookPost() != null) {
				text.append("Facebook: Posting mechanism not yet implemented.").append(nl);				
			}
			text.append(nl).append("Have a nice day!").append(nl).append("The Job Announcer ;-)");
			message.setMsg(text.toString());
			mailingService.post(message);
		} catch (EmailException e) {
		    log.info("Failed to mail Notification with subject '" + message.getSubject() + "'.");
			log.throwing(getClass().getName(), "post", e);
		}
	}

	@Override
	public String findRequester(Long jobAnnouncementId) {
		return find(jobAnnouncementId).getRequester();
	}

	@Override
	public String findEditor(Long jobAnnouncementId) {
		return find(jobAnnouncementId).getEditor();
	}

}
