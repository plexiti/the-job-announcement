package com.plexiti.camunda.bpm.model;

import org.camunda.bpm.engine.task.Task;

public interface TaskAware {
	
	Task getTask();
	void setTask(Task task);
	
}
