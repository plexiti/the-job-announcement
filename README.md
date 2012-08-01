# Introduction

The Job Announcement is a web application we have built in order to showcase a business process-centric
application based on the [Java EE 6](http://www.oracle.com/technetwork/java/javaee/overview/index.html) technology stack
and the [camunda fox BPM Platform](http://www.camunda.com/fox) running on the [JBoss Application Server 7](http://www.jboss.org/jbossas/).

![The Job Announcement Splash Screen][1]

The main points we wanted to make with this showcase are:

* Business process-centric applications should be a pleasure for the eyes and have the user in the center, i.e. not be a PIA to use!
We have therefore focused not only on the looks (with [Twitter Bootstrap](http://twitter.github.com/bootstrap/)) but also
on the usability of the application by hiding as much as possible the nonessential aspects of the business process that backs
the application.

* While we consider the business process engine as the main building block of a process-centric application,
we are also strong believers in using the right tool for the job and have thus we leverage the [Apache Camel
integration framework](http://camel.apache.org/) to integrate with external systems like [Twitter](https://twitter.com/TheJobAnnouncer).

* Focus on testability: we provide [readable business process unit tests](https://bitbucket.org/plexiti/the-job-announcement-fox/src/64c9cfc28413/src/test/java/com/camunda/fox/showcase/jobannouncement/process/JobAnnouncementTest.java)
and [integration tests](https://bitbucket.org/plexiti/the-job-announcement-fox/src/64c9cfc28413/src/test/java/com/camunda/fox/showcase/jobannouncement/process/ProcessDeploymentAndStartIT.java) based on [JBoss Arquillian](http://www.jboss.org/arquillian.html).

# Business Processes

Two business processes are implemented in the showcase.

* The Job Announcement
![The Job Announcement BPMN 2.0 diagram][2]
* The Job Announcement: Publish job announcement
![The Job Announcement Publish job announcement BPMN 2.0 diagram][3]

# Getting Started

In order to be able to "play" with the showcase yourself you will need to do the following steps:

1. Request a trial version of camunda fox EE platform by filling up [this form](http://www.camunda.com/fox/trial/?).
1. Download the camunda fox EE platform, install it locally and start it.
1. Once you have received your credentials to access the camunda fox enterprise repository,
configure your Maven settings.xml as explained [here](https://app.camunda.com/confluence/display/foxUserGuide/Maven+configuration#Mavenconfiguration-Credentialsforcamundafoxenterpriserepository).
You will need [Maven](http://maven.apache.org/) 3.0.3 or higher.
1. Clone this repository with `git clone git@bitbucket.org:plexiti/the-job-announcement-fox.git`
1. Build the application with `mvn package` and deploy it with `mvn jboss-as:deploy
1. Point your browser to `http://localhost:8080/the-job-announcement/`

# Credits

This application was developed by Martin Schimak and Rafael Cordones Marcos. Furthermore, these are the people, projects and tools that helped us build this showcase:

* [camunda fox showcases](https://bitbucket.org/camunda/fox-showcases/) from [camunda services GmbH](http://www.camunda.com/)
* [JSF 2 + Twitter Bootstrap](http://rkovacevic.blogspot.co.at/2012/05/jsf-2-twitter-bootstrap.html)
* [Nils Preusker](http://www.nilspreusker.de/)
* [Bootstrap 2.0 Tabs JQuery Ajax Example](http://www.mightywebdeveloper.com/coding/bootstrap-2-tabs-jquery-load-content/)
* Icons by [GLYPHICONS](http://glyphicons.com/)
* [Validate different validation groups depending on different buttons](http://www.dirkreske.de/button-based-bean-validation/)
* [Sticky Footer with Bootstrap](https://gist.github.com/1855032)
* [The Signavio Process Editor](http://www.signavio.com/en/products/overview.html) by [Signavio](http://www.signavio.com/)

# Feedback and Future Work

We see this project as a permanent work in progress, we already have several features in the pipeline which
we intend to implement and publish. In the meantime send us your feedback to <TODO@TODO.COM>

# License

This software is licensed under the terms you may find in the file named `LICENSE.txt` in the root directory.

[1]: https://bitbucket.org/plexiti/the-job-announcement-fox/downloads/the-job-announcement-showcase-splash-screen.png
[2]: https://bitbucket.org/plexiti/the-job-announcement-fox/downloads/Stellenausschreibung-Ebene-Engine.png
[3]: https://bitbucket.org/plexiti/the-job-announcement-fox/downloads/Stellenausschreibung-Ebene-Durchfuehrung-Engine.png