#Start with a base image containing Java runtime
FROM openjdk:11-slim as build

#Add the application jar to the container
COPY target/auth0-security-0.0.1-SNAPSHOT.jar app.jar

ENV AUTH0_AUDIENCE $AUTH0_AUDIENCE
ENV AUTH0_DOMAIIN $AUTH0_DOMAIN

#Execute the application
ENTRYPOINT ["java","-jar","/app.jar"]
