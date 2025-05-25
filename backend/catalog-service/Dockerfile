FROM openjdk:24-jdk
ADD target/catalog-service.jar .
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "catalog-service.jar"]