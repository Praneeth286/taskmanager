# Task Manager API

A REST API for a personal task manager, built with Spring Boot. Users register, log in to get a JWT, and then create, list, categorize and delete their own tasks. The React client lives in [taskmanager-frontend](https://github.com/Praneeth286/taskmanager-frontend).

**Stack:** Java 21 · Spring Boot 4.1 · Spring Security + JWT (jjwt) · Spring Data JPA · PostgreSQL 16 · Flyway · Docker · GitHub Actions

## Features

- **JWT authentication**: register and log in; passwords are hashed with BCrypt and tokens expire after one hour
- **Per-user tasks**: every task has an owner; the task list, updates and deletes are limited to the logged-in user
- **Categories**: tasks can be assigned to a category; only users with the `ADMIN` role can create categories
- **Validation and error handling**: request validation with a global exception handler
- **Versioned schema**: Flyway migrations, with Hibernate set to `validate` so the schema never drifts silently
- **CI**: GitHub Actions runs the test suite against a real Postgres service on every push and pull request to `main`

## Running locally

### With Docker Compose (easiest)

```bash
docker compose up --build
```

This starts Postgres 16 and the API on `http://localhost:8080`. Flyway creates the schema on first boot.

> The `JWT_SECRET` in `docker-compose.yml` is for local development only. Use your own secret anywhere else.

### With Maven

Start a Postgres database, then set these environment variables:

| Variable | Example |
|---|---|
| `DATABASE_URL` | `jdbc:postgresql://localhost:5432/taskmanager` |
| `DATABASE_USERNAME` | `postgres` |
| `DATABASE_PASSWORD` | `postgres` |
| `JWT_SECRET` | a long random string (64+ characters) |
| `FRONTEND_URL` | `http://localhost:5173` (allowed CORS origin) |

```bash
./mvnw spring-boot:run
```

### Tests

```bash
./mvnw test
```

Tests cover the task service, the task entity, and the security rules on the task endpoints. They need the same environment variables as above.

## API

All endpoints except `/auth/**` and `/actuator/health` require an `Authorization: Bearer <token>` header.

| Method | Path | Description |
|---|---|---|
| `POST` | `/auth/register` | Create an account. Body: `{"username", "password"}` |
| `POST` | `/auth/login` | Returns a JWT. Body: `{"username", "password"}` |
| `GET` | `/tasks` | List your tasks. Optional `?done=true\|false` filter |
| `POST` | `/tasks` | Create a task. Body: `{"title"}` (required, non-blank) |
| `PUT` | `/tasks/{taskId}/category/{categoryId}` | Assign a task to a category |
| `DELETE` | `/tasks/{id}` | Delete one of your tasks |
| `GET` | `/categories` | List categories |
| `POST` | `/categories` | Create a category (`ADMIN` only). Body: `{"name"}` |
| `GET` | `/actuator/health` | Health check |

### Example

```bash
# register and log in
curl -s -X POST localhost:8080/auth/register -H 'Content-Type: application/json' \
  -d '{"username":"demo","password":"demo123"}'
TOKEN=$(curl -s -X POST localhost:8080/auth/login -H 'Content-Type: application/json' \
  -d '{"username":"demo","password":"demo123"}')

# create and list tasks
curl -s -X POST localhost:8080/tasks -H "Authorization: Bearer $TOKEN" \
  -H 'Content-Type: application/json' -d '{"title":"Write the README"}'
curl -s localhost:8080/tasks -H "Authorization: Bearer $TOKEN"
```

## Data model

```
app_user (id, username, password, role)
    │ 1
    │
    │ *
task (id, title, is_done, category_id, user_id) ── * ──── 1 ── category (id, name)
```

Migrations are in `src/main/resources/db/migration`.

## Project layout

```
src/main/java/com/praneeth/taskmanager/
├── AuthController.java          # /auth/register, /auth/login
├── TaskController.java          # /tasks endpoints
├── CategoryController.java      # /categories endpoints
├── TaskService.java             # business logic + ownership checks
├── CategoryService.java
├── SecurityConfig.java          # stateless security, route rules, BCrypt
├── JwtAuthFilter.java / JwtUtil.java
├── CorsConfig.java              # allows the frontend origin
├── GlobalExceptionHandler.java
└── Task / Category / User entities and repositories
```
