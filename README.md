# Java MFA Authentication System

A Java Swing prototype of a multi-factor authentication (MFA) system for secure university system access. Users sign in with a password, then confirm with a one-time password (OTP) sent by email or SMS (simulated).

## Architecture

Layered, MVC-inspired design:

| Layer | Package | Classes |
|---|---|---|
| Presentation (View) | `gui` | `LoginFrame`, `MFAFrame` |
| Controller | `controller` | `AuthController` |
| Service | `service` | `AuthenticationService` |
| Strategy | `authentication` | `AuthenticationMethod`, `PasswordAuthentication`, `EmailOTPAuthentication`, `SMSOTPAuthentication` |
| Factory | `factory` | `AuthenticationFactory` |
| Model | `model` | `User` |
| Entry point | `app` | `Main` |

Design patterns: **Strategy** (interchangeable authentication methods), **Factory** (creating authentication strategies) and a **layered MVC** structure.

## Requirements

- Java 17+
- Maven 3.8+

## Run

```bash
mvn compile
mvn exec:java -Dexec.mainClass=app.Main
```

You can also run `app.Main` from your IDE. The demo users are hard-coded in `Main.java`, for example `john` / `password123`.

## Testing

```bash
mvn test                                    # JUnit 5 tests + JaCoCo report (target/site/jacoco/index.html)
mvn org.pitest:pitest-maven:mutationCoverage # PIT mutation testing (target/pit-reports/)
```

The tests cover the authentication strategies, factory, service, controller and model. The GUI and entry point are excluded from mutation testing.
