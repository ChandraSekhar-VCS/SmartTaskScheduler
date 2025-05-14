package service;

import model.RecurrenceType;
import model.Task;
import util.DateUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskManager {
    private final List<Task> taskList;

    public TaskManager(List<Task> preloadedTasks) {
        this.taskList = preloadedTasks != null ? preloadedTasks : new ArrayList<>();
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void addTask(String name, LocalDateTime dueDateTime, RecurrenceType recurrenceType) {
        Task task = new Task(name, dueDateTime,recurrenceType);
        taskList.add(task);
        System.out.println("Added Task: " + task.getName());
    }

    public boolean deleteTask(String taskId){
        for (Task task : taskList){
            if (task.getId().equals(taskId)){
                taskList.remove(task);
                return true;
            }
        }
        return false;
    }

    public void listAllTasks(){

        if(taskList.isEmpty()){
            System.out.println("No Tasks available");
        }
        else{
            for(Task task : taskList){
                System.out.println(task + "---" + task.getDueDateTime());
            }
        }
    }

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

    public void listTasksDueToday(){
        LocalDate today =  LocalDate.now();
        boolean found = false;
        if(taskList.isEmpty()){
            System.out.println("No Tasks available");
            return;
        }
        for(Task task : taskList){
            LocalDate effectiveDueDate = task.getDueDateTime().toLocalDate();
            RecurrenceType recurrence = task.getRecurrenceType();
            if(recurrence != RecurrenceType.NONE){
                while(effectiveDueDate.isBefore(today)){
                    effectiveDueDate = DateUtils.incrementDate(effectiveDueDate, recurrence);
                }
            }
            if(effectiveDueDate.isEqual(today)){
                found = true;
                System.out.println(task.getName() + " → Due Today (" + effectiveDueDate + ") [Recurs: " + recurrence + "]");
            }
        }
        if(!found){
            System.out.println("No Tasks due today");
        }
    }

    public void listTasksDueThisWeek(){
        LocalDate weekStart = DateUtils.getStartOfWeek();
        LocalDate weekEnd = DateUtils.getEndOfWeek();
        boolean found = false;
        if(taskList.isEmpty()){
            System.out.println("No Tasks available");
            return;
        }
        for(Task task : taskList){
            LocalDate effectiveDueDate = task.getDueDateTime().toLocalDate();
            RecurrenceType recurrence = task.getRecurrenceType();
            if(recurrence != RecurrenceType.NONE){
                while(effectiveDueDate.isBefore(weekStart)){
                    effectiveDueDate = DateUtils.incrementDate(effectiveDueDate, recurrence);
                }
            }
            if (!effectiveDueDate.isBefore(weekStart) && !effectiveDueDate.isAfter(weekEnd)) {
                found = true;
                System.out.println(task.getName() + " → Due on " + effectiveDueDate + " [Recurs: " + recurrence + "]");
            }
        }
        if(!found){
            System.out.println("No Tasks due this week");
        }
    }

    public void listTasksDueThisMonth() {
        LocalDate monthStart = DateUtils.getStartOfMonth();
        LocalDate monthEnd = DateUtils.getEndOfMonth();
        boolean found = false;

        if (taskList.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }

        for (Task task : taskList) {
            LocalDate effectiveDueDate = task.getDueDateTime().toLocalDate();
            RecurrenceType recurrence = task.getRecurrenceType();

            // Adjust recurring tasks forward into this month
            if (recurrence != RecurrenceType.NONE) {
                while (effectiveDueDate.isBefore(monthStart)) {
                    effectiveDueDate = DateUtils.incrementDate(effectiveDueDate, recurrence);
                }
            }

            if (!effectiveDueDate.isBefore(monthStart) && !effectiveDueDate.isAfter(monthEnd)) {
                found = true;
                System.out.println(task.getName() + " → Due on " + effectiveDueDate + " [Recurs: " + recurrence + "]");
            }
        }

        if (!found) {
            System.out.println("No tasks due this month.");
        }
    }

    public String getTimeRemaining(String taskId){
        for(Task task : taskList){
            if(task.getId().equals(taskId)){
                return task.timeRemaining();
            }
        }
        return "Task not found";
    }

    public void sortTasksByDeadline(){
        taskList.sort(Comparator.comparing(Task::getDueDateTime));
        for(Task task : taskList){
            System.out.println(task);
        }
    }
}
