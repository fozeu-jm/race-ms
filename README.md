
# PMU RACE MICRO SERVICE

As part of the modernisation of the PMU Information System towards an event-based architecture, it was required to develop a new microservice responsible for the life cycle of a race and its starters.



## Installation

To install this project you will need a MYSQL database, kafka and maven.
In application.yml in Framework module you can set the data source properties like so :

```bash
  spring:
  datasource:
    url: jdbc:mysql://localhost:yourSqlPort/databaseName?useSSL=false&serverTimezone=UTC
    username: yourUserName
    password: yourPassword
```
For Kafka, you will add some properties in the same application.yml file like so :

```bash
  kafka:
    bootstrap-servers: localhost:9092
    topic: your-topic
```
After that do a clean install with maven using : 
```bash
  mvn clean install
```
Then run the project.

    
## Architecture
This project was build using the clean architecture as described by Robert Martin. Following this architectural design the project is devided into four main modules :
* Domain Module : It contains all the business entities and their factories
* Usecase Module : This module contains all the use cases implemented in the application. Only our pure business logic is found in this first two modules with no external dependencies to details like databases. We have input boundaries which helps external layers to communicate with our use cases.
* Adapters Module  : This module contains our adapters, controllers, repositories and configuration
* Framework: This module basically contains our main application that specifies the framework and different drivers used for the project.
## Go Further...
To go further in improving this project we cas : 
* Add integration tests to tests all the different components of the app 
* Dockerize the app with the help of Docker and docker-compose so we can bring up the app seamlessly indepent of the environnement 
* Deploy the application to a public cloud service like Amazon using ECS. 
