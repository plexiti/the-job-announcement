# Introduction

The Job Announcement is a web application built in order to showcase a business process-centric
application based on the [Java EE 6](http://www.oracle.com/technetwork/java/javaee/overview/index.html) technology stack
and the [camunda fox BPM Platform](http://www.camunda.com/fox) running on the [JBoss Application Server 7](http://www.jboss.org/jbossas/).
An online version of the showcase can be found at [http://the-job-announcement.com/](http://the-job-announcement.com/).

![The Job Announcement Splash Screen][1]

# Supporting Process

The application supports the process of creating a job position announcement starting from the need of
finding a new employee. A manager can start the process by requesting a new job announcement giving a
brief description of her needs. She will then submit her request to the Human Resources department which
will review and create a detailed job announcement with an optional Twitter and/or Facebook post. The job
announcement can be reviewed and approved (or not) by the original manager. Eventually, the job announcement
is published to Twitter and Facebook.

Two BPMN 2.0 business processes definitions implemented the abovementioned process (see below).

## The Job Announcement
![The Job Announcement BPMN 2.0 diagram][2]
## The Job Announcement: Publish Job Announcement
![The Job Announcement Publish job announcement BPMN 2.0 diagram][3]

# Yes, we've got issues!

We welcome any feedback. You can send as an e-mail at [hello@plexiti.com](mailto:hello@plexiti.com) or use [Bitbucket's
issue management system for this project](https://bitbucket.org/plexiti/the-job-announcement-fox/issues).

# Building the showcase yourself

In order to be able to "play" with the showcase yourself you will need to do the following steps:

1. Download the camunda fox Platform Community Edition (version 1.28.0 or higher) from [here](https://app.camunda.com/confluence/display/foxUserGuide/Getting+Started).
1. Install it, start it with `<FOX_PLATFORM_HOME>/server/jboss-as-7.1.0.Final/bin$ ./standalone.sh`
1. Make sure JBoss AS 7 is running by pointing your browser to `http://localhost:8080/`
1. Make sure you have the following installed *and working*:
    * [Java Platform (*JDK*) 1.6.x](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
    * [Maven](http://maven.apache.org/) 3.0.x
    * [Git](http://git-scm.com/) 1.7.x
1. Clone this repository with `git clone git@bitbucket.org:plexiti/the-job-announcement-fox.git`
1. Build the application with `mvn package` and deploy it with `mvn jboss-as:deploy`
1. Point your browser to `http://localhost:8080/the-job-announcement/` and enjoy!

# Credits

This application was developed by Martin Schimak and Rafael Cordones Marcos at [plexiti](http://plexiti.com/). Furthermore, these people, projects and tools helped us build this showcase:

* [The Signavio Process Editor](http://www.signavio.com/en/products/overview.html) by [Signavio](http://www.signavio.com/)
* [camunda fox showcases](https://bitbucket.org/camunda/fox-showcases/) from [camunda services GmbH](http://www.camunda.com/)
* [JSF 2 + Twitter Bootstrap](http://rkovacevic.blogspot.co.at/2012/05/jsf-2-twitter-bootstrap.html)
* [Nils Preusker](http://www.nilspreusker.de/)
* [Bootstrap 2.0 Tabs JQuery Ajax Example](http://www.mightywebdeveloper.com/coding/bootstrap-2-tabs-jquery-load-content/)
* Icons by [GLYPHICONS](http://glyphicons.com/)
* [Validate different validation groups depending on different buttons](http://www.dirkreske.de/button-based-bean-validation/)
* [Sticky Footer with Bootstrap](https://gist.github.com/1855032)

# Feedback and Future Work

We see this project as a permanent work in progress, we already have several features in the pipeline which
we intend to implement and publish. In the meantime send us your feedback to [hello@plexiti.com](mailto:hello@plexiti.com)

# License

This software is licensed under the terms you may find in the file named `LICENSE.txt` in the root directory.

[1]: https://bitbucket.org/plexiti/the-job-announcement-fox/downloads/the-job-announcement-showcase-splash-screen-v3.png
[2]: https://bitbucket.org/plexiti/the-job-announcement-fox/downloads/Stellenausschreibung-Ebene-Engine.png
[3]: https://bitbucket.org/plexiti/the-job-announcement-fox/downloads/Stellenausschreibung-Ebene-Durchfuehrung-Engine.png