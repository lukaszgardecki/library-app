FROM eclipse-temurin:24-jdk
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
ADD target/api-gateway.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]