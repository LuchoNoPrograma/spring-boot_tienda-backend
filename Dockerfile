FROM openjdk:17-jdk-alpine
VOLUME /tmp
EXPOSE 9093
ARG JAR_FILE=target/TiendaDBII-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]