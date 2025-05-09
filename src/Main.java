import model.Task;
import service.TaskManager;
import util.DateUtils;
import util.InvalidDateFormatException;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();

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
            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    System.out.print("Enter task name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter due date and time (dd-MM-yyyy HH:mm): ");
                    String dueInput = scanner.nextLine();
                    try {
                        LocalDateTime dueDateTime = DateUtils.parseDateTime(dueInput);
                        taskManager.addTask(name, dueDateTime);
                    } catch (InvalidDateFormatException e) {
                        System.out.println("❌ Error: " + e.getMessage());
                    }
                    break;

                case "2":
                    System.out.print("Enter task ID to delete: ");
                    String deleteId = scanner.nextLine();
                    boolean deleted = taskManager.deleteTask(deleteId);
                    System.out.println(deleted ? "Task deleted." : "Task not found.");
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

                case "0":
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
