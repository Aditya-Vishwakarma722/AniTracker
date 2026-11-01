# 🎬 AniTracker

```{=html}
<p align="center">
```
`<img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21" />`{=html}
`<img src="https://img.shields.io/badge/Spring_Boot-4.1.1-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot 4.1.1" />`{=html}
`<img src="https://img.shields.io/badge/MongoDB-Atlas-47A248?style=for-the-badge&logo=mongodb&logoColor=white" alt="MongoDB Atlas" />`{=html}
`<img src="https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Maven" />`{=html}
```{=html}
</p>
```
**AniTracker** is a backend project for discovering and tracking Anime,
Movies, TV Shows, Cartoons, and other forms of media.

The long-term goal is to build a **multi-user personal media discovery
and tracking platform**. Users will be able to explore media
information, find external viewing sources, maintain their own
libraries, track watch progress, and receive recommendations.

AniTracker is **not** a video-hosting or streaming platform. It will
provide media information and links to external services rather than
hosting video content.

The project is being developed incrementally, with a focus on
understanding backend architecture and implementing reliable features
before starting frontend development.

------------------------------------------------------------------------

## 🛠️ Technical Stack

  --------------------------------------------------------------------------
Category         Technology             Purpose
  ---------------- ---------------------- ----------------------------------
☕ **Language**  **Java 21**            Core application development

🚀 **Framework** **Spring Boot 4.1.1**  REST API & backend development

🗄️ **Database**  **MongoDB Atlas**      NoSQL document storage

🔗 **Data        **Spring Data          MongoDB repository & data access
Access**         MongoDB**

📦 **Build       **Maven**              Dependency management & builds
Tool**

⚡ **Utilities** **Lombok**             Boilerplate code reduction

✅               **Jakarta Validation** Request data validation
**Validation**

🧪 **Testing**   **JUnit 5, Mockito &   Unit, controller & integration
Spring Boot Test**     testing
  --------------------------------------------------------------------------

------------------------------------------------------------------------

## ✨ Implemented Features

The current implementation provides the initial **Media CRUD API**. It
is a working foundation, not yet the complete multi-user AniTracker
application.

-   🎞️ **Media CRUD** --- Create, retrieve, update, and delete media
    entries.
-   📋 **Media Types** --- Supports Anime, Movies, TV Shows, and
    Cartoons.
-   📌 **Media Status** --- Supports Planned, In Progress, Completed, On
    Hold, and Dropped.
-   ⭐ **Ratings** --- Optional ratings between `0` and `10`.
-   ✅ **Request Validation** --- Validates required fields and rating
    ranges on create and update requests.
-   ⚠️ **Global Exception Handling** --- Centralized handling for
    validation failures, invalid request values, and missing resources.
-   🔎 **Resource Lookup** --- Retrieve a media entry using its MongoDB
    ID.
-   🛡️ **Safe Updates** --- Updates only existing media resources; a PUT
    request does not create a missing resource.
-   🗑️ **Safe Deletion** --- Checks that a media resource exists before
    deleting it.
-   📡 **HTTP Responses** --- Uses appropriate response statuses,
    including `200 OK`, `400 Bad Request`, `404 Not Found`, and
    `204 No Content`.
-   📦 **DTO-based Requests** --- Uses `MediaRequest` to separate
    incoming API data from the MongoDB entity.
-   📤 **DTO-based Responses** --- Uses `MediaResponse` to control the
    data returned by the API.
-   🔄 **Centralized Mapping** --- Uses `MediaMapper` to convert between
    request DTOs, entities, and response DTOs.
-   🧪 **Automated Tests** --- Includes application-context, service,
    controller, MongoDB connection, and API integration tests.

> **Current limitation:** Media records are not yet associated with
> individual user accounts. The current CRUD API is an initial
> media-management foundation. Multi-user ownership and personal watch
> tracking will be introduced through a planned domain-model redesign.

------------------------------------------------------------------------

## 🗄️ Current Data Model

### `Media`

The `Media` class represents a media document stored in MongoDB.

  -------------------------------------------------------------------------------
Field           Type            Description
  --------------- --------------- -----------------------------------------------
`id`            `String`        MongoDB document identifier

`name`          `String`        Media title

`description`   `String`        Optional description

`type`          `MediaType`     Anime, Movie, TV Show, or Cartoon

`status`        `MediaStatus`   Planned, In Progress, Completed, On Hold, or
Dropped

`rating`        `Double`        Optional rating between `0` and `10`
-------------------------------------------------------------------------------

**Architecture note:** In the current prototype, `status` and `rating`
are stored directly in `Media`. Before implementing multi-user
libraries, we plan to separate shared media metadata from user-specific
information. Watch status, progress, and a user's personal rating should
belong to that user's library entry rather than to a shared media
record.

### `MediaType`

``` text
ANIME
MOVIE
TV_SHOW
CARTOON
```

### `MediaStatus`

``` text
PLANNED
IN_PROGRESS
COMPLETED
ON_HOLD
DROPPED
```

------------------------------------------------------------------------

## 📦 DTOs & Mapping

AniTracker separates API data from the MongoDB entity using **Data
Transfer Objects (DTOs)**.

### `MediaRequest`

Used when receiving media data from the client.

``` text
MediaRequest
├── name
├── description
├── type
├── status
└── rating
```

The MongoDB `id` is generated and managed by the application/database;
it is not accepted as part of the request DTO.

### `MediaResponse`

Used when returning media data to the client.

``` text
MediaResponse
├── id
├── name
├── description
├── type
├── status
└── rating
```

### `MediaMapper`

Converts:

-   `MediaRequest` → `Media`
-   `Media` → `MediaResponse`

This keeps conversion logic out of the controller and allows the API
contract to evolve independently from the database entity.

------------------------------------------------------------------------

## 🌐 API

### Media Endpoints

  -------------------------------------------------------------------------------
Method     Endpoint            Description                   Success
  ---------- ------------------- ----------------------------- ------------------
`POST`     `/api/media`        Create a new media entry      `200 OK`

`GET`      `/api/media`        Retrieve all media entries    `200 OK`

`GET`      `/api/media/{id}`   Retrieve a specific media     `200 OK`
entry

`PUT`      `/api/media/{id}`   Update an existing media      `200 OK`
entry

`DELETE`   `/api/media/{id}`   Delete an existing media      `204 No Content`
entry
  -------------------------------------------------------------------------------

### Example Request

``` http
POST /api/media
Content-Type: application/json
```

``` json
{
  "name": "Attack on Titan",
  "description": "A story about humanity fighting against Titans.",
  "type": "ANIME",
  "status": "COMPLETED",
  "rating": 9.5
}
```

### Example Response

``` json
{
  "id": "generated-id",
  "name": "Attack on Titan",
  "description": "A story about humanity fighting against Titans.",
  "type": "ANIME",
  "status": "COMPLETED",
  "rating": 9.5
}
```

------------------------------------------------------------------------

## ✅ Validation

Incoming create and update requests are validated using **Jakarta
Validation** through `@Valid` on the request DTO.

Current request validation rules include:

-   `name` cannot be blank.
-   `type` is required.
-   `status` is required.
-   `rating` is optional.
-   If provided, `rating` must be between `0` and `10`.

Invalid enum values and malformed JSON request bodies are also handled
centrally.

Example validation response:

``` json
{
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "name": "Name is required"
  }
}
```

------------------------------------------------------------------------

## ⚠️ Exception Handling

AniTracker uses a centralized `GlobalExceptionHandler` annotated with
`@RestControllerAdvice`.

### Resource Not Found

When a requested media ID does not exist:

``` json
{
  "status": 404,
  "message": "Media not found with id: example-id",
  "errors": null
}
```

The same not-found handling is used for invalid GET, PUT, and DELETE
resource IDs.

### Invalid Request Values

Invalid enum values (for example, `ANIMEE` instead of `ANIME`) and
unreadable JSON request bodies return `400 Bad Request` with an
explanatory error.

### Current HTTP Statuses

  ----------------------------------------------------------------------------
Status              Meaning                      Example
  ------------------- ---------------------------- ---------------------------
`200 OK`            Request completed            Create, GET, and PUT
successfully

`204 No Content`    Resource deleted             DELETE
successfully

`400 Bad Request`   Request data is invalid      Validation or invalid JSON
value

`404 Not Found`     Requested resource doesn't   Invalid media ID
exist
  ----------------------------------------------------------------------------

------------------------------------------------------------------------

## 🧪 Testing

The project includes the following test classes:

  -----------------------------------------------------------------------
Test Class                                Purpose
  ----------------------------------------- -----------------------------
`AniTrackerApplicationTests`              Verifies that the Spring
application context loads

`MongoConnectionTest`                     Checks MongoDB connectivity
and basic document operations

`MediaServiceTest`                        Tests service-layer behaviour
using unit tests and mocks

`MediaControllerTest`                     Tests controller/API
behaviour

`MediaIntegrationTest`                    Tests API operations together
with the application and
database
  -----------------------------------------------------------------------

The project has previously passed its implemented test suite with the
required MongoDB test environment variable configured. Run the tests
locally to verify the current checkout and environment.

### Running Tests

``` bash
./mvnw test
```

On Windows:

``` powershell
mvnw.cmd test
```

If Maven is installed globally, `mvn test` can also be used.

------------------------------------------------------------------------

## ⚙️ Configuration

The application reads its MongoDB connection string from an environment
variable rather than storing credentials in source control.

Configure:

``` text
MONGODB_URI
MONGODB_TEST_URI
```

-   `MONGODB_URI` --- MongoDB connection string used by the application.
-   `MONGODB_TEST_URI` --- MongoDB connection string used by tests that
    require a test database.

Set these variables in your IDE run configurations or your local
environment. Do not commit real credentials or connection strings to
GitHub.

------------------------------------------------------------------------

## 📂 Project Structure

``` text
src/
├── main/
│   ├── java/com/codersHub/AniTracker/
│   │   ├── AniTrackerApplication.java
│   │   │
│   │   ├── controller/
│   │   │   └── MediaController.java
│   │   │
│   │   ├── dto/
│   │   │   ├── MediaRequest.java
│   │   │   └── MediaResponse.java
│   │   │
│   │   ├── entity/
│   │   │   ├── Media.java
│   │   │   ├── MediaStatus.java
│   │   │   └── MediaType.java
│   │   │
│   │   ├── exception/
│   │   │   ├── ErrorResponse.java
│   │   │   ├── GlobalExceptionHandler.java
│   │   │   └── ResourceNotFoundException.java
│   │   │
│   │   ├── mapper/
│   │   │   └── MediaMapper.java
│   │   │
│   │   ├── repository/
│   │   │   └── MediaRepository.java
│   │   │
│   │   └── service/
│   │       └── MediaService.java
│   │
│   └── resources/
│       └── application.properties
│
└── test/
    ├── java/com/codersHub/AniTracker/
    │   ├── AniTrackerApplicationTests.java
    │   ├── MongoConnectionTest.java
    │   ├── MediaIntegrationTest.java
    │   ├── controller/
    │   │   └── MediaControllerTest.java
    │   └── service/
    │       └── MediaServiceTest.java
    │
    └── resources/
        └── application.properties
```

------------------------------------------------------------------------

## 🚀 Development Roadmap

### Completed

-   [x] Spring Boot project initialization
-   [x] MongoDB Atlas integration
-   [x] Media entity
-   [x] Media type and status enums
-   [x] MongoDB repository
-   [x] Service layer
-   [x] REST controller
-   [x] Media CRUD operations
-   [x] Request validation
-   [x] Global exception handling
-   [x] Resource not found handling
-   [x] Invalid request/enum value handling
-   [x] Safe PUT/update handling
-   [x] Safe DELETE handling
-   [x] Appropriate HTTP status responses
-   [x] `MediaRequest` and `MediaResponse` DTOs
-   [x] `MediaMapper`
-   [x] DTO integration across media endpoints
-   [x] MongoDB connection test
-   [x] Service unit tests
-   [x] Controller tests
-   [x] Media integration tests

### Next: Foundation & Architecture

-   [ ] Review and finalize the multi-user domain model
-   [ ] Separate shared media metadata from personal library data
-   [ ] Design the `User` and `UserMedia` relationship
-   [ ] Define ownership and data-isolation rules
-   [ ] Refactor existing media CRUD around the finalized model
-   [ ] Add/update tests for the revised architecture

### Planned

-   [ ] User registration and profile management
-   [ ] Password hashing and login
-   [ ] Spring Security integration
-   [ ] JWT-based authentication
-   [ ] Authorization and role-based access control
-   [ ] Personal media libraries
-   [ ] Watch status and episode/season progress
-   [ ] Personal ratings and notes
-   [ ] Media discovery and external metadata integration
-   [ ] Genres, tags, and categories
-   [ ] Search, filtering, pagination, and sorting
-   [ ] External streaming/watch links and availability information
-   [ ] Recommendation functionality
-   [ ] Swagger / OpenAPI documentation
-   [ ] Database indexes and query optimization
-   [ ] Caching and other performance improvements
-   [ ] Deployment and production-readiness improvements

------------------------------------------------------------------------

## 🎯 Project Goal

AniTracker aims to evolve from an initial Media CRUD API into a
**multi-user media discovery and personal tracking backend**.

The intended high-level architecture separates three important concepts:

``` text
User
  │
  │ owns
  ▼
UserMedia / LibraryEntry
  │
  │ references
  ▼
Media
```

-   **User** --- account and profile information.
-   **Media** --- shared information about a title, such as its name,
    description, type, genres, and release details.
-   **UserMedia / LibraryEntry** --- a user's relationship with a title,
    including watch status, progress, and personal rating.

This separation will allow multiple users to track the same title
independently without duplicating its shared metadata.

The development roadmap is:

``` text
Project Setup
    ↓
MongoDB Integration
    ↓
Media CRUD
    ↓
Validation & Exception Handling
    ↓
DTOs & Mapping
    ↓
Testing
    ↓
Multi-user Domain Design
    ↓
User Management
    ↓
Authentication & Authorization
    ↓
Personal Media Libraries
    ↓
Watch Progress
    ↓
Media Discovery & External Data
    ↓
Search, Filtering & Recommendations
    ↓
Documentation, Optimization & Deployment
```

The goal is to build AniTracker incrementally while understanding
**why** each component is needed, rather than simply adding features
without understanding their purpose.

> 🚧 **Status: Active Development**
>
> The initial Media CRUD foundation, MongoDB integration, request
> validation, centralized exception handling, DTO mapping, and initial
> automated tests are implemented. The next milestone is to finalize the
> multi-user domain architecture before introducing user accounts,
> authentication, and personal libraries.
