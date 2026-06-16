[![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk&logoColor=white)](https://www.oracle.com/java/technologies/downloads/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![JWT](https://img.shields.io/badge/JWT-jjwt%200.12.6-black?logo=jsonwebtokens&logoColor=white)](https://github.com/jwtk/jjwt)
[![Docker](https://img.shields.io/badge/Docker-multi--stage-2496ED?logo=docker&logoColor=white)](https://www.docker.com/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Supabase-4169E1?logo=postgresql&logoColor=white)](https://supabase.com/)
[![MySQL](https://img.shields.io/badge/MySQL-8-4479A1?logo=mysql&logoColor=white)](https://www.mysql.com/)
[![GitHub Actions](https://img.shields.io/badge/CI%2FCD-GitHub%20Actions-2088FF?logo=githubactions&logoColor=white)](https://github.com/nishithsai00/movie_booking_application/actions)
[![Deployed on Render](https://img.shields.io/badge/Deployed%20on-Render-46E3B7?logo=render&logoColor=white)](https://movie-booking-application-8t3w.onrender.com/swagger-ui/index.html)
# ReserveShow

Movie ticket booking REST API. Handles concurrent seat booking, JWT auth, and automated seat release scheduling.

Live: https://movie-booking-application-8t3w.onrender.com/swagger-ui/index.html

---

## Tech Stack

| Technology | Purpose |
|------------|---------|
| Java 21 | Core language |
| Spring Boot 3.5 | Application framework |
| Spring Security | Authentication & authorization |
| JWT (jjwt 0.12.6) | Stateless token-based auth |
| Spring Data JPA + Hibernate | ORM & database access |
| MySQL 8 | Local development database |
| PostgreSQL (Supabase) | Production database |
| Docker (multi-stage build) | Maven compile to JRE Alpine runtime |
| GitHub Actions | CI/CD — build and deploy on push |
| Render | Cloud deployment |
| Maven | Build & dependency management |

Monitored during development with [spring-boot-insights](https://central.sonatype.com/artifact/io.github.nishithsai00/spring-boot-insights) — a custom API monitoring library I built and published to Maven Central. Tracks per-request SQL query counts and flags N+1 patterns via Hibernate's StatementInspector.

---

## Key Features

- JWT authentication with role-based access control (USER / ADMIN)
- Pessimistic locking — DB-level write locks on seat rows prevent double-booking under concurrent requests
- Seats held for 5 minutes during booking, automatically released if payment is not completed
- Scheduled job runs every 5 minutes 30 seconds to release expired seat locks — the 30-second gap over the 5-minute payment window is intentional, handles the edge case where a payment request and the scheduler try to update the same row at the same time
- Captcha-based payment simulation with 5-minute timeout
- Booking cancellation allowed up to 3 hours before show time
- Centralized exception handling via @RestControllerAdvice — clean JSON errors for all cases, no stack traces exposed
- Jakarta Bean Validation on all inputs
- Movie poster upload and retrieval via multipart API

---

## Project Structure

```
src/main/java/com/nishith/demo/
├── config/
│   ├── JwtFilter.java
│   ├── MyUserDetails.java
│   └── Securityconfig.java
├── controllers/
│   ├── BookingController.java
│   ├── MovieController.java
│   ├── ShowController.java
│   ├── TheatherController.java
│   └── UserController.java
├── exceptionHandler/
│   ├── GlobalExceptionHandler.java
│   ├── MovieNotFoundException.java
│   └── EmptyListException.java
├── model/
│   ├── Booking.java
│   ├── Movie.java
│   ├── PaymentSimulation.java
│   ├── SeatSelection.java
│   ├── Shows.java
│   ├── Theather.java
│   └── Users.java
├── repo/
├── service/
│   ├── AutoScheduling.java
│   ├── BookingsService.java
│   ├── JwtService.java
│   ├── MovieService.java
│   ├── ShowService.java
│   ├── TheatherService.java
│   └── UserAuthService.java
└── ReserveShowApplication.java
```

---

## Local Setup

Prerequisites: Java 21+, MySQL 8, Maven 3.8+

**1. Clone the repo**

```bash
git clone https://github.com/nishithsai00/movie_booking_application
cd movie_booking_application
```

**2. Create the database**

```sql
CREATE DATABASE bookmyshow;
```

**3. Create src/main/resources/application.properties**

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bookmyshow
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.open-in-view=false
jwt.secret=YOUR_SECRET_KEY_MIN_32_CHARS
```

**4. Run**

```bash
mvn spring-boot:run
```

App starts at http://localhost:8080

---

## Docker

```bash
docker build -t reserveshow .
docker run -p 8080:8080 reserveshow
```

Multi-stage build. First stage compiles with Maven, second stage runs on eclipse-temurin:21-jre-alpine. No full JDK in the final image.

---

## API Endpoints

### Auth

| Method | URL | Description |
|--------|-----|-------------|
| POST | /signup | Register new user |
| POST | /login | Login, returns JWT token |

### Movies

| Method | URL | Auth | Description |
|--------|-----|------|-------------|
| GET | /movies | Public | Get all movies |
| GET | /movies/{id} | Public | Get movie by ID |
| GET | /movie/name/{name} | Public | Search by name |
| GET | /movie/location/{location} | Public | Get movies by city |
| GET | /movie/{id}/image | Public | Get movie poster |
| GET | /movie/sort | Public | Filter and sort movies |
| POST | /addmovie | ADMIN | Add movie with optional poster |
| PUT | /movie/{id} | ADMIN | Update movie |

### Shows

| Method | URL | Auth | Description |
|--------|-----|------|-------------|
| GET | /shows | USER | Get all shows |
| POST | /shows | ADMIN | Create show, auto-generates 96 seats (A1 to L8) |
| PUT | /shows/{id} | ADMIN | Reschedule show |
| DELETE | /removeshow | ADMIN | Delete show |
| GET | /movie/{showid} | USER | Seat availability for a show |
| GET | /movie/{location}/{name} | Public | Shows by movie and location |

### Theatres

| Method | URL | Auth | Description |
|--------|-----|------|-------------|
| GET | /theatres | Public | Get all theatres |
| POST | /theatres | ADMIN | Add theatre |
| PUT | /theatres/{id} | ADMIN | Edit theatre |
| DELETE | /theatres | ADMIN | Delete theatre |
| GET | /theather/{location} | Public | Theatres by location |

### Bookings

| Method | URL | Auth | Description |
|--------|-----|------|-------------|
| POST | /book | USER | Book seats, returns captcha and total |
| POST | /payment | USER | Complete payment within 5 minutes |
| GET | /mybookings | USER | Current user's bookings |
| GET | /allbookings | ADMIN | All bookings |
| GET | /booking/{id} | ADMIN | Booking by ID |
| POST | /admin/createadmin | ADMIN | Create admin user |

All protected endpoints require:

```
Authorization: Bearer your_jwt_token
```

Token expires after 11 hours.

---

## Architecture Decisions

**Pessimistic locking over optimistic locking**
Two users picking the same seat simultaneously is the core problem. Without a lock, both read "available," both write "booked," seat gets double-sold. Pessimistic locking issues a SELECT ... FOR UPDATE on the seat row inside the @Transactional boundary. Second request waits at the DB level until the first commits, reads the updated status, and fails cleanly. No application-level retry logic needed.

**Stateless JWT**
No session storage on the server. Every request carries its own token. Any instance validates any token without shared state — straightforward to scale horizontally.

**Scheduler at 5 minutes 30 seconds, not 5 minutes**
The payment window is 5 minutes. If the scheduler ran exactly at 5 minutes, it could try to release a seat at the same moment a user's payment request arrives at 4:59. Both would hit the same row simultaneously. The 30-second gap ensures the payment either completes or gets rejected by the payment endpoint's own time check before the scheduler touches the row.

**Two-pass booking validation**
Before writing anything, all requested seats are validated in a single pass. Only if every seat is available does the write happen. If one seat fails, the whole request is rejected with zero DB writes — no partial state to clean up.

---

## Notes

- First admin must be inserted directly into the DB with role = ADMIN. After that use POST /admin/createadmin
- Seats are auto-generated per show in A1 to L8 format (12 rows x 8 seats = 96 seats)
- Cancellation allowed only more than 3 hours before show time
- Payment window is 5 minutes from booking — after that seats are released automatically
