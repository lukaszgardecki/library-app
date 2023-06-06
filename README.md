# Library-app

A library management system that allows you to store, retrieve, update and delete books' and users' data. The users can make reservations for books and than borrow them.

## Technologies and libraries
Project is created with:
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html)
- [Spring Boot 6](https://spring.io/projects/spring-boot)
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
3. Go to the project directory:
```bash
cd library-app
```
4. Build a project:
```bash
./mvnw install
```
5. Run all containers:
```bash
docker-compose up -d
```
6. Please be patient. It may take a while.

## API Endpoints

### Swagger
- Swagger is available at `http://localhost:8080/swagger-ui/index.html`.

### Retrieve all books on the platform:
- Request: `GET` `http://localhost:8080/api/v1/books`
- Access: *ALL*
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*

### Retrieve a single book:
- Request: `GET` `http://localhost:8080/api/v1/books/{id}`
- Parameters: `{id}` - id of item to fetch
- Access: *ALL*
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Create a new book:
- Request: `POST` `http://localhost:8080/api/v1/books`
- Request body example:
  ```
  { 
     "title": "Example title",
     "author": "Johny Bravo",
     "publisher": "John Smith",
     "release_year": 2023,
     "pages": 123,
     "isbn": "123456789-X"
  }
  ```
- Access: *ADMIN*
- Server responses:
  - **201 Created** - *request was successful, the resource is successfully created and return the newly created resource as JSON* 
  - **401 Unauthorized** - *the user is not authenticated*
  - **403 Forbidden** - *the request is not allowed, e.g., the user is not allowed to create a book*

### Edit a single book:
- Request: `PUT` `http://localhost:8080/api/v1/books`
- Request body example:
  ```
  { 
     "id": 25,
     "title": "Example title",
     "author": "Johny Bravo",
     "publisher": "John Smith",
     "release_year": 2023,
     "pages": 123,
     "isbn": "123456789-X"
  }
  ```
- Access: *ADMIN*
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **401 Unauthorized** - *the user is not authenticated*
  - **403 Forbidden** - *the request is not allowed, e.g., the user is not allowed to update a book*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Edit the details of a single book:
- Request: `PATCH` `http://localhost:8080/api/v1/books/{id}`
- Parameters: `{id}` - id of item to update
- Request body template:
  ```
  { 
     "id": 25,
     "title": "Example title",
     "author": "Johny Bravo",
     "publisher": "John Smith",
     "release_year": 2023,
     "pages": 123,
     "isbn": "123456789-X"
  }
  ```
- Request body example:
  ```
  { 
     "pages": 123,
     "isbn": "123456789-X"
  }
  ```
- Access: *ADMIN*
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **400 Bad Request** - *a required attribute of the API request is missing*
  - **401 Unauthorized** - *the user is not authenticated*
  - **403 Forbidden** - *the request is not allowed, e.g., the user is not allowed to update a book*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Delete a single book:
- Request: `DELETE` `http://localhost:8080/api/v1/books/{id}`
- Parameters: `{id}` - id of item to delete
- Access: *ADMIN*
- Server responses:
  - **204 No Content** - *request was successful, the resource is successfully deleted*
  - **401 Unauthorized** - *the user is not authenticated*
  - **403 Forbidden** - *the request is not allowed, e.g., the user is not allowed to delete a book*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*
  - **406 Not Acceptable** - *a resource could not be accessed, a books is not returned*

### Retrieve all users on the platform:
- Request: `GET` `http://localhost:8080/api/v1/users`
- Access: *ADMIN*
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **401 Unauthorized** - *the user is not authenticated*
  - **403 Forbidden** - *the request is not allowed, e.g., the user is not allowed to retrieve all users' data*

### Retrieve a single user:
- Request: `GET` `http://localhost:8080/api/v1/users/{id}`
- Parameters: `{id}` - id of user to fetch
- Access: *ADMIN, USER*
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON. User is allowed to retrieve their data only*
  - **401 Unauthorized** - *the user is not authenticated*
  - **403 Forbidden** - *the request is not allowed, e.g., the user is not allowed to retrieve another user's data*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Edit the details of a single user:
- Request: `PATCH` `http://localhost:8080/api/v1/users/{id}`
- Parameters: `{id}` - id of user to update
- Request body template:
  ```
  { 
     "firstName": "John",
     "lastName": "Smith",
     "email": "user@example.com",
     "password": "easypass",
     "cardNumber": "00000012",
     "role": "USER"
  }
  ```
- Request body example:
  ```
  { 
     "firstName": "John",
     "lastName": "Smith",
     "password": "easypass"
  }
  ```
- Access: *ADMIN, USER*
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON. User is allowed to update their data only*
  - **400 Bad Request** - *a required attribute of the API request is missing*
  - **401 Unauthorized** - *the user is not authenticated*
  - **403 Forbidden** - *the request is not allowed, e.g., the user is not allowed to change another user's data*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Delete a single user:
- Request: `DELETE` `http://localhost:8080/api/v1/users/{id}`
- Parameters: `{id}` - id of user to delete
- Access: *ADMIN, USER*
- Server responses:
  - **204 No Content** - *request was successful, the resource is successfully deleted. User is allowed to delete their account only*
  - **401 Unauthorized** - *the user is not authenticated*
  - **403 Forbidden** - *the request is not allowed, e.g., the user is not allowed to change another user's data*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*
  - **406 Not Acceptable** - *a resource could not be accessed, user's books are not returned*

### Sign up a new user account:
- Request: `POST` `http://localhost:8080/api/v1/register`
- Request body example:
  ```
  { 
     "firstName": "John",
     "lastName": "Smith",
     "email": "user@example.com",
     "password": "hardpass"
  }
  ```
- Access: *ALL*
- Server responses:
  - **201 Created** - *request was successful, the resource is successfully created and return the newly created resource as JSON*
  - **406 Not Acceptable** - *a resource could not be accessed, the user with this email already exists*

### Log in an existing user account:
- Request: `POST` `http://localhost:8080/api/v1/login`
- Request body example:
  ```
  { 
     "username": "user@example.com",
     "password": "hardpass"
  }
  ```
- Access: *ALL*
- Server responses:
  - **200 OK** - *request was successful*
  - **404 Not Found** - *a resource could not be accessed, e.g., a username or password for a resource could not be found*

### Retrieve all reservations on the platform:
- Request: `GET` `http://localhost:8080/api/v1/reservations`
- Access: *ADMIN*
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON. User is allowed to retrieve their data only*
  - **401 Unauthorized** - *the user is not authenticated*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Retrieve all user's reservations:
- Request: `GET` `http://localhost:8080/api/v1/reservations?userId={id}`
- Parameters: `{id}` - id of a user
- Access: *ADMIN, USER*
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **401 Unauthorized** - *the user is not authenticated*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Retrieve a single reservation:
- Request: `GET` `http://localhost:8080/api/v1/reservations/{id}`
- Parameters: `{id}` - id of reservation to fetch
- Access: *ADMIN, USER*
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON. User is allowed to retrieve their data only*
  - **401 Unauthorized** - *the user is not authenticated* 
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Create a new reservation:
- Request: `POST` `http://localhost:8080/api/v1/reservations`
- Request body example:
  ```
  { 
     "userId": 25,
     "bookId": 123
  }
  ```
- Access: *ADMIN, USER*
- Server responses:
  - **201 Created** - *request was successful, the resource is successfully created and return the newly created resource as JSON*
  - **401 Unauthorized** - *the user is not authenticated*
  - **403 Forbidden** - *the request is not allowed, e.g., the user is not allowed to make a reservation for another ones*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*
  - **406 Not Acceptable** - *a resource could not be accessed, the book is not available*

### Delete a single reservation:
- Request: `DELETE` `http://localhost:8080/api/v1/reservations/{id}`
- Parameters: `{id}` - id of reservation to delete
- Access: *ADMIN, USER*
- Server responses:
  - **204 No Content** - *request was successful, the resource is successfully deleted. User is allowed to delete their data only*
  - **401 Unauthorized** - *the user is not authenticated*
  - **403 Forbidden** - *the request is not allowed, e.g., the user is not allowed to delete reservations those are not their*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Retrieve all checkouts on the platform:
- Request: `GET` `http://localhost:8080/api/v1/checkouts`
- Access: *ADMIN*
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **401 Unauthorized** - *the user is not authenticated*
  - **403 Forbidden** - *the request is not allowed, e.g., the user is not allowed to retrieve a data*

### Retrieve all user's checkouts:
- Request: `GET` `http://localhost:8080/api/v1/checkouts?userId={id}`
- Parameters: `{id}` - id of a user
- Access: *ADMIN, USER*
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **401 Unauthorized** - *the user is not authenticated*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Create a new checkout:
- Request: `POST` `http://localhost:8080/api/v1/checkouts`
- Request body example:
  ```
  { 
     "userId": 25,
     "bookId": 123
  }
  ```
- Access: *ADMIN*
- Server responses:
    - **201 Created** - *request was successful, the resource is successfully created and return the newly created resource as JSON*
  - **401 Unauthorized** - *the user is not authenticated*
  - **403 Forbidden** - *the request is not allowed, e.g., the user is not allowed to create a data*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource or book's reservation could not be found*
  - **406 Not Acceptable** - *a resource could not be accessed, the book is already reserved*

### Return a book:
- Request: `PATCH` `http://localhost:8080/api/v1/checkouts/return?bookId={id}`
- Parameters: `{id}` - id of the book to return
- Access: *ADMIN*
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **401 Unauthorized** - *the user is not authenticated*
  - **403 Forbidden** - *the request is not allowed, e.g., the user is not allowed to return a book*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*
  - **406 Not Acceptable** - *a resource could not be accessed, the book is already returned*

## Status
Project is in progress with some active features and another improvements to make.
 