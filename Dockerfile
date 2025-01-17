FROM ubuntu:24.04 as build

RUN apt-get update
RUN apt-get install openjdk-21 

COPY . .

RUN apt-get install maven 

# Install dependencies
RUN mvn clean install

FROM openjdk:21-jdk-slim
EXPOSE 8080

COPY --from=build /target/todolist-1.0.0.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]