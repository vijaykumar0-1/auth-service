# Auth Service

A production-ready authentication & authorization microservice built using Spring Boot.
This service provides secure user authentication using JWT access tokens and database-backed refresh tokens, and is designed to be reused as a standalone auth / identity service in any backend system.

🟢 Deployed on Render and accessible publicly via Swagger UI.

---

✨ Features
	•	User registration
	•	Secure login using Spring Security
	•	JWT access token authentication
	•	Refresh token implementation (stored in DB)
	•	Token refresh flow
	•	Secure logout
	•	Password hashing with BCrypt
	•	Centralized logging (SLF4J)
	•	Input validation (Jakarta Bean Validation)
	•	Swagger / OpenAPI documentation
	•	Docker support
	•	Clean layered architecture

---

🧱 Tech Stack
	•	Java 17
	•	Spring Boot
	•	Spring Security
	•	JWT (io.jsonwebtoken)
	•	MongoDB
	•	Gradle
	•	Swagger / OpenAPI
	•	Docker
	•	SLF4J + Logback

---

📁 Project Structure

src/main/java/com/authservice
├── common        # Shared constants (messages, etc.)
├── config        # Security & application configuration
├── controllers   # REST controllers
├── dto           # Request/response DTOs
├── entity        # MongoDB entities
├── repository    # Data access layer
├── security      # JWT & security filters/providers
├── service       # Business logic (interfaces + implementations)
└── AuthServiceApplication.java

---

🔐 Authentication Flow

1️⃣ User Registration

POST /user/register

Registers a new user with encrypted password storage.

---

2️⃣ Login

POST /auth/login

Request body:
	•	email
	•	password

Response:
	•	access token (JWT)
	•	refresh token (stored in DB)

---

3️⃣ Access Protected APIs

All protected endpoints require:

Authorization: Bearer <ACCESS_TOKEN>

---

4️⃣ Refresh Token

POST /auth/refresh

Used when the access token expires.
Returns a new access token if the refresh token is valid.

---

5️⃣ Logout

POST /auth/logout

Header:
Authorization: Bearer <ACCESS_TOKEN>
	•	Deletes refresh token(s)
	•	Logout is idempotent
	•	Access token expires naturally

---

🔎 Swagger / API Docs

After starting the application:

https://usermanagementapi-k62m.onrender.com/swagger-ui.html

Use the Authorize button and provide:

Bearer <ACCESS_TOKEN>

⚙️ Configuration (application.yml)

This file should NOT be committed.

spring:
data:
mongodb:
uri: mongodb+srv://:@cluster.mongodb.net/authdb
database: authdb

jwt:
secret: your-secret-key
expiration: 3600000
refreshExpiration: 86400000

---

🪵 Logging
	•	Uses SLF4J
	•	Logs login, refresh, and logout events
	•	Never logs passwords or tokens

🧠 Design Notes
	•	Controllers depend on service interfaces
	•	JWT is stateless
	•	Refresh tokens are persisted
	•	Logout invalidates refresh tokens only
	•	Designed to extend for RBAC and multi-tenant systems

---

📌 Status

Stable — v1.0.0

---

👤 Author

Vijay Kumar
Backend Engineer | Java | Spring Boot

📄 License

Open for learning, reuse, and extension.
