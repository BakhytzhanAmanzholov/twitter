FROM maven:3.6.0-jdk-8-slim AS build
COPY src /home/MQ-Moderation/src
COPY pom.xml /home/MQ-Moderation/
USER root
RUN --mount=type=cache,target=/root/.m2 mvn -DskipTests=true -f /home/MQ-Moderation/pom.xml clean package

FROM openjdk:11-jre-slim
COPY --from=build /home/MQ-Moderation/target/mqModeration.jar /usr/local/lib/mqModeration.jar
EXPOSE 8383
ENTRYPOINT ["java", "-jar", "/usr/local/lib/mqModeration.jar"]