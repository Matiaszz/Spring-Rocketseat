
FROM ubuntu:latest AS build

RUN apt-get update && \
    apt-get install -y openjdk-21 maven && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

COPY . .

RUN mvn clean install

FROM openjdk:21-jdk-slim
EXPOSE 8080

COPY --from=build /target/todolist-1.0.0.jar app.jar

# Set the entry point for the container
ENTRYPOINT ["java", "-jar", "app.jar"]