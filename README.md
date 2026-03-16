I see the confusion! You want all of it in **one single document** so you don't have to piece it together.

I've combined everything—the Overview, the Tech Stack, the Data Flow, and the full "How to Run" guide (including steps 3 and 4)—into this one block below.

**Delete everything currently in your `README.md` and replace it with this:**

---

```markdown
# ⚡ No Reboot HQ: Event-Driven Configuration Manager

## 📖 Overview
In modern enterprise microservices, restarting a server just to update a configuration variable (like a feature toggle, theme color, or maintenance mode) causes unacceptable downtime. **No Reboot HQ** solves this by utilizing an event-driven architecture to broadcast configuration changes in real-time across a distributed system. 

When an admin updates a setting via the React UI, the Spring Boot application updates the PostgreSQL database, refreshes the high-speed Redis cache, and publishes a message to an Apache Kafka topic. Any subscribed microservice can listen to this topic and instantly update its internal state—**zero downtime, zero reboots.**

## 🏗️ Tech Stack
* **Frontend:** React (Vite), HTML5, CSS3, JavaScript (ES6+)
* **Backend:** Java 17, Spring Boot 3, Spring Data JPA, Spring Kafka, Spring Data Redis
* **Messaging:** Apache Kafka (Message Broker)
* **Database & Cache:** PostgreSQL, Redis
* **Infrastructure:** Docker, Docker Compose

## 🔄 The Data Flow
1.  **UI Trigger:** User updates a configuration (e.g., `THEME_COLOR`) via the React Dashboard.
2.  **API Processing:** Spring Boot receives the request, persists it to **PostgreSQL**, and evicts the **Redis** cache.
3.  **Event Broadcast:** The application acts as a **Kafka Producer**, sending a JSON update message to the `config-updates` topic.
4.  **Real-Time Reaction:** A **Kafka Listener** (Consumer) intercepts the message instantly, allowing the system to update without a restart.

---

## 🚀 How to Run Locally

### 1. Prerequisites
* Docker Desktop installed and running
* Java 17 (JDK)
* Node.js (v18+)

### 2. Start the Infrastructure (Docker)
From the `config-manager` directory, run:
```bash
docker-compose up -d

```

*This starts PostgreSQL (5432), Redis (6379), and Kafka (9092) in the background.*

### 3. Launch the Backend (Spring Boot)

Inside the `config-manager` directory, run:

```bash
./mvnw clean spring-boot:run

```

*The API will be live at `http://localhost:8080`.*

### 4. Launch the Frontend (React)

Open a **new terminal window**, navigate to the `noreboot-ui` directory, and run:

```bash
npm install
npm run dev

```

*The UI will be live at `http://localhost:5173`. Hold Cmd and click the link to view the dashboard!*

---

## 👨‍💻 Key Accomplishments

* **Full-Stack Integration:** Configured CORS to allow secure communication between React and Spring Boot.
* **Real-Time Messaging:** Implemented a Pub/Sub model using Kafka to eliminate system downtime for config changes.
* **Performance Optimization:** Utilized Redis as a Cache-Aside layer to reduce PostgreSQL read latency and ensure high availability.

---

**Author:** Pratyush Singh