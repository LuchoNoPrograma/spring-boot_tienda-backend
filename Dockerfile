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
EXPOSE 9093
ENTRYPOINT ["java","-jar","app.jar"]

# Usar una imagen base con JDK 11 y Maven
#FROM maven:3.8.3-openjdk-17 AS build
#
## Establecer un directorio de trabajo
#WORKDIR /app
#
## Copiar archivos de tu proyecto al directorio de trabajo
#COPY . /app
#
## Ejecutar Maven para construir el proyecto
#RUN mvn clean package -DskipTests
#
## Crear una nueva imagen basada en OpenJDK 11
#FROM openjdk:17-jdk-slim
#
## Exponer el puerto que utilizará la aplicación
#EXPOSE 9093
#
## Copiar el archivo JAR construido desde la etapa anterior8089
#COPY --from=build /app/target/TiendaDBII-0.0.1-SNAPSHOT.jar /app/TiendaDBII-0.0.1-SNAPSHOT.jar
#
## Establecer el punto de entrada para ejecutar la aplicación
#ENTRYPOINT ["java", "-jar", "/app/TiendaDBII-0.0.1-SNAPSHOT.jar"]