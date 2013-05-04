package com.plexiti.camunda.bpm.showcase.jobannouncement.service;

import java.io.InputStream;

public interface UserImageService {

	InputStream getImage(String userId);

}