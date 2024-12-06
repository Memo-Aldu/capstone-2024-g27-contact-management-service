# Contacts Management Service

## Overview
The **Contacts Management Service** is a modular Spring Boot application designed to manage contact information efficiently and integrate seamlessly with the broader communication ecosystem. Built with **Spring Modulith**, this service streamlines the storage, retrieval, and management of contacts for talent acquisition and engagement. It serves as a core component for **DPR Group**, enabling reliable and scalable contact management capabilities.

---
## Features
- **Comprehensive Contact Management**:
  - Create, update, and delete contacts.
  - Search and filter contacts by various attributes.
- **Group Contacts**:
  - Organize contacts into groups for easier segmentation.
- **Integration Ready**:
  - API endpoints for seamless integration with other services like SMS Management.
- **Scalability and Reliability**:
  - Modular design supports scaling for large contact databases.
  - Comprehensive test coverage ensures system reliability.
- **Postman Collection**:
  - Pre-configured API requests for testing and integration.

---

## Architecture
The application is structured into three core modules:
1. **Core Module**:
   - Shared domain models, database configuration, and event listeners.
2. **Contacts Module**:
   - Handles all CRUD operations and search functionality for contacts.
3. **Integration Module**:
   - Provides APIs and hooks for integrating with external systems, such as the SMS Management Service.

Each module is designed for high cohesion and loose coupling, ensuring maintainability and flexibility.

---

## Requirements
- **Java 21**: Required for running the application.
- **Maven**: For dependency management.
- **MongoDBL**: Relational database for contact data storage.
- **Docker**: To run Mongo and the application in containers.

---

## Installation

1. **Clone the Repository**:
   ```bash
   git clone git@github.com:Memo-Aldu/capstone-2024-g27-contacts-management-service.git
   cd capstone-2024-g27-contacts-management-service
   ```

2. **Set Up Environment Variables**:
   - Copy the example file:
     ```bash
     cp .env.example .env
     ```
   - Update `.env` with:
     - PostgreSQL configuration
     - Application-specific settings

3. **Run PostgreSQL**:
   ```bash
   docker-compose up -d postgres
   ```

4. **Start the Application**:
   ```bash
   ./start-dev.sh
   ```

---

## Testing

### Unit and Integration Tests
Run all tests:
```bash
mvn test
```
- **Coverage**:
  - High-level test coverage for contact management features.

---

## Usage

### API Endpoints
The service exposes RESTful endpoints for managing contacts. Refer to the included **Postman Collection** for detailed usage examples.

#### Example Endpoints
- **Create Contact**:
  ```bash
  POST /api/v1/contacts
  ```
- **Search Contacts**:
  ```bash
  GET /api/v1/contacts?query=John
  ```

### Authentication
- OAuth2 Bearer Token is required for all endpoints.
- Use the `auth` endpoint in the Postman Collection to generate tokens.

---

## Configuration

### PostgreSQL Setup
- PostgreSQL runs in a Docker container.
- Configure database connection parameters in the `.env` file.

### Application Settings
- Update application-specific settings like default pagination values in the `.env` file.

---

## Contact
For questions or support, please contact **Memo Aldujaili** at [maldu064@uottawa.ca](mailto:maldu064@uottawa.ca).
