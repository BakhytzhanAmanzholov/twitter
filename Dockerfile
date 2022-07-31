FROM maven:3.6.0-jdk-8-slim AS build
COPY src /home/twitter/src
COPY pom.xml /home/twitter/
USER root
RUN --mount=type=cache,target=/root/.m2 mvn -DskipTests=true -f /home/twitter/pom.xml clean package

FROM openjdk:11-jre-slim
COPY --from=build /home/twitter/target/app.jar /usr/local/lib/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/app.jar"]