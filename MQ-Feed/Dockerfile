FROM maven:3.6.0-jdk-8-slim AS build
COPY src /home/MQ-Feed/src
COPY pom.xml /home/MQ-Feed/
USER root
RUN --mount=type=cache,target=/root/.m2 mvn -DskipTests=true -f /home/MQ-Feed/pom.xml clean package

FROM openjdk:11-jre-slim
COPY --from=build /home/MQ-Feed/target/mqFeed.jar /usr/local/lib/mqFeed.jar
EXPOSE 8282
ENTRYPOINT ["java", "-jar", "/usr/local/lib/mqFeed.jar"]