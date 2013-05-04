package com.plexiti.camunda.bpm.service;

import java.util.List;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.task.Task;

import com.plexiti.camunda.bpm.model.TaskAware;
import com.plexiti.persistence.service.EntityService;

public interface ProcessAwareEntityService<E extends TaskAware> extends EntityService<E> {

	void associate(BusinessProcess process, E entity);
	
	E find(BusinessProcess businessProcess);
	E findOrNew(BusinessProcess businessProcess);

	E associate(Task task);	
	E associate(E entity);	
	List<E> listAssociated(String assignee, String... taskDefinitionKey);
	
}
