# Session 004 - Spring Boot + PostgreSQL + JPA + First REST API

**Date:** 2026-08-05

---

# Objectives

- Connect Spring Boot with PostgreSQL
- Understand Hibernate ORM
- Create the first Entity
- Learn JPA Repository
- Save data using Hibernate
- Build the first REST API
- Understand JSON ↔ Java Object conversion

---

# 1. PostgreSQL Setup

Installed PostgreSQL.

Created:

- Database
- User
- Password

```sql
CREATE DATABASE identity_db;

CREATE USER identity_user WITH PASSWORD '1234';

GRANT ALL PRIVILEGES ON DATABASE identity_db TO identity_user;
GRANT ALL ON SCHEMA public TO identity_user;
```

Verified connection using IntelliJ Database Tool.

---

# 2. Spring Boot Database Configuration

Configured database connection.

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/identity_db
spring.datasource.username=identity_user
spring.datasource.password=1234

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Learned:

- Spring Boot creates the DataSource.
- Hibernate uses the DataSource to communicate with PostgreSQL.

---

# 3. Hibernate ORM

Learned that Hibernate is an ORM (Object Relational Mapping).

Instead of writing SQL manually:

```
Java Object
        ↓
Hibernate
        ↓
SQL
        ↓
PostgreSQL
```

Hibernate automatically generates SQL.

---

# 4. Entity

Created:

```java
@Entity
@Table(name = "users")
public class User
```

Learned:

`@Entity`

Tells Hibernate:

> This Java class represents a database table.

`@Table`

Specifies the table name.

---

# 5. Primary Key

Added

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

Learned:

`@Id`

Marks the Primary Key.

`@GeneratedValue`

Database generates IDs automatically.

Use `Long` instead of `long`.

Reason:

`Long` supports `null`.

Before inserting:

```
id = null
```

After saving:

```
id = 1
```

---

# 6. Getters and Setters

Initially there were no setters.

Therefore:

```
user.setFirstName(...)
```

gave compilation errors.

Generated:

- Getters
- Setters

Did NOT generate:

```
setId()
```

Reason:

The application should not manually modify the Primary Key.

Hibernate/database generates it.

Learned:

Setters are mainly used by our application.

Example:

```java
//user.setEmail("new@gmail.com");
```

during updates.

Hibernate does not replace setters.

---

# 7. Repository

Created

```java
public interface UserRepository
        extends JpaRepository<User, Long> {
}
```

Learned:

Although the interface is empty,

Spring Data JPA creates an implementation automatically during application startup.

Conceptually:

```
UserRepository
        ↓
Spring Data JPA
        ↓
Generated Implementation
        ↓
Bean
        ↓
Injected into Service
```

No implementation was written manually.

---

# 8. Service Layer

Created

```java
public User createUser(User user)
{
    return userRepository.save(user);
}
```

Learned:

Service receives the entity.

Service performs business logic.

Service delegates database operations to Repository.

---

# 9. Controller Layer

Created

```java
@PostMapping("/users")
public User createUser(@RequestBody User user)
{
    return userService.createUser(user);
}
```

Learned:

Controller should remain thin.

Controller only:

- Receives request
- Calls Service
- Returns response

Business logic belongs inside Service.

---

# 10. @RequestBody

Learned:

```
@RequestBody
```

means:

Read JSON from the HTTP Request Body

↓

Convert JSON

↓

Create Java Object

Spring uses Jackson internally.

Example:

Incoming JSON

```json
{
    "firstName":"Saman",
    "lastName":"Perera"
}
```

becomes

```java
User user
```

automatically.

---

# 11. @RestController

Learned the reverse process.

```
Java Object
      ↓
@RestController
      ↓
Jackson
      ↓
JSON
      ↓
HTTP Response
```

Spring automatically converts Java objects back into JSON.

---

# 12. Save()

Learned:

```
userRepository.save(user)
```

If

```
id == null
```

Hibernate inserts a new row.

If

```
id != null
```

Hibernate treats it as an existing entity (conceptually an update).

---

# 13. First REST API

Created

```
POST /api/v1/identity/users
```

Body:

```json
{
    "firstName":"Kamal",
    "lastName":"Gunarathne",
    "email":"kamal@gmail.com",
    "password":"5534"
}
```

Flow:

```
Postman

↓

HTTP POST

↓

Controller

↓

@RequestBody

↓

User Object

↓

Service

↓

Repository

↓

Hibernate

↓

PostgreSQL

↓

Generated ID

↓

Controller returns User

↓

@RestController

↓

JSON

↓

Postman
```

Successfully inserted data into PostgreSQL.

---

# 14. Spring Data JPA Query Methods

Learned that Spring generates queries based on method names.

Examples:

```java
findByEmail(String email)

existsByEmail(String email)

findByLastName(String lastName)

existsByFirstName(String firstName)
```

No implementation required.

Spring generates SQL automatically.

---

# Important Concepts Learned

- ORM
- Hibernate
- Entity
- Primary Key
- GeneratedValue
- Repository
- JpaRepository
- Automatic Repository Implementation
- Constructor Injection
- JSON → Java Object
- Java Object → JSON
- @RequestBody
- @RestController
- save()
- PostgreSQL Integration

---

# Biggest Achievement

Built the first complete backend request flow.

```
Client

↓

HTTP Request

↓

Controller

↓

Service

↓

Repository

↓

Hibernate

↓

PostgreSQL

↓

Response

↓

Client
```

This is the first fully working REST endpoint connected to a real PostgreSQL database.

---

# Next Session (Session 005)

Topics:

- GET API
- findAll()
- List<User>
- GET /users
- Get User by ID
- Optional<User>
- ResponseEntity
- Proper REST API responses