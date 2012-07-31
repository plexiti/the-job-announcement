# Introduction

The Job Announcement is a web application which we have built with the aim to be have a showcase of a business process-centric
application built around JEE technologies and the [camunda fox BPM Platform](http://www.camunda.com/fox).

The main business process this application supports can be seen in the following BPMN 2.0 model diagram:
<script type="text/javascript" src="https://editor.signavio.com/mashup/signavio.js"></script>
<script type="text/plain">
{
    url: "https://editor.signavio.com/p/model/9708839e17224f738a799e2ae133c461",
    authToken: "9fbe01266d678077e1c68911796aebc13e5bf3c2e6047e232bfac9b328bb_36a85752ab518d88d47651a3472aeee9ab1fc26fb5394fc88a5e2aad2d161_62ec9f231ca522ffd238d725c8156851f2c86d2f7433306cce9fe828367e45",
    overflowX: "fit",
    overflowY: "fit",
    zoomSlider: true,
    linkSubProcesses: false
}
</script>

The main points we wanted to make with this showcase are:
* [Business process-centric applications](TODO: reference needed) should be a pleasure for the eyes and have the user in the center, i.e. not be a PIA to use!
We have therefore focused not only on the looks (with [Twitter Bootstrap](http://twitter.github.com/bootstrap/)) but also
on the usability of the application by hiding as much as possible the nonessential aspects of the business process that backs
the application.

* While we consider the business process engine as the main building block of a process-centric application,
we are also strong believers in using the right tool for the job and have thus we leverage the [Apache Camel
integration framework](http://camel.apache.org/) to integrate with external systems like [Twitter](https://twitter.com/TheJobAnnouncer).

* Focus on testability: we provide readable unit tests, ... TODO

# Getting Started

In order to be in a position to play with the showcase yourself you will need to do the following steps:
1. Request a trial version of camunda fox ee platform by filling up [this form]().

# Technologies Used

* A user interface implemented with [JavaServer Faces 2.1+Facelets](http://javaserverfaces.java.net/) and [Twitter Bootstrap](http://twitter.github.com/bootstrap/) with a customized version provided by [Bootswatch](http://bootswatch.com/).

# Credits

This are the people and projects that helped us kickstart this project:

* camunda fox showcases: https://bitbucket.org/camunda/fox-showcases/
* JSF 2 + Twitter Bootstrap: http://rkovacevic.blogspot.co.at/2012/05/jsf-2-twitter-bootstrap.html
* Nils Preusker: http://www.nilspreusker.de/
* Bootstrap 2.0 Tabs JQuery Ajax Example: http://www.mightywebdeveloper.com/coding/bootstrap-2-tabs-jquery-load-content/
* http://glyphicons.com/
* Validate different validation groups depending on different buttons: http://www.dirkreske.de/button-based-bean-validation/
* Sticky Footer with Bootstrap: https://gist.github.com/1855032
* Signavio process editor

# Feedback and Future Work

We see this project as a permanent work in progress and already have several features in the pipeline the
we intend to implement and publish. Send you feedback to us at <TODO@TODO.COM>

# License

This software is licensed under the terms you may find in the file named "LICENSE.txt" in the root directory.
