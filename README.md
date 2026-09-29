# 🎬 AniTracker

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21" />
  <img src="https://img.shields.io/badge/Spring_Boot-4.1.1-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot 4.1.1" />
  <img src="https://img.shields.io/badge/MongoDB-Atlas-47A248?style=for-the-badge&logo=mongodb&logoColor=white" alt="MongoDB Atlas" />
  <img src="https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Maven" />
</p>

A personal **media tracking REST API** built with **Spring Boot** and **MongoDB** for managing Anime, Movies, TV Shows, Cartoons, and other types of media.

AniTracker is being developed as a hands-on backend project, starting with a structured CRUD API and gradually expanding toward authentication, authorization, testing, search, filtering, and other backend concepts.

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
* ✅ **Request Validation** — Validates required fields and rating ranges for create and update requests.
* ⚠️ **Global Exception Handling** — Centralized handling of validation and resource-not-found errors.
* 🔎 **Resource Lookup** — Retrieve individual media entries using their ID.
* 🛡️ **Safe Updates** — Updates only existing media resources and prevents accidental creation through PUT requests.
* 🗑️ **Safe Deletion** — Verifies that a media resource exists before deleting it.
* 📡 **Explicit HTTP Responses** — Uses appropriate HTTP status codes such as `400`, `404`, and `204`.
* 📦 **DTO-based API Requests** — Uses `MediaRequest` to separate incoming API data from the database entity.
* 📤 **DTO-based API Responses** — Uses `MediaResponse` to control the data exposed by the API.

---

## 🗄️ Data Model

### `Media`

The `Media` class represents a media document stored in MongoDB.

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

## 📦 DTOs

AniTracker separates API data from the MongoDB entity using **Data Transfer Objects (DTOs)**.

### `MediaRequest`

Used when receiving media data from the client.

```text
MediaRequest
├── name
├── description
├── type
├── status
└── rating
```

The MongoDB `id` is intentionally not accepted from the client when creating media.

### `MediaResponse`

Used when returning media data to the client.

```text
MediaResponse
├── id
├── name
├── description
├── type
├── status
└── rating
```

This separation allows the API contract to evolve independently from the database entity.

---

## 🌐 API

### Media Endpoints

| Method   | Endpoint          | Description                     | Success          |
| :------- | :---------------- | :------------------------------ | :--------------- |
| `POST`   | `/api/media`      | Create a new media entry        | `200 OK`         |
| `GET`    | `/api/media`      | Retrieve all media entries      | `200 OK`         |
| `GET`    | `/api/media/{id}` | Retrieve a specific media entry | `200 OK`         |
| `PUT`    | `/api/media/{id}` | Update an existing media entry  | `200 OK`         |
| `DELETE` | `/api/media/{id}` | Delete an existing media entry  | `204 No Content` |

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

### Example Response

```json
{
  "id": "generated-id",
  "name": "Attack on Titan",
  "description": "A story about humanity fighting against Titans.",
  "type": "ANIME",
  "status": "COMPLETED",
  "rating": 9.5
}
```

---

## ✅ Validation

Incoming create and update requests are validated using **Jakarta Validation**.

Current validation rules include:

* `name` cannot be blank.
* `type` is required.
* `status` is required.
* `rating` is optional.
* If provided, `rating` must be between `0` and `10`.

Example validation response:

```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "name": "Name is required"
  }
}
```

---

## ⚠️ Exception Handling

AniTracker uses a centralized `GlobalExceptionHandler` for handling common API errors.

### Resource Not Found

When a requested media ID does not exist:

```json
{
  "status": 404,
  "message": "Media not found with id: example-id",
  "errors": null
}
```

The same handling is used for invalid GET, PUT, and DELETE resource IDs.

### Current HTTP Statuses

| Status            | Meaning                          | Example            |
| :---------------- | :------------------------------- | :----------------- |
| `200 OK`          | Request completed successfully   | GET, POST, PUT     |
| `204 No Content`  | Resource deleted successfully    | DELETE             |
| `400 Bad Request` | Request validation failed        | Invalid media data |
| `404 Not Found`   | Requested resource doesn't exist | Invalid media ID   |

---

## 📂 Project Structure

```text
src/main/java/com/codersHub/AniTracker/
├── controller/
│   └── MediaController.java
│
├── dto/
│   ├── MediaRequest.java
│   └── MediaResponse.java
│
├── entity/
│   ├── Media.java
│   ├── MediaType.java
│   └── MediaStatus.java
│
├── repository/
│   └── MediaRepository.java
│
├── service/
│   └── MediaService.java
│
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
* [x] Safe PUT/update handling
* [x] Safe DELETE handling
* [x] Appropriate HTTP status responses
* [x] `MediaRequest` DTO
* [x] `MediaResponse` DTO
* [x] Request-to-entity mapping
* [x] Entity-to-response mapping

### Planned

* [ ] Complete DTO integration across all endpoints
* [ ] Improve DTO/entity mapping structure
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

AniTracker is being developed as a practical **Spring Boot backend project**, gradually evolving from a basic CRUD application into a structured multi-user media management platform.

The project focuses on learning how the different layers of a backend application work together while introducing professional backend practices incrementally.

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
DTOs & Data Mapping
    ↓
Authentication & Authorization
    ↓
Testing & Documentation
    ↓
Search, Filtering & Pagination
    ↓
Advanced Backend Concepts
```

The goal is to build AniTracker incrementally while understanding **why** each component is needed, rather than simply adding features without understanding their purpose.

> 🚧 **Status: Active Development**
>
> AniTracker is currently in its early development phase, with the core Media CRUD API, validation, exception handling, and initial DTO-based request/response separation implemented.
