# CampusTrack — Student Course & Attendance Management System

An original Java + MySQL desktop application for managing students, courses, attendance, and marks.

## Features
- Admin login
- Student registration and management
- Course management
- Student enrollment
- Attendance recording
- Marks management
- Search/filter students
- Dashboard statistics
- MySQL database
- Java Swing GUI
- Maven project structure

## Requirements
- Java 17+
- MySQL 8+
- Maven 3.8+

## Database setup

1. Start MySQL.
2. Run `database/campustrack.sql`.
3. Open `src/main/java/com/campustrack/db/DBConnection.java`.
4. Change the MySQL username/password if required.

Default database settings:
- Host: localhost
- Port: 3306
- Database: campustrack
- Username: root
- Password: root

The SQL file creates a default administrator:
- Username: admin
- Password: admin123

## Run

```bash
mvn clean compile
mvn exec:java
```

Or run:

`com.campustrack.Main`

## Project structure

```text
CampusTrack/
├── database/
│   └── campustrack.sql
├── src/main/java/com/campustrack/
│   ├── Main.java
│   ├── db/DBConnection.java
│   ├── model/
│   │   ├── Student.java
│   │   ├── Course.java
│   │   └── User.java
│   ├── dao/
│   │   ├── StudentDAO.java
│   │   └── CourseDAO.java
│   └── ui/
│       ├── LoginFrame.java
│       └── MainFrame.java
├── pom.xml
└── README.md
```

## Academic project note

This project is intentionally different from a Library Management System. Its domain is student academic administration, with separate entities and workflows for students, courses, enrollment, attendance, and marks.
