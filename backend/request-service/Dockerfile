FROM openjdk:24-jdk
ADD target/request-service.jar .
EXPOSE 8087
ENTRYPOINT ["java", "-jar", "request-service.jar"]