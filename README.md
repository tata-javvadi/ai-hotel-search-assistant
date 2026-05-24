# AI Hotel Search Assistant

A Spring Boot API that turns natural language hotel search requests into safe PostgreSQL `SELECT` queries, executes them against a hotel database, and returns the generated SQL with matching results.

## Features

- Natural language hotel search powered by Spring AI.
- Groq-compatible OpenAI chat endpoint configuration.
- SQL validation that only allows single `SELECT` statements.
- PostgreSQL-backed hotel, room, and amenity data.
- REST endpoint for searching hotels from a simple JSON request.

## Tech Stack

- Java 17
- Spring Boot 4
- Spring AI
- Spring JDBC
- PostgreSQL
- Maven Wrapper

## Project Structure

```text
src/main/java/com/tatajavvadi/ai_hotel_search
+-- controller      REST API endpoints
+-- model           Request and response records
+-- service         SQL generation, validation, and execution
`-- web             API exception handling

src/main/resources
+-- application.properties
+-- schema.sql      Database tables
`-- data.sql        Seed hotel data
```

## Prerequisites

- Java 17 or newer
- Access to the configured PostgreSQL database
- A Groq API key
- The database password for the configured Neon PostgreSQL database

The application reads secrets from environment variables:

```powershell
$env:GROQ_API_KEY="your-groq-api-key"
$env:DB_PASSWORD="your-database-password"
```

## Run Locally

From the project root:

```powershell
.\mvnw.cmd spring-boot:run
```

The API starts on:

```text
http://localhost:8080
```

## API Usage

Search hotels with a natural language query:

```http
POST /api/hotels/search
Content-Type: application/json
```

Example request:

```json
{
  "query": "Find hotels in Goa under 5000 with Wifi"
}
```

Example curl command:

```powershell
curl -X POST "http://localhost:8080/api/hotels/search" `
  -H "Content-Type: application/json" `
  -d "{\"query\":\"Find hotels in Goa under 5000 with Wifi\"}"
```

Example response shape:

```json
{
  "naturalLanguageQuery": "Find hotels in Goa under 5000 with Wifi",
  "generatedSql": "SELECT ...",
  "valid": true,
  "rowCount": 1,
  "results": []
}
```

## Database

On startup, Spring initializes the database from:

- `src/main/resources/schema.sql`
- `src/main/resources/data.sql`

The schema includes:

- `hotels`
- `rooms`
- `hotel_amenities`

Seed data includes hotels in Goa, Hyderabad, Bangalore, Manali, Jaipur, and Mumbai.

## Safety Notes

Generated SQL is validated before execution. The validator rejects:

- Blank SQL
- Non-`SELECT` statements
- Multiple statements
- Mutating or administrative SQL keywords
- Queries outside the supported hotel schema

## Run Tests

```powershell
.\mvnw.cmd test
```
