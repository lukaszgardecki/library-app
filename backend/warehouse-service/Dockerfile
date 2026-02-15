FROM eclipse-temurin:24-jdk
ADD target/warehouse-service.jar .
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "warehouse-service.jar"]