package service;

import model.Task;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskManager {
    private List<Task> taskList;

    /**
     * TaskManager constructor that initializes the taskList with a new ArrayList
     */
    public TaskManager(List<Task> preloadedTasks) {
        this.taskList = preloadedTasks != null ? preloadedTasks : new ArrayList<>();
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    /**
     * Adds task to the Task Managers Task list
     *
     * <p>Takes task name and the due date as an input and creates a new task and add it to the list</p>
     * @param name - name of the task
     * @param dueDateTime - LocalDateTime indicating the due date of the task
     */
    public void addTask(String name, LocalDateTime dueDateTime){
        Task task = new Task(name, dueDateTime);
        taskList.add(task);
        System.out.println("Added Task: " + task.getName());
    }

    /**
     * Deletes the task with the specified task ID
     * @param taskId - the id of the task to be deleted
     * @return if task with specified id is found deletes and returns true, else false
     */
    public boolean deleteTask(String taskId){
        for (Task task : taskList){
            if (task.getId().equals(taskId)){
                taskList.remove(task);
                return true;
            }
        }
        return false;
    }

    /**
     * Prints all the tasks that are available
     */
    public void listAllTasks(){
        if(taskList.isEmpty()){
            System.out.println("No Tasks available");
        }
        else{
            for(Task task : taskList){
                System.out.println(task);
            }
        }
    }

    /**
     * Prints a list of task that are overdue
     */
    public void listOverdueTasks(){
        if(taskList.isEmpty()){
            System.out.println("No Tasks available");
        }
        else{
            for(Task task : taskList){
                if(task.isOverdue()){
                    System.out.println(task);
                }
            }
        }
    }

    /**
     * Prints a list of tasks that are due Today
     */
    public void listTasksDueToday(){
        LocalDate today =  LocalDate.now();
        if(taskList.isEmpty()){
            System.out.println("No Tasks available");
        }
        else{
            for(Task task : taskList){
                LocalDate dueDate = task.getDueDateTime().toLocalDate();
                if(dueDate.isEqual(today)){
                    System.out.println(task);
                }
            }
        }
    }
    /**
     * Prints a List fo tasks that are due the current week
     */
    public void listTasksDueThisWeek(){
        LocalDate today =  LocalDate.now();
        LocalDate weekStart = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate weekEnd = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
        for(Task task : taskList){
            LocalDate dueDate = task.getDueDateTime().toLocalDate();
            if((dueDate.isEqual(weekStart) || dueDate.isAfter(weekStart)) &&
                    (dueDate.isEqual(weekEnd) || dueDate.isBefore(weekEnd))){
                System.out.println(task);
            }
        }
    }

    /**
     * Prints a List of tasks that are due the current month
     */
    public void listTasksDueThisMonth(){
        LocalDate today =  LocalDate.now();
        LocalDate monthStart = today.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate monthEnd = today.with(TemporalAdjusters.lastDayOfMonth());
        for(Task task : taskList){
            LocalDate dueDate =  task.getDueDateTime().toLocalDate();
            if((dueDate.isEqual(monthStart) || dueDate.isAfter(monthStart)) &&
                    (dueDate.isEqual(monthEnd) || dueDate.isBefore(monthEnd))){
                System.out.println(task);
            }
        }
    }

    /**
     * Method to get the time remaining for the specified task
     *
     * <p>
     *     Takes taskId as a parameter and checks if a task with the specified task is present or not
     *     If present returns the time remaining for the specified task
     *     If not present the returns "Task not found"
     * </p>
     * @param taskId - the ID of the task for which the remaining time has to be calculated
     * @return time remaining of task is present else returns Task not found
     */
    public String getTimeRemaining(String taskId){
        for(Task task : taskList){
            if(task.getId().equals(taskId)){
                return task.timeRemaining();
            }
        }
        return "Task not found";
    }

    /**
     * Sorts the tasks List based on the due date and prints them in order
     */
    public void sortTasksByDeadline(){
        taskList.sort(Comparator.comparing(Task::getDueDateTime));
        for(Task task : taskList){
            System.out.println(task);
        }
    }
}
