package com.camunda.fox.showcase.jobannouncement.service;

public interface PostingService<P> {

	public String post(P posting);
	
}
