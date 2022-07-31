FROM maven:3.6.0-jdk-8-slim AS build
COPY src /home/MQ-Email/src
COPY pom.xml /home/MQ-Email/
USER root
RUN --mount=type=cache,target=/root/.m2 mvn -DskipTests=true -f /home/MQ-Email/pom.xml clean package

FROM openjdk:11-jre-slim
COPY --from=build /home/MQ-Email/target/mqEmail.jar /usr/local/lib/mqEmail.jar
EXPOSE 8181
ENTRYPOINT ["java", "-jar", "/usr/local/lib/mqEmail.jar"]