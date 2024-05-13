# Library-app

Library-app is a library management system - REST API that allows users to make the operations like:
- creating their personal account with an individual library card. Each newly created library card is assigned a barcode, which allows for easy and quick identification.
- fetching information about the books and their copies
- making and canceling a reservation for copies of books
- borrowing, extending and returning books
- updating their personal data

Additionally, some operations can only be performed by members with administrator privileges:
- creating, updating and deleting books and their copies. Each newly created copy of the book is assigned a barcode, which allows for easy and quick identification.
- assigning books to racks, which allows better organization and faster locating of books in the library
- updating and deleting other members
- updating and managing members' data, e.g. library cards, fees etc.
- managing book reservations and loans
- managing payments

Operations that require authentication are secured by the JSON Web Token. Executing a protected operations requires adding an 'Authorization' HTTP header with a generated bearer token to the request. If token is expired, the member must log in again to generate a new one.

System is designed to charge members for late return of books, so remember to return them on time!

## Technologies and libraries
Project is created with:
- [Java 18](https://www.oracle.com/java/technologies/javase/jdk18-archive-downloads.html)
- [Spring Boot 3.1](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Spring HATEOAS](https://docs.spring.io/spring-hateoas/docs/current/reference/html/)
- [Spring Security](https://spring.io/projects/spring-security)
- [Hibernate](https://hibernate.org/)
- [Maven](https://maven.apache.org/)
- [Liquibase](https://www.liquibase.org/)
- [H2](https://www.h2database.com/html/main.html)
- [MySQL](https://www.mysql.com/)
- [BCrypt](https://en.wikipedia.org/wiki/Bcrypt)
- [Swagger](https://swagger.io/specification/)
- [Docker](https://www.docker.com/)
- [Lombok](https://projectlombok.org/)

## Run with Docker
1. [Install Docker Compose](https://docs.docker.com/compose/install/)

2. Clone the project:
```bash
git clone https://github.com/lukaszgardecki/library-app.git
```
3. Go to the backend directory:
```bash
cd library-app/backend
```
4. Build a project:
```bash
./mvnw install
```
5. Go to the project main directory:
```bash
cd ..
```
6. Run all containers:
```bash
docker-compose up -d
```
7. Please be patient. It may take a while.

## API Endpoints
Documentation is available at [Wiki](https://github.com/lukaszgardecki/library-app/wiki).

## Swagger
Swagger is available at `http://localhost:8080/swagger-ui/index.html`.


## Status
Project is in progress with some active features and another improvements to make.
 