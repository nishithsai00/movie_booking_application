# 🎬 ReserveShow — Movie Ticket Booking Backend

A production-grade REST API backend replicating core BookMyShow features. Built with Spring Boot, featuring JWT authentication, concurrent seat booking with pessimistic locking, automated seat release scheduling, and comprehensive exception handling.

---

## ⚙️ Tech Stack

| Technology | Purpose |
|------------|---------|
| Java 21 | Core language |
| Spring Boot 3.5 | Application framework |
| Spring Security | Authentication & authorization |
| JWT (jjwt 0.12.6) | Stateless token-based auth |
| Spring Data JPA + Hibernate | ORM & database access |
| MySQL 8 | Relational database |
| Maven | Build & dependency management |

---

## 🚀 Key Features

- **JWT Authentication** — Stateless login with role-based access control (USER / ADMIN)
- **Pessimistic Locking** — DB-level write locks on seat rows prevent double-booking under concurrent requests
- **Seat Hold & Expiry** — Seats locked for 5 minutes during booking; automatically released if payment is not completed
- **Scheduled Job** — Spring `@Scheduled` task runs every 60 seconds to release expired seat locks
- **Payment Simulation** — Captcha-based payment verification with 5-minute payment timeout
- **Booking Cancellation** — Users can cancel bookings up to 3 hours before show time
- **Global Exception Handling** — Clean error responses for all exceptions including JWT errors, validation failures, and access denial — no stacktraces exposed
- **Input Validation** — Jakarta Bean Validation on all user inputs with descriptive error messages
- **Image Upload** — Movie poster upload and retrieval via multipart API

---

## 🏗️ Project Structure

```
src/main/java/com/nishith/demo/
├── config/
│   ├── JwtFilter.java          # JWT token validation filter
│   ├── MyUserDetails.java      # UserDetails implementation
│   └── Securityconfig.java     # Spring Security filter chain
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
├── repo/                       # Spring Data JPA repositories
├── service/
│   ├── AutoScheduling.java     # Scheduled seat release job
│   ├── BookingsService.java    # Core booking + payment logic
│   ├── JwtService.java
│   ├── MovieService.java
│   ├── ShowService.java
│   ├── TheatherService.java
│   └── UserAuthService.java
└── BookMyShowApplication.java
```

---

## 🛠️ Local Setup

### Prerequisites
- Java 21+
- MySQL 8 running locally
- Maven 3.8+

### Steps

**1. Clone the repository**
```bash
git clone https://github.com/nishithsai00/movie_booking_application
cd movie_booking_application
```

**2. Create MySQL database**
```sql
CREATE DATABASE bookmyshow;
```

**3. Configure application.properties**

Create `src/main/resources/application.properties` with:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bookmyshow
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.open-in-view=false

jwt.secret=YOUR_SECRET_KEY_MIN_32_CHARACTERS_LONG
```

**4. Run the application**
```bash
mvn spring-boot:run
```

App starts at `http://localhost:8080`

---

## 📡 API Endpoints

### Auth (Public)
| Method | URL | Description |
|--------|-----|-------------|
| POST | `/signup` | Register new user |
| POST | `/login` | Login — returns JWT token |

### Movies (Public)
| Method | URL | Auth | Description |
|--------|-----|------|-------------|
| GET | `/movies` | None | Get all movies |
| GET | `/movies/{id}` | None | Get movie by ID |
| GET | `/movie/name/{name}` | None | Search movie by name |
| GET | `/movie/location/{location}` | None | Get movies by city |
| GET | `/movie/{id}/image` | None | Get movie poster image |
| GET | `/movie/sort` | None | Filter/sort movies |
| POST | `/addmovie` | ADMIN | Add movie with optional poster |
| PUT | `/movie/{id}` | ADMIN | Update movie details |

### Shows
| Method | URL | Auth | Description |
|--------|-----|------|-------------|
| GET | `/shows` | USER | Get all shows |
| POST | `/shows` | ADMIN | Create show (auto-generates seats) |
| PUT | `/shows/{id}` | ADMIN | Reschedule existing show |
| DELETE | `/removeshow` | ADMIN | Delete a show |
| GET | `/movie/{showid}` | USER | Get seat availability for a show |
| GET | `/movie/{location}/{name}` | None | Get shows by movie + location |

### Theatres
| Method | URL | Auth | Description |
|--------|-----|------|-------------|
| GET | `/theatres` | None | Get all theatres |
| POST | `/theatres` | ADMIN | Add theatre |
| PUT | `/theatres/{id}` | ADMIN | Edit theatre |
| DELETE | `/theatres` | ADMIN | Delete theatre |
| GET | `/theather/{location}` | None | Get theatres by location |

### Bookings
| Method | URL | Auth | Description |
|--------|-----|------|-------------|
| POST | `/book` | USER | Book seats — returns captcha + total |
| POST | `/payment` | USER | Complete payment with captcha |
| GET | `/mybookings` | USER | Get current user's bookings |
| GET | `/allbookings` | ADMIN | Get all bookings |
| GET | `/booking/{id}` | ADMIN | Get booking by ID |

### Admin
| Method | URL | Auth | Description |
|--------|-----|------|-------------|
| POST | `/admin/createadmin` | ADMIN | Create new admin user |

---

## 🔐 Authentication

All protected endpoints require a `Bearer` token in the Authorization header:

```
Authorization: Bearer <your_jwt_token>
```

Get the token from `POST /login`. Token expires after 11 hours.

---

## 💡 Architecture Decisions

### Why Pessimistic Locking?
Seat booking is a classic race condition. Two users can select seat A1 simultaneously — without locking, both could succeed and double-book. Pessimistic locking acquires a DB-level write lock on the seat row during the transaction, so concurrent requests queue up instead of corrupting data.

### Why Stateless JWT?
No server-side session storage needed. Every request carries its own authentication. Scales horizontally — any instance can validate any token without shared session state.

### Why Scheduled Seat Release?
Seats locked during a booking hold are automatically freed after 5 minutes if payment isn't completed. Prevents seats from being permanently blocked by abandoned bookings.

### Why Two-Pass Booking Logic?
Pass 1 validates ALL seats before touching the DB. Pass 2 writes only after all seats are confirmed available. If validation fails, zero DB writes have occurred — nothing to roll back, no partial state.

---

## 🧪 Running Tests

```bash
mvn test
```

---

## 📝 Notes

- First admin must be created directly in the DB (`role = 'ADMIN'`). After that, use `POST /admin/createadmin`
- Seat naming format: `A1` to `L8` (12 rows × 8 seats = 96 seats per show)
- Cancellation is only allowed more than 3 hours before show time
- Payment must be completed within 5 minutes of booking or seats are released
