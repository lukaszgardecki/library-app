FROM openjdk:24-jdk
ADD target/loan-service.jar .
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "loan-service.jar"]