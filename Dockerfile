FROM ubuntu:latest AS build

RUN apt-get update && \
    apt-get install -y wget software-properties-common

RUN add-apt-repository ppa:openjdk-r/ppa && apt-get update

RUN apt-get install -y openjdk-21

COPY . .

RUN apt-get install -y maven

RUN mvn clean install

FROM openjdk:21-jdk-slim

EXPOSE 8080

COPY --from=build /target/todolist-1.0.0.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]
