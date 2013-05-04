package com.plexiti.camunda.bpm.showcase.jobannouncement.service;

public interface PostingService<P> {

	public String post(P posting);
	
}
