FROM openjdk:24-jdk
ADD target/auth-service.jar .
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "auth-service.jar"]