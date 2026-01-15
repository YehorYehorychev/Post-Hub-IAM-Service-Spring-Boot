# IAM SERVICE [Spring Boot Project]

A comprehensive RESTful API service for Post Hub, providing user and post management with advanced search capabilities, pagination, and soft-delete functionality.

## 🚀 Features

### Core Features
- **User Management**: Complete CRUD operations with soft-delete support
- **Post Management**: Full CRUD operations with user attribution
- **Advanced Search**: Dynamic search with multiple criteria for both users and posts
- **Pagination**: Built-in pagination support for all list endpoints
- **Soft Delete**: Logical deletion for users and posts (data retention)
- **Data Validation**: Comprehensive input validation using Jakarta Validation
- **MapStruct Integration**: Automated DTO-Entity mapping
- **Exception Handling**: Centralized error handling with custom responses

### Technical Features
- PostgreSQL Database with Flyway migrations
- RESTful API with standardized response format
- JPA/Hibernate integration with optimized queries
- Automatic timestamp management (created_at, updated)
- Unique constraints (username, email, post title)
- Foreign key relationships with cascade operations

## 🛠 Technologies Used

- **Framework**: Spring Boot 4.0.1
- **Language**: Java 25
- **Database**: PostgreSQL 42.7.8
- **Migrations**: Flyway 11.11.2
- **ORM**: Spring Data JPA / Hibernate
- **Mapping**: MapStruct 1.6.3
- **Validation**: Jakarta Validation API 3.1.1
- **Utilities**: Apache Commons Lang3 3.20.0, Lombok
- **Build Tool**: Maven
- **Web**: Spring Web MVC with Tomcat

## 📋 Prerequisites

- Java 25 or higher
- Maven 3.6+
- PostgreSQL 12+ installed and running
- Database: `post_hub_local`

## 🔧 Setup Instructions

### 1. Create PostgreSQL Database
```bash
createdb -U postgres post_hub_local
```

### 2. Configure Database Connection
Update `src/main/resources/application.properties` if needed:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/post_hub_local
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
```

### 3. Build the Project
```bash
# Clean and compile with annotation processors
mvn clean compile

# Build package
mvn clean package -DskipTests
```

### 4. Run the Application
```bash
# Using Maven
mvn spring-boot:run

# Or using the JAR
java -jar target/iam_service-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8189`

## 🗄️ Database Schema

### Tables
- **users**: User accounts with authentication and profile information
- **posts**: User-generated posts with content and engagement metrics

### Flyway Migrations
- Migrations run automatically on startup
- Schema: `v1_iam_service`
- Location: `src/main/resources/db/migration/`

### Sample Data
The initial migration includes:
- 3 sample users (john_doe, dwayne_carter, test_user)
- 2 sample posts with likes

## 📡 API Endpoints

### Users API (`/users`)

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| `GET` | `/users/{id}` | Get user by ID | - |
| `POST` | `/users/create` | Create new user | `NewUserRequest` |
| `PUT` | `/users/{id}` | Update user | `UpdateUserRequest` |
| `DELETE` | `/users/{id}` | Soft delete user | - |
| `GET` | `/users/all?page=0&limit=10` | Get all users (paginated) | - |
| `POST` | `/users/search?page=0&limit=10` | Search users | `UserSearchRequest` |

### Posts API (`/posts`)

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| `GET` | `/posts/{id}` | Get post by ID | - |
| `POST` | `/posts/create` | Create new post | `PostRequest` |
| `PUT` | `/posts/{id}` | Update post | `UpdatePostRequest` |
| `DELETE` | `/posts/{id}` | Soft delete post | - |
| `GET` | `/posts/all?page=0&limit=10` | Get all posts (paginated) | - |
| `POST` | `/posts/search?page=0&limit=10` | Search posts | `PostSearchRequest` |

### Request/Response Examples

#### Create User
```json
POST /users/create
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "securePassword123"
}
```

#### Create Post
```json
POST /posts/create
{
  "title": "My First Post",
  "content": "This is the content of my first post",
  "likes": 0
}
```

#### Search Posts
```json
POST /posts/search?page=0&limit=10
{
  "title": "First",
  "content": "post",
  "minLikes": 5
}
```

#### Response Format
All endpoints return a standardized response:
```json
{
  "message": "Operation successful",
  "payload": { /* data */ },
  "success": true
}
```

Paginated responses include metadata:
```json
{
  "message": "",
  "payload": {
    "content": [ /* items */ ],
    "pagination": {
      "totalElements": 100,
      "pageSize": 10,
      "currentPage": 1,
      "totalPages": 10
    }
  },
  "success": true
}
```

## 📁 Project Structure

```
src/main/java/com/post_hub/iam_service/
├── controller/              # REST controllers
│   ├── PostController.java
│   └── UserController.java
├── service/                # Service interfaces
│   ├── PostService.java
│   ├── UserService.java
│   └── impl/              # Service implementations
│       ├── PostServiceImpl.java
│       └── UserServiceImpl.java
├── repositories/           # JPA repositories
│   ├── PostRepository.java
│   └── UserRepository.java
├── mapper/                # MapStruct mappers
│   ├── PostMapper.java
│   └── UserMapper.java
├── model/                 # Data models
│   ├── dto/              # Data Transfer Objects
│   ├── entities/         # JPA entities
│   ├── enums/            # Enumerations
│   ├── exception/        # Custom exceptions
│   ├── request/          # Request DTOs
│   └── response/         # Response DTOs
├── advice/               # Global exception handlers
├── config/              # Configuration classes
└── utils/               # Utility classes

src/main/resources/
├── db/migration/          # Flyway migration scripts
│   └── V1__init.sql
└── application.properties # Application configuration
```

## ⚙️ Configuration

### Application Properties
- **Server Port**: 8189
- **Database**: PostgreSQL with Hibernate auto-update
- **Logging**: TRACE level for application, DEBUG for Spring Web and Flyway
- **Flyway**: Enabled with automatic migrations

### Maven Profiles
- `local-idea`: Development profile for IntelliJ IDEA
- `prod`: Production profile

### Annotation Processing
The project uses annotation processors for:
- Lombok (code generation)
- MapStruct (DTO mapping)
- Lombok-MapStruct binding

## 🔍 Features Deep Dive

### Soft Delete
- Users and posts are never physically deleted
- `deleted` boolean flag marks records as deleted
- Queries automatically filter out deleted records
- `findByIdAndDeletedFalse()` pattern used throughout

### Unique Constraints
- Username (unique across users)
- Email (unique across users)
- Post title (unique across posts)
- Duplicate attempts return 409 Conflict

### Search Functionality
- Dynamic criteria building
- Case-insensitive search
- Partial matching for text fields
- Range queries for numeric fields (likes)
- Automatic pagination

### Timestamp Management
- `created_at`: Set automatically on creation
- `updated`: Updated automatically on modifications
- `last_login`: Tracked for users

## 🚨 Error Handling

The application handles:
- 404 Not Found: Resource doesn't exist
- 409 Conflict: Unique constraint violation
- 400 Bad Request: Validation errors
- 500 Internal Server Error: Unexpected errors

All errors return structured responses with meaningful messages.

## 🧪 Development

### Building with Different Profiles
```bash
# Local development
mvn clean package -Plocal-idea

# Production
mvn clean package -Pprod
```

### Generating MapStruct Implementations
MapStruct generates implementations during compilation:
```bash
mvn clean compile
```
Generated files: `target/generated-sources/annotations/`

## 📝 Notes

- Authentication is currently a placeholder (TODO: implement JWT/OAuth2)
- Spring Security is commented out (future enhancement)
- The application uses `-parameters` compiler flag for proper parameter name retention
- MapStruct with Lombok requires proper annotation processor ordering
- All timestamps are in the server's timezone

## 🤝 Contributing

When contributing:
1. Follow Java naming conventions (camelCase)
2. Use MapStruct for entity-DTO conversions
3. Implement soft delete for all entities
4. Add validation annotations to request DTOs
5. Update Flyway migrations (never modify existing ones)
6. Write unit tests for new features

## 📄 License

This project is part of the Post Hub IAM service.

