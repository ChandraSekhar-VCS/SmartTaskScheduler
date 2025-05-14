# Smart Task Scheduler

A command-line-based Task Management application built with **pure Core Java**.
It supports task creation, deadlines, recurrence (daily, weekly, monthly), completion tracking, and file-based persistence — all powered by Java 8+ Date and Time API.

---

## Features

* Add tasks with due date & time
* Set recurrence: `DAILY`, `WEEKLY`, `MONTHLY`, or `NONE`
* View time remaining for any task
* Real-time labels: “Due Today”, “Due in < 1 Hour”
* Mark tasks as completed
* Persistent storage (via `tasks.dat`)
* Filter views: Overdue, Today, This Week, This Month
* Sort tasks by deadline
* Robust Java 8+ `java.time` usage

---

## Project Structure

```
SmartTaskScheduler/
├── model/
│   ├── Task.java
│   └── RecurrenceType.java
├── service/
│   └── TaskManager.java
├── util/
│   ├── DateUtils.java
│   └── InvalidDateFormatException.java
├── persistance/
│   └── TaskPersistenceService.java
├── data/
│   └── tasks.dat (auto-created)
└── Main.java
```

---

## Data Persistence

Tasks are automatically saved in:

```
src/data/tasks.dat
```

File is auto-created and reused across runs.

---

## 😋 Author

**Chandra Sekhar Vipparla**

[GitHub Profile](https://github.com/ChandraSekhar-VCS)

[LinkedIn Profile](www.linkedin.com/in/chandra-sekhar-vipparla)

