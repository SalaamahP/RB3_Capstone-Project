<img width="1331" height="1048" alt="image" src="https://github.com/user-attachments/assets/6ac54181-7ef1-4efa-87bf-67870416dbac" />

# Capstone Project 
Student Event Management System -SEMS

This project is a **web application** built with:
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
