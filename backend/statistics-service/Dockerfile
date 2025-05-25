FROM openjdk:24-jdk
ADD target/statistics-service.jar .
EXPOSE 8091
ENTRYPOINT ["java", "-jar", "statistics-service.jar"]