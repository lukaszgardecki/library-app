<h1 align="center">
  <img src="https://github.com/user-attachments/assets/32b4658e-617c-401c-a993-56f21160954f" alt="LibraryApp" width="400">
</h1>
<h4 align="center">Library Management System</h4>

<p align="center">
  <a href="#description">Description</a> •
  <a href="#technologies-and-libraries">Technologies and libraries</a> •
  <a href="#architecture">Architecture</a> •
  <a href="#run-with-docker">Run with Docker</a> •
  <a href="#about-the-project">About</a>
</p>

![obraz](https://github.com/user-attachments/assets/0d831e91-961b-4ddf-abe4-aa3bb7a10c21)


## Description

<p align="justify">
<b>LibraryApp</b> is a web application designed for <b>comprehensive library management</b>, developed using <code>Java</code> for the backend and <code>Angular</code> for the frontend. The system enables both <b>library staff</b> and <b>users</b> to efficiently access library resources and manage processes related to <b>borrowing</b> and <b>reserving documents</b>. Every user can freely <b>browse the library catalog</b> without the need to log in; however, placing a book order requires logging into a personal account. After successfully creating an account, the user receives a <b>library card</b>, which serves as their identification when visiting the library in person.
</p>
<p align="justify">
<b>Book orders</b> are placed directly from the user's profile and are then forwarded to the <b>storage department</b> for processing. There, staff members <b>prepare the requested items</b> and deliver them to the <b>pickup point</b>. Once the book is ready for collection, the user receives a <b>notification</b> and can retrieve the book from a library staff member.
</p>
<p align="justify">
The system enforces a <b>return deadline</b> for borrowed documents, as determined by the library. If this deadline is exceeded, <b>financial penalties</b> are applied, which must be settled before the user is allowed to borrow additional items. If a document is currently checked out by another user, it can be <b>reserved</b> – this creates a <b>waiting list</b>. In the event that a user has borrowed a book which is subsequently reserved by someone else, the loan <b>cannot be renewed</b>.
</p>
<p align="justify">
<b>LibraryApp</b> supports a <b>diverse role system</b> that allows for tailored access and functionalities depending on the needs of specific user groups, including both readers and staff. The primary role is the <b>standard user</b> – who can place orders, reserve documents, collect loans, and track the history of their borrowed items.
</p>
<p align="justify">
In addition, the system provides roles dedicated to <b>library staff</b>: <ul> <li><b>Administrator</b> – has full access to all system functions, managing users, catalog structure, permissions, and general application settings.</li> <li><b>Storage Staff Member</b> – responsible for processing orders – preparing requested books, moving them to pickup points, and updating their status in the system.</li> <li><b>Cashier</b> – oversees financial operations – handling fees for late returns and ensuring that users settle their penalties before borrowing additional materials.</li> </ul>
</p>


## Technologies and libraries

<p>Project is created with:</p>
<p align="center">
  <a href="https://www.oracle.com/java/technologies/downloads/#java24"><img src="https://img.shields.io/badge/Java-24-grey?style=for-the-badge&logo=openjdk&logoColor=white&labelColor=ED8B00" alt="Java 24"></a>
  <a href="https://angular.io/v19"><img src="https://img.shields.io/badge/Angular-19-grey?style=for-the-badge&logo=angular&logoColor=white&labelColor=DD0031" alt="Angular 19"></a>
  <a href="https://spring.io/projects/spring-boot"><img src="https://img.shields.io/badge/Spring_Boot-3.4.4-grey?style=for-the-badge&logo=spring-boot&logoColor=white&labelColor=6DB33F" alt="Spring Boot 3.4.4"></a>
  <a href="https://spring.io/projects/spring-security"><img src="https://img.shields.io/badge/Spring_Security-3.4.4-grey?style=for-the-badge&logo=spring-security&logoColor=white&labelColor=6DB33F" alt="Spring Security 3.4.4"></a>
  <a href="https://hibernate.org/"><img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white" alt="Hibernate"></a>
  <a href="https://maven.apache.org/"><img src="https://img.shields.io/badge/apache_maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Apache Maven"></a>
  <a href="https://www.mysql.com/"><img src="https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL Database"></a>
  <a href="https://www.h2database.com/html/main.html"><img src="https://img.shields.io/badge/H2%20Database-grey?style=for-the-badge&logo=database&logoColor=white" alt="H2 Database"></a>
  <a href="https://www.liquibase.org/"><img src="https://img.shields.io/badge/Liquibase-2962FF?style=for-the-badge&logo=liquibase&logoColor=white" alt="Liquibase"></a>
  <a href="https://kafka.apache.org/"><img src="https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apache-kafka&logoColor=white" alt="Kafka"></a>
  <a href="https://www.docker.com/"><img src="https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white" alt="Docker"></a>
  <a href="https://jwt.io/"><img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=json%20web%20tokens&logoColor=white" alt="JWT"></a>
  <a href="https://en.wikipedia.org/wiki/Bcrypt"><img src="https://img.shields.io/badge/BCrypt-grey?style=for-the-badge&logoColor=white" alt="BCrypt"></a>
  <a href="https://projectlombok.org/"><img src="https://img.shields.io/badge/Lombok-F7766F?style=for-the-badge&logo=lombok&logoColor=white" alt="Lombok"></a>
</p>

## Architecture
<p align="justify">
This project is designed for scalability and flexibility, leveraging a <b><i>microservices</i></b> approach. Each microservice acts as an independent unit, responsible for a specific set of business functionalities, which     simplifies development, deployment, and management.
</p>
<p align="justify">
Furthermore, each microservice is built following the principles of <b><i>Hexagonal Architecture (Ports & Adapters)</i></b>. This approach ensures a strong separation between the core business logic and external technologies (such as databases, APIs, or messaging systems). This design facilitates easier testing, allows for swapping out technological components, and maintains the cleanliness of the business code.
</p>



### Key Architectural Aspects:
* **Independent Deployment**: Each microservice can be deployed independently.
* **Separation of Concerns**: Clearly defined boundaries for business contexts.
* **Testability**: Easier testing of domain logic due to isolation from adapters.
* **Technological Flexibility**: Ability to change technologies (e.g., database) without impacting the business core.


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


## About the Project

<p align="justify">
  The project is in progress, featuring active development and upcoming enhancements. It is being developed for <i>educational purposes</i> to demonstrate various technologies and architectural patterns.
</p>
<p align="justify">
  The frontend of this application is built upon the open-source and free <a href="https://adminlte.io/"><i>AdminLTE</i></a> dashboard template, which provided a robust foundation for the user interface.
</p>
