FROM ubuntu:latest AS build

RUN apt-get update && \
    apt-get install -y wget software-properties-common

RUN wget -q https://packages.openjdk.java.net/openjdk21/ubuntu/openjdk-21_21.0.5-1_amd64.deb && \
    dpkg -i openjdk-21_21.0.5-1_amd64.deb && \
    apt-get install -f -y  # To fix dependencies if needed

COPY . .

RUN apt-get install -y maven

RUN mvn clean install

FROM openjdk:21-jdk-slim

EXPOSE 8080

COPY --from=build /target/todolist-1.0.0.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]
