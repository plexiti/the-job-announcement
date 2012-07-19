package com.camunda.fox.showcase.jobannouncement.service.impl;

import java.util.logging.Logger;

import javax.inject.Named;

import com.camunda.fox.showcase.jobannouncement.model.FacebookPosting;
import com.camunda.fox.showcase.jobannouncement.service.PostingService;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;

@Named("facebookPostingService")
public class FacebookPostingServiceImpl implements PostingService<FacebookPosting> {

	private static Logger log = Logger.getLogger(FacebookPostingServiceImpl.class.getName());

	private static String ACCESS_TOKEN = "AAAGNc8pj2IYBAKDY4PAKACUcF24JwwyI8gB4fo3uZAObH3qCMjQf5ZAsyXtIHBM5gi6fwsVSMeb10yNwU3hDh1DBkKmNNywn6S9FZAIpa45Om4ZBnSEc";
	private static String FACEBOOK_PAGE = "388345047896677";

	@Override
	public String post(FacebookPosting posting) {
		try {
			FacebookClient facebookClient = new DefaultFacebookClient(ACCESS_TOKEN);
			FacebookType publishMessageResponse = facebookClient.publish(FACEBOOK_PAGE + "/links", FacebookType.class, 
				Parameter.with("message", posting.getMessage()),
				Parameter.with("link", posting.getLink()),
				Parameter.with("og:name", posting.getName()),
				Parameter.with("description", posting.getDescription()),
				Parameter.with("icon", posting.getPicture()),
				Parameter.with("picture", posting.getPicture())
			);
			String facebookUrl = "http://www.facebook.com/permalink.php?story_fbid=" + publishMessageResponse.getId() + "&id=" + FACEBOOK_PAGE;
			log.info("Successfully posted [" + posting.getMessage() + "] to Facebook at [" + facebookUrl + "].");
			return facebookUrl;
		} catch (RuntimeException e) {
		    log.warning("Failed to post [" + posting.getMessage()  + "] to Facebook.");
			log.throwing(getClass().getName(), "post", e);
			throw e;
		}
	}
	
}
