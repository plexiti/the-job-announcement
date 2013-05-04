package com.plexiti.camunda.bpm.showcase.jobannouncement.process;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.test.fluent.FluentProcessEngineTestRule;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;

import javax.inject.Inject;

import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.*;
import static com.plexiti.camunda.bpm.showcase.jobannouncement.process.ProcessConstants.*;

@RunWith(Arquillian.class)
public class ProcessDeploymentAndStartIT {

    @Deployment
    public static WebArchive createDeployment() {
        MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class)
                                                              .loadMetadataFromPom("pom.xml");

        return ShrinkWrap.create(WebArchive.class, "process-smoke-test.war")
                .addAsLibraries(resolver.artifact("org.camunda.bpm:camunda-engine-cdi").resolveAsFiles())
                .addAsLibraries(resolver.artifact("org.camunda.bpm.javaee:camunda-ejb-client").resolveAsFiles())

                // prepare as process application archive for fox platform
                .addAsWebResource("META-INF/processes.xml", "WEB-INF/classes/META-INF/processes.xml")
                .addAsResource(JOBANNOUNCEMENT_PROCESS_RESOURCE)
                .addAsResource(JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE)

                // add camunda BPM fluent testing dependency
                .addAsLibraries(resolver.artifact("org.camunda.bpm.incubation:camunda-bpm-fluent-engine-api").resolveAsFiles())
                .addAsLibraries(resolver.artifact("org.camunda.bpm.incubation:camunda-bpm-fluent-assertions").resolveAsFiles())
                ;
    }

    @Rule
    public FluentProcessEngineTestRule bpmnFluentTestRule = new FluentProcessEngineTestRule(this);

    @Inject
    @SuppressWarnings("cdi-ambiguous-dependency")
    private RuntimeService runtimeService;

    @Test
    public void testDeploymentAndStartInstance() throws InterruptedException {
      newProcessInstance(JOBANNOUNCEMENT_PROCESS).start();
      assertThat(processInstance()).isStarted();
      System.out.println("Started process instance with id " + processInstance().getId());

      assertThat(processInstance()).isWaitingAt(TASK_DESCRIBE_POSITION);
    }
}
