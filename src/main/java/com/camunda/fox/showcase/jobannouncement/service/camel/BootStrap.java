package com.camunda.fox.showcase.jobannouncement.service.camel;

import org.apache.camel.component.cdi.CdiCamelContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * BootStrap the Apache Camel context on a CDI environment.
 * See http://camel.apache.org/cdi.html
 * and
 */
@Singleton
@Startup
public class BootStrap {

    Logger logger = Logger.getLogger(BootStrap.class.getName());

    @Inject
    CdiCamelContext camelCtx;

    @Inject
    TwitterPostingCamelRoute twitterCamelRoute;

    @PostConstruct
    public void init() throws Exception {
            logger.info(">> Creating CamelContext and registering Camel routes ...");

            // Configure the Twitter Camel route
            twitterCamelRoute.setConsumerKey("B05xfeYmoEJjrikPo0Nv3Q");
            twitterCamelRoute.setConsumerSecret("vUqywyCeh2Z97rOCOymX6fpkqntAEhnISeN6KjGZ3Pk");
            twitterCamelRoute.setAccessToken("620425776-Hd9mhhDlCtrqGNADne9w3yv6CaTnaCXyXSex0I5j");
            twitterCamelRoute.setAccessTokenSecret("Gw0aiW3VYYpUazTRjjLaaUw5o1a0ivvlaslW0t7s40s");

            // Add Camel routes
            camelCtx.addRoutes(twitterCamelRoute);

            // Start Camel Context
            camelCtx.start();

            logger.info(">> CamelContext created and camel routes started.");
    }

    @PreDestroy
    public void stop() throws Exception {
       camelCtx.stop();
    }
}