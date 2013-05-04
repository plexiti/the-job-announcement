package com.plexiti.camunda.bpm.showcase.jobannouncement.model;

public class FacebookPosting {

	private String message;
	private String link;
	private String name;
	private String description;
	private String picture;

	public String getMessage() {
		return message;
	}

	public FacebookPosting setMessage(String text) {
		this.message = text;
		return this;
	}

	public String getLink() {
		return link;
	}

	public FacebookPosting setLink(String link) {
		this.link = link;
		return this;
	}

	public String getName() {
		return name;
	}

	public FacebookPosting setName(String name) {
		this.name = name;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public FacebookPosting setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getPicture() {
		return picture;
	}

	public FacebookPosting setPicture(String picture) {
		this.picture = picture;
		return this;
	}

}
