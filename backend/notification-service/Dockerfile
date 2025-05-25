FROM openjdk:24-jdk
ADD target/notification-service.jar .
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "notification-service.jar"]