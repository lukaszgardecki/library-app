# Library-app

Library-app is a library management system - REST API that allows users to make the operations like:
- creating their personal account with an individual library card. Each newly created library card is assigned a barcode, which allows for easy and quick identification.
- fetching information about the books and their copies
- making and canceling a reservation for copies of books
- borrowing, extending and returning books
- updating their personal data

Additionally, some operations can only be performed by members with administrator privileges:
- creating, updating and deleting books and their copies. Each newly created copy of the book is assigned a barcode, which allows for easy and quick identification.
- updating and deleting other members
- updating and managing members' data, e.g. library cards, fees etc.
- managing book reservations and loans

Operations that require authentication are secured by the JSON Web Token. Executing a protected operations requires adding an 'Authorization' HTTP header with a generated bearer token to the request. If token is expired, the member must log in again to generate a new one.

System is designed to charge members for late return of books, so remember to return them on time!

## Technologies and libraries
Project is created with:
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html)
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

---

### Register a member:
- Request: `POST` `http://localhost:8080/api/v1/register`
- Access: ***ALL***
- Headers: `none`
- Path params: `none`
- Query params: `none`
- Request body:
  - body is required
  - not all fields are required (email and password are required)
  - template:
    ```
    { 
        "firstName": "John",
        "lastName": "Smith",
        "email": "member@example.com",
        "password": "easypass",
        "streetAddress": "Konopacka 3",
        "zipCode": "00-000",
        "city": "Warsaw",
        "state": "Mazowieckie",
        "country": "Poland",
        "phone": "123456789"
    }
    ```
- Server responses:
  - **200 OK** - *request was successful, member has been successfully created, token is returned as JSON*
  - **400 Bad Request** - *a required body is missing*
  - **403 Forbidden** - *a member with this email address already exists*

### Authenticate a member:
- Request: `POST` `http://localhost:8080/api/v1/authenticate`
- Access: ***ALL***
- Headers: `none`
- Path params: `none`
- Query params: `none`
- Request body:
  - body is required
  - template:
    ```
    { 
        "username": "admin@example.com",
        "password": "adminpass"
    }
    ```
- Server responses:
  - **200 OK** - *request was successful, member has been successfully authenticated, token is returned as JSON*
  - **400 Bad Request** - *a required body is missing*
  - **403 Forbidden** - *bad credentials*

---

### Retrieve all books on the platform:
- Request: `GET` `http://localhost:8080/api/v1/books?{page}&{size}`
- Access: ***ALL***
- Headers: (not required)
- Path params: `none`
- Query params:
  - `{page}` - *page number (optional), e.g. page=0. Default page is 0*
  - `{size}` - *page size (optional), e.g. size=5. Default size is 20*
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*

### Retrieve a single book:
- Request: `GET` `http://localhost:8080/api/v1/books/{bookId}`
- Access: ***ALL***
- Headers: (not required)
- Path params:
  - `{bookId}` - id of book to fetch
- Query params: `none`
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **400 Bad Request** - *path is wrong, e.g., an ID is not a number*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Create a new book:
- Request: `POST` `http://localhost:8080/api/v1/books`
- Access: ***ADMIN***
- Headers:
  - **Authorization** - contains the bearer token, e.g. `Bearer {token}` (required)
- Path params: `none`
- Query params: `none`
- Request body:
  - body is required
  - not all fields are required (minimum 1 field)
  - template (all fields):
    ```
    { 
        "title": "Example title",
        "subject": "Example subject",
        "publisher": "Example publisher",
        "ISBN": "Example ISBN",
        "language": "Example language",
        "pages": 123
    }
    ```
- Server responses:
  - **201 Created** - *request was successful, the resource is successfully created*
  - **400 Bad Request** - *a required body is missing*
  - **403 Forbidden** - *the request is not allowed, e.g., the member is not allowed to create new books or is not authenticated*

### Replace a single book:
- Request: `PUT` `http://localhost:8080/api/v1/books/{bookId}`
- Access: ***ADMIN***
- Headers:
  - **Authorization** - contains the bearer token, e.g. `Bearer {token}` (required)
- Path params:
  - `{bookId}` - id of book to replace
- Query params: `none`
- Request body:
  - body is required
  - not all fields are required (minimum 1 field)
  - template (all fields):
    ```
    { 
        "title": "Example title",
        "subject": "Example subject",
        "publisher": "Example publisher",
        "ISBN": "Example ISBN",
        "language": "Example language",
        "pages": 123
    }
    ```
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **400 Bad Request** - *a required body is missing or path is wrong, e.g., an ID is not a number*
  - **403 Forbidden** - *the request is not allowed, e.g., the member is not allowed to update a book or is not authenticated*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Edit the details of a single book:
- Request: `PATCH` `http://localhost:8080/api/v1/books/{bookId}`
- Access: ***ADMIN***
- Headers:
  - **Authorization** - contains the bearer token, e.g. `Bearer {token}` (required)
- Path params:
  - `{bookId}` - id of book to edit
- Query params: `none`
- Request body:
  - body is required
  - not all fields are required (minimum 1 field)
  - template (all fields):
    ```
    { 
        "title": "Example title",
        "subject": "Example subject",
        "publisher": "Example publisher",
        "ISBN": "Example ISBN",
        "language": "Example language",
        "pages": 123
    }
    ```
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **400 Bad Request** - *a required body is missing or path is wrong, e.g., an ID is not a number*
  - **403 Forbidden** - *the request is not allowed, e.g., the member is not allowed to update a book or is not authenticated*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Delete a single book:
- Request: `DELETE` `http://localhost:8080/api/v1/books/{bookId}`
- Access: ***ADMIN***
- Headers:
  - **Authorization** - contains the bearer token, e.g. `Bearer {token}` (required)
- Path params:
  - `{bookId}` - id of book to delete
- Query params: `none`
- Server responses:
  - **204 No Content** - *request was successful, the resource is successfully deleted*
  - **400 Bad Request** - *path is wrong, e.g., an ID is not a number*
  - **403 Forbidden** - *the request is not allowed, e.g., the member is not allowed to delete a book or is not authenticated*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

---

### Retrieve all copies of books on the platform:
- Request: `GET` `http://localhost:8080/api/v1/book-items?{page}&{size}`
- Access: ***ALL***
- Headers: (not required)
- Path params: `none`
- Query params:
  - `{page}` - *page number (optional), e.g. page=0. Default page is 0*
  - `{size}` - *page size (optional), e.g. size=5. Default size is 20*
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*

### Retrieve a single copy of book:
- Request: `GET` `http://localhost:8080/api/v1/book-items/{bookItemId}`
- Access: ***ALL***
- Headers: (not required)
- Path params:
  - `{bookItemId}` - id of copy to fetch
- Query params: `none`
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **400 Bad Request** - *path is wrong, e.g., an ID is not a number*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Create a new copy of book:
- Request: `POST` `http://localhost:8080/api/v1/book-items`
- Access: ***ADMIN***
- Headers:
  - **Authorization** - contains the bearer token, e.g. `Bearer {token}` (required)
- Path params: `none`
- Query params: `none`
- Request body:
  - body is required
  - not all fields are required (bookId is required)
  - template (all fields):
    ```
    { 
        "isReferenceOnly": true,
        "price": 123.45,
        "format": "MAGAZINE",
        "dateOfPurchase": "2023-12-23",
        "publicationDate": "2023-12-24",
        "bookId": 3
    }
    ```
- Server responses:
  - **201 Created** - *request was successful, the resource is successfully created and returned as JSON*
  - **400 Bad Request** - *a required body is missing*
  - **403 Forbidden** - *the request is not allowed, e.g., the member is not allowed to create new copies of books or is not authenticated*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Replace a single copy of book:
- Request: `PUT` `http://localhost:8080/api/v1/book-items/{bookItemId}`
- Access: ***ADMIN***
- Headers:
  - **Authorization** - contains the bearer token, e.g. `Bearer {token}` (required)
- Path params:
  - `{bookItemId}` - id of copy to replace
- Query params: `none`
- Request body:
  - body is required
  - not all fields are required (bookId is required)
    - template (all fields):
      ```
      { 
          "isReferenceOnly": true,
          "borrowed": "2023-12-25",
          "dueDate": "2023-12-26",
          "price": 123.45,
          "format": "MAGAZINE",
          "status": "AVAILABLE",
          "dateOfPurchase": "2023-12-23",
          "publicationDate": "2023-12-24",
          "bookId": 3
      }
      ```
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **400 Bad Request** - *a required body is missing or path is wrong, e.g., an ID is not a number*
  - **403 Forbidden** - *the request is not allowed, e.g., the member is not allowed to update a copy of book or is not authenticated*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Edit the details of a single copy of book:
- Request: `PATCH` `http://localhost:8080/api/v1/book-items/{bookItemId}`
- Access: ***ADMIN***
- Headers:
  - **Authorization** - contains the bearer token, e.g. `Bearer {token}` (required)
- Path params:
  - `{bookItemId}` - id of copy to edit
- Query params: `none`
- Request body:
  - body is required
  - not all fields are required (minimum 1 field)
  - template (all fields):
    ```
    { 
        "isReferenceOnly": true,
        "borrowed": "2023-12-25",
        "dueDate": "2023-12-26",
        "price": 123.45,
        "format": "MAGAZINE",
        "status": "AVAILABLE",
        "dateOfPurchase": "2023-12-23",
        "publicationDate": "2023-12-24",
        "bookId": 3
    }
    ```
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **400 Bad Request** - *a required body is missing or path is wrong, e.g., an ID is not a number*
  - **403 Forbidden** - *the request is not allowed, e.g., the member is not allowed to update a book or is not authenticated*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Delete a single copy of book:
- Request: `DELETE` `http://localhost:8080/api/v1/book-items/{bookItemId}`
- Access: ***ADMIN***
- Headers:
  - **Authorization** - contains the bearer token, e.g. `Bearer {token}` (required)
- Path params:
  - `{bookItemId}` - id of copy to delete
- Query params: `none`
- Server responses:
  - **204 No Content** - *request was successful, the resource is successfully deleted*
  - **400 Bad Request** - *path is wrong, e.g., an ID is not a number*
  - **403 Forbidden** - *the request is not allowed, e.g., the member is not allowed to delete a copy of book or is not authenticated*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*
  - **409 Conflict** - *a resource could not be deleted because it was reserved or borrowed*

---

### Retrieve all members on the platform:
- Request: `GET` `http://localhost:8080/api/v1/members?{page}&{size}`
- Access: ***ADMIN***
- Headers:
  - **Authorization** - contains the bearer token, e.g. `Bearer {token}` (required)
- Path params: `none`
- Query params: 
  - `{page}` - *page number (optional), e.g. page=0. Default page is 0*
  - `{size}` - *page size (optional), e.g. size=5. Default size is 20*
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **403 Forbidden** - *the request is not allowed, e.g., the member is not allowed to retrieve all members' data or is not authenticated*

### Retrieve a single member:
- Request: `GET` `http://localhost:8080/api/v1/members/{memberId}`
- Access: ***ADMIN, USER*** (data owner)
- Headers:
  - **Authorization** - contains the bearer token, e.g. `Bearer {token}` (required)
- Path params: 
  - `{memberId}` - id of member to fetch
- Query params: `none`
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON. Member is allowed to retrieve their data only*
  - **400 Bad Request** - *path is wrong, e.g., an ID is not a number*
  - **403 Forbidden** - *the request is not allowed, e.g., the member is not allowed to retrieve another member's data or is not authenticated*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found.*

### Edit the details of a single member:
- Request: `PATCH` `http://localhost:8080/api/v1/members/{memberId}`
- Access: ***ADMIN, USER*** (data owner)
- Headers:
  - **Authorization** - contains the bearer token, e.g. `Bearer {token}` (required)
- Path params:
  - `{memberId}` - id of member to update
- Query params: `none`
- Request body:
  - body is required
  - not all fields are required (minimum 1 field)
  - template (all fields):
    ```
    { 
        "firstName": "John",
        "lastName": "Smith",
        "email": "member@example.com",
        "password": "easypass",
        "streetAddress": "Konopacka 3",
        "zipCode": "00-000",
        "city": "Warsaw",
        "state": "Mazowieckie",
        "country": "Poland",
        "phone": "123456789"
    }
    ```
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON. Member is allowed to update their data only*
  - **400 Bad Request** - *a required body is missing or a path is wrong, e.g., an ID is not a number*
  - **403 Forbidden** - *the request is not allowed, e.g., the member is not allowed to change another member's data or is not authenticated*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Delete a single member:
- Request: `DELETE` `http://localhost:8080/api/v1/members/{memberId}`
- Access: ***ADMIN, USER*** (data owner)
- Headers:
  - **Authorization** - contains the bearer token, e.g. `Bearer {token}` (required)
- Path params:
  - `{memberId}` - id of member to delete
- Query params: `none`
- Server responses:
  - **204 No Content** - *request was successful, the resource is successfully deleted. Member is allowed to delete their account only*
  - **400 Bad Request** - *path is wrong, e.g., an ID is not a number*
  - **403 Forbidden** - *the request is not allowed, e.g., the member is not allowed to delete another member's account or is not authenticated*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*
  - **409 Conflict** - *a resource could not be deleted, member has not returned all books or has outstanding fees*

---

### Retrieve all reservations on the platform:
- Request: `GET` `http://localhost:8080/api/v1/reservations?{page}&{size}`
- Access: ***ADMIN***
- Headers:
  - **Authorization** - contains the bearer token, e.g. `Bearer {token}` (required)
- Path params: `none`
- Query params:
  - `{page}` - *page number (optional), e.g. page=0. Default page is 0*
  - `{size}` - *page size (optional), e.g. size=5. Default size is 20*
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **403 Forbidden** - *the request is not allowed, e.g., the member is not allowed to retrieve all members' data or is not authenticated*

### Retrieve all reservations of member:
- Request: `GET` `http://localhost:8080/api/v1/reservations?{memberId}&{page}&{size}`
- Access: ***ADMIN, USER*** (data owner)
- Headers:
  - **Authorization** - contains the bearer token, e.g. `Bearer {token}` (required)
- Path params: `none`
- Query params:
  - `{memberId}` - *id of member to fetch reservations (optional), e.g. memberId=123*
  - `{page}` - *page number (optional), e.g. page=0. Default page is 0*
  - `{size}` - *page size (optional), e.g. size=5. Default size is 20*
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON. Member is allowed to retrieve their data only*
  - **400 Bad Request** - *path is wrong, e.g., an ID is not a number*
  - **403 Forbidden** - *the request is not allowed, e.g., the member is not allowed to retrieve another members' data or is not authenticated*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found. Member is not allowed to retrieve another members' data*

### Retrieve a single reservation:
- Request: `GET` `http://localhost:8080/api/v1/reservations/{reservationId}`
- Access: ***ADMIN, USER*** (data owner)
- Headers:
  - **Authorization** - contains the bearer token, e.g. `Bearer {token}` (required)
- Path params:
  - `{reservationId}` - id of reservation to fetch
- Query params: `none`
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON. Member is allowed to retrieve their data only*
  - **400 Bad Request** - *path is wrong, e.g., an ID is not a number*
  - **403 Forbidden** - *the request is not allowed, e.g., the member is not allowed to retrieve another users' data or is not authenticated*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Create a new reservation:
- Request: `POST` `http://localhost:8080/api/v1/reservations`
- Access: ***ADMIN, USER*** (data owner)
- Headers:
  - **Authorization** - contains the bearer token, e.g. `Bearer {token}` (required)
- Path params: `none`
- Query params: `none`
- Request body:
  - body is required
  - template:
    ```
    { 
        "memberId": 12,
        "bookBarcode": "540200000007"
    }
    ```
- Server responses:
  - **201 Created** - *request was successful, the resource is successfully created and return the newly created resource as JSON*
  - **400 Bad Request** - *a required body is missing or path is wrong, e.g., an ID is not a number*
  - **403 Forbidden** - *the request is not allowed, e.g., the member is not allowed to create reservations for another members or is not authenticated*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID or a barcode for a resource could not be found*
  - **409 Conflict** - *Occurs in one of the following situations:*
    - *member has reserved the maximum number of books*
    - *member has already reserved this book*
    - *the copy of book is lost*

### Cancel a single reservation:
- Request: `DELETE` `http://localhost:8080/api/v1/reservations/{reservationId}`
- Access: ***ADMIN, USER*** (data owner)
- Headers:
  - **Authorization** - contains the bearer token, e.g. `Bearer {token}` (required)
- Path params: `none`
- Query params: `none`
- Request body:
  - body is required
  - template:
    ```
    { 
        "memberId": 12,
        "bookBarcode": "540200000007"
    }
    ```
- Server responses:
  - **204 No content** - *request was successful, the resource is successfully canceled. Member is allowed to delete their data only*
  - **400 Bad Request** - *a required body is missing or path is wrong, e.g., an ID is not a number*
  - **403 Forbidden** - *the request is not allowed, e.g., the member is not allowed to cancel another members' data or is not authenticated*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID or a barcode for a resource could not be found*

---

### Retrieve all loans on the platform:
- Request: `GET` `http://localhost:8080/api/v1/lendings?{page}&{size}`
- Access: ***ADMIN***
- Headers:
  - **Authorization** - contains the bearer token, e.g. `Bearer {token}` (required)
- Path params: `none`
- Query params:
  - `{page}` - *page number (optional), e.g. page=0. Default page is 0*
  - `{size}` - *page size (optional), e.g. size=5. Default size is 20*
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON*
  - **403 Forbidden** - *the request is not allowed, e.g., the member is not allowed to retrieve all members' data or is not authenticated*

### Retrieve all loans of member:
- Request: `GET` `http://localhost:8080/api/v1/lendings?{memberId}&{page}&{size}`
- Access: ***ADMIN, USER*** (data owner)
- Headers:
  - **Authorization** - contains the bearer token, e.g. `Bearer {token}` (required)
- Path params: `none`
- Query params:
  - `{memberId}` - *id of member to fetch loans (optional), e.g. memberId=123*
  - `{page}` - *page number (optional), e.g. page=0. Default page is 0*
  - `{size}` - *page size (optional), e.g. size=5. Default size is 20*
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON. Member is allowed to retrieve their data only*
  - **400 Bad Request** - *path is wrong, e.g., an ID is not a number*
  - **403 Forbidden** - *the request is not allowed, e.g., the member is not allowed to retrieve another members' data or is not authenticated*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found. Member is not allowed to retrieve another members' data*

### Retrieve a single loan:
- Request: `GET` `http://localhost:8080/api/v1/lendings/{lendingId}`
- Access: ***ADMIN, USER*** (data owner)
- Headers:
  - **Authorization** - contains the bearer token, e.g. `Bearer {token}` (required)
- Path params:
  - `{lendingId}` - id of loan to fetch
- Query params: `none`
- Server responses:
  - **200 OK** - *request was successful, the resource itself is returned as JSON. Member is allowed to retrieve their data only*
  - **400 Bad Request** - *path is wrong, e.g., an ID is not a number*
  - **403 Forbidden** - *the request is not allowed, e.g., the member is not allowed to retrieve another users' data or is not authenticated*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID for a resource could not be found*

### Create a new loan (borrow a book):
- Request: `POST` `http://localhost:8080/api/v1/lendings`
- Access: ***ADMIN***
- Headers:
  - **Authorization** - contains the bearer token, e.g. `Bearer {token}` (required)
- Path params: `none`
- Query params: `none`
- Request body:
  - body is required
  - template:
    ```
    { 
        "memberId": 12,
        "bookBarcode": "540200000007"
    }
    ```
- Server responses:
  - **201 Created** - *request was successful, the resource is successfully created and return the newly created resource as JSON*
  - **400 Bad Request** - *a required body is missing or path is wrong, e.g., an ID is not a number*
  - **403 Forbidden** - *the request is not allowed, e.g., the member is not allowed to create loans or is not authenticated*
  - **404 Not Found** - *a resource could not be accessed, e.g., an ID or a barcode for a resource could not be found*
  - **409 Conflict** - *Occurs in one of the following situations:*
    - *member has outstanding fines*
    - *member has borrowed the maximum number of books*

### Extend a book loan:
- Request: `POST` `http://localhost:8080/api/v1/lendings/renew?{bookBarcode}`
- Access: ***ADMIN***
- Headers:
  - **Authorization** - contains the bearer token, e.g. `Bearer {token}` (required)
- Path params: `none`
- Query params:
  - `{bookBarcode}` - *a barcode of book (required), e.g. bookBarcode=54020000005*
- Server responses:
  - **200 OK** - *request was successful, the resource is successfully extended and returned as JSON*
  - **400 Bad Request** - *a required param is missing*
  - **403 Forbidden** - *the request is not allowed, e.g., the member is not allowed to extend loans or is not authenticated*
  - **404 Not Found** - *a resource could not be accessed, e.g., a barcode for a resource could not be found*
  - **409 Conflict** - *Occurs in one of the following situations:*
    - *the book has been already reserved by another member*
    - *the book extension operation is performed after the planned return date*




### Return a book:
- Request: `POST` `http://localhost:8080/api/v1/lendings/return?{bookBarcode}`
- Access: ***ADMIN***
- Headers:
  - **Authorization** - contains the bearer token, e.g. `Bearer {token}` (required)
- Path params: `none`
- Query params:
  - `{bookBarcode}` - *a barcode of book (required), e.g. bookBarcode=54020000005*
- Server responses:
  - **200 OK** - *request was successful, the operation of book returning was accepted and returned as JSON*
  - **400 Bad Request** - *a required param is missing*
  - **403 Forbidden** - *the request is not allowed, e.g., the member is not allowed to perform a book returning operation or is not authenticated*
  - **404 Not Found** - *a resource could not be accessed, e.g., a barcode for a resource could not be found*
  - **409 Conflict** - *Occurs in one of the following situations:*
    - *the book has been already reserved by another member*
    - *the book extension operation is performed after the planned return date*

---

## Status
Project is in progress with some active features and another improvements to make.
 