# FROM openjdk:17-jdk

# COPY target/taks.management.ai.jar .

# EXPOSE 8282

# ENTRYPOINT [ "java", "-jar", "taks.management.ai.jar" ]

FROM eclipse-temurin:17-jdk-focal
 
WORKDIR /app
 
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
 
COPY src ./src
 
CMD ["./mvnw", "spring-boot:run"]