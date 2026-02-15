FROM eclipse-temurin:24-jdk
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
ADD target/config-server.jar .
EXPOSE 8079
ENTRYPOINT ["java", "-jar", "config-server.jar"]