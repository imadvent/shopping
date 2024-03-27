#FROM openjdk:17-jdk-alpine
#ARG JAR_FILE=target/*.jar
#COPY ./target/spring-shopping-docker.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

FROM openjdk:17
ADD target/spring-shopping-docker.jar spring-shopping-docker.jar
ENTRYPOINT ["java","-jar","/spring-shopping-docker.jar"]