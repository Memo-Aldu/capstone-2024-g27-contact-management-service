# Contact Management Service

The **Contact Management Service** is a RESTful API designed to manage contacts and contact lists for the **Universal CRM Suite**. It provides endpoints for creating, updating, deleting, and retrieving contact information, as well as managing contact lists associated with users. The service is built with **Spring Boot** and connects to a **PostgreSQL** database for persistent storage.

## Table of Contents
- [Overview](#overview)
- [Configuration](#configuration)
- [Running the Service Locally](#running-the-service-locally)
- [Testing](#testing)
- [Deployment](#deployment)
- [Environment Variables](#environment-variables)
- [Contact](#contact)


## Overview

The **Contact Management Service** manages contact information and contact lists. It allows users to:
- Create, update, and delete contacts.
- Fetch contacts by user or contact list.
- Search for contacts by name.
- Create, update, and delete contact lists.

The service integrates with the **SMS Management Service** and **CRM Gateway**, enabling seamless management of contact-related data across the CRM suite.


## Configuration

The service is configured to run with a **PostgreSQL** database for storing contact information. The application uses **Spring Profiles** to separate environments (e.g., `dev`, `prod`, `qa`).

### Active Spring Profile:
```properties
SPRING_PROFILES_ACTIVE=dev
```

### Server Configuration:
```properties
APP_PORT=8081
APP_HOST=localhost
CONTAINER_PORT=8080
CONTAINER_HOST=0.0.0.0
NETWORK_NAME=contact-service-network
```

### Database Configuration:
```properties
POSTGRES_USERNAME=postgres
POSTGRES_PASSWORD=root
POSTGRES_HOST=postgres
POSTGRES_PORT=5432
POSTGRES_DB=contact_service_db
```

### Hibernate Configuration:
```properties
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_HIBERNATE_DEFAULT_SCHEMA=public
```

## Running the Service Locally

To run the **Contact Management Service** locally:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/Memo-Aldu/capstone-2024-g27-contact-management-service.git
   cd capstone-2024-g27-contact-management-service
   ```

2. **Configure environment variables** for local development in the `.env` or `application.properties` file.

3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

The service should be available at `http://localhost:8081`.


## Testing

The **Contact Management Service** includes several layers of testing:

- **Unit Tests**: Test individual service methods and logic.
- **Integration Tests**: Test interactions between services and the database.
- **End-to-End Tests**: Simulate API interactions to validate functionality.

To run the tests:

```bash
mvn test
```


## Deployment

For deploying the **Contact Management Service**:

1. **Build the project**:
   ```bash
   mvn clean package
   ```

2. **Deploy the service**:
   - Dockerize the service.
   - Deploy to a cloud provider (e.g., Azure or AWS).
   - Ensure environment variables are set for production.

## Environment Variables

- **POSTGRES_USERNAME**: The username for the PostgreSQL database.
- **POSTGRES_PASSWORD**: The password for the PostgreSQL database.
- **POSTGRES_HOST**: The host of the PostgreSQL database.
- **POSTGRES_PORT**: The port of the PostgreSQL database.
- **POSTGRES_DB**: The database name for the contact management service.
- **APP_PORT**: The port for the local application.
- **APP_HOST**: The host for the local application.

Ensure to set the proper values for these variables in both development and production environments.

## Contact
For questions or support, please contact **Memo Aldujaili** at [maldu064@uottawa.ca](mailto:maldu064@uottawa.ca).
