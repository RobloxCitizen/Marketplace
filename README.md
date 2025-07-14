# Marketplace Microservices

A microservices-based project for managing products, orders, users, and notifications. Built using Spring Boot, Kafka, PostgreSQL, and Docker.

## 🧱 Architecture

The project consists of the following microservices:

| Service              | Port | Description                                         |
|----------------------|------|-----------------------------------------------------|
| user-service         | 8082 | Registration, authentication, and user management  |
| product-service      | 8080 | Product management                                  |
| order-service        | 8083 | Order creation and tracking                         |
| notification-service | 8084 | Sending notifications via Kafka                     |

Additional components:
- PostgreSQL — databases for order, product, and user services
- Kafka + Zookeeper — communication between services
- Docker Compose — to run all services in containers

## 📦 Technologies

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- Spring Security (JWT)
- Spring Kafka
- PostgreSQL
- Docker + Docker Compose
- Swagger / OpenAPI

## 🚀 Getting Started

1. Clone the repository:

```bash
git clone https://github.com/your-username/marketplace.git
cd marketplace
