FROM openjdk:24-jdk
ADD target/api-gateway.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]