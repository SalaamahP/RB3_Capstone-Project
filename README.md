![UML_RB3 drawio](https://github.com/user-attachments/assets/07e21c99-8c32-45c4-becf-2dce6874f4f4)
# Capstone Project – SEMS

This project is a **full-stack web application** built with:
- **Backend:** Java Spring Boot (RESTful APIs, MySQL database)
- **Frontend:** React + Material UI (user interface)
- **Build tools:** Maven (backend), npm/yarn (frontend)

Make sure you have these installed:

- **Java 21** (or the version defined in pom.xml)
- **Node.js (>=18)** + npm (or yarn)
- **MySQL** running locally (or change DB settings)



Database Setup
1. Start MySQL.
2. Update src/main/resources/application.properties with your DB username & password

Running the Backend

From the root folder:

On Linux / Mac
./mvnw spring-boot:run

On Windows (PowerShell / CMD)
mvnw.cmd spring-boot:run

Running the Frontend

cd frontend
npm install   # install dependencies
npm run dev   # start dev server
The frontend will run at: http://localhost:5173

It automatically calls backend APIs at http://localhost:8080.