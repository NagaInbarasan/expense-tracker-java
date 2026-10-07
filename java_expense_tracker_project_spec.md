# Java Expense Tracker — College Project Specification

## 1. Project Overview

**Project Name:** Expense Tracker  
**Purpose:** A simple desktop expense-tracking application developed in Java for college submission.

The application allows a user to record daily expenses, view stored expenses, search/filter them, calculate totals, see category-wise spending, and delete expenses.

The prototype demonstrates:

- Java programming
- Object-Oriented Programming (OOP)
- Java Swing GUI
- SQLite database
- JDBC database connectivity
- CRUD operations
- Basic validation
- Simple application workflow

---

# 2. Tech Stack

| Component | Technology |
|---|---|
| Programming Language | Java |
| GUI | Java Swing |
| Database | SQLite |
| Database Connectivity | JDBC |
| Build/Run | Java JDK |
| IDE | VS Code / IntelliJ IDEA |
| Architecture | Layered OOP structure |

---

# 3. Main Features

## Required Features

1. Add a new expense
2. View all expenses
3. Edit an expense
4. Delete an expense
5. Search/filter expenses
6. Filter by category
7. Filter by date
8. Calculate total expenses
9. Display category-wise totals
10. Store data permanently in SQLite
11. Validate user input
12. Show success/error messages

## Optional Features

- Monthly budget
- Remaining budget
- Expense charts
- Dark mode
- Export to CSV
- Login system
- Recurring expenses
- Income tracking
- Dashboard cards

Build the required features first.

---

# 4. Recommended File Structure

```text
ExpenseTracker/
│
├── lib/
│   └── sqlite-jdbc.jar
│
├── database/
│   └── expenses.db
│
├── src/
│   ├── Main.java
│   │
│   ├── model/
│   │   └── Expense.java
│   │
│   ├── database/
│   │   └── DatabaseManager.java
│   │
│   ├── dao/
│   │   └── ExpenseDAO.java
│   │
│   ├── service/
│   │   └── ExpenseService.java
│   │
│   └── ui/
│       ├── MainFrame.java
│       ├── DashboardPanel.java
│       ├── AddExpensePanel.java
│       └── ExpenseTablePanel.java
│
├── README.md
└── .gitignore
```

---

# 5. Responsibility of Each File

## `Main.java`

Application entry point.

Responsibilities:

- Start the Java application
- Initialize the database
- Open the main Swing window

Flow:

```text
main()
  ↓
DatabaseManager.initializeDatabase()
  ↓
MainFrame
  ↓
Application starts
```

---

## `model/Expense.java`

Represents one expense object.

Suggested fields:

```java
private int id;
private String title;
private double amount;
private String category;
private String date;
private String description;
```

The class contains:

- Constructors
- Getters
- Setters
- Optional `toString()`

Example object:

```text
Expense
├── id = 1
├── title = "College Books"
├── amount = 850
├── category = "Education"
├── date = "2026-10-07"
└── description = "Semester books"
```

---

## `database/DatabaseManager.java`

Handles the SQLite connection and initialization.

Responsibilities:

- Create SQLite connection
- Create the `expenses` table if it does not exist
- Provide a reusable connection method

Database URL:

```text
jdbc:sqlite:database/expenses.db
```

---

## `dao/ExpenseDAO.java`

Handles direct database operations.

CRUD methods:

```java
addExpense()
getAllExpenses()
getExpenseById()
updateExpense()
deleteExpense()
searchExpenses()
```

The DAO contains SQL-related code.

Example:

```sql
INSERT INTO expenses
(title, amount, category, date, description)
VALUES (?, ?, ?, ?, ?);
```

---

## `service/ExpenseService.java`

Contains application/business logic.

Responsibilities:

- Validate expense data
- Call DAO methods
- Calculate totals
- Calculate category summaries
- Apply filtering rules

The UI should not directly contain SQL logic.

Architecture:

```text
UI
 ↓
ExpenseService
 ↓
ExpenseDAO
 ↓
SQLite
```

---

## `ui/MainFrame.java`

Main application window.

Suggested layout:

```text
+------------------------------------------------+
|              EXPENSE TRACKER                   |
+------------------------------------------------+
| Dashboard | Add Expense | Expenses             |
+------------------------------------------------+
|                                                |
|              Selected panel                    |
|                                                |
+------------------------------------------------+
```

Use `JFrame` as the main window.

---

## `ui/DashboardPanel.java`

Displays summary information.

Example:

```text
Total Expenses     ₹12,450
This Month         ₹4,820
Transactions       27
```

Optional category summary:

```text
Food              ₹2,100
Transport         ₹1,350
Education         ₹1,000
Shopping            ₹370
```

---

## `ui/AddExpensePanel.java`

Form used to enter a new expense.

Fields:

```text
Title
Amount
Category
Date
Description
```

Buttons:

```text
[ Add Expense ] [ Clear ]
```

---

## `ui/ExpenseTablePanel.java`

Displays expenses using `JTable`.

Suggested columns:

```text
ID | Title | Amount | Category | Date | Description
```

Add Edit/Delete controls as needed.

---

# 6. Database Design

## Database

```text
database/expenses.db
```

## Table

```sql
CREATE TABLE IF NOT EXISTS expenses (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    amount REAL NOT NULL,
    category TEXT NOT NULL,
    date TEXT NOT NULL,
    description TEXT
);
```

## Table Structure

| Column | Type | Description |
|---|---|---|
| id | INTEGER | Unique expense ID |
| title | TEXT | Expense name |
| amount | REAL | Expense amount |
| category | TEXT | Expense category |
| date | TEXT | Expense date |
| description | TEXT | Optional details |

---

# 7. Categories

Use a fixed category list initially:

```text
Food
Transport
Education
Shopping
Bills
Entertainment
Health
Travel
Other
```

In Swing, use:

```java
JComboBox<String>
```

---

# 8. Application Workflow

## Application Startup

```text
Start Program
     ↓
Initialize Database
     ↓
Create expenses table if required
     ↓
Open MainFrame
     ↓
Load existing expenses
     ↓
Display Dashboard
```

---

## Add Expense Workflow

```text
User opens Add Expense
        ↓
Enters expense details
        ↓
Clicks Add Expense
        ↓
Validate input
        ↓
Is input valid?
   ┌────┴────┐
  No        Yes
   ↓          ↓
Show error   Create Expense object
              ↓
        ExpenseService
              ↓
           ExpenseDAO
              ↓
           SQLite
              ↓
       Success response
              ↓
       Refresh dashboard
              ↓
       Refresh expense table
```

---

## View Expense Workflow

```text
User opens Expenses
        ↓
ExpenseService
        ↓
ExpenseDAO.getAllExpenses()
        ↓
SQLite SELECT
        ↓
List<Expense>
        ↓
Populate JTable
```

---

## Edit Expense Workflow

```text
User selects expense
        ↓
Load selected record
        ↓
Show existing values
        ↓
User modifies values
        ↓
Validate input
        ↓
ExpenseService
        ↓
ExpenseDAO.updateExpense()
        ↓
SQLite UPDATE
        ↓
Refresh JTable
        ↓
Refresh dashboard
```

---

## Delete Expense Workflow

```text
User selects expense
        ↓
Click Delete
        ↓
Show confirmation dialog
        ↓
User confirms?
   ┌────┴────┐
  No        Yes
   ↓          ↓
Cancel    ExpenseService
             ↓
       ExpenseDAO.deleteExpense()
             ↓
          SQLite
             ↓
        Refresh table
             ↓
        Refresh totals
```

---

# 9. Data Flow

## Overall Data Flow

```text
             USER
               │
               ▼
        ┌──────────────┐
        │  Swing UI    │
        └──────┬───────┘
               │
               ▼
        ┌──────────────┐
        │   Service    │
        │ Business     │
        │   Logic      │
        └──────┬───────┘
               │
               ▼
        ┌──────────────┐
        │     DAO      │
        │ Database Ops │
        └──────┬───────┘
               │
               ▼
        ┌──────────────┐
        │    SQLite    │
        │  expenses.db │
        └──────────────┘
```

---

# 10. Example Add-Expense Data Flow

Suppose the user enters:

```text
Title: College Books
Amount: 850
Category: Education
Date: 2026-10-07
Description: Semester books
```

Flow:

```text
JTextField / JComboBox
        ↓
Expense object
        ↓
ExpenseService
        ↓
ExpenseDAO
        ↓
PreparedStatement
        ↓
SQLite
```

Database row:

```text
1 | College Books | 850.0 | Education | 2026-10-07 | Semester books
```

---

# 11. Core Logic

## 11.1 Add Expense Logic

Pseudo-code:

```text
read form values

if title is empty
    show error

if amount <= 0
    show error

if category is empty
    show error

if date is invalid
    show error

create Expense object

send object to service

service sends object to DAO

DAO executes INSERT

show success message
```

---

## 11.2 Total Expense Logic

```text
total = 0

for each expense
    total = total + expense.amount

return total
```

Formula:

```text
Total Expense = Σ Expense Amounts
```

Example:

```text
Food          ₹250
Transport     ₹100
Education     ₹850
Shopping      ₹400
-------------------
Total        ₹1600
```

---

## 11.3 Category Summary Logic

Pseudo-code:

```text
create empty map

for each expense
    category = expense.category
    amount = expense.amount

    if category already exists
        add amount to existing total
    else
        create new category entry
```

Example:

```text
Food          ₹2500
Transport     ₹1400
Education     ₹2200
Shopping      ₹1800
```

---

## 11.4 Search Logic

Search can check:

```text
title
category
description
```

Example:

```text
Search: "book"
```

Possible results:

```text
College Books
Programming Book
Data Structures Book
```

---

## 11.5 Delete Logic

```text
get selected expense ID

ask for confirmation

if confirmed:
    DELETE FROM expenses WHERE id = ?
    reload expenses
```

---

# 12. Validation Rules

## Title

```text
Cannot be empty
```

## Amount

```text
Must be numeric
Must be greater than 0
```

Examples:

```text
850      → valid
99.50    → valid
0        → invalid
-100     → invalid
abc      → invalid
```

## Category

```text
Must be selected
```

## Date

Recommended format:

```text
YYYY-MM-DD
```

Example:

```text
2026-10-07
```

---

# 13. OOP Concepts Demonstrated

## Encapsulation

Private fields:

```java
private double amount;
```

Access through getters/setters.

## Abstraction

Database and business operations are separated from the UI.

Examples:

```text
ExpenseDAO
ExpenseService
```

## Inheritance

Custom Swing panels can extend:

```java
JPanel
```

The main window can extend:

```java
JFrame
```

## Polymorphism

Methods can be overridden or used through common parent types where appropriate.

---

# 14. JDBC Workflow

JDBC connects Java to SQLite.

Typical flow:

```text
1. Open connection
2. Prepare SQL statement
3. Set parameters
4. Execute query
5. Read ResultSet when required
6. Close resources
```

Use `PreparedStatement` instead of string concatenation.

Example:

```java
String sql = """
    INSERT INTO expenses
    (title, amount, category, date, description)
    VALUES (?, ?, ?, ?, ?)
    """;

PreparedStatement ps = connection.prepareStatement(sql);

ps.setString(1, expense.getTitle());
ps.setDouble(2, expense.getAmount());
ps.setString(3, expense.getCategory());
ps.setString(4, expense.getDate());
ps.setString(5, expense.getDescription());

ps.executeUpdate();
```

---

# 15. UI Design

## Main Window

```text
+------------------------------------------------------+
|                 EXPENSE TRACKER                      |
+------------------------------------------------------+
| Dashboard | Add Expense | Expenses                  |
+------------------------------------------------------+
|                                                      |
|              Selected screen content                |
|                                                      |
+------------------------------------------------------+
```

## Dashboard

Show:

```text
Total Expenses
Number of Transactions
Current Month Spending
Category Summary
```

## Add Expense

Use:

```text
JLabel
JTextField
JComboBox
JButton
```

## Expense List

Use:

```text
JTable
JScrollPane
JButton
```

---

# 16. Program Logic by Layer

```text
UI Layer
│
│  User interaction
│  Forms
│  Buttons
│  Tables
│
▼
Service Layer
│
│  Validation
│  Calculations
│  Filtering
│  Business rules
│
▼
DAO Layer
│
│  SQL queries
│  CRUD operations
│
▼
Database Layer
│
│  SQLite
│
▼
expenses.db
```

---

# 17. Development Order

## Phase 1 — Project Setup

1. Install Java JDK
2. Create project folder
3. Add SQLite JDBC dependency
4. Create packages
5. Create `Main.java`

## Phase 2 — Database

1. Create `DatabaseManager`
2. Create SQLite database
3. Create `expenses` table
4. Test connection

## Phase 3 — Model

1. Create `Expense.java`
2. Add fields
3. Add constructor
4. Add getters/setters

## Phase 4 — DAO

Implement:

```text
addExpense()
getAllExpenses()
updateExpense()
deleteExpense()
searchExpenses()
```

Test database operations before building the full GUI.

## Phase 5 — Service

Implement:

```text
validation
total calculation
category summary
search/filter logic
```

## Phase 6 — GUI

Build:

```text
MainFrame
DashboardPanel
AddExpensePanel
ExpenseTablePanel
```

## Phase 7 — Integration

Connect:

```text
UI → Service → DAO → SQLite
```

## Phase 8 — Testing

Test every operation.

---

# 18. Testing Checklist

## Add

- [ ] Valid expense is added
- [ ] Empty title rejected
- [ ] Invalid amount rejected
- [ ] Negative amount rejected
- [ ] Category required

## View

- [ ] Existing expenses appear
- [ ] Table refreshes correctly

## Edit

- [ ] Existing values load
- [ ] Updated values save
- [ ] Invalid updates rejected

## Delete

- [ ] Confirmation appears
- [ ] Correct row is deleted
- [ ] Table refreshes

## Search

- [ ] Search by title works
- [ ] Search by category works
- [ ] No-result state works

## Dashboard

- [ ] Total is correct
- [ ] Transaction count is correct
- [ ] Category totals are correct

## Database

- [ ] Data remains after restarting application
- [ ] Database is created automatically
- [ ] No SQL errors appear during normal operations

---

# 19. Example User Journey

```text
Start application
      ↓
Dashboard shows ₹0
      ↓
Open Add Expense
      ↓
Add "Lunch" = ₹120
      ↓
Add "Bus" = ₹50
      ↓
Open Expenses
      ↓
Two records displayed
      ↓
Dashboard total = ₹170
      ↓
Search "Lunch"
      ↓
Lunch record displayed
      ↓
Edit Lunch → ₹150
      ↓
Dashboard total = ₹200
      ↓
Delete Bus
      ↓
Dashboard total = ₹150
```

---

# 20. Initial Database State

On first run:

```text
expenses table exists
0 records
```

Display an empty state:

```text
No expenses found.
Add your first expense.
```

Do not hard-code fake financial records.

---

# 21. Error Handling

Use `try-catch` around database operations.

Expected errors:

```text
Database connection failure
SQL failure
Invalid user input
Number format error
Missing required fields
```

Show user-friendly messages.

Avoid showing raw stack traces to normal users. During development, log exceptions to the console.

---

# 22. Resource Management

Use try-with-resources for database objects.

Example:

```java
try (Connection connection = ...;
     PreparedStatement statement = ...;
     ResultSet resultSet = ...) {

    // database operation

} catch (SQLException e) {
    e.printStackTrace();
}
```

This ensures resources are released correctly.

---

# 23. Git Workflow

Recommended repository:

```text
expense-tracker-java
```

Initial setup:

```bash
git init
git add .
git commit -m "Initial expense tracker project"
```

After changes:

```bash
git add .
git commit -m "Add expense CRUD functionality"
git push
```

Suggested commit stages:

```text
Initial project setup
Database connection added
Expense model added
CRUD operations added
Service logic added
Swing UI added
Dashboard added
Testing and bug fixes
Final submission
```

---

# 24. `.gitignore`

Recommended:

```gitignore
*.class
.idea/
.vscode/
out/
target/
.DS_Store
```

If the SQLite database should not be committed:

```gitignore
database/*.db
```

---

# 25. Running the Project

## Requirements

```text
JDK 17+
SQLite JDBC JAR
```

Recommended:

```text
JDK 17 or JDK 21
```

Add the SQLite JDBC JAR to the project.

Example terminal build:

```bash
javac -cp "lib/sqlite-jdbc.jar" -d out $(find src -name "*.java")
```

Run on Linux/macOS:

```bash
java -cp "out:lib/sqlite-jdbc.jar" Main
```

Run on Windows:

```bash
java -cp "out;lib/sqlite-jdbc.jar" Main
```

Using an IDE is also acceptable.

---

# 26. Minimum Viable Prototype

The project is complete for the basic college demo when these work:

```text
✅ Application launches
✅ SQLite database is created
✅ Add expense works
✅ View expenses works
✅ Edit expense works
✅ Delete expense works
✅ Search/filter works
✅ Total expense calculation works
✅ Category summary works
✅ Data survives application restart
```

---

# 27. Future Enhancements

Possible future scope:

```text
Monthly budgets
Budget alerts
Income management
Savings goals
Charts
CSV/PDF export
User accounts
Multiple wallets
Recurring transactions
Cloud synchronization
AI-based expense categorization
```

Treat these as future scope unless explicitly required.

---

# 28. Recommended Class Relationship

```text
                    ┌───────────────┐
                    │     Main      │
                    └───────┬───────┘
                            │
                            ▼
                    ┌───────────────┐
                    │   MainFrame   │
                    └───────┬───────┘
                            │
              ┌─────────────┼─────────────┐
              ▼             ▼             ▼
        DashboardPanel  AddExpensePanel  ExpenseTablePanel
              │             │             │
              └─────────────┼─────────────┘
                            ▼
                    ┌───────────────┐
                    │ExpenseService │
                    └───────┬───────┘
                            ▼
                    ┌───────────────┐
                    │  ExpenseDAO   │
                    └───────┬───────┘
                            ▼
                    ┌───────────────┐
                    │DatabaseManager│
                    └───────┬───────┘
                            ▼
                    ┌───────────────┐
                    │    SQLite     │
                    └───────────────┘
```

---

# 29. Important Implementation Rules

1. Keep SQL code inside the DAO/database layer.
2. Keep calculations and validation inside the service layer.
3. Keep Swing event-handling code inside the UI layer.
4. Use `PreparedStatement`.
5. Do not store passwords or sensitive information in this prototype.
6. Do not hard-code expense records.
7. Refresh the table and dashboard after add/edit/delete.
8. Use `double` for this simple prototype; `BigDecimal` is preferable for financial-grade software.
9. Keep classes small and focused.
10. Test each layer before integrating the complete application.

---

# 30. College Demonstration Flow

Use this sequence during the demo:

```text
1. Launch application
2. Show dashboard
3. Add 3–4 expenses
4. Show expense table
5. Edit one expense
6. Search/filter an expense
7. Delete one expense
8. Show updated total
9. Close application
10. Reopen application
11. Show that data is still stored
12. Explain OOP + JDBC + SQLite architecture
```

---

# 31. One-Line Project Description

> A Java Swing-based desktop Expense Tracker that uses SQLite and JDBC to perform CRUD operations, store expenses, calculate spending summaries, and provide a simple graphical interface.

---

# 32. Final Architecture Summary

```text
Java Swing
   ↓
UI Components
   ↓
ExpenseService
   ↓
ExpenseDAO
   ↓
JDBC
   ↓
SQLite
   ↓
expenses.db
```

Core flow:

```text
User Input
   ↓
Validation
   ↓
Business Logic
   ↓
Database Operation
   ↓
Updated Data
   ↓
Updated UI
```
