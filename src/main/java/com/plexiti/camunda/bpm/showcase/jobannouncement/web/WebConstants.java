package com.plexiti.camunda.bpm.showcase.jobannouncement.web;

import java.util.Map;

import com.plexiti.camunda.bpm.showcase.jobannouncement.process.ProcessConstants;
import com.plexiti.helper.Maps;

public abstract class WebConstants {

	public static final Map<String, String[]> FROM_PHASE_TO_TASK = Maps.hashMap(String.class, String[].class)
		.put(ProcessConstants.TASK_DESCRIBE_POSITION, new String [] { ProcessConstants.TASK_DESCRIBE_POSITION, ProcessConstants.TASK_CORRECT_ANNOUNCEMENT})
		.put(ProcessConstants.TASK_REVIEW_ANNOUNCEMENT, new String [] { ProcessConstants.TASK_REVIEW_ANNOUNCEMENT })
		.put(ProcessConstants.TASK_INITIATE_ANNOUNCEMENT, new String [] { ProcessConstants.TASK_INITIATE_ANNOUNCEMENT })
	.build();

}
