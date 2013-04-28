package com.camunda.fox.showcase.jobannouncement.service.camel;

import com.camunda.fox.showcase.jobannouncement.model.FacebookPosting;
import com.camunda.fox.showcase.jobannouncement.model.JobAnnouncement;
import com.camunda.fox.showcase.jobannouncement.service.JobAnnouncementService;
import com.camunda.fox.showcase.jobannouncement.service.PostingService;
import com.camunda.fox.showcase.jobannouncement.service.impl.FacebookPostingServiceImpl;
import com.camunda.fox.showcase.jobannouncement.service.impl.JobAnnouncementServiceImpl;
import com.camunda.fox.showcase.jobannouncement.service.impl.MailingServiceImpl;
import com.plexiti.activiti.model.TaskAware;
import com.plexiti.activiti.service.ProcessAwareEntityService;
import com.plexiti.activiti.service.ProcessAwareJpaEntityServiceImpl;
import com.plexiti.helper.Objects;
import com.plexiti.helper.Strings;
import com.plexiti.persistence.service.EntityService;
import com.plexiti.persistence.service.JpaEntityServiceImpl;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.exporter.ExplodedExporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;

@RunWith(Arquillian.class)
public class TwitterPostingCamelTestIT {

    @Deployment
    public static WebArchive createDeployment() {
        MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class)
                                                              .loadMetadataFromPom("pom.xml");

        WebArchive war = ShrinkWrap.create(WebArchive.class, "camel-twitter-posting-test.war")
                .addAsLibraries(resolver.artifact("com.camunda.fox.platform:fox-platform-client").resolveAsFiles())
                .addAsLibraries(resolver.artifact("org.apache.camel:camel-core").resolveAsFiles())
                .addAsLibraries(resolver.artifact("org.apache.camel:camel-cdi").resolveAsFiles())
                .addAsLibraries(resolver.artifact("org.apache.camel:camel-twitter").resolveAsFiles())
                .addAsLibraries(resolver.artifact("org.apache.commons:commons-email").resolveAsFiles())
                .addAsLibraries(resolver.artifact("com.restfb:restfb").resolveAsFiles())

                .addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"))
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")

                .addClass(ContextBootStrap.class)
                .addClass(TwitterPostingCamelRoute.class)

                .addClass(PostingService.class)
                .addClass(JobAnnouncementService.class)
                .addClass(JobAnnouncementServiceImpl.class)

                .addClass(FacebookPostingServiceImpl.class)
                .addClass(MailingServiceImpl.class)

                .addClass(ProcessAwareEntityService.class)
                .addClass(ProcessAwareJpaEntityServiceImpl.class)
                .addClass(EntityService.class)
                .addClass(JpaEntityServiceImpl.class)
                .addClass(TaskAware.class)
                .addClass(JobAnnouncement.class)
                .addClass(FacebookPosting.class)
                .addClass(Strings.class)
                .addClass(Objects.class)

                // add fluent assertions dependency
                .addAsLibraries(resolver.artifact("org.easytesting:fest-assert").resolveAsFiles())
                ;

        /*
         * For troubleshooting purposes use the following two lines to export the WAR to the filesystem
         * to see if everything needed is there!
         */
        //File destinationDir = new File("/Users/rafa/dev/plexiti/vc/the-job-announcement-fox/target");
        //war.as(ExplodedExporter.class).exportExploded(destinationDir);

        return war;
    }

    @Inject
    @SuppressWarnings("cdi-ambiguous-dependency")
    private JobAnnouncementService jobAnnouncementService;

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    UserTransaction utx;

    @Test
    public void testCamelTwitterPosting() throws InterruptedException, SystemException, NotSupportedException,
                                                 RollbackException, HeuristicRollbackException, HeuristicMixedException {

        assertThat(jobAnnouncementService).isNotNull();
        assertThat(entityManager).isNotNull();

        JobAnnouncement announcement = new JobAnnouncement();
        //announcement.setId(1L);
   		announcement.setNeed("A good software developer!");
   		announcement.setRequester("gonzo");
   		announcement.setTwitterMessage("Wir suchen ein Softwareentwickler!");
   		announcement.setJobTitle("Software Developer");

        jobAnnouncementService.persist(announcement);
        Long announcementId = announcement.getId();
        assertThat(announcementId).isNotNull();

        /* Check that we can retrieve the announcement by id */
        announcement = jobAnnouncementService.find(announcementId);
        assertThat(announcement).isNotNull();

        jobAnnouncementService.postToTwitter(announcementId);
    }
}
