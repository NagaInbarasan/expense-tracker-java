# Expense Tracker

A simple desktop expense-tracking application developed in Java.

## Tech Stack
- **Language**: Java
- **GUI**: Java Swing
- **Database**: SQLite
- **Connectivity**: JDBC

## How to Run

1. Make sure you have JDK installed.
2. Compile the project (from the project root directory):
   ```bash
   javac -cp "lib/sqlite-jdbc.jar" -d out src/*.java src/model/*.java src/dao/*.java src/database/*.java src/service/*.java src/ui/*.java
   ```
3. Run the application:
   - On Windows:
     ```bash
     java -cp "out;lib/sqlite-jdbc.jar" Main
     ```
   - On Mac/Linux:
     ```bash
     java -cp "out:lib/sqlite-jdbc.jar" Main
     ```
