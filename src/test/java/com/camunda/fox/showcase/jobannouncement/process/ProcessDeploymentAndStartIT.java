package com.camunda.fox.showcase.jobannouncement.process;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;

import static com.camunda.fox.showcase.jobannouncement.process.ProcessConstants.*;
import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.*;

@RunWith(Arquillian.class)
public class ProcessDeploymentAndStartIT {

    @Deployment
    public static WebArchive createDeployment() {
        MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class)
                                                              .loadMetadataFromPom("pom.xml");

        return ShrinkWrap.create(WebArchive.class, "process-smoke-test.war")
                .addAsLibraries(resolver.artifact("com.camunda.fox.platform:fox-platform-client").resolveAsFiles())

                // prepare as process application archive for fox platform
                .addAsWebResource("META-INF/processes.xml", "WEB-INF/classes/META-INF/processes.xml")
                .addAsResource("com/camunda/fox/showcase/jobannouncement/process/job-announcement.bpmn")
                .addAsResource("com/camunda/fox/showcase/jobannouncement/process/job-announcement-publication.bpmn")

                // add fluent assertions dependency
                .addAsLibraries(resolver.artifact("org.easytesting:fest-assert").resolveAsFiles())
                ;
    }

    @Inject
    @SuppressWarnings("cdi-ambiguous-dependency")
    private RuntimeService runtimeService;

    @Test
    public void testDeploymentAndStartInstance() throws InterruptedException {
        HashMap<String, Object> variables = new HashMap<String, Object>();

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("job-announcement", variables);
        String id = processInstance.getId();
        assertThat(processInstance).isNotNull();
        System.out.println("Started process instance id " + id);

        List<String> activityIds = runtimeService.getActiveActivityIds(id);

        assertThat(activityIds.size()).isEqualTo(1);
        assertThat("edit").isIn(activityIds);
    }
}
