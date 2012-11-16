
			---------------------
			  REMICS "DOME" DEMO
			      SINTEF ICT
				 2012
			---------------------


The objective of this demo is twofold: 

 (1) Illustrate the mediation between two data models, using two
     different algorithms 

 (2) Illustrate the comparison facilities provided by the mediation
     framework to compare data models


1. DEMO SET UP
==============

There are two ways to set up the mediation framework so as to showcase
this demo: using local installation or using the SINTEF-DEMO server.


1.1 Local set-up
----------------

  Before to start, there is a third party dependency: MongoDB that you
  must have running on your machine in order to be able to showcase
  the demo. Once you have installed MongoDB, you need to:


  (a) Start the mongodb server. For instance, using the command

      $> mongod --dbpath="~/tmp/mongodb/data"


  (b) Retrieve the source code from the GitHub repository. To do so,
      go to directory where you want to place the code (say ~/tmp for
      instance) and enter the following commnands:

      $> cd ~/tmp/
      $> git clone https://github.com/SINTEF-9012/mediation-portal.git  


  (c) Compile the complete application by entering the following commands:

      $> cd net.modelbased.mediation
      $> mvn clean install
  
      NB: Compiling the application will trigger test procedures which
      expects that your MongoDB data base does not alerady contain
      models.


  (d) Start the mediation services, using the following commands:

      $> cd ..
      $> cd net.modelbased.mediation.portal
      $> mvn jetty:run

      This starts a jetty server but will not return, so you then have
      to open a second terminal to start the webapp side


  (e) Start the mediation front-end using the following commands:

      $> cd ..
      $> cd net.modelbased.mediation.gui
      $> mvn jetty:run

      This also starts a second jetty server, listening on
      localhost:9090. You may want to open a browser and check whether
      you are able to access the mediation portal api, at the address:

      http://localhost:9090

      Once you face the front page of the mediation portal, you must
      configure this front end so as it uses your local set up. To do
      so, click on "settings" on the top menu bar, and then select
      "LOCALHOST" among the available back ends.

      Your are now ready to showcase :)


1.2 Using the SINTEF-DEMO server
--------------------------------

  Using the SINTEF-DEMO server is a much simpler set-up, which only
  requires a browser. Proceed as follows:

  (a) Go to the following address, where the DEMO server is available:

   http://54.247.114.191/net.modelbased.mediation.gui-0.0.1-SNAPSHOT/index.html





2. DEMO STORY LINE
==================


2.1 Mediating between two models
--------------------------------

  Here, we want to illustrate our ability to import data models and to
  mediate between them. Three main steps here:

    - Import the source data model (the internal representation used
       by DOME)

    - Import the target data model (the standard OTA data model)

    - Trigger the mediation and see the result

    
  (a) Starting from the front page of the mediation portal, click on
  "Repositories" on the top menu bar. You now see the form to import
  models.

    - Fill in a unique ID for the the source models (e.g.,
      REMICS-DOME) and a short description (e.g., "The internal data
      model used by DOME").

    - Select the file to upload. The data model is located in the
      source code that you retrived at:

      ~/tmp/net.modelbased.mediation.samples.dome/src/main/resources/source.mod
 	
    - Select the type of model that you want to import: "SINTEF Mof
      Textual Notation"

    - Click on import.

    - You may want to visualize the models by clicking on there names
      in the right handside table.

  (b) Proceed in a similar way for the target data model, which is located in

      ~/tmp/net.modelbased.mediation.samples.dome/src/main/resources/target.mod

  (c) Trigger the mediation. To do so, click on "Mediator" on the top
  menu-bar.

    - You can now find the form, by selecting the source model and the
      target among which you want to mediate (say "REMICS-DOME" and
      "REMICS-OTA" for instance).

    - Select the algorithm you want to use (random or syntatic)

    - You now see the mapping available on the right side of the page


2.2 Comparing Algorithms' Effectiveness
---------------------------------------

  Here we want to show how one can use the mediation framework to
  evaluate the effectiveness of mediation algorithm on specific
  cases. To do so, proceed as follows:

  (a) Trigger a second mediation, between the two same models, but
  using a different algorithm, e.g., "random" if you used "syntactic"
  in the first place. (c.f. step 2.1.a)

  (b) Assuming one of these two mappings is the good one (the so
  called "oracle"), we can visualize the relevance of the other one.

     - Click on the "Comparator" service, on the top menu bar

     - Select the oracle you want as an oracle

     - Select the mapping you want to compare with oracle

     - Click on "Launch evaluation" you should see
