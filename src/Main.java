import model.RecurrenceType;
import model.Task;
import persistance.TaskPersistenceService;
import service.TaskManager;
import util.DateUtils;
import util.InvalidDateFormatException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Task> tasks = TaskPersistenceService.loadTasks();
        TaskManager taskManager = new TaskManager(tasks);
        boolean running = true;
        while (running) {
            System.out.println("\n=== Smart Task Scheduler ===");
            System.out.println("1. Add Task");
            System.out.println("2. Delete Task");
            System.out.println("3. View All Tasks");
            System.out.println("4. View Overdue Tasks");
            System.out.println("5. View Today's Tasks");
            System.out.println("6. View This Week's Tasks");
            System.out.println("7. View This Month's Tasks");
            System.out.println("8. View Time Remaining (by ID)");
            System.out.println("9. Sort Tasks by Deadline");
            System.out.println("10. Mark TAsk as Done");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    System.out.print("Enter task name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter due date and time (dd-MM-yyyy HH:mm): ");
                    String dueInput = scanner.nextLine();
                    System.out.print("Choose the recurrence type (NONE / DAILY / WEEKLY / MONTHLY): ");
                    String recurrenceInput = scanner.nextLine();
                    try {
                        LocalDateTime dueDateTime = DateUtils.parseDateTime(dueInput);
                        RecurrenceType recurrenceType = RecurrenceType.valueOf(recurrenceInput.toUpperCase());
                        taskManager.addTask(name, dueDateTime, recurrenceType);
                        TaskPersistenceService.saveTasks(taskManager.getTaskList());
                    } catch (InvalidDateFormatException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case "2":
                    System.out.print("Enter task ID to delete: ");
                    String deleteId = scanner.nextLine();
                    boolean deleted = taskManager.deleteTask(deleteId);
                    if(deleted){
                        TaskPersistenceService.saveTasks(taskManager.getTaskList());
                        System.out.println("Task deleted successfully");
                    }
                    else {
                        System.out.println("Task not found");
                    }
                    break;

                case "3":
                    taskManager.listAllTasks();
                    break;

                case "4":
                    taskManager.listOverdueTasks();
                    break;

                case "5":
                    taskManager.listTasksDueToday();
                    break;

                case "6":
                    taskManager.listTasksDueThisWeek();
                    break;

                case "7":
                    taskManager.listTasksDueThisMonth();
                    break;

                case "8":
                    System.out.print("Enter task ID: ");
                    String id = scanner.nextLine();
                    System.out.println(taskManager.getTimeRemaining(id));
                    break;

                case "9":
                    taskManager.sortTasksByDeadline();
                    break;

                case "10":
                    System.out.print("Enter task ID to mark as done: ");
                    String doneId = scanner.nextLine();
                    boolean marked = taskManager.markTaskAsDone(doneId);
                    if (marked) {
                        System.out.println("Task marked as completed.");
                        TaskPersistenceService.saveTasks(taskManager.getTaskList());
                    } else {
                        System.out.println("Task not found or already completed.");
                    }
                    break;

                case "0":
                    TaskPersistenceService.saveTasks(taskManager.getTaskList());
                    running = false;
                    System.out.println("Exiting Smart Task Scheduler...");
                    break;

                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }

        scanner.close();
    }
}
