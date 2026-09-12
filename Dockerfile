#Technically, Docker builds two stages, but only one final application image is created/tagged:

#Build stage: temporary/intermediate Maven + Java image layer, usually retained only in Docker’s build cache.
#Final stage: the Nginx image containing index.html; this is the image you run, tag, and push.

# Stage 1: Build the Java application
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

# Tests already run in the GitHub Actions test stage
RUN mvn clean package -DskipTests

# Run the application to generate target/index.html
RUN java -jar target/Java-CICD-with-SonarQube-Docker-and-AWS-1.0-SNAPSHOT.jar


# Stage 2: Serve the generated HTML
FROM nginx:alpine

COPY --from=build /app/target/index.html /usr/share/nginx/html/index.html

EXPOSE 80
