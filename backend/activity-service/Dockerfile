FROM openjdk:24-jdk
ADD target/activity-service.jar .
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "activity-service.jar"]