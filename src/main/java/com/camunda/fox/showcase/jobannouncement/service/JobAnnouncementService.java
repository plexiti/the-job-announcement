package com.camunda.fox.showcase.jobannouncement.service;

import java.util.List;

import com.camunda.fox.showcase.jobannouncement.model.JobAnnouncement;
import com.plexiti.activiti.service.ProcessAwareEntityService;


public interface JobAnnouncementService extends ProcessAwareEntityService<JobAnnouncement> {

	List<JobAnnouncement> listPublished();
	List<JobAnnouncement> listInvolved(String involvedUser);
	
	void postToWebsite(Long jobAnnouncementId);
	void postToTwitter(Long jobAnnouncementId);
	void postToFacebook(Long jobAnnouncementId);
	
	void notifyAboutPostings(Long jobAnnouncementId);
	
	String findRequester(Long jobAnnouncementId); 
	String findEditor(Long jobAnnouncementId); 

}
