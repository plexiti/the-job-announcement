package com.camunda.fox.showcase.jobannouncement.web;

import java.util.logging.Logger;

import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.context.Conversation;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;

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
	
	public FacesContext getFacesContext() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		if (ctx == null)
			throw new ContextNotActiveException("FacesContext is not active");
		return ctx;
	}

}
