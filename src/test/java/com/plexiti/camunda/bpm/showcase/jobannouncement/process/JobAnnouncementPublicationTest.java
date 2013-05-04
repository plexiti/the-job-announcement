package com.plexiti.camunda.bpm.showcase.jobannouncement.process;
import static org.mockito.Mockito.*;

import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.fluent.FluentProcessEngineTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import com.plexiti.camunda.bpm.showcase.jobannouncement.model.JobAnnouncement;
import com.plexiti.camunda.bpm.showcase.jobannouncement.service.JobAnnouncementService;

import static com.plexiti.camunda.bpm.showcase.jobannouncement.process.ProcessConstants.JOBANNOUNCEMENT_PUBLICATION_PROCESS;
import static com.plexiti.camunda.bpm.showcase.jobannouncement.process.ProcessConstants.JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE;
import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.*;
import static org.mockito.Matchers.any;


public class JobAnnouncementPublicationTest {

  @Rule
  public ProcessEngineRule activitiRule = new ProcessEngineRule();
  @Rule
  public FluentProcessEngineTestRule bpmnFluentTestRule = new FluentProcessEngineTestRule(this);

  @Mock
  public JobAnnouncementService jobAnnouncementService;
	@Mock
  public JobAnnouncement jobAnnouncement;

	String positionDescription = "In a village of La Mancha, the name of which I have no desire to call to mind, there lived not long since one of those developers that keep a laptop in the laptop-rack, an old VT100 terminal, a lean hack, and a greyhound for coursing.";

  @Test
  @Deployment(resources = { JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE })
  public void testPublishOnlyOnCompanyWebsite() {

    when(jobAnnouncement.getId()).thenReturn(1L);

    newProcessInstance(JOBANNOUNCEMENT_PUBLICATION_PROCESS)
      .setVariable("jobAnnouncementId", jobAnnouncement.getId())
      .setVariable("twitter", false)
      .setVariable("facebook", false)
      .start();

    verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
    verify(jobAnnouncementService, never()).postToTwitter(any(Long.class));
    verify(jobAnnouncementService, never()).postToFacebook(any(Long.class));

    assertThat(processInstance()).isFinished();

    verifyNoMoreInteractions(jobAnnouncementService);
  }

  @Test
  @Deployment(resources = { JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE })
  public void testPublishAlsoOnTwitter() {

    newProcessInstance(JOBANNOUNCEMENT_PUBLICATION_PROCESS)
      .setVariable("jobAnnouncementId", jobAnnouncement.getId())
      .setVariable("twitter", true)
      .setVariable("facebook", false)
      .start();

    verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
    verify(jobAnnouncementService).postToTwitter(jobAnnouncement.getId());
    verify(jobAnnouncementService, never()).postToFacebook(any(Long.class));

    assertThat(processInstance()).isFinished();

    verifyNoMoreInteractions(jobAnnouncementService);
  }

  @Test
  @Deployment(resources = { JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE })
  public void testPublishAlsoOnFacebook() {

    newProcessInstance(JOBANNOUNCEMENT_PUBLICATION_PROCESS)
      .setVariable("jobAnnouncementId", jobAnnouncement.getId())
      .setVariable("twitter", false)
      .setVariable("facebook", true)
      .start();

    verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
    verify(jobAnnouncementService, never()).postToTwitter(any(Long.class));
    verify(jobAnnouncementService).postToFacebook(jobAnnouncement.getId());

    assertThat(processInstance()).isFinished();

    verifyNoMoreInteractions(jobAnnouncementService);
  }

  @Test
  @Deployment(resources = { JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE })
  public void testPublishAlsoOnFacebookAndTwitter() {

    newProcessInstance(JOBANNOUNCEMENT_PUBLICATION_PROCESS)
      .setVariable("jobAnnouncementId", jobAnnouncement.getId())
      .setVariable("facebook", true)
      .setVariable("twitter", true)
      .start();

    verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
    verify(jobAnnouncementService).postToTwitter(jobAnnouncement.getId());
    verify(jobAnnouncementService).postToFacebook(jobAnnouncement.getId());

    assertThat(processInstance()).isFinished();

    verifyNoMoreInteractions(jobAnnouncementService);
  }
}
