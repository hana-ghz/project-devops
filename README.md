# Devops project
## Description of the project

This project is created to simulate the process of a **CI/CD pipeline** using **Jenkins**.

It is a simple CRUD applicaton written in SpringBoot, the focus is made on the process that our application will go through after every push/merge to the master application.
Our application is a simple school library every student has a student identifcation card, can borrow a book and return it.
Every book has an author and at some point in time it is borrowed by only one student.

The process of our pipeline will follow these steps : 
* Increment application version from the previous release
* Run the unit tests
* Scan the code using the sonarQube
* Build the JAR artifcat and push it to a local Nexus repository.
* Build the docker image out of the jar and push it to dockerhub.




## Test the application locally
* Install the required dependencies.
* Launch mysql database in a docker container using the shell script db.sh that runs a mysql container (MUST HAVE: Docker)
* Once the database container is launched, we can run our application from the IDE of choice
* To run the application unit tests:
```
mvn test
```

## Pipeline steps
For that purpose, all needed tools are being run using their docker images: Jenkins, SonarQube and Nexus

### Code analysis with sonarQube

In this step, I run sonarQube container with postgres and pgAdmin containers for the database it uses.
Required volumes are attached in order to save the sonarQube data, logs and extensions.


### Pushing JAR to Nexus repository

Prequisites of the step:
(Nexus container should be running)
* The nexus plugin and configuration added to POM.xml file.
* The nexus user credentials are added to Jenkins container.

Run ```mvn clean deploy```
  
### Building docker image and push it to dockerhub

Prequisites of the step:
* Build a docker image using Dockerfile.
* Configure the appropriate credentials for Dockerhub account in Jenkins.
* Push the docker image to the repo.
