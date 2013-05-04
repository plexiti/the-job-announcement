package com.plexiti.camunda.bpm.showcase.jobannouncement.web;

import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.plexiti.camunda.bpm.showcase.jobannouncement.model.JobAnnouncement;
import com.plexiti.camunda.bpm.showcase.jobannouncement.service.JobAnnouncementService;

@RunWith(MockitoJUnitRunner.class)
public class JobAnnouncementBeanTest {
	
	@Mock BusinessProcess businessProcess;
	@Mock JobAnnouncementService jobAnnouncementService;
	@InjectMocks JobAnnouncementBean jobAnnouncementBean =  new JobAnnouncementBean();
	
	@Test
	public void testNewAnnouncement() {
		
		JobAnnouncement jobAnnouncement = new JobAnnouncement();

		when((jobAnnouncementService.findOrNew((BusinessProcess) any()))).thenReturn(jobAnnouncement);

		// A first call will deliver a fresh object. The businessProcess will have been consulted whether the object already exists.
		
		jobAnnouncement = jobAnnouncementBean.getJobAnnouncement();

		verify(jobAnnouncementService).findOrNew((BusinessProcess) any());
		assertThat(jobAnnouncement).isNotNull();
		assertThat(jobAnnouncement.getId()).isNull();
		
		// A second call will deliver the same object.
		JobAnnouncement jobAnnouncement2 = jobAnnouncementBean.getJobAnnouncement();

		verifyNoMoreInteractions(businessProcess);
		
		assertThat(jobAnnouncement2).isEqualTo(jobAnnouncement);

	}
	
	@Test
	public void testExistingAnnouncement() {

		Long jobAnnouncementId = 1L;
		JobAnnouncement jobAnnouncement = new JobAnnouncement(); 
		jobAnnouncement.setId(jobAnnouncementId);

		when((jobAnnouncementService.findOrNew((BusinessProcess) any()))).thenReturn(jobAnnouncement);

		// A first call will deliver an object from the database. The businessProcess will have 
		// been consulted whether the object already exists. The entityManager will have delivered the object

		jobAnnouncement = jobAnnouncementBean.getJobAnnouncement();
		
		verify(jobAnnouncementService).findOrNew((BusinessProcess) any());

		assertThat(jobAnnouncement).isNotNull();
		assertThat(jobAnnouncement.getId()).isEqualTo(jobAnnouncementId);

		// A second call will deliver the same object.

		JobAnnouncement jobAnnouncement2 = jobAnnouncementBean.getJobAnnouncement();
		assertThat(jobAnnouncement2).isEqualTo(jobAnnouncement);

		verifyNoMoreInteractions(businessProcess);

	}

}
