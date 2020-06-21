FROM openjdk:8-jdk-alpine as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw install -DskipTests

FROM openjdk:8-jdk-alpine
ARG BUILD_TARGET=/workspace/app/target
COPY --from=build ${BUILD_TARGET}/*.jar ./stress-test.jar
ENTRYPOINT ["java","-jar","stress-test.jar"]