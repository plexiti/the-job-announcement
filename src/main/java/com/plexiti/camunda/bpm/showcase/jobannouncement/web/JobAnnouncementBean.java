package com.plexiti.camunda.bpm.showcase.jobannouncement.web;

import static com.plexiti.camunda.bpm.showcase.jobannouncement.process.ProcessConstants.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.plexiti.camunda.bpm.showcase.jobannouncement.model.JobAnnouncement;
import com.plexiti.camunda.bpm.showcase.jobannouncement.service.JobAnnouncementService;
import com.plexiti.helper.Entities;

@Named("jobAnnouncements")
@ConversationScoped
public class JobAnnouncementBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 8916001355236217574L;

	private String comment;
	private String lastComment;
		
	@Inject
	private JobAnnouncementService jobAnnouncementService;

	private Long jobAnnouncementId;
	private JobAnnouncement jobAnnouncement;
	
	public Long getJobAnnouncementId() {
		String id = getParameter("id");
		return id != null ? Long.parseLong(id) : null;
	}

	@Named @Produces
	public JobAnnouncement getJobAnnouncement() {
		Long id = getJobAnnouncementId();
		if (id != null && !id.equals(jobAnnouncementId)) {
			jobAnnouncementId = id;
			jobAnnouncement = jobAnnouncementService.find(id);
		}
		return jobAnnouncement = (jobAnnouncement != null ? jobAnnouncement : jobAnnouncementService.findOrNew(businessProcess));
	}
	
	@Named
	public String getComment() {
		String comment = (String) runtimeService.getVariable(businessProcess.getExecutionId(), JOBANNOUNCEMENT_PROCESS_VARIABLE_COMMENT);
		log.info("Retrieved 'comment' from process variable: " + comment);
		return comment;
	}

	@Named
	public String getLastComment() {
		String lastComment = (String) runtimeService.getVariable(businessProcess.getExecutionId(), JOBANNOUNCEMENT_PROCESS_VARIABLE_LAST_COMMENT);
		log.info("Retrieved 'last_comment' from process variable: " + lastComment);
		return lastComment;
	}

	public void setComment(String comment) {
		log.info("Setting 'comment' to: " + comment);
		this.comment = comment;
	}
	
	public String showNew() {
		beginConversation();
		return "new";
	}

	public String showPublished() {
		beginConversation();
		log.info("showPublished job announcement #" + getJobAnnouncement().getId());
		return "published";
	}

	public String showForm(String taskId, String phase) {
		beginConversation();
		businessProcess.startTask(taskId);
		if (phase.equals("edit")) {
		    getJobAnnouncement().setEditor(userBean.getLoggedInUser());
			jobAnnouncementService.merge(jobAnnouncement);
		}
		if (!phase.equals("publish")) {
			taskService.claim(taskId, userBean.getLoggedInUser());
		}
		return phase;
	}

	public String doSendToEditing() {
	    log.info("Starting process with key '" + JOBANNOUNCEMENT_PROCESS + "'");
		jobAnnouncementService.associate(businessProcess, jobAnnouncement);
		getJobAnnouncement().setRequester(userBean.getLoggedInUser()); 
	    businessProcess.startProcessByKey(JOBANNOUNCEMENT_PROCESS);
		saveEntity();
		endConversation();
		return "start";
	}

	public String doRequestReview() {
		log.info("doRequestR");
		log.info("Saving 'comment' to process variable 'last_comment': " + comment);
		businessProcess.setVariable(JOBANNOUNCEMENT_PROCESS_VARIABLE_LAST_COMMENT, comment);
		/*
		 * Since we allow a user to "Save" the status of the form and come back to it later, we need to be able to store
		 * the contents of the "comment" field into the 'comment' process variable and retrieve it upon returning to the
		 * form. *But* we need to make sure we delete that process vairiable once the process has been submitted for the next step.
		 * Otherwise, it will appear in the next form.
		 */
		runtimeService.setVariable(businessProcess.getExecutionId(), JOBANNOUNCEMENT_PROCESS_VARIABLE_COMMENT, null);
		businessProcess.completeTask();
		saveEntity();
		endConversation();
		return "start";
	}

	public String doApprovePublication() {
		businessProcess.setVariable(JOBANNOUNCEMENT_PROCESS_VARIABLE_APPROVED, true);
		businessProcess.completeTask();
		endConversation();
		return "start";
	}

	public String doRequestCorrection() {
		businessProcess.setVariable(JOBANNOUNCEMENT_PROCESS_VARIABLE_APPROVED, false);
		businessProcess.setVariable(JOBANNOUNCEMENT_PROCESS_VARIABLE_LAST_COMMENT, comment);
		runtimeService.setVariable(businessProcess.getExecutionId(), JOBANNOUNCEMENT_PROCESS_VARIABLE_COMMENT, null);
		businessProcess.completeTask();
		saveEntity();
		endConversation();
		return "start";
	}

	public String doPublish() {
		taskService.claim(businessProcess.getTaskId(), userBean.getLoggedInUser());
		businessProcess.setVariable(JOBANNOUNCEMENT_PROCESS_VARIABLE_TWITTER, jobAnnouncement.getTwitterMessage() != null);
		businessProcess.setVariable(JOBANNOUNCEMENT_PROCESS_VARIABLE_FACEBOOK, jobAnnouncement.getFacebookPost() != null);
		businessProcess.completeTask();
		endConversation();
		return "start";
	}

	public String doSave() {
		log.info("doSave: 'comment'" + this.comment);
		log.info("doSave: 'lastComment'" + this.lastComment);
		log.info("Saving 'comment' to process variable: " + comment);
		/*
		 * We need to save the contents of the field to the process variable to be able to retrieve it next time.
		 */
		runtimeService.setVariable(businessProcess.getExecutionId(), JOBANNOUNCEMENT_PROCESS_VARIABLE_COMMENT, comment);
		saveEntity();
		endConversation();
		return "start";
	}
		
	private void saveEntity() {
		log.info("saveEntity");
		jobAnnouncementService.merge(jobAnnouncement);
	}

	public String doCancel() {
		endConversation();
		return "start";
	}

	public String doDelete() {
		runtimeService.deleteProcessInstance(businessProcess.getProcessInstanceId(), "Process canceled as requested by user '" + userBean.getLoggedInUser() + "'");
		jobAnnouncementService.delete(jobAnnouncement);
		endConversation();
		return "start";
	}
	
	public String doMenu(String menu) {
		endConversation();
		return menu;
	}
	
	@Inject
	Map<String, List<JobAnnouncement>> announcementTables;
	
	String loggedInUser;
		
	@Produces @Named @RequestScoped
	Map<String, List<JobAnnouncement>> getAnnouncementTables() {
		loggedInUser = userBean.getLoggedInUser();
		Map<String, List<JobAnnouncement>> table = new HashMap<String, List<JobAnnouncement>>();
		table.put("own", jobAnnouncementService.listInvolved(loggedInUser));
		table.put("edit", jobAnnouncementService.listAssociated(loggedInUser, WebConstants.FROM_PHASE_TO_TASK.get("edit")));
		table.put("review", jobAnnouncementService.listAssociated(loggedInUser, WebConstants.FROM_PHASE_TO_TASK.get("review")));
		table.put("publish", jobAnnouncementService.listAssociated(loggedInUser, WebConstants.FROM_PHASE_TO_TASK.get("publish")));
		table.put("published", jobAnnouncementService.listPublished());
		return table;
	}
	
	public int getJobAnnouncementsCount(String tab) {
		if (!userBean.getLoggedInUser().equals(loggedInUser)) {
			loggedInUser = userBean.getLoggedInUser();
			announcementTables = getAnnouncementTables();
		}
		return announcementTables.get(tab).size();
	}

	public List<JobAnnouncement> getJobAnnouncements(String tab) {
		if (!userBean.getLoggedInUser().equals(loggedInUser)) {
			loggedInUser = userBean.getLoggedInUser();
			announcementTables = getAnnouncementTables();
		}
		return announcementTables.get(tab);
	}
	
	public boolean isAssignable(String taskId) {
		boolean assignable = taskService.createTaskQuery().taskId(taskId).taskCandidateUser(userBean.getLoggedInUser()).count() > 0;
		if (!assignable) {
			assignable = taskService.createTaskQuery().taskId(taskId).taskAssignee(userBean.getLoggedInUser()).count() > 0;
		} else if (taskService.createTaskQuery().taskId(taskId).singleResult().getTaskDefinitionKey().equals(TASK_DESCRIBE_POSITION)) {
			// Four eyes principle
			assignable = !userBean.getLoggedInUser().equals(jobAnnouncementService.find((Long) taskService.getVariable(taskId, Entities.idVariableName(JobAnnouncement.class))).getRequester());
		}
		return assignable;
	}
	
	public String getButtonMessageKey(String taskDefinitionKey) {
		return "button." + taskDefinitionKey;
	}
	
}
