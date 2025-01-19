FROM ubuntu:latest AS build

RUN apt-get update
RUN apit-get install openjdk-21 -y

COPY . .

RUN apt-get install maven -y

# Install dependencies
RUN mvn clean install

FROM openjdk:21-jdk-slim
EXPOSE 8080

COPY --from=build /target/todolist-1.0.0.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]