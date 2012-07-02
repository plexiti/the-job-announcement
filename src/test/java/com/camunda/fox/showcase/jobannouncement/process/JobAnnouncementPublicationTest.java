package com.camunda.fox.showcase.jobannouncement.process;
import static com.camunda.fox.showcase.jobannouncement.process.ProcessConstants.*;
import static com.plexiti.activiti.test.ActivitiFestConditions.*;
import static org.fest.assertions.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.activiti.engine.test.Deployment;
import org.junit.Test;
import org.mockito.Mock;

import com.camunda.fox.showcase.jobannouncement.model.JobAnnouncement;
import com.camunda.fox.showcase.jobannouncement.service.JobAnnouncementService;
import com.plexiti.activiti.test.TestProcessInstance;
import com.plexiti.activiti.test.mocking.ActivitiMockitoTest;
import com.plexiti.helper.Entities;

public class JobAnnouncementPublicationTest extends ActivitiMockitoTest {
	
	@Mock public JobAnnouncementService jobAnnouncementService;
	@Mock public JobAnnouncement jobAnnouncement;

	String positionDescription = "In a village of La Mancha, the name of which I have no desire to call to mind, there lived not long since one of those developers that keep a laptop in the laptop-rack, an old VT100 terminal, a lean hack, and a greyhound for coursing.";

	@Test
	@Deployment(resources = { JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE })
	public void testPublishOnlyOnCompanyWebsite() {
		
		when(jobAnnouncement.getId()).thenReturn(1L);
		
		start(new TestProcessInstance(JOBANNOUNCEMENT_PUBLICATION_PROCESS)
			.withVariable(Entities.idVariableName(JobAnnouncement.class), jobAnnouncement.getId())
			.withVariable("twitter", false)
			.withVariable("facebook", false)
		);
		
		verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
		verify(jobAnnouncementService, never()).postToTwitter(any(Long.class));
		verify(jobAnnouncementService, never()).postToFacebook(any(Long.class));
		assertThat(process().execution()).is(finished());

		verifyNoMoreInteractions(jobAnnouncementService);

	}

	@Test
	@Deployment(resources = { JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE })
	public void testPublishAlsoOnTwitter() {
		
		start(new TestProcessInstance(JOBANNOUNCEMENT_PUBLICATION_PROCESS)
			.withVariable(Entities.idVariableName(JobAnnouncement.class), jobAnnouncement.getId())
			.withVariable("twitter", true)
			.withVariable("facebook", false)
		);

		verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
		verify(jobAnnouncementService).postToTwitter(jobAnnouncement.getId());
		verify(jobAnnouncementService, never()).postToFacebook(any(Long.class));
		assertThat(process().execution()).is(finished());

		verifyNoMoreInteractions(jobAnnouncementService);
		
	}

	@Test
	@Deployment(resources = { JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE })
	public void testPublishAlsoOnFacebook() {
		
		start(new TestProcessInstance(JOBANNOUNCEMENT_PUBLICATION_PROCESS)
			.withVariable(Entities.idVariableName(JobAnnouncement.class), jobAnnouncement.getId())
			.withVariable("twitter", false)
			.withVariable("facebook", true)
		);

		verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
		verify(jobAnnouncementService, never()).postToTwitter(any(Long.class));
		verify(jobAnnouncementService).postToFacebook(jobAnnouncement.getId());
		assertThat(process().execution()).is(finished());

		verifyNoMoreInteractions(jobAnnouncementService);
		
	}

	@Test
	@Deployment(resources = { JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE })
	public void testPublishAlsoOnFacebookAndTwitter() {
		
		start(new TestProcessInstance(JOBANNOUNCEMENT_PUBLICATION_PROCESS)
			.withVariable(Entities.idVariableName(JobAnnouncement.class), jobAnnouncement.getId())
			.withVariable("facebook", true)
			.withVariable("twitter", true)
		);

		verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
		verify(jobAnnouncementService).postToTwitter(jobAnnouncement.getId());
		verify(jobAnnouncementService).postToFacebook(jobAnnouncement.getId());
		assertThat(process().execution()).is(finished());

		verifyNoMoreInteractions(jobAnnouncementService);
		
	}

}
