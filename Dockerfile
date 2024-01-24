# Use the official OpenJDK 17 base image
FROM adoptopenjdk:17-jre-hotspot

# Set the working directory in the container
WORKDIR /app

# Copy the Spring Boot JAR file from the host system to the container
COPY target/your-spring-boot-app.jar /app/app.jar

# Run the Spring Boot application
CMD ["java", "-jar", "app.jar"]