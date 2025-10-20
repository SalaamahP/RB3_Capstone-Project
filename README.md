![WhatsApp Image 2025-09-15 at 18 58 58_27f1cdaf](https://github.com/user-attachments/assets/84dfc110-66a0-48f3-84e7-90c809f736f8)


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
3. Run the script to add an admin

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

Script to add an Admin

INSERT INTO Role (Role_Name, Description)
VALUES ('ADMIN', 'Administrator with full privileges')
ON DUPLICATE KEY UPDATE Role_Name=Role_Name;


INSERT INTO User (Name, Surname, Email, Password, Phone)
VALUES ('Super', 'Admin', 'superadmin@gmail.com', '$2a$10$moT3CeaPmK.9uceAcj0FduN5Ch.A1Bk1nPaniFRhiVL1bmC4pH9kC', '0812345678'); 


SET @adminUserId = (SELECT User_Id FROM User WHERE Email='superadmin@gmail.com' LIMIT 1);
SET @adminRoleId = (SELECT Role_Id FROM Role WHERE Role_Name='ADMIN' LIMIT 1);

INSERT INTO user_role (user_id, role_id)
VALUES (@adminUserId, @adminRoleId);

//--password: pass123
