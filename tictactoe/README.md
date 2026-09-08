# 🎮 TicTacToe — Full-Stack Java Application

> A full-stack Tic-Tac-Toe web application built with **Java 17 and Spring Boot**, featuring secure user authentication, persistent game history, leaderboards, and an AI opponent powered by the **Minimax algorithm with Alpha-Beta Pruning**.

The project demonstrates full-stack Java development using a structured **MVC architecture**, Spring Security, Spring Data JPA, H2 database persistence, and algorithmic game AI.

---

## ✨ Key Features

- 🔐 Secure user registration and login
- 🔑 BCrypt password hashing
- 🛡️ Spring Security authentication and authorization
- 🤖 Easy AI with randomized move selection
- 🧠 Hard AI using **Minimax + Alpha-Beta Pruning**
- 👥 Local two-player mode
- 🏆 Persistent leaderboard
- 📊 Win / Loss / Draw tracking
- 📜 Complete game history
- 🎯 Move-by-move game log
- 💾 Persistent H2 database
- 📱 Responsive web interface
- ⚡ Spring Boot backend architecture

---

## 🧠 AI Game Engine

The application provides two AI difficulty levels.

### Easy Mode

The AI selects from available board positions using randomized move selection.

This provides a simple opponent suitable for casual gameplay.

### Hard Mode — Minimax

Hard mode uses the **Minimax algorithm** to evaluate possible future game states.

```text
Current Board
     │
     ▼
Generate Possible Moves
     │
     ▼
Build Game State Tree
     │
     ▼
Evaluate Terminal States
     │
     ├── AI Win    → +10
     ├── Draw      →   0
     └── Human Win → -10
     │
     ▼
Minimax Evaluation
     │
     ▼
Alpha-Beta Pruning
     │
     ▼
Select Optimal Move
```

The AI acts as the **maximizing player**, while the human player is treated as the **minimizing player**.

### Alpha-Beta Pruning

Alpha-Beta Pruning improves Minimax efficiency by eliminating branches of the game tree that cannot influence the final decision.

This reduces unnecessary game-state evaluations while preserving the optimal result.

---

## 🏗️ System Architecture

The application follows a layered Spring Boot architecture:

```text
                        ┌─────────────────┐
                        │      User       │
                        └────────┬────────┘
                                 │
                                 ▼
                     ┌─────────────────────┐
                     │ Thymeleaf / HTML UI │
                     └──────────┬──────────┘
                                │
                                ▼
                     ┌─────────────────────┐
                     │     Controller      │
                     │       Layer         │
                     └──────────┬──────────┘
                                │
                  ┌─────────────┴─────────────┐
                  │                           │
                  ▼                           ▼
        ┌──────────────────┐        ┌──────────────────┐
        │   Game Service   │        │ Spring Security  │
        └────────┬─────────┘        └────────┬─────────┘
                 │                           │
                 ▼                           ▼
        ┌──────────────────┐        ┌──────────────────┐
        │    AI Engine     │        │ Authentication   │
        │ Minimax + A/B    │        │   + BCrypt       │
        └────────┬─────────┘        └──────────────────┘
                 │
                 ▼
        ┌──────────────────┐
        │ Repository Layer │
        │ Spring Data JPA  │
        └────────┬─────────┘
                 │
                 ▼
        ┌──────────────────┐
        │   H2 Database    │
        │ Users / Games    │
        └──────────────────┘
```

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.2 |
| **Architecture** | MVC / Layered Architecture |
| **Security** | Spring Security |
| **Password Security** | BCrypt |
| **Authentication** | Session-Based Authentication |
| **Database** | H2 Embedded Database |
| **ORM** | Hibernate / Spring Data JPA |
| **Template Engine** | Thymeleaf |
| **Frontend** | HTML5, CSS3, Vanilla JavaScript |
| **AI Algorithm** | Minimax + Alpha-Beta Pruning |
| **Build Tool** | Apache Maven |

---

## 📂 Project Structure

```text
tictactoe/
│
├── pom.xml
│
└── src/
    └── main/
        │
        ├── java/
        │   └── com/
        │       └── tictactoe/
        │
        │           ├── TicTacToeApplication.java
        │           │   └── Spring Boot application entry point
        │           │
        │           ├── config/
        │           │   └── SecurityConfig.java
        │           │       └── Spring Security configuration
        │           │
        │           ├── controller/
        │           │   ├── AuthController.java
        │           │   │   └── Login and registration
        │           │   │
        │           │   └── GameController.java
        │           │       └── Game pages and API endpoints
        │           │
        │           ├── model/
        │           │   ├── User.java
        │           │   │   └── Player JPA entity
        │           │   │
        │           │   └── GameRecord.java
        │           │       └── Game history JPA entity
        │           │
        │           ├── repository/
        │           │   ├── UserRepository.java
        │           │   └── GameRecordRepository.java
        │           │
        │           └── service/
        │               ├── AIEngine.java
        │               │   └── Minimax AI engine
        │               │
        │               ├── GameService.java
        │               │   └── Core game logic
        │               │
        │               └── UserDetailsServiceImpl.java
        │                   └── Spring Security integration
        │
        └── resources/
            │
            ├── application.properties
            │
            └── templates/
                ├── login.html
                ├── register.html
                ├── game.html
                └── history.html
```

---

## 🔐 Authentication & Security

The application uses **Spring Security** for authentication and access control.

### Password Security

Passwords are hashed using:

```text
BCryptPasswordEncoder
```

Plain-text passwords are therefore not intended to be stored directly in the database.

### Session Authentication

After successful login, Spring Security maintains the authenticated user's session.

Protected application routes require authentication.

### Access Control

Public routes include:

```text
/login
/register
```

Game functionality and user-specific information are protected behind authentication.

### CSRF Protection

Spring Security's CSRF protection is used to protect state-changing requests against Cross-Site Request Forgery attacks.

---

## 💾 Database Design

The application uses an embedded **H2 database** with Spring Data JPA.

Primary entities include:

### User

Stores registered player information and authentication-related data.

### GameRecord

Stores completed game information used for:

- Game history
- Wins
- Losses
- Draws
- Leaderboard statistics

The database is file-based, allowing data to persist across application restarts.

```text
User
 │
 │ plays
 ▼
GameRecord
 │
 ├── Result
 ├── Opponent Type
 ├── Moves
 └── Game Information
```

---

## 🎯 Game Modes

### 👤 Player vs Easy AI

The computer selects randomized valid moves.

### 🧠 Player vs Hard AI

The computer uses:

**Minimax + Alpha-Beta Pruning**

to calculate an optimal move.

### 👥 Player vs Player

Two players can play locally on the same device.

---

## 📊 Score Tracking

The application maintains player statistics including:

```text
Wins
Losses
Draws
```

These results are stored persistently and used by the leaderboard system.

---

## 🏆 Leaderboard

The leaderboard displays player performance and ranks users based on recorded game results.

Game results are persisted through:

```text
Spring Data JPA
        ↓
Hibernate
        ↓
H2 Database
```

---

## 📜 Game History

Authenticated users can view previously completed games.

The history interface includes stored game information and a compact board representation for reviewing previous matches.

---

## 🧩 Core Concepts Demonstrated

This project demonstrates several important software engineering concepts.

### Minimax Algorithm

Minimax explores possible future game states and determines the optimal move assuming both players make rational decisions.

```text
AI          → Maximizer
Human       → Minimizer

AI Win      → +10
Draw        → 0
Human Win   → -10
```

### Alpha-Beta Pruning

Optimizes Minimax by avoiding branches that cannot affect the final decision.

### Spring Security

Provides:

- Authentication
- Authorization
- Session management
- BCrypt password hashing
- CSRF protection

### Spring Data JPA

Provides repository abstractions for interacting with database entities without manually implementing standard CRUD operations.

### Hibernate

Maps Java objects to relational database tables through ORM.

### MVC Architecture

The project separates application responsibilities into:

```text
Model
  ↓
Application Data

View
  ↓
Thymeleaf / HTML

Controller
  ↓
HTTP Request Handling

Service
  ↓
Business Logic

Repository
  ↓
Database Access
```

This improves maintainability and separation of concerns.

---

## 🚀 Getting Started

### Prerequisites

Make sure the following are installed:

- Java 17+
- Maven 3.6+
- Git

Check your installed versions:

```bash
java -version
```

```bash
mvn -version
```

---

## 📥 Installation

### 1. Clone the Repository

```bash
git clone https://github.com/suhail-m01/Tic-Tac-Toe.git
```

### 2. Navigate to the Project

```bash
cd Tic-Tac-Toe/tictactoe
```

### 3. Run with Maven

```bash
mvn spring-boot:run
```

Alternatively, build the application first:

```bash
mvn clean package -DskipTests
```

Then run the generated JAR:

```bash
java -jar target/tictactoe-1.0.0.jar
```

---

## 🌐 Access the Application

After starting the server, open:

```text
http://localhost:8080
```

The H2 database is automatically initialized when the application starts.

Persistent application data is stored in:

```text
tictactoe-db.mv.db
```

---

## 🖥️ Application Preview

Add your actual screenshots to an `assets/` folder and replace the paths below.

### 🔐 Login & Registration

```markdown
![Login Page](assets/login.png)
```

### 🎮 Game Interface

```markdown
![Game Interface](assets/game.png)
```

### 🏆 Leaderboard

```markdown
![Leaderboard](assets/leaderboard.png)
```

### 📜 Game History

```markdown
![Game History](assets/history.png)
```

> Remove this instruction block after adding the actual screenshots.

---

## 🎓 Learning Outcomes

Through this project, I explored and implemented:

- Full-stack development using Spring Boot
- MVC and layered application architecture
- Authentication and authorization
- Secure password storage with BCrypt
- Database persistence using JPA/Hibernate
- Algorithmic AI using Minimax
- Alpha-Beta search optimization
- Server-side rendering using Thymeleaf
- Game-state management
- Persistent user statistics

---

## 🔮 Future Improvements

Potential enhancements include:

- [ ] Online multiplayer
- [ ] WebSocket-based real-time gameplay
- [ ] OAuth2 / social authentication
- [ ] PostgreSQL/MySQL production database
- [ ] REST API architecture
- [ ] Docker containerization
- [ ] Automated unit and integration tests
- [ ] Player ranking system
- [ ] Matchmaking
- [ ] Deployment to a cloud platform

---

## ⚠️ Project Scope

This project is primarily designed to demonstrate **full-stack Java development, Spring Boot architecture, security, persistence, and game-tree algorithms**.

H2 is used for convenient local persistence. A production deployment would typically use a production-grade database and additional operational security, testing, monitoring, and deployment controls.

---

## 👨‍💻 Author

**Suhail Ahamed**

MCA Student | Python & Java Developer | AI/ML Enthusiast

Interested in building applications using **AI, algorithms, backend systems, APIs, automation, and full-stack technologies**.

---

## ⭐ Support

If you found this project interesting, consider giving the repository a ⭐.# TicTacToe — Full Stack Java (Spring Boot)

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
