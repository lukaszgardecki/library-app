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
- [BCrypt](https://en.wikipedia.org/wiki/Bcrypt)
- [Swagger](https://swagger.io/specification/)
- [Lombok](https://projectlombok.org/)

## API Endpoints

### Swagger
- Swagger is available at `/swagger-ui/index.html` endpoint.

### Retrieve all books on the platform:
- Request: `GET` `/api/v1/books`
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **204 No Content** - *request was successful, the resource is empty*

### Retrieve a single book:
- Request: `GET` `/api/v1/books/{id}`
- Parameters: `{id}` - id of item to fetch
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Create a new book:
- Request: `POST` `/api/v1/books`
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
- Server responses:
  - **201 Created** - *request was successful, the resource is successfully created and return the newly created resource as JSON*

### Edit a single book:
- Request: `PUT` `/api/v1/books`
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
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Edit the details of a single book:
- Request: `PATCH` `/api/v1/books/{id}`
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
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **400 Bad Request** - *a required attribute of the API request is missing*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Delete a single book:
- Request: `DELETE` `/api/v1/books/{id}`
- Parameters: `{id}` - id of item to delete
- Server responses:
  - **204 No Content** - *request was successful, the resource is successfully deleted*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Retrieve all users on the platform:
- Request: `GET` `/api/v1/users`
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **204 No Content** - *request was successful, the resource is empty*

### Retrieve a single user:
- Request: `GET` `/api/v1/users/{id}`
- Parameters: `{id}` - id of user to fetch
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Edit the details of a single user:
- Request: `PATCH` `/api/v1/users/{id}`
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
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **400 Bad Request** - *a required attribute of the API request is missing*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Delete a single user:
- Request: `DELETE` `/api/v1/users/{id}`
- Parameters: `{id}` - id of user to delete
- Server responses:
  - **204 No Content** - *request was successful, the resource is successfully deleted*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Sign up a new user account:
- Request: `POST` `/api/v1/register`
- Request body example:
  ```
  { 
     "firstName": "John",
     "lastName": "Smith",
     "email": "user@example.com",
     "password": "hardpass"
  }
  ```
- Server responses:
  - **201 Created** - *request was successful, the resource is successfully created and return the newly created resource as JSON*
  - **406 Not Acceptable** - *a resource could not be accessed, the user with this email already exists*

### Log in an existing user account:
- Request: `POST` `/api/v1/login`
- Request body example:
  ```
  { 
     "username": "user@example.com",
     "password": "hardpass"
  }
  ```
- Server responses:
  - **200 OK** - *request was successful*
  - **404 Not Found** - *a resource could not be accessed, e.g., a username or password for a resource could not be found*

### Log out an existing user account:
- Request: `POST` `/api/v1/logout`
- Server responses:
  - **200 OK** - *request was successful*

### Retrieve all reservations on the platform:
- Request: `GET` `/api/v1/reservations`
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **204 No Content** - *request was successful, the resource is empty*

### Retrieve all user's reservations:
- Request: `GET` `/api/v1/reservations?userId={id}`
- Parameters: `{id}` - id of a user
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **204 No Content** - *request was successful, the resource is empty*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Retrieve a single reservation:
- Request: `GET` `/api/v1/reservations/{id}`
- Parameters: `{id}` - id of reservation to fetch
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Create a new reservation:
- Request: `POST` `/api/v1/reservations`
- Request body example:
  ```
  { 
     "userId": 25,
     "bookId": 123
  }
  ```
- Server responses:
  - **201 Created** - *request was successful, the resource is successfully created and return the newly created resource as JSON*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*
  - **406 Not Acceptable** - *a resource could not be accessed, the book is already reserved*

### Delete a single reservation:
- Request: `DELETE` `/api/v1/reservations/{id}`
- Parameters: `{id}` - id of reservation to delete
- Server responses:
  - **204 No Content** - *request was successful, the resource is successfully deleted*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Retrieve all checkouts on the platform:
- Request: `GET` `/api/v1/checkouts`
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **204 No Content** - *request was successful, the resource is empty*

### Retrieve all user's checkouts:
- Request: `GET` `/api/v1/checkouts?userId={id}`
- Parameters: `{id}` - id of a user
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **204 No Content** - *request was successful, the resource is empty*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Create a new checkout:
- Request: `POST` `/api/v1/checkouts`
- Request body example:
  ```
  { 
     "userId": 25,
     "bookId": 123
  }
  ```
- Server responses:
  - **201 Created** - *request was successful, the resource is successfully created and return the newly created resource as JSON*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource or book's reservation could not be found*
  - **406 Not Acceptable** - *a resource could not be accessed, the book is already reserved*

### Return a book:
- Request: `PATCH` `/api/v1/checkouts/return?bookId={id}`
- Parameters: `{id}` - id of the book to return
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*
  - **406 Not Acceptable** - *a resource could not be accessed, the book is already returned*

## Status
Project is in progress with some active features and another improvements to make.
 