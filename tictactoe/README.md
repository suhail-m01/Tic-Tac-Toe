# TicTacToe — Full Stack Java (Spring Boot)

A production-ready Tic-Tac-Toe game built with Spring Boot, H2 embedded database, Thymeleaf, and Spring Security.

---

## Project Structure

```
tictactoe/
├── pom.xml
└── src/main/
    ├── java/com/tictactoe/
    │   ├── TicTacToeApplication.java       ← Entry point
    │   ├── config/
    │   │   └── SecurityConfig.java         ← Spring Security
    │   ├── controller/
    │   │   ├── AuthController.java         ← Login / Register
    │   │   └── GameController.java         ← Game pages + API
    │   ├── model/
    │   │   ├── User.java                   ← Player entity (JPA)
    │   │   └── GameRecord.java             ← Game history entity
    │   ├── repository/
    │   │   ├── UserRepository.java
    │   │   └── GameRecordRepository.java
    │   └── service/
    │       ├── AIEngine.java               ← Minimax AI
    │       ├── GameService.java            ← Core game logic
    │       └── UserDetailsServiceImpl.java ← Spring Security hook
    └── resources/
        ├── application.properties
        └── templates/
            ├── login.html
            ├── register.html
            ├── game.html                   ← Main game UI
            └── history.html
```

---

## Requirements

- Java 17+
- Maven 3.6+

Check versions:
```bash
java -version
mvn -version
```

---

## Run the Project

```bash
cd tictactoe

# Option 1: Maven wrapper (recommended)
mvn spring-boot:run

# Option 2: Build and run JAR
mvn clean package -DskipTests
java -jar target/tictactoe-1.0.0.jar
```

Open: **http://localhost:8080**

The app auto-creates the H2 database on first run. Data persists in `tictactoe-db.mv.db` file.

---

## Features

| Feature | Details |
|---|---|
| Auth | Register + Login with BCrypt password hashing |
| vs AI (Easy) | Random move selection |
| vs AI (Hard) | **Minimax + Alpha-Beta pruning** — unbeatable |
| vs Friend | Local 2-player on same device |
| Score Tracking | Wins/Losses/Draws persisted in H2 |
| Leaderboard | Live-updating, sorted by wins |
| Game History | Full history with mini board preview |
| Move Log | In-game sidebar showing every move |
| Responsive | Works on mobile too |

---

## Key Concepts (for viva)

**Minimax Algorithm**
- AI explores all possible game states in a tree
- Assigns +10 for AI win, -10 for human win, 0 for draw
- Maximizer (AI) picks highest score, Minimizer (human) picks lowest
- Alpha-Beta pruning cuts branches that can't affect the result → faster

**Spring Security**
- BCryptPasswordEncoder hashes passwords (never stored plain)
- Session-based authentication with CSRF protection
- URL-level access control (only /login and /register are public)

**JPA + H2**
- Entities mapped to DB tables via @Entity annotations
- H2 is file-based so data survives restarts
- Spring Data repositories provide CRUD without SQL

**MVC Pattern**
- Controller receives HTTP request
- Service layer handles business logic
- Repository layer handles DB
- Template (View) renders the response

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.2 |
| Security | Spring Security (BCrypt + Sessions) |
| Database | H2 (embedded, file-based) |
| ORM | Spring Data JPA / Hibernate |
| Templates | Thymeleaf |
| Frontend | HTML5 + CSS3 + Vanilla JS |
| Build | Apache Maven |
