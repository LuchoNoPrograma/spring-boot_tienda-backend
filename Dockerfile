#FROM openjdk:17-jdk-alpine
#VOLUME /tmp
#EXPOSE 9093
#ARG JAR_FILE=target/TiendaDBII-0.0.1-SNAPSHOT.jar
#ADD ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

#
# Build stage
#
FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY --from=build /target/TiendaDBII-0.0.1-SNAPSHOT.jar app.jar
#EXPOSE 9093
ENTRYPOINT ["java","-jar","app.jar"]