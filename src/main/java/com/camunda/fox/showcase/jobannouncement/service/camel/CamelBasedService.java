package com.camunda.fox.showcase.jobannouncement.service.camel;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.RetentionPolicy.*;
import static java.lang.annotation.ElementType.*;

@Qualifier
@Retention(RUNTIME)
@Target({FIELD, TYPE})
public @interface CamelBasedService {
}

