# Web crawler 

Basic web crawler [Spring Boot](http://projects.spring.io/spring-boot/)  app.

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Kotlin 1.2.70](kotlinlang.org)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.mukesh.webcrawler` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```
To crawl your website use this location 

```
http://localhost:8080/crawl?site=<SITE_NAME>
```
## Trade offs

- List all the links on given site and does not visit sub-domain links

- List all only the Images media types
 
- Does not lists any imports links

## TODO Improvement 

- Links available on site could be arranged more elegantly by following site depth
- Asynchronous calls could be used to visit all inner links