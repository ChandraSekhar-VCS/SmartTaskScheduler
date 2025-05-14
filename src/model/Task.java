package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
/**
 * Represents a task in the Smart Task Scheduler system.
 *
 * <p>Each task has a unique identifier, a descriptive name, a due date-time,
 * and a creation timestamp. The task provides utility methods to determine
 * whether it is overdue, calculate time remaining, and generate formatted output
 * for user-friendly display.</p>
 *
 * <p>This class relies entirely on the {@code java.time} API and is designed to be
 * immutable in its identity (ID and creation time), while allowing updates to
 * the task's name and deadline.</p>
 *
 * @author <a href="https://github.com/ChandraSekhar-VCS">Chandra Sekhar Vipparla</a>
 * @since 1.0
 */
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String id;
    private String name;
    private LocalDateTime dueDateTime;
    private final LocalDateTime createdAt;
    private RecurrenceType recurrenceType;
    /**
     * Constructs a Task with the given name and due date-time.
     * Automatically generates a unique task ID and sets creation time to the current moment.
     *
     * @param name the name or description of the task
     * @param dueDateTime the date and time by which the task is due
     */

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
    public void setrecurrenceType(RecurrenceType recurrenceType) {this.recurrenceType = recurrenceType;}

    /**
     * Checks whether the task is overdue.
     *
     * @return true if the task's due date-time is before the current time, false otherwise
     */

    public boolean isOverdue(){
        return dueDateTime.isBefore(LocalDateTime.now());
    }

    /**
     * Calculates the time remaining until the task's due date-time.
     *
     * @return a string indicating the remaining days, hours, and minutes,
     *         or "Overdue" if the deadline has already passed
     */

    public String timeRemaining(){
        LocalDateTime now = LocalDateTime.now();
        if(dueDateTime.isBefore(now)){
            return "Overdue";
        }
        long days = ChronoUnit.DAYS.between(now, dueDateTime);
        long hours =  ChronoUnit.HOURS.between(now, dueDateTime)%24;
        long minutes =  ChronoUnit.MINUTES.between(now, dueDateTime)%60;

        return days + " days, " + hours + " hours, " + minutes + " minutes left";
    }
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    @Override
    public String toString() {
        return "[" + id + "] " + name + "\nDue: " + dueDateTime.format(FORMATTER) +
                "\nCreated: " + createdAt.format(FORMATTER) +
                "\nStatus: " + (isOverdue() ? "Overdue" : "✅ Active") +
                "\nTime Left: " + timeRemaining() +
                "\nRecurs: " + recurrenceType;
    }
}
