package com.plexiti.camunda.bpm.showcase.jobannouncement.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.camunda.bpm.engine.task.Task;
import org.hibernate.validator.constraints.NotBlank;

import com.plexiti.camunda.bpm.model.TaskAware;
import com.plexiti.helper.Strings;

@Entity
public class JobAnnouncement implements TaskAware, Serializable {

	private static final long serialVersionUID = 7458230741177377962L;
	
	public static interface Edit {}

	@Id
	@GeneratedValue
	private Long id;

	@NotBlank (message = "{jobAnnouncement.notblank}")
	@Size(max = 1000, message = "{jobAnnouncement.size}")
	@Column(name = "need")
    private String need;
	
	@NotBlank (message = "{jobAnnouncement.notblank}")
	@Size(max = 100, message = "{jobAnnouncement.size}")
	@Column(name = "job_title")
	private String jobTitle;
	
	@NotBlank (message = "{jobAnnouncement.notblank}", groups = Edit.class )
	@Size(max = 1000, message = "{jobAnnouncement.size}")
	@Column(name = "job_description")
	private String jobDescription;

	@Size(max = 240, message = "{jobAnnouncement.size}")
	@Column(name = "facebook_post")
	private String facebookPost;

	@Size(max = 119, message = "{jobAnnouncement.size}")
	@Column(name = "twitter_message")
	private String twitterMessage;
	
	@Column(name = "published")
	private Date published;
	
	@Column(name = "requester")
	private String requester;
	
	@Column(name = "editor")
	private String editor;
	
	@Column(name = "twitter_url")
	private String twitterUrl;

	@Column(name = "facebook_url")
	private String facebookUrl;
	
	@Transient
	private Task task;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNeed() {
		return need;
	}

	public void setNeed(String need) {
		this.need = Strings.trimToNull(need);
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = Strings.trimToNull(jobTitle);
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = Strings.trimToNull(jobDescription);
	}

	public String getFacebookPost() {
		return facebookPost;
	}

	public void setFacebookPost(String facebookPost) {
		this.facebookPost = Strings.trimToNull(facebookPost);
	}

	public String getTwitterMessage() {
		return twitterMessage;
	}

	public void setTwitterMessage(String message) {
		this.twitterMessage = Strings.trimToNull(message);
	}
	
	public Date getPublished() {
		return published;
	}

	public void setPublished(Date published) {
		this.published = published;
	}
	
	public String getRequester() {
		return requester;
	}

	public void setRequester(String requester) {
		this.requester = Strings.trimToNull(requester);
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = Strings.trimToNull(editor);
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public String getTwitterUrl() {
		return twitterUrl;
	}

	public void setTwitterUrl(String twitterUrl) {
		this.twitterUrl = Strings.trimToNull(twitterUrl);
	}

	public String getFacebookUrl() {
		return facebookUrl;
	}

	public void setFacebookUrl(String facebookUrl) {
		this.facebookUrl = Strings.trimToNull(facebookUrl);
	}

	public String getWebsiteUrl() {
		// TODO Construct correct URL here.
		return "http://the-job-announcement.com/view.jsf?id=" + id;
	}
	
	public String getTwitterMessageWithLink() {
		return getTwitterMessage() + " " + getWebsiteUrl();
	}
	
	public String getFacebookPostWithLink() {
		return getFacebookPost() + " " + getWebsiteUrl();
	}

}
