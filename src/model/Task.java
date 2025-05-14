package model;

import util.DateUtils;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String id;
    private String name;
    private LocalDateTime dueDateTime;
    private final LocalDateTime createdAt;
    private RecurrenceType recurrenceType;

    public Task(String name, LocalDateTime dueDateTime, RecurrenceType recurrenceType) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.dueDateTime = dueDateTime;
        this.createdAt = LocalDateTime.now();
        this.recurrenceType = recurrenceType;
    }

    //GETTERS
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public LocalDateTime getDueDateTime() {
        return dueDateTime;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public RecurrenceType getRecurrenceType() {return recurrenceType;}

    //SETTERS
    public void setName(String name) {
        this.name = name;
    }
    public void setDueDateTime(LocalDateTime dueDateTime) {
        this.dueDateTime = dueDateTime;
    }
    public void setRecurrenceType(RecurrenceType recurrenceType) {this.recurrenceType = recurrenceType;}

    public boolean isOverdue() {
        if (recurrenceType == RecurrenceType.NONE) {
            return dueDateTime.isBefore(LocalDateTime.now());
        }

        // Compute projected next due date
        LocalDate projectedDate = dueDateTime.toLocalDate();
        LocalDate today = LocalDate.now();

        while (projectedDate.isBefore(today)) {
            projectedDate = DateUtils.incrementDate(projectedDate, recurrenceType);
        }

        return projectedDate.isBefore(today);
    }

    public String timeRemaining() {
        LocalDateTime now = LocalDateTime.now();

        if (recurrenceType == RecurrenceType.NONE) {
            if (dueDateTime.isBefore(now)) return "Overdue";
            Duration duration = Duration.between(now, dueDateTime);
            return DateUtils.formatDuration(duration);
        }

        // Recurring task
        LocalDateTime nextDue = dueDateTime;
        while (nextDue.isBefore(now)) {
            switch (recurrenceType) {
                case DAILY: nextDue = nextDue.plusDays(1); break;
                case WEEKLY: nextDue = nextDue.plusWeeks(1); break;
                case MONTHLY: nextDue = nextDue.plusMonths(1); break;
                default: break;
            }
        }

        Duration duration = Duration.between(now, nextDue);
        return DateUtils.formatDuration(duration);
    }

    public LocalDateTime getNextDueDate(){
        LocalDateTime next = dueDateTime;
        while(next.isBefore(LocalDateTime.now())){
            switch (recurrenceType){
                case DAILY: next = next.plusDays(1); break;
                case WEEKLY: next = next.plusWeeks(1); break;
                case MONTHLY: next = next.plusMonths(1); break;
                default: break;
            }
        }
        return next;
    }

    public String getDoubleSoonLable(){
        LocalDateTime  now =  LocalDateTime.now();
        LocalDateTime nextDueDate = getNextDueDate();
        Duration duration = Duration.between(now, nextDueDate);
        if(duration.isNegative()){
            return "Overdue";
        }
        else if(duration.toHours() <= 1){
            return "Due in Less than 1 Hour";
        }
        else if(duration.toHours() <= 24){
            return "Due Today";
        }
        else{
            return "";
        }
    }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    @Override
    public String toString() {
        return "[" + id + "] " + name + "\nDue: " + dueDateTime.format(FORMATTER) +
                "\nCreated: " + createdAt.format(FORMATTER) +
                "\nStatus: " + (isOverdue() ? "Overdue" : "Active") +
                "\nTime Left: " + timeRemaining() +
                "\nRecurs: " + recurrenceType;
    }
}
