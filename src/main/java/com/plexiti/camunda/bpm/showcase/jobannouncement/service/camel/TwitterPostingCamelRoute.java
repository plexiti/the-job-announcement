package com.plexiti.camunda.bpm.showcase.jobannouncement.service.camel;

import org.apache.camel.builder.RouteBuilder;

import java.util.logging.Logger;

/**
 * Apache Camel based Twitter posting implementation.
 *
 * See http://camel.apache.org/twitter.html
 */
public class TwitterPostingCamelRoute extends RouteBuilder {

    Logger logger = Logger.getLogger(TwitterPostingCamelRoute.class.getName());

    public TwitterPostingCamelRoute() {
        System.out.println(">> TwitterPostingCamelRoute instantiated");
    }

    @Override
    public void configure() throws Exception {

        from("direct:tweets")
            .inOut("twitter://timeline/user")
            .to("log:com.plexiti.camunda.bpm.showcase.jobannouncement.service.camel?level=INFO&showAll=true&multiline=true")
            /*
             * Since we are using a 'direct' endpoint the calling method will receive back the body of the message,
             * i.e. a twitter4j.Status object
             */

            //.transform().simple("${body.id}")
            //.to("bean:jobAnnouncementService?method=updateTweetId(${header.jobAnnouncementId}, ${body.id})")
            ;
    }
}