package com.camunda.fox.showcase.jobannouncement.service.impl;

import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.*;

import javax.persistence.EntityManager;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.identity.UserQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.camunda.fox.showcase.jobannouncement.model.JobAnnouncement;

@RunWith(MockitoJUnitRunner.class)
public class FacebookServiceImplIT {
	
	@Mock EntityManager entityManager;
	@Mock IdentityService identityService;
	@Mock UserQuery userQueryRequester;
	@Mock User requester;
	@InjectMocks JobAnnouncementServiceImpl service = new JobAnnouncementServiceImpl();
	FacebookPostingServiceImpl postingService = new FacebookPostingServiceImpl();
	JobAnnouncement announcement = new JobAnnouncement();

	@Before
	public void init() {
		service.facebookPostingService = postingService;
		announcement.setId(1L);
		announcement.setRequester("gonzo");
		announcement.setFacebookPost("Wir suchen einen Wunderwuzzi!");
		announcement.setJobTitle("Wunderwuzzi");
		Mockito.when(entityManager.find(JobAnnouncement.class, 1L)).thenReturn(announcement);		
		Mockito.when(identityService.createUserQuery()).thenReturn(userQueryRequester);
		Mockito.when(identityService.createUserQuery().userId(announcement.getRequester())).thenReturn(userQueryRequester);
		Mockito.when(identityService.createUserQuery().userId(announcement.getRequester()).singleResult()).thenReturn(requester);
		Mockito.when(requester.getId()).thenReturn("gonzo");
		Mockito.when(requester.getFirstName()).thenReturn("Gonzo");
		Mockito.when(requester.getLastName()).thenReturn("The Great");
	}
	
	@Test
	public void test() {
		service.postToFacebook(1L);
		assertThat(announcement.getFacebookUrl()).isNotNull();
	}

}
