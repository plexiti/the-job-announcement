package com.plexiti.activiti.model;

import org.camunda.bpm.engine.task.Task;

public interface TaskAware {
	
	Task getTask();
	void setTask(Task task);
	
}
