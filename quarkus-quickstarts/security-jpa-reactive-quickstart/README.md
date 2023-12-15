Quarkus - Security Jakarta Persistence - Reactive
========================

This guide demonstrates how your Quarkus application can use a database, Hibernate Reactive 
and Quarkus Security Jakarta Persistence Reactive extension to store your user identities.

## Start the database

You need a database to store the user identities/roles. Here, we are using [PostgreSQL](https://www.postgresql.org).
To ease the setup, we have provided a `docker-compose.yml` file which start a PostgreSQL container, bind the network ports
and finally creates the users and their credentials by importing the `import.sql` file.

The database can be started using:
 ```bash
 docker-compose up
 ```  

Once the database is up you can start your Quarkus application.

Note you do not need to start the database when running your application in dev mode or testing. It will be started automatically as a Dev Service.

## Start the application

The application can be started using: 

```bash
mvn compile quarkus:dev
```  

## Test the application

### From  the CLI
The application exposes 4 endpoints:

* `/api/public`
* `/api/admin`
* `/api/users/me`
* `/api/users`

You can try these endpoints with an http client (`curl`, `HTTPie`, etc).
Here you have some examples to check the security configuration:

```bash
curl -i -X POST -u admin:admin -d user http://localhost:8080/api/users # create 'user'
curl -i -X GET http://localhost:8080/api/public  # 'public'
curl -i -X GET http://localhost:8080/api/admin  # unauthorized
curl -i -X GET -u admin:admin http://localhost:8080/api/admin # 'admin'
curl -i -X GET http://localhost:8080/api/users/me # 'unauthorized'
curl -i -X GET -u user:user http://localhost:8080/api/users/me # 'user'
```

_NOTE:_ Stop the database using: `docker-compose down; docker-compose rm`

### Integration testing

We have provided integration tests based on [Dev Services for PostgreSQL](https://quarkus.io/guides/dev-services#databases) to verify the security configuration in JVM and native modes. The test and dev modes containers will be launched automatically because all the PostgreSQL configuration properties are only enabled in production (`prod`) mode.


The test can be executed using: 

```bash
# JVM mode
mvn test

# Native mode
mvn verify -Dnative
```

## Running in native

You can compile the application into a native binary using:

`mvn clean package -Dnative`

_Note: You need to have a proper GraalVM configuration to build a native binary._

and run with:

`./target/security-jpa-reactive-quickstart-1.0.0-SNAPSHOT-runner` 

_NOTE:_ Don't forget to start the database.
