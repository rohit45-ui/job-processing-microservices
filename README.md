# 🚀 Enterprise Job Processing & Notification Platform

A production-style Event-Driven Microservices project built using **Spring Boot**, **Spring Cloud**, **Kafka**, **Docker**, **Eureka Discovery**, **API Gateway**, and **Config Server**.

This project demonstrates how multiple independent services communicate asynchronously using Apache Kafka and are managed through Spring Cloud components.

---

# 📌 Architecture

```
                        +----------------------+
                        |      API Gateway     |
                        |       Port 9090      |
                        +----------+-----------+
                                   |
                     +-------------+-------------+
                     |                           |
               Eureka Discovery Server (8761)
                     |
   ---------------------------------------------------------
   |                     |                     |
   |                     |                     |
Job Service         Worker Service      Notification Service
(8081)              (8082)              (8083)
   |                     |                     |
   |                     |                     |
   +-------- Kafka Event Bus ------------------+
                  (Apache Kafka)
                     |
                Config Server
                   (8888)

```

---

# 🛠 Tech Stack

* Java 17
* Spring Boot
* Spring Cloud
* Spring MVC
* Spring Data JPA
* MySQL
* Apache Kafka
* Eureka Discovery Server
* Spring Cloud Config Server
* Spring Cloud Gateway
* Docker
* Maven
* Git & GitHub

---

# 📦 Microservices

## Config Server

* Centralized configuration
* Loads properties from GitHub repository

---

## Eureka Discovery Server

* Service Registration
* Service Discovery

---

## API Gateway

* Single Entry Point
* Load Balancing
* Dynamic Routing

---

## Job Service

Responsibilities:

* Create Job
* Get Job
* Update Job Status
* Publish Kafka Events

---

## Worker Service

Responsibilities:

* Consume Job Events
* Process Job
* Update Job Status
* Publish Completion Event

---

## Notification Service

Responsibilities:

* Consume Completed Job Events
* Send Notification (Console Simulation)

---

# 🔄 Event Flow

```
Client

   │

POST /jobs

   │

   ▼

Job Service

   │

Publish Event

   │

Kafka

   │

Consume

   ▼

Worker Service

   │

Process Job

   │

PATCH Job Status

   │

Publish Completed Event

   │

Kafka

   │

Consume

   ▼

Notification Service

   │

Notification Sent

```

---

# 📂 Project Structure

```
Applications

│

├── ApiGatewayApplication

├── ConfigServerApplication

├── DiscoveryServer

├── JobServiceApplication

├── WorkerServiceApplication

├── NotificationServiceApplication

└── docker
```

---

# ⚙️ Running the Project

## 1. Clone Repository

```
git clone https://github.com/rohit45-ui/job-processing-microservices.git
```

---

## 2. Start Config Server

```
localhost:8888
```

---

## 3. Start Eureka Server

```
localhost:8761
```

---

## 4. Start Kafka

```
docker compose up -d
```

---

## 5. Start Services

* Job Service
* Worker Service
* Notification Service
* API Gateway

---

# 🌐 API Endpoints

## Create Job

POST

```
/jobs
```

Body

```json
{
   "jobName":"Generate Report"
}
```

---

## Get All Jobs

GET

```
/jobs
```

---

## Get Job By Id

GET

```
/jobs/{id}
```

---

# 🖥 Ports

| Service              | Port |
| -------------------- | ---- |
| Config Server        | 8888 |
| Eureka Server        | 8761 |
| API Gateway          | 9090 |
| Job Service          | 8081 |
| Worker Service       | 8082 |
| Notification Service | 8083 |
| Kafka                | 9092 |
| MySQL                | 3306 |

---

# 📸 Screenshots

Add screenshots here:

* Eureka Dashboard
* API Gateway
* Config Server
* Kafka Console
* Postman Requests
* Console Logs

---

# 🚀 Future Improvements

* Docker Compose for all services
* JWT Authentication
* Resilience4j
* Circuit Breaker
* Retry
* Rate Limiter
* Zipkin
* Prometheus
* Grafana
* Kubernetes Deployment

---

# 👨‍💻 Author

**Rohit Andhare**

Java Full Stack Developer

GitHub:
https://github.com/rohit45-ui
