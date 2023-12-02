# Account Service Application

This application is designed for managing sign up and login accounts.

## Diagrams
* [Sign Up Diagram](diagrams/SignUpDiagram.jpeg)
* [Login Diagram](diagrams/LoginDiagram.jpeg)
* [Component Diagram](diagrams/ComponentDiagram.jpeg)

## Endpoints

The API exposes the following endpoints:

* `POST /sign-up`: Create an account.
* `GET /login`: Login to the system.

For examples, refer to the Postman collection provided.
[Postman collection](postman/account service.postman_collection.json)

## Main Frameworks and Libraries

| Feature                      | Library                 |
|------------------------------|-------------------------|
| Build tool                   | Gradle                  |
| HTTP server                  | Spring Boot Starter Web | 
| Database integration         | H2                      |


## Environment setup

### Prerequisites

Ensure you have the following tools installed:

1. Java 11
2. Gradle 7.4

### Starting the Applications

1. Run AccountServiceApplication.java to start the API.

### Improvements

Here's a roadmap for enhancing the Coupons application:

1. Increase Test Coverage
   * Expand unit test coverage for critical components.
   * Consider adding tests for edge cases and error scenarios.

2. Integration Tests
   * Introduce integration tests to ensure seamless collaboration between modules.
   * Test the interaction of various components in a real-world scenario.

3. REST Services Validations
   * Strengthen input validations for REST services.

4. Swagger Documentation
   * Integrate Swagger for comprehensive API documentation.
   * Enable developers to understand and interact with the API effortlessly.