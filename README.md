# TDSE-Taller-de-modularizacion-con-virtualizacines-Introduccion-a-Docker

### Docker Virtualization & AWS Deployment

![Java](https://img.shields.io/badge/Java-17-orange)
![Maven](https://img.shields.io/badge/Maven-Build-red)
![Docker](https://img.shields.io/badge/Docker-Container-blue)
![AWS](https://img.shields.io/badge/AWS-EC2-yellow)
![Status](https://img.shields.io/badge/Project-Academic-green)

---

# Author

**Ricardo Andres Ayala**  
Systems Engineering Student  

---

# Table of Contents

1. Project Overview  
2. Objectives  
3. System Architecture  
4. Project Structure  
5. Framework Design  
6. API Endpoints  
7. Local Execution  
8. Docker Containerization  
9. Running Multiple Containers  
10. Docker Compose Configuration  
11. DockerHub Deployment  
12. AWS Deployment  
13. Concurrency Implementation  
14. Graceful Shutdown  
15. Technologies Used  
16. Screenshots  
17. Conclusions  

---

# 1. Project Overview

This project presents the development of a **minimal Java Web Framework** implemented completely **from scratch**, without using frameworks such as Spring.

The framework provides the basic infrastructure required to build lightweight web applications, including routing, request handling, response generation, and static file serving.

Once the framework was implemented, the application was:

- Containerized using **Docker**
- Published to **DockerHub**
- Deployed in the cloud using **AWS EC2**

The objective of the project is to demonstrate concepts related to:

- Software modularization
- Microframework design
- Containerization
- Distributed execution
- Cloud deployment

---

# 2. Objectives

The main objectives of the project are:

- Design and implement a **minimal Java micro web framework**
- Implement **HTTP request routing**
- Support **static file serving**
- Create a **modular architecture**
- Containerize the application using **Docker**
- Deploy the application in **AWS EC2**

---

# 3. System Architecture

The system follows a **layered modular architecture**, separating the responsibilities of request handling, routing, controller logic, and response generation.

```
Client (Browser)
│
▼
HTTP Request
│
▼
MicroFramework Server
│
┌───────────────┐
│ Route Handler │
└───────────────┘
│
┌──────────────────┐
│ ControllerLoader │
└──────────────────┘
│
┌────────────────────┐
│ Static File Handler │
└────────────────────┘
│
▼
Controller
│
▼
HTTP Response

```

---

# 4. Project Structure

````
MicroFrameworkProject
│
├── src
│   └── main
│       └── java
│           └── org/example
│
│               ├── Main.java
│               │
│               ├── framework
│               │   ├── MicroFramework.java
│               │   ├── Request.java
│               │   ├── Response.java
│               │   ├── ControllerLoader.java
│               │
│               └── controllers
│                   └── GreetingController.java
│
├── webroot
│   └── index.html
│
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md

````

---

# 5. Framework Design

The framework was implemented using **Java sockets** and a basic **HTTP protocol parser**.

The core class `MicroFramework` manages:

- HTTP request parsing
- Route registration
- Static file handling
- Response generation

---

## Route Registration

```java
get("/hello", (req, res) -> {
    res.addHeader("Content-Type", "text/plain");
    return "Hello " + req.getValues("name");
});


---
```
## Static File Configuration

```java
staticfiles("webroot");
```

---

## Server Initialization

```java
start(8080);
```

---

# 6. API Endpoints

## Hello Endpoint

```
GET /hello?name
```

Example:

```
http://localhost:8080/hello?name
```

Response

```
Hello
```

---

## Pi Endpoint

```
GET /pi
```

Example:

```
http://localhost:8080/pi
```

Response

```
3.141592653589793
```

---

# 7. Local Execution

## Compile the Project

```bash
mvn clean install
```

This command generates:

```
target/classes
target/dependency
```

Where all required dependencies are copied.

---

## Run the Application

```bash
java -cp "target/classes:target/dependency/*" org.example.Main
```

Then open your browser:

```
http://localhost:8080/hello
```

---

# 8. Docker Containerization

The application was packaged into a Docker container using the following configuration.

## Dockerfile

```dockerfile
FROM openjdk:17

WORKDIR /usrapp/bin

ENV PORT 6000

COPY target/classes /usrapp/bin/classes
COPY target/dependency /usrapp/bin/dependency

CMD ["java","-cp","./classes:./dependency/*","org.example.Main"]
```

---

# 9. Build Docker Image

Build the container image:

```bash
docker build -t microframework-app .
```

Verify the image:

```bash
docker images
```

---

# 10. Running Multiple Containers

Run multiple container instances to simulate distributed execution.

```bash
docker run -d -p 34000:6000 --name container1 microframework-app
docker run -d -p 34001:6000 --name container2 microframework-app
docker run -d -p 34002:6000 --name container3 microframework-app
```

Verify running containers:

```bash
docker ps
```

Access through the browser:

```
http://localhost:34000/hello
http://localhost:34001/hello
http://localhost:34002/hello
```

---

# 11. Docker Compose Configuration

`docker-compose.yml`

```yaml
version: '2'

services:

  web:
    build:
      context: .
      dockerfile: Dockerfile
    container_name: web
    ports:
      - "8087:6000"

  db:
    image: mongo:3.6.1
    container_name: db
    ports:
      - "27017:27017"
```

Run services:

```bash
docker-compose up -d
```

Verify containers:

```bash
docker ps
```

---

# 12. DockerHub Deployment

Tag the image:

```bash
docker tag microframework-app <dockerhub-user>/microframework-app
```

Login to DockerHub:

```bash
docker login
```

Push the image:

```bash
docker push <dockerhub-user>/microframework-app:latest
```

---

# 13. AWS Deployment

The application was deployed using an **AWS EC2 instance**.

## Install Docker

```bash
sudo yum update -y
sudo yum install docker
```

Start Docker:

```bash
sudo service docker start
```

Add user to Docker group:

```bash
sudo usermod -a -G docker ec2-user
```

Reconnect to the instance for the changes to take effect.

---

## Run Container in AWS

```bash
docker run -d -p 42000:6000 --name microframeworkaws <dockerhub-user>/microframework-app
```

Access the service:

```
http://<EC2-PUBLIC-IP>:42000/hello
```

Example:

```
http://ec2-xx-xx-xx-xx.compute.amazonaws.com:42000/hello
```

---

# 14. Concurrency Implementation

The framework supports **concurrent request handling** through **multi-threading**.

Each incoming connection is processed in an independent thread, allowing the server to handle multiple HTTP requests simultaneously.

---

# 15. Graceful Shutdown

The server includes a **graceful shutdown mechanism** that ensures:

* Active connections are properly closed
* Running threads finish execution
* System resources are safely released

---

# 16. Technologies Used

| Technology     | Purpose                       |
| -------------- | ----------------------------- |
| Java 17        | Core programming language     |
| Maven          | Dependency management         |
| Docker         | Application containerization  |
| Docker Compose | Multi-container configuration |
| AWS EC2        | Cloud deployment              |
| GitHub         | Version control               |

---

# 17. Screenshots

## Local Execution

```
/images/local-run.png
```

## Docker Containers Running

```
/images/docker-containers.png
```

## DockerHub Repository

```
/images/dockerhub.png
```

## AWS Deployment

```
/images/aws-deployment.png
```

---

# 18. Conclusions

This project demonstrates how to:

* Design and implement a **custom Java web framework**
* Apply **modular software architecture principles**
* Package applications using **Docker containers**
* Deploy scalable services in **AWS EC2**

The implementation illustrates key concepts of **modern cloud-native application development**, combining containerization, distributed execution, and infrastructure deployment.
