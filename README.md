<div align="center">

```text
   _____         .__ ___________                     __                 
  /  _  \   ____ |__|__    ___/___________    ____ |  | __ ___________ 
 /  /_\  \ /    \|  | |    |  \_  __ \__  \ _/ ___\|  |/ // __ \_  __ \
/    |    \   |  \  | |    |   |  | \// __ \\  \___|    <\  ___/|  | \/
\____|__  /___|  /__| |____|   |__|  (____  /\___  >__|_ \\___  >__|   
        \/     \/                         \/     \/     \/    \/       
```

### 🎬 Personal Media Discovery & Watchlist Engine

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21" />
  <img src="https://img.shields.io/badge/Spring_Boot-4.1.1-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot 4.1.1" />
  <img src="https://img.shields.io/badge/MongoDB-Atlas-47A248?style=for-the-badge&logo=mongodb&logoColor=white" alt="MongoDB Atlas" />
  <img src="https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Maven" />
  <img src="https://img.shields.io/badge/Status-Active_Development-F59E0B?style=for-the-badge" alt="Status" />
</p>

<kbd>🧭 <a href="#-the-vision">Vision</a></kbd>
<kbd>🏗️ <a href="#-system-architecture">Architecture</a></kbd>
<kbd>🗄️ <a href="#-domain-model--dto-shield">Data Model</a></kbd>
<kbd>🌐 <a href="#-api-control-center">API Reference</a></kbd>
<kbd>⚡ <a href="#-quick-start-terminal">Quick Start</a></kbd>
<kbd>🗺️ <a href="#-engineering-roadmap">Roadmap</a></kbd>

</div>

---

## 🧭 The Vision

<table>
<tr>
<td width="65%" valign="top">

### 💡 Why AniTracker?
Tracking watchlists across half a dozen streaming platforms, sticky notes, and fragmented tracker sites gets messy fast. **AniTracker** is being engineered as the unified backend engine for a multi-user media discovery and personal tracking platform.

Instead of rushing into a bloated full-stack app with fragile foundations, this project takes an **architecture-first approach**—building resilient REST contracts, strict DTO boundaries, centralized error handling, and multi-tier test coverage before scaling into multi-tenant user libraries.

</td>
<td width="35%" valign="top">

### 🎯 Supported Formats
```text
┌────────────────────────────┐
│  ⛩️ ANIME                  │
│  🍿 MOVIE                  │
│  📺 TV_SHOW                │
│  🎨 CARTOON                │
└────────────────────────────┘
```
*Track across 5 lifecycle states:*
`PLANNED` • `IN_PROGRESS` • `COMPLETED` • `ON_HOLD` • `DROPPED`

</td>
</tr>
</table>

> [!IMPORTANT]
> **Platform Scope:** AniTracker is a **metadata catalog and tracking engine**, not a video-hosting or streaming service. It indexes media information, tracks personal watch progress, and links out to official external viewing platforms.

---

## 🛠️ Tech Stack & Tooling Matrix

<table>
  <tr>
    <td align="center" width="25%">
      <h3>☕ Core Runtime</h3>
      <b>Java 21</b><br/>
      <sub>Modern language features, strict typing, and high-performance runtime</sub>
    </td>
    <td align="center" width="25%">
      <h3>🚀 Web Framework</h3>
      <b>Spring Boot 4.1.1</b><br/>
      <sub>REST API routing, dependency injection & lifecycle orchestration</sub>
    </td>
    <td align="center" width="25%">
      <h3>🗄️ Cloud Database</h3>
      <b>MongoDB Atlas</b><br/>
      <sub>NoSQL document persistence via Spring Data MongoDB</sub>
    </td>
    <td align="center" width="25%">
      <h3>🛡️ Quality & Build</h3>
      <b>Maven + JUnit 5</b><br/>
      <sub>Jakarta Validation, Lombok, Mockito & integration testing</sub>
    </td>
  </tr>
</table>

---

## 🏗️ System Architecture

AniTracker enforces strict separation of concerns. External API contracts are completely decoupled from internal MongoDB documents—controllers never touch raw database entities, and clients can never spoof internal database IDs.

```text
╔══════════════════════════════════════════════════════════════════════════════╗
║                         ANITRACKER REQUEST PIPELINE                          ║
╠══════════════════════════════════════════════════════════════════════════════╣
║                                                                              ║
║   [ Client JSON Payload ]                                                    ║
║              │                                                               ║
║              ▼                                                               ║
║   ┌─────────────────────┐      ┌─────────────────────────────────────────┐   ║
║   │   MediaController   │ ───► │ Jakarta @Valid checks MediaRequest DTO  │   ║
║   └──────────┬──────────┘      └─────────────────────────────────────────┘   ║
║              │                                                               ║
║              ▼                                                               ║
║   ┌─────────────────────┐      ┌─────────────────────────────────────────┐   ║
║   │     MediaMapper     │ ───► │ Translates DTO ⇄ Entity (hides DB IDs)  │   ║
║   └──────────┬──────────┘      └─────────────────────────────────────────┘   ║
║              │                                                               ║
║              ▼                                                               ║
║   ┌─────────────────────┐      ┌─────────────────────────────────────────┐   ║
║   │    MediaService     │ ───► │ Enforces safe PUT/DELETE & domain rules │   ║
║   └──────────┬──────────┘      └─────────────────────────────────────────┘   ║
║              │                                                               ║
║              ▼                                                               ║
║   ┌─────────────────────┐      ┌─────────────────────────────────────────┐   ║
║   │   MediaRepository   │ ───► │ Spring Data MongoDB Atlas persistence   │   ║
║   └─────────────────────┘      └─────────────────────────────────────────┘   ║
║                                                                              ║
╚══════════════════════════════════════════════════════════════════════════════╝
```

### ✨ Implemented Foundation (v0.1 Feature Grid)

<table>
  <tr>
    <td width="50%" valign="top">
      <h4>🎞️ Complete Media CRUD</h4>
      Create, inspect, update, and purge media entries across Anime, Movies, TV Shows, and Cartoons with clean HTTP semantics (<code>200</code>, <code>204</code>, <code>400</code>, <code>404</code>).
    </td>
    <td width="50%" valign="top">
      <h4>🛡️ Safe & Idempotent Mutations</h4>
      <code>PUT</code> requests strictly update existing entries (preventing accidental ghost creation), and <code>DELETE</code> operations verify existence before removal.
    </td>
  </tr>
  <tr>
    <td width="50%" valign="top">
      <h4>📦 Strict DTO Boundary</h4>
      Inbound payloads (<code>MediaRequest</code>) strip out ID fields, while outbound payloads (<code>MediaResponse</code>) control exactly what gets serialized to the client.
    </td>
    <td width="50%" valign="top">
      <h4>⚠️ Global Exception Shield</h4>
      A centralized <code>@RestControllerAdvice</code> intercepts validation failures, bad enums, malformed JSON, and missing IDs—returning structured error envelopes.
    </td>
  </tr>
</table>

---

## 🗄️ Domain Model & DTO Shield

```text
┌───────────────────────────┐        ┌───────────────────────────┐        ┌───────────────────────────┐
│   📥 MediaRequest (DTO)   │        │   🗄️ Media (@Document)    │        │  📤 MediaResponse (DTO)   │
├───────────────────────────┤        ├───────────────────────────┤        ├───────────────────────────┤
│ • name        [Required]  │        │ • id          [ObjectId]  │        │ • id                      │
│ • description [Optional]  │ ─────► │ • name                    │ ─────► │ • name                    │
│ • type        [Required]  │ Mapper │ • description             │ Mapper │ • description             │
│ • status      [Required]  │        │ • type        [MediaType] │        │ • type                    │
└───────────────────────────┘        │ • status    [MediaStatus] │        │ • status                  │
  (No ID accepted from user)         └───────────────────────────┘        └───────────────────────────┘
```

> [!WARNING]
> **Upcoming Domain Split (Phase 2 Architecture Refactor):**
> Right now, `status` lives directly inside the `Media` document as a single-user prototype. Before multi-user accounts launch, the schema is being split into three distinct concepts so thousands of users can track the same show without duplicating catalog metadata:
>
> ```text
> ┌──────────────┐     owns     ┌─────────────────────────────┐   references   ┌─────────────────────────┐
> │  👤 User     │ ───────────► │ 📚 UserMedia (LibraryEntry) │ ─────────────► │ 🎬 Media (Shared Info)  │
> │  • Profile   │              │  • Watch Status & Progress  │                │  • Title & Synopsis     │
> │  • Auth/JWT  │              │  • Personal Notes           │                │  • Type, Genres & Links │
> └──────────────┘              └─────────────────────────────┘                └─────────────────────────┘
> ```

---

## 🌐 API Control Center

| Method | Endpoint | Operation | Status Code |
| :--- | :--- | :--- | :--- |
| <kbd>POST</kbd> | `/api/media` | Register a new media title in the catalog | `200 OK` |
| <kbd>GET</kbd> | `/api/media` | Retrieve all tracked media entries | `200 OK` |
| <kbd>GET</kbd> | `/api/media/{id}` | Retrieve a specific media entry by MongoDB ID | `200 OK` |
| <kbd>PUT</kbd> | `/api/media/{id}` | Update an existing media entry (safe check) | `200 OK` |
| <kbd>DELETE</kbd> | `/api/media/{id}` | Delete an existing media entry | `204 No Content` |

### 📡 Interactive Payload Inspector
*(Click any box below to expand the live JSON request/response contract)*

<details open>
<summary><b>🟢 1. Create Media Entry — <code>POST /api/media</code> (200 OK)</b></summary>
<br/>

**Client Request:**
```http
POST /api/media
Content-Type: application/json

{
  "name": "Attack on Titan",
  "description": "A story about humanity fighting against Titans.",
  "type": "ANIME",
  "status": "COMPLETED"
}
```

**Server Response (`200 OK`):**
```json
{
  "id": "664f1b2e8f9a1c2d3e4f5a6b",
  "name": "Attack on Titan",
  "description": "A story about humanity fighting against Titans.",
  "type": "ANIME",
  "status": "COMPLETED"
}
```
</details>

<details>
<summary><b>🟡 2. Validation / Enum Failure — <code>POST & PUT</code> (400 Bad Request)</b></summary>
<br/>

Triggered when `@Valid` catches a blank `name`, malformed JSON, or an invalid enum string (e.g., `"ANIMEE"`):

```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "name": "Name is required"
  }
}
```
</details>

<details>
<summary><b>🔴 3. Missing Resource Lookup — <code>GET, PUT & DELETE</code> (404 Not Found)</b></summary>
<br/>

Interpreted by `GlobalExceptionHandler` when a target MongoDB ID does not exist:

```json
{
  "status": 404,
  "message": "Media not found with id: 664f1b2e8f9a1c2d3e4f9999",
  "errors": null
}
```
</details>

---

## ⚡ Quick Start Terminal

> [!TIP]
> **Zero Hardcoded Secrets:** AniTracker reads database credentials strictly from environment variables (`MONGODB_URI` and `MONGODB_TEST_URI`). Never commit real Atlas connection strings to Git.

```text
┌──────────────────────────────────────────────────────────────────────────────┐
│ 🖥️ STEP 1: Export Environment Variables                                      │
└──────────────────────────────────────────────────────────────────────────────┘
```

**Linux / macOS (Bash/Zsh):**
```bash
export MONGODB_URI="mongodb+srv://<user>:<password>@<cluster>.mongodb.net/anitracker"
export MONGODB_TEST_URI="mongodb+srv://<user>:<password>@<cluster>.mongodb.net/anitracker_test"
```

**Windows (PowerShell):**
```powershell
$env:MONGODB_URI="mongodb+srv://<user>:<password>@<cluster>.mongodb.net/anitracker"
$env:MONGODB_TEST_URI="mongodb+srv://<user>:<password>@<cluster>.mongodb.net/anitracker_test"
```

```text
┌──────────────────────────────────────────────────────────────────────────────┐
│ 🚀 STEP 2: Boot the Engine or Run Tests                                      │
└──────────────────────────────────────────────────────────────────────────────┘
```

```bash
# Start the Spring Boot server (Unix / macOS)
./mvnw spring-boot:run

# Start the Spring Boot server (Windows)
mvnw.cmd spring-boot:run
```

---

## 🧪 Automated Verification Suite

Every architectural boundary is verified before code moves forward. Run `./mvnw test` (or `mvnw.cmd test` on Windows) to execute the full suite:

| Test Suite | Layer / Scope | Verification Target |
| :--- | :--- | :--- |
| 🧩 `AniTrackerApplicationTests` | **Smoke Test** | Ensures the Spring Boot application context wires up cleanly |
| 🔌 `MongoConnectionTest` | **Infrastructure** | Verifies live connectivity and document CRUD against MongoDB |
| ⚙️ `MediaServiceTest` | **Unit (Mockito)** | Tests service business rules, mapper calls & exceptions in isolation |
| 🌐 `MediaControllerTest` | **Web Slice** | Validates REST routing, validation triggers, and HTTP status codes |
| 🔗 `MediaIntegrationTest` | **End-to-End** | Executes full API lifecycles against `MONGODB_TEST_URI` |

---

## 📂 Blueprint & Package Structure

```text
📦 AniTracker
 ┣ 📂 src/main/java/com/codersHub/AniTracker
 ┃ ┣ 📜 AniTrackerApplication.java          # 🚀 Spring Boot entry point
 ┃ ┣ 📂 controller
 ┃ ┃ ┗ 📜 MediaController.java              # 🌐 REST API endpoints (/api/media)
 ┃ ┣ 📂 dto
 ┃ ┃ ┣ 📜 MediaRequest.java                 # 📥 Inbound payload + Jakarta @Valid rules
 ┃ ┃ ┗ 📜 MediaResponse.java                # 📤 Outbound serialized API contract
 ┃ ┣ 📂 entity
 ┃ ┃ ┣ 📜 Media.java                        # 🗄️ MongoDB @Document entity
 ┃ ┃ ┣ 📜 MediaStatus.java                  # 📌 Enum: PLANNED, IN_PROGRESS, COMPLETED...
 ┃ ┃ ┗ 📜 MediaType.java                    # 🎞️ Enum: ANIME, MOVIE, TV_SHOW, CARTOON
 ┃ ┣ 📂 exception
 ┃ ┃ ┣ 📜 ErrorResponse.java                # ⚠️ Standardized JSON error structure
 ┃ ┃ ┣ 📜 GlobalExceptionHandler.java       # 🛡️ @RestControllerAdvice interceptor
 ┃ ┃ ┗ 📜 ResourceNotFoundException.java    # 🔍 Custom 404 domain exception
 ┃ ┣ 📂 mapper
 ┃ ┃ ┗ 📜 MediaMapper.java                  # 🔄 DTO ⇄ Entity conversion engine
 ┃ ┣ 📂 repository
 ┃ ┃ ┗ 📜 MediaRepository.java              # 🔗 Spring Data MongoRepository interface
 ┃ ┗ 📂 service
 ┃   ┗ 📜 MediaService.java                 # 🧠 Core business logic & safe CRUD checks
 ┗ 📂 src/test/java/com/codersHub/AniTracker # 🧪 Unit, Controller & Integration tests
```

---

## 🗺️ Engineering Roadmap

```text
  [ PHASE 1: Core CRUD & DTO Shield ]      ✅ COMPLETED
                  │
                  ▼
  [ PHASE 2: Multi-User Domain Split ]     🚧 NEXT UP (Current Milestone)
                  │
                  ▼
  [ PHASE 3: Auth, JWT & User Libraries ]  ⏳ PLANNED
                  │
                  ▼
  [ PHASE 4: Discovery, Search & Scale ]   🔮 FUTUREHORIZON
```

<details open>
<summary><b>✅ Phase 1: Core Media Engine (Completed)</b></summary>

- [x] Spring Boot 4.1.1 & Java 21 initialization with MongoDB Atlas
- [x] Core `Media` entity, `MediaType` & `MediaStatus` enums, and `MediaRepository`
- [x] Full CRUD REST API with safe `PUT` and `DELETE` existence checks
- [x] `MediaRequest` and `MediaResponse` DTO isolation + `MediaMapper`
- [x] Declarative Jakarta validation & `@RestControllerAdvice` global exception handling
- [x] Complete unit, controller, database connection, and API integration test suites
</details>

<details open>
<summary><b>🚧 Phase 2: Multi-User Architecture Refactor (In Progress)</b></summary>

- [ ] Finalize the multi-user domain model (`User` ➔ `UserMedia` ➔ `Media`)
- [ ] Decouple shared media catalog metadata from personal watch status
- [ ] Define strict data-isolation and ownership rules per user account
- [ ] Refactor existing CRUD services and automated tests around the split architecture
</details>

<details>
<summary><b>⏳ Phase 3: Security, Identity & Personal Libraries (Planned)</b></summary>

- [ ] User registration, profile management, and secure password hashing
- [ ] Spring Security integration with stateless JWT authentication & role-based access control
- [ ] Personal library endpoints with episode/season progress counters and personal notes
</details>

<details>
<summary><b>🔮 Phase 4: Discovery, Optimization & Production (Future)</b></summary>

- [ ] External metadata integration, genres, tags, and official streaming availability links
- [ ] Advanced search, multi-criteria filtering, pagination, and sorting
- [ ] Personalized media recommendation engine
- [ ] Interactive Swagger / OpenAPI documentation
- [ ] MongoDB indexing, caching layers, and production deployment readiness
</details>

---

<div align="center">
  <kbd>⚡ Built incrementally with a focus on clean backend architecture</kbd>
</div>
