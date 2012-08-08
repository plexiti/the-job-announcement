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

    public TwitterPostingCamelRoute() {
        System.out.println(">> TwitterPostingCamelRoute instantiated");
    }

    @Override
    public void configure() throws Exception {

        from("direct:tweets")
            .log(">> Started")
            // Set Body with text "Bean Injected"
            .setBody().simple("Bean Injected")
            // Lookup for bean injected by CDIcontainer
            .beanRef("helloWorld", "sayHello")
            // Display response received in log when calling HelloWorld
            .log(">> Response : ${body}");

    }
}