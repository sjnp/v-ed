# Base Image
FROM openjdk:11-jdk-alpine

VOLUME /tmp

EXPOSE 8443

ARG JAR_FILE=target/backend-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} backend.jar

ENTRYPOINT ["java", "-jar", "/backend.jar"]