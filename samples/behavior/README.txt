====
    This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]

    Copyright (C) 2012-  SINTEF ICT
    Contact: Franck Chauvel <franck.chauvel@sintef.no>

    Module: root

    Mediation Portal is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as
    published by the Free Software Foundation, either version 3 of
    the License, or (at your option) any later version.

    Mediation Portal is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General
    Public License along with Mediation Portal. If not, see
    <http://www.gnu.org/licenses/>.
====

			---------------------
			  REMICS "DOME" DEMO
			      SINTEF ICT
				 2012
			---------------------


The objectives of this demo are: 

 (1) To give an overview of the DSL used to specify behavior mediators 

 (2) To compile this DSL to WP6 models@runtime engine, which provides a graphical view of the mediator, useful for testing purposes

 (3) To compile this DSL to FP7 DiVA models@runtime, which provide model-based dynamic deployment capabilities, in order to validate the dynamic replacement of a service by another one


1. Setup
========

Make sure you have Java and Maven installed on your computer

- cd into org.remics.dome (this project contains the business object of the DOME case study)
- mvn clean install


1. Overview of the DSL
======================

- Launch ThingMLEditor.jar
- choose org.remics.dome.models/src/main/models as the folder where to initialize the editor
- in the editor, relevant models are located in studies/remics/dome
- open the "mediatorToAlternative" model, which is the most complete sample


2. Compiling to WP6 Models@runtime engine
=========================================

- in the editor: Compile->Experimental/PauWare
- it will generate a new folder in C:\Users\your-login\AppData\Local\Temp\ThingML_temp\Dome_demo or in a similar temp folder if you are using Linux or Mac OS.
- cd into this generated folder, then execute:
	$ mvn clean package exec:java -Dexec.mainClass="org.thingml.generated.MainPauWareDome_demo"
- you should see something like:

Tests run: 0, Failures: 0, Errors: 0, Skipped: 0

[INFO]
[INFO] --- maven-jar-plugin:2.3.2:jar (default-jar) @ Dome_demo ---
[INFO] Building jar: C:\Users\your-login\AppData\Local\Temp\ThingML_temp\Dome_demo\ta
rget\Dome_demo-1.0-SNAPSHOT.jar
[INFO]
[INFO] >>> exec-maven-plugin:1.2.1:java (default-cli) @ Dome_demo >>>
[INFO]
[INFO] <<< exec-maven-plugin:1.2.1:java (default-cli) @ Dome_demo <<<
[INFO]
[INFO] --- exec-maven-plugin:1.2.1:java (default-cli) @ Dome_demo ---
Waiting...
done
        {INFO}behavior((ManageTrips))

Waiting...
done
        {INFO}behavior((((((((ManagePickUpPoints))))))))


- in a web browser, go to: localhost:8082
- it open a web page where you can actually interact with the 

2. Compiling to FP7 DiVA models@runtime engine
==============================================

- remove the "Dome_demo" folder from your temp folder
- in the editor, Compile->Java/Swing, Compile->Scala/SMaC (please close the pop-up window/terminal), Compile->Java/Kevoree
- cd into this generated folder, then execute:
	$ mvn clean install
- it should produce something like:

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 32.861s
[INFO] Finished at: Wed Nov 21 15:44:51 CET 2012
[INFO] Final Memory: 35M/451M
[INFO] ------------------------------------------------------------------------

- you can now run KevoreeEditorSnapshot.jar
- Model->Load CoreLibrary->JaveSE
- Model->Load Library->browse to C:\Users\your-login\AppData\Local\Temp\ThingML_temp\Dome_demo\target\Dome_demo-1.0-SNAPSHOT
- You can now create a configuration with:
  - a JavaSE node called "node0"
  - a group (green icons) of type NanoRESTGroup, managing node0
  - a client component
  - a server component
  - connect the client's ports to the server's port using defMSG channels
- you can now run KevoreeRuntimeSnapshotGUI.jar, and wait a few second for proper initialization
- in the graphical editor, select node0 and "push"
- it should deploy the configuration
- in the graphical editor, update the configuration (e.g., by removing the server and replacing it with the mediator + alternative server)
- push this new configuration


