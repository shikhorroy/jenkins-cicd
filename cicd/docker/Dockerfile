# Use a base image with Amazon Corretto JDK 17 installed
FROM amazoncorretto:17-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY build/libs/webflux-demo-0.0.1.jar app.jar

# Expose the port that the application will listen on
EXPOSE 8081

# Set the entry point to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
