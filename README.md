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

To launch , enter:

    docker ?????

Finally, you can access the web-services at the following URLs:

    http://localhost:8080/???????????
    http://localhost:8080/???????????
    http://localhost:8080/???????????