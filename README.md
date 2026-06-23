# Auth Service (Spring Boot Backend)

JWT-based authentication backend for the login feature. Provides registration,
login, logout, and a protected endpoint, with BCrypt password hashing and
stateless JWT security.

## Tech stack

- Java 17, Spring Boot 3.2
- Spring Security + JWT (jjwt)
- Spring Data JPA (PostgreSQL in prod, H2 for dev/test)
- BCrypt password hashing
- JUnit 5 + MockMvc tests

## API endpoints

| Method | Path                | Auth      | Description                       |
|--------|---------------------|-----------|-----------------------------------|
| POST   | /api/auth/register  | Public    | Register a new user, returns JWT  |
| POST   | /api/auth/login     | Public    | Authenticate, returns JWT         |
| POST   | /api/auth/logout    | Public    | Clears security context           |
| GET    | /api/auth/me        | Protected | Returns current user info         |

## Environment variables

| Variable                  | Required   | Default (dev)            | Description                              |
|---------------------------|------------|--------------------------|------------------------------------------|
| PORT                      | no         | 8080                     | HTTP port                                |
| DATABASE_URL              | yes (prod) | jdbc:postgresql://...    | JDBC URL                                 |
| DATABASE_USERNAME         | yes (prod) | postgres                 | DB user                                  |
| DATABASE_PASSWORD         | yes (prod) | postgres                 | DB password (secret)                     |
| APP_JWT_SECRET            | yes        | dev-only default         | JWT signing secret, >= 256 bits (secret) |
| APP_JWT_EXPIRATION_MS     | no         | 86400000                 | Token lifetime in ms                     |
| APP_CORS_ALLOWED_ORIGINS  | yes (prod) | http://localhost:5173    | Comma-separated allowed frontend origins |

> Set APP_CORS_ALLOWED_ORIGINS to your deployed frontend URL so the browser can call the API.

## Run locally (H2, no DB needed)

```bash
SPRING_PROFILES_ACTIVE=dev mvn spring-boot:run
```

## Run tests

```bash
mvn clean verify
```

## Build & run with Docker

```bash
docker build -t auth-service .
docker run -p 8080:8080 \
  -e APP_JWT_SECRET=your-strong-secret \
  -e DATABASE_URL=jdbc:postgresql://host:5432/authdb \
  -e DATABASE_USERNAME=postgres \
  -e DATABASE_PASSWORD=secret \
  -e APP_CORS_ALLOWED_ORIGINS=https://your-frontend-url \
  auth-service
```
