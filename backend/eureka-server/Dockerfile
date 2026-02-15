FROM eclipse-temurin:24-jdk
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
ADD target/eureka-server.jar .
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "eureka-server.jar"]