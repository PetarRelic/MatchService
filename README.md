# MatchService

A Spring Boot service for managing matches as part of the SkillSync application.

## Features

- RESTful API with OpenAPI/Swagger documentation
- Caffeine-based caching
- Configurable via `application.properties`

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.6+

### Build

```sh
mvn clean package
```

### Run
```sh
mvn spring-boot:run
```
or
```sh
java -jar target/matchservice-*.jar
```

### API Documentation
Swagger UI: http://localhost:9091/swagger-ui.html
OpenAPI docs: http://localhost:9091/v3/api-docs
