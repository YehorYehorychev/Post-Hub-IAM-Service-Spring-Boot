# IAM SERVICE [Spring Boot Project]

This is a Spring Boot project that implements a Post Hub service with comment management functionality.

## Features
- Post Management (CRUD operations)
- Comment Management with multiple service implementations
- PostgreSQL Database with Flyway migrations
- RESTful API Endpoints
- JPA/Hibernate integration

## Technologies Used
- Spring Boot 4.0.1
- Spring Data JPA
- Spring Web MVC
- PostgreSQL 42.7.8
- Flyway 11.11.2 (Database migrations)
- Lombok
- Java 25

## Prerequisites
- Java 25 or higher
- Maven 3.6+
- PostgreSQL 12+ installed and running
- Database: `post_hub_local`
- Database user: `yehor` with password: `admin`

## Setup Instructions

### 1. Create PostgreSQL Database
```bash
createdb -U yehor post_hub_local
```

### 2. Configure Database Connection
Update `src/main/resources/application.properties` if needed:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/post_hub_local
spring.datasource.username=yehor
spring.datasource.password=admin
```

### 3. Build and Run
```bash
# Clean and build the project
mvn clean package -DskipTests

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8189`

## Database Migrations
Flyway will automatically create the `v1_iam_service` schema and run migrations on startup.

Migration files are located in: `src/main/resources/db/migration/`

## API Endpoints

### Comments
- `POST /comments/create` - Create a basic comment
- `POST /comments/createAdvanced` - Create an advanced comment with timestamp

### Posts
Check `PostController` for available endpoints.

## Project Structure
```
src/main/java/com/post_hub/iam_service/
├── controller/          # REST controllers
├── service/            # Service interfaces
│   └── impl/          # Service implementations
├── model/             # DTOs, entities, enums
├── repositories/      # JPA repositories
├── config/           # Configuration classes
└── security/         # Security configuration
```

## Notes
- The project uses two implementations of `CommentService`:
  - `basicCommentService` - Simple comment creation
  - `advancedCommentService` - Comment creation with timestamp and uppercase transformation
- Flyway migrations are enabled and will run automatically on startup
- The application uses `-parameters` compiler flag for proper Spring dependency injection

