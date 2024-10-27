# FROM openjdk:17-jdk

# COPY target/taks.management.ai.jar .

# EXPOSE 8282

# ENTRYPOINT [ "java", "-jar", "taks.management.ai.jar" ]

# FROM eclipse-temurin:17-jdk-focal
 
# WORKDIR /app
 
# COPY .mvn/ .mvn
# COPY mvnw pom.xml ./
# RUN ./mvnw dependency:go-offline
 
# COPY src ./src

 
# CMD ["./mvnw", "spring-boot:run"]

FROM eclipse-temurin:17-jdk-focal

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper and pom.xml files to cache dependencies
COPY .mvn/ .mvn
COPY mvnw  ./
RUN chmod +x mvnw
COPY pom.xml  ./

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy the application source code to the container
COPY src ./src

# Package the application
RUN ./mvnw clean package -DskipTests

# Expose the default Spring Boot port
EXPOSE 8080
CMD ["java", "-jar", "./target/taks.management.ai-0.0.1-SNAPSHOT.jar"]