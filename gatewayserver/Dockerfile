FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
COPY src src

RUN chmod +x mvnw && ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8072

ENTRYPOINT ["java","-Dspring.config.import=optional:configserver:http://configserver:8071","-Dspring.cloud.config.uri=http://configserver:8071","-Deureka.client.serviceUrl.defaultZone=http://eurekaserver:8070/eureka","-Dspring.data.redis.host=host.docker.internal","-Dspring.data.redis.port=6379","-jar","app.jar"]