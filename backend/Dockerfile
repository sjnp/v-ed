# Base Image
FROM openjdk:11-jre-slim
RUN useradd -m spring
USER spring

VOLUME /tmp

ARG JAR_FILE=target/backend-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} backend.jar

ENTRYPOINT ["java", "-jar", "/backend.jar"]