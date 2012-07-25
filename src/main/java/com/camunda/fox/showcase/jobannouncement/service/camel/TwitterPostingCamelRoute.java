package com.camunda.fox.showcase.jobannouncement.service.camel;

import org.apache.camel.builder.RouteBuilder;

import java.util.logging.Logger;

/**
 * Apache Camel based Twitter posting implementation.
 *
 * See http://camel.apache.org/twitter.html
 */
public class TwitterPostingCamelRoute extends RouteBuilder {

    Logger logger = Logger.getLogger(TwitterPostingCamelRoute.class.getName());

    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String accessTokenSecret;

    public TwitterPostingCamelRoute() {
        System.out.println(">> TwitterPostingCamelRoute instantiated");
    }

    @Override
    public void configure() throws Exception {

        from("direct:twitter")
            .log(">> Started")
            // Set Body with text "Bean Injected"
            .setBody().simple("Bean Injected")
            // Lookup for bean injected by CDIcontainer
            .beanRef("helloWorld", "sayHello")
            // Display response received in log when calling HelloWorld
            .log(">> Response : ${body}");

    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setAccessTokenSecret(String accessTokenSecret) {
        this.accessTokenSecret = accessTokenSecret;
    }
}