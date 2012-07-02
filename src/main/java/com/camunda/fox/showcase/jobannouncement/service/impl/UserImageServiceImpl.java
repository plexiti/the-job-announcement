package com.camunda.fox.showcase.jobannouncement.service.impl;

import java.io.InputStream;

import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.engine.IdentityService;

import com.camunda.fox.showcase.jobannouncement.service.UserImageService;

@Named("UserImageService")
public class UserImageServiceImpl implements UserImageService {

	@Inject
	@SuppressWarnings("cdi-ambiguous-dependency")
	private IdentityService identityService;

	@Override
	public InputStream getImage (String userId) {
		return identityService.getUserPicture(userId).getInputStream();
	}

}
