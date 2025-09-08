# 📚 Bookstore API

A sample **Spring Boot 3.5.x** application designed to showcase **real-world testing practices**.  
This project demonstrates **unit tests, slice tests, and integration tests** using Spring Boot’s testing stack.

---

## 🚀 Features

- CRUD API for managing books (`BookController`)
- DTO & Entity mapping with **MapStruct**
- Repository layer with **Spring Data JPA**
- In-memory **H2 Database** for fast development & tests
- Global exception handling with standardized **ApiError**
- Comprehensive **testing coverage**:
    - ✅ Unit tests with JUnit & Mockito
    - ✅ Slice tests with `@WebMvcTest` and `@DataJpaTest`
    - ✅ Integration tests with `@SpringBootTest` and `TestRestTemplate`

---

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot 3.5.x**
- **Spring Data JPA**
- **H2 Database**
- **MapStruct**
- **JUnit 5**
- **Mockito**
- **Spring Boot Test (MockMvc, TestRestTemplate)**

---

## ⚡ Getting Started

### Prerequisites
- Java 17+
- Maven 3.9+

### Run Application
```bash
mvn spring-boot:run
```

Application will be available at: http://localhost:8080

H2 Console: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:bookstore

---

## 📖 API Endpoints
###Book Management
| Method | Endpoint                      | Description               |
| ------ | ----------------------------- | ------------------------- |
| POST   | `/api/books`                  | Create a new book         |
| GET    | `/api/books/{id}`             | Get book by ID            |
| GET    | `/api/books`                  | List all books            |
| PUT    | `/api/books/{id}`             | Update a book             |
| DELETE | `/api/books/{id}`             | Delete a book             |
| PATCH  | `/api/books/{id}/discontinue` | Mark book as DISCONTINUED |
| GET    | `/api/books/status/{status}`  | Filter by status          |

---

## 🧪 Testing
Run all tests:
```bash
mvn test
```

**Test Coverage**

- **Unit tests** → `BookServiceTest`

- **Slice tests** → `BookControllerTest`, `BookRepositoryTest`

- **Integration tests** → `BookIntegrationTest`

---

## 🔑 Key Learnings

- Difference between unit, slice, and integration tests in Spring Boot

- Using `@WebMvcTest`, `@DataJpaTest`, `@SpringBootTest` effectively

- Mocking with `@Mock` vs `@MockBean`

- Exception handling & validation best practices

- H2 for lightweight integration testing