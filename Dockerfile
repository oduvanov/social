# the base image
FROM openjdk:23

# the JAR file path
ARG JAR_FILE=target/*.jar

# Copy the JAR file from the build context into the Docker image
COPY ${JAR_FILE} social-1.0.0-RELEASE.jar

CMD apt-get update -y

# Set the default command to run the Java application
ENTRYPOINT ["java", "-Xmx2048M", "-jar", "/social-1.0.0-RELEASE.jar"]
