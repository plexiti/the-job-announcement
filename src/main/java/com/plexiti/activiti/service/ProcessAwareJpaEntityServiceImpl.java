package com.plexiti.activiti.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import javax.inject.Inject;

import org.activiti.cdi.BusinessProcess;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import com.plexiti.activiti.model.TaskAware;
import com.plexiti.helper.Entities;
import com.plexiti.persistence.service.JpaEntityServiceImpl;

public abstract class ProcessAwareJpaEntityServiceImpl<E extends TaskAware> extends JpaEntityServiceImpl<E> implements ProcessAwareEntityService<E> {

	private static final long serialVersionUID = 5420369946125726273L;
	
	@Inject
	@SuppressWarnings("cdi-ambiguous-dependency")
	protected TaskService taskService;

	@Inject
	@SuppressWarnings("cdi-ambiguous-dependency")
	protected RuntimeService runtimeService;

	@Override
	public E find(BusinessProcess process) {
		E entity = find(entityId(process));
		entity.setTask(process.getTask());
		return entity;
	}

	@Override
	public E associate (Task task) {
		E entity = entity(task);
		entity.setTask(task);
		return entity;
	}

	@Override
	public E associate (E entity) {
		List<Task> tasks = taskService.createTaskQuery().processVariableValueEquals(entityVariableName(), entityId(entity)).orderByTaskCreateTime().desc().list();
		if (tasks.size() > 0) {
			entity.setTask(tasks.get(0));			
		}
		return entity;
	}

	@Override
	public E findOrNew(BusinessProcess process) {
		return entityExists(process) ? find(process) : newInstance ();
	}
	
	@Override
	public List<E> listAssociated(String assignee, String... taskDefinitionKeys) {
		assert taskDefinitionKeys != null && taskDefinitionKeys.length > 0;
		List<Task> tasks = new ArrayList<Task>();
		for (String taskDefinitionKey: taskDefinitionKeys) {
			tasks.addAll(taskService.createTaskQuery().taskDefinitionKey(taskDefinitionKey).list());
		}
		Stack<E> entities = new Stack<E>();
		Iterator<Task> it = tasks.iterator();
		while (it.hasNext()) {
			Task task = it.next();
			if (assignee.equals(task.getAssignee())) {
				entities.push(associate(task));
				it.remove();
			}
		}
		for (Task task: tasks) {
			entities.push(associate(task));			
		}
		return entities;
	}

	public void associate(BusinessProcess process, E entity) {
		persistAndFlush(entity);
		Object entityId = entityId(entity);
	    log.info("Associating process '" + processKey() + "' with entity '" + entityVariableName() + "' (id '" + entityId + "').");
	    process.setVariable(entityVariableName(), entityId);
	}

	protected String entityVariableName() {
		return Entities.idVariableName(getEntityClass());
	}
	
	protected E entity(Task task) {
		return find(entityId(task));
	}

	protected E entity(Execution execution) {
		return find(entityId(execution));
	}

	protected Object entityId(Task task) {
		return (Object) runtimeService.getVariable(task.getExecutionId(), entityVariableName());
	}

	protected Object entityId(Execution execution) {
		return (Object) runtimeService.getVariable(execution.getId(), entityVariableName());
	}

	protected Object entityId(BusinessProcess process) {
		return process.getVariable(entityVariableName());
	}
	
	protected Object entityId(E entity) {
		return (Object) entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
	}

	protected boolean entityExists(BusinessProcess process) {
		return entityId(process) != null;
	}
	
	protected ProcessInstance processInstance(Task task) {
		return processInstance(task.getProcessInstanceId());
	}

	protected ProcessInstance processInstance(String processInstanceId) {
		return runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
	}
	
	protected abstract String processKey();
	
}
