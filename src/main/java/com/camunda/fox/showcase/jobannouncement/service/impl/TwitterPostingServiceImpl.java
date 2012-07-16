package com.camunda.fox.showcase.jobannouncement.service.impl;

import java.util.logging.Logger;

import javax.inject.Named;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import com.camunda.fox.showcase.jobannouncement.service.PostingService;

@Named("twitterPostingService")
public class TwitterPostingServiceImpl implements PostingService<String> {

	private static Logger log = Logger.getLogger(TwitterPostingServiceImpl.class.getName());
	
	private static TwitterFactory factory = new TwitterFactory(
		new ConfigurationBuilder()
			.setDebugEnabled(true)
			.setOAuthConsumerKey("B05xfeYmoEJjrikPo0Nv3Q")
			.setOAuthConsumerSecret("vUqywyCeh2Z97rOCOymX6fpkqntAEhnISeN6KjGZ3Pk")
			.setOAuthAccessToken("620425776-Hd9mhhDlCtrqGNADne9w3yv6CaTnaCXyXSex0I5j")
			.setOAuthAccessTokenSecret("Gw0aiW3VYYpUazTRjjLaaUw5o1a0ivvlaslW0t7s40s")
			.build()
	);
	
	@Override
	public String post(String tweet) {
		log.info("About to tweet [" + tweet + "].");
		try {
		    Twitter twitter = factory.getInstance();
		    Status status = twitter.updateStatus(tweet);
		    String tweetUrl = "http://twitter.com/" + status.getUser().getScreenName() + "/status/" + status.getId();
		    log.info("Successfully tweeted [" + status.getText() + "] at [" + tweetUrl + "].");
		    return tweetUrl;
		} catch (TwitterException e) {
		    log.warning("Failed to tweet [" + tweet + "].");
			log.throwing(getClass().getName(), "post", e);
			throw new RuntimeException(e);
		}
	}

}
