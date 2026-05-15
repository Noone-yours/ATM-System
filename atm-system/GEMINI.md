# ATM System Project - Architecture & Team Standards

## 🏗 Architectural Overview
This project follows a **Feature-Based Modular Architecture**, ensuring that different teams can work on their respective domains with minimal friction.

### Package Structure & Team Ownership:
- **`com.atm.auth` (Member 1)**: Authentication & Authorization.
  - *Core Components*: `SecurityConfig`, `AuthService`.
- **`com.atm.withdraw` (Member 2)**: Withdrawal Logic.
  - *Core Components*: `WithdrawService`, `WithdrawController`.
- **`com.atm.deposit` (Member 3)**: Deposit Logic.
  - *Core Components*: `DepositService`.
- **`com.atm.core` (Member 4)**: Core Data & Persistence.
  - *Core Components*: `AccountRepository`, `TransactionEntity`, `AccountEntity`.
- **`com.atm.receipt` (Member 5)**: Receipt Generation & Printing.
  - *Core Components*: `TemplateGenerator`, `PrintService`.
- **`src/test/java` (Member 6)**: System Integration Testing.
  - *Focus*: End-to-end scenarios and integration flows.

### Layering within Modules:x
Each feature module (except `core`) should follow:
- `*.controller`: API Endpoints.
- `*.service`: Business Logic.
- `*.dto`: Request/Response objects.

## 🔒 Cybersecurity Mandates
As an ATM system, security is the highest priority.

### 1. Authentication & Authorization
- Use **Spring Security** for session management.
- Implement **MFA (Multi-Factor Authentication)** or secure PIN handling.
- Ensure all endpoints are protected by appropriate roles (e.g., `ROLE_USER`, `ROLE_ADMIN`).

### 2. Input Validation
- All inputs (PINs, Amounts) must be strictly validated.
- Amounts must be positive and within reasonable limits.

### 3. Transactional Integrity
- Use `@Transactional` for all balance-modifying operations.
- Ensure atomicity: If a withdrawal fails at any point, the balance must not change.

### 4. Data Protection
- PII (PINs, Account Numbers) must be encrypted or masked in logs.
- Use TLS for all communications.

## 🛠 Tech Stack
- **Runtime**: Java 17
- **Framework**: Spring Boot 3.x
- **Persistence**: Spring Data JPA
- **Database**: PostgreSQL
- **Security**: Spring Security

## 🚀 Development Workflow
1. **Branching**: Each team works on a dedicated feature branch (e.g., `feature/auth`, `feature/withdraw`).
2. **Merge Requests**: Require at least one peer review.
3. **Tests**: All changes must include unit tests. Integration tests are managed by Team 6.
