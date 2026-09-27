# 🎬 AniTracker

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21" />
  <img src="https://img.shields.io/badge/Spring_Boot-4.1.1-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot 4.1.1" />
  <img src="https://img.shields.io/badge/MongoDB-Atlas-47A248?style=for-the-badge&logo=mongodb&logoColor=white" alt="MongoDB Atlas" />
  <img src="https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Maven" />
</p>

A personal **media tracking REST API** built with **Spring Boot** and **MongoDB** for managing Anime, Movies, TV Shows, Cartoons, and other types of media.

AniTracker is being developed as a hands-on backend project, starting with a simple CRUD API and gradually expanding toward validation, authentication, authorization, testing, search, filtering, and other backend features.

---

## 🛠️ Technical Stack

| Category           | Technology              | Purpose                          |
| :----------------- | :---------------------- | :------------------------------- |
| ☕ **Language**     | **Java 21**             | Core application development     |
| 🚀 **Framework**   | **Spring Boot 4.1.1**   | REST API & backend development   |
| 🗄️ **Database**   | **MongoDB Atlas**       | NoSQL document storage           |
| 🔗 **Data Access** | **Spring Data MongoDB** | MongoDB repository & data access |
| 📦 **Build Tool**  | **Maven**               | Dependency management & builds   |
| ⚡ **Utilities**    | **Lombok**              | Boilerplate code reduction       |
| ✅ **Validation**   | **Jakarta Validation**  | Request data validation          |

---

## ✨ Current Features

* 🎞️ **Media CRUD** — Create, retrieve, update, and delete media entries.
* 📋 **Media Types** — Supports Anime, Movies, TV Shows, and Cartoons.
* 📌 **Watch Status** — Supports Planned, In Progress, Completed, On Hold, and Dropped.
* ⭐ **Ratings** — Optional ratings from `0` to `10`.
* ✅ **Request Validation** — Validates required fields and rating ranges.
* ⚠️ **Exception Handling** — Centralized handling for validation errors and missing resources.
* 🔎 **Resource Lookup** — Retrieve individual media entries using their ID.
* 🛡️ **Safe Updates** — Updates only existing media resources instead of accidentally creating new documents.
* 🗑️ **Resource Existence Checks** — Delete operations verify that the requested resource exists.

---

## 🗄️ Current Data Model

### `Media`

| Field         | Type     | Description                                          |
| :------------ | :------- | :--------------------------------------------------- |
| `id`          | `String` | MongoDB document identifier                          |
| `name`        | `String` | Media title                                          |
| `description` | `String` | Optional description                                 |
| `type`        | `Enum`   | Anime, Movie, TV Show, or Cartoon                    |
| `status`      | `Enum`   | Planned, In Progress, Completed, On Hold, or Dropped |
| `rating`      | `Double` | Optional rating between `0` and `10`                 |

### `MediaType`

```text
ANIME
MOVIE
TV_SHOW
CARTOON
```

### `MediaStatus`

```text
PLANNED
IN_PROGRESS
COMPLETED
ON_HOLD
DROPPED
```

---

## 🌐 API

### Media Endpoints

| Method   | Endpoint          | Description                     |
| :------- | :---------------- | :------------------------------ |
| `POST`   | `/api/media`      | Create a new media entry        |
| `GET`    | `/api/media`      | Retrieve all media entries      |
| `GET`    | `/api/media/{id}` | Retrieve a specific media entry |
| `PUT`    | `/api/media/{id}` | Update an existing media entry  |
| `DELETE` | `/api/media/{id}` | Delete an existing media entry  |

### Example Request

```http
POST /api/media
Content-Type: application/json
```

```json
{
  "name": "Attack on Titan",
  "description": "A story about humanity fighting against Titans.",
  "type": "ANIME",
  "status": "COMPLETED",
  "rating": 9.5
}
```

### Validation

Invalid requests return a structured error response.

```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "name": "Name is required"
  }
}
```

### Resource Not Found

Requests for non-existent media return:

```json
{
  "status": 404,
  "message": "Media not found with id: example-id",
  "errors": null
}
```

---

## 📂 Project Structure

```text
src/main/java/com/codersHub/AniTracker/
├── controller/
│   └── MediaController.java
├── service/
│   └── MediaService.java
├── repository/
│   └── MediaRepository.java
├── entity/
│   ├── Media.java
│   ├── MediaType.java
│   └── MediaStatus.java
└── exception/
    ├── ResourceNotFoundException.java
    ├── ErrorResponse.java
    └── GlobalExceptionHandler.java
```

---

## 🚀 Development Roadmap

### Completed

* [x] Spring Boot project initialization
* [x] MongoDB Atlas integration
* [x] Media entity
* [x] Media type and status enums
* [x] MongoDB repository
* [x] Service layer
* [x] REST controller
* [x] Basic CRUD operations
* [x] Request validation
* [x] Global exception handling
* [x] Resource not found handling
* [x] Safe update handling
* [x] Delete resource existence check

### Planned

* [ ] DTOs & request/response separation
* [ ] Improved HTTP response handling with `ResponseEntity`
* [ ] User accounts & personal collections
* [ ] User authentication
* [ ] JWT-based security
* [ ] Authorization / RBAC
* [ ] Search & filtering
* [ ] Pagination & sorting
* [ ] Tags & categories
* [ ] Watch progress
* [ ] External links
* [ ] Unit testing with JUnit & Mockito
* [ ] Integration testing
* [ ] Swagger / OpenAPI documentation
* [ ] Database indexes & optimization
* [ ] Caching
* [ ] Advanced backend features

---

## 🎯 Project Goal

AniTracker is being developed as a practical **Spring Boot backend project**.

The project starts with the fundamentals of building a REST API and will gradually introduce more advanced backend concepts as development progresses.

```text
REST API
    ↓
Spring Boot
    ↓
MongoDB
    ↓
CRUD
    ↓
Validation
    ↓
Exception Handling
    ↓
DTOs
    ↓
Authentication & Authorization
    ↓
Testing & Documentation
    ↓
Search, Filtering & Pagination
    ↓
Advanced Backend Concepts
```

The goal is to build AniTracker incrementally while learning how the different parts of a real Spring Boot backend work together.

> 🚧 **Status: Active Development**
>
> AniTracker is currently in its early development phase, with the core Media CRUD functionality implemented.
