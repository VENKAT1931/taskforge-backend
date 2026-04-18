# Use Java 17 image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy project files
COPY . .
RUN rm -rf target

# Give permission to mvnw
RUN chmod +x mvnw



# Build the project
RUN ./mvnw clean package -DskipTests
EXPOSE 8080
# Run the app
CMD ["java", "-jar", "target/taskforge-0.0.1-SNAPSHOT.jar"]