package com.camunda.fox.showcase.jobannouncement.service.impl;

import com.camunda.fox.showcase.jobannouncement.service.PostingService;
import com.camunda.fox.showcase.jobannouncement.service.camel.CamelBasedService;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;

import javax.enterprise.inject.Default;
import javax.inject.Named;

/**
 * Twiter posting implementation that uses Apache Camel to post to twitter.
 *
 */
@CamelBasedService
public class TwitterPostingServiceCamelImpl implements PostingService<String> {

    @EndpointInject(uri="direct:twitter")
    ProducerTemplate producer;

    @Override
    public String post(String posting) {
        producer.sendBody(posting);

        return posting;
    }
}
