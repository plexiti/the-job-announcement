package com.plexiti.activiti.model;

import org.activiti.engine.task.Task;

public interface TaskAware {
	
	Task getTask();
	void setTask(Task task);
	
}
