package com.camunda.fox.showcase.jobannouncement.service.impl;

import javax.persistence.EntityManager;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.camunda.fox.showcase.jobannouncement.model.JobAnnouncement;
import com.camunda.fox.showcase.jobannouncement.service.impl.JobAnnouncementServiceImpl;
import com.camunda.fox.showcase.jobannouncement.service.impl.MailingServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class MailingServiceImplTest {
	
	@Mock EntityManager entityManager;
	@Mock IdentityService identityService;
	@Mock UserQuery userQueryRequester;
	@Mock UserQuery userQueryEditor;
	@Mock User requester;
	@Mock User editor;
	
	@InjectMocks JobAnnouncementServiceImpl service = new JobAnnouncementServiceImpl();	
	@InjectMocks MailingServiceImpl mailingService = new MailingServiceImpl();
	
	{
		service.mailingService = mailingService;
	}

	@Test
	public void test() {
		
		JobAnnouncement announcement = new JobAnnouncement();
		announcement.setId(1L);
		announcement.setRequester("gonzo");
		announcement.setEditor("fozzie");
		announcement.setJobTitle("Software Developer");
		announcement.setTwitterMessage("Software Developer");
		
		Mockito.when(requester.getId()).thenReturn("gonzo");
		Mockito.when(requester.getFirstName()).thenReturn("Gonzo");
		Mockito.when(requester.getLastName()).thenReturn("The Great");
		
		Mockito.when(editor.getId()).thenReturn("kermit");
		Mockito.when(editor.getFirstName()).thenReturn("Kermit");
		Mockito.when(editor.getLastName()).thenReturn("The Frog");

		Mockito.when(entityManager.find(JobAnnouncement.class, 1L)).thenReturn(announcement);
		Mockito.when(identityService.createUserQuery()).thenReturn(userQueryRequester);
		Mockito.when(identityService.createUserQuery().userId(announcement.getRequester())).thenReturn(userQueryRequester);
		Mockito.when(identityService.createUserQuery().userId(announcement.getRequester()).singleResult()).thenReturn(requester);
		Mockito.when(identityService.createUserQuery().userId(announcement.getEditor())).thenReturn(userQueryEditor);
		Mockito.when(identityService.createUserQuery().userId(announcement.getEditor()).singleResult()).thenReturn(editor);
		
		service.notifyAboutPostings(1L);
		
	}

}
