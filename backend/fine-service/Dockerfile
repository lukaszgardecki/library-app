FROM openjdk:24-jdk
ADD target/fine-service.jar .
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "fine-service.jar"]