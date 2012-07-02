package com.camunda.fox.showcase.jobannouncement.web;

import java.util.logging.Logger;

import javax.enterprise.context.Conversation;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.activiti.cdi.BusinessProcess;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;

public abstract class AbstractBean {

	protected Logger log = Logger.getLogger(getClass().getName());

	@Inject
	@SuppressWarnings("cdi-ambiguous-dependency")
	protected BusinessProcess businessProcess;
	
	@Inject
	@SuppressWarnings("cdi-ambiguous-dependency")
	protected IdentityService identityService;
	
	@Inject
	@SuppressWarnings("cdi-ambiguous-dependency")
	protected RuntimeService runtimeService;
	
	@Inject
	@SuppressWarnings("cdi-ambiguous-dependency")
	protected TaskService taskService;

	@Inject
	protected UserBean userBean;
	
	@Inject
	protected JobAnnouncementBean jobAnnouncementBean;

	@Inject
	protected Conversation conversation;
 
	protected void beginConversation() {
		if (conversation.isTransient()) {
			conversation.begin();
		}
	}

	protected void endConversation() {
		if (!conversation.isTransient()) {
			conversation.end();
		}
	}
	
	public String getParameter(String key) {
		FacesContext context = FacesContext.getCurrentInstance();
		return context != null ? context.getExternalContext().getRequestParameterMap().get(key) : null;
	}

}
