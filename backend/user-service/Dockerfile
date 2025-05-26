FROM openjdk:24-jdk
ADD target/user-service.jar .
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "user-service.jar"]