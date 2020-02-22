# ensibs-bakery
Bakery web-services for the ENSIBS SOA course

## Requirements

- Maven
- Docker with a Tomcat image

## Documentation

You can generate the Javadoc by entering:

    mvn javadoc:javadoc

It will be located in the `bakery-server/target/site/apidocs/fr` folder.

## Installation

First, compile the WAR by entering:

    mvn clean package

Then move to the `bakery-server/target` folder:

    cd bakery-server/target

To launch the web-services in the Docker container, enter:

    docker run --rm --name bakery-tomcat -v bakery-server-1.war:/usr/local/tomcat/webapps/bakery-server-1.war -it -p 8080:8080 tomcat:9.0.12-jre10-slim

Finally, you can access the web-services at the following URLs:

    http://localhost:8080/???????????
    http://localhost:8080/???????????
    http://localhost:8080/???????????