package util;

import model.RecurrenceType;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class DateUtils {

    private DateUtils() {}

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public static LocalDateTime parseDateTime(String input) {
        try {
            return LocalDateTime.parse(input, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException("Invalid date-time format. Expected pattern: dd-MM-yyyy HH:mm");
        }
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(FORMATTER);
    }

    public static TemporalAdjuster nextWorkingDayAdjuster() {
        return temporal -> {
            LocalDate date = LocalDate.from(temporal);
            DayOfWeek day = date.getDayOfWeek();

            switch (day) {
                case FRIDAY:
                    return date.plusDays(3);
                case SATURDAY:
                    return date.plusDays(2);
                default:
                    return date.plusDays(1);
            }
        };
    }

    public static LocalDate incrementDate(LocalDate date, RecurrenceType recurrenceType){
        switch (recurrenceType){
            case DAILY :
                return date.plusDays(1);
            case WEEKLY:
                return date.plusWeeks(1);
            case MONTHLY:
                return date.plusMonths(1);
            default:
                return date;
        }
    }

    public static String formatDuration(Duration duration) {
        if (duration.isZero() || duration.isNegative()) {
            return "Expired";
        }

        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;

        return days + " days, " + hours + " hours, " + minutes + " minutes";
    }

    public static LocalDate getStartOfWeek() {
        return LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    public static LocalDate getEndOfWeek() {
        return LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
    }

    public static LocalDate getStartOfMonth() {
        return LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
    }

    public static LocalDate getEndOfMonth() {
        return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
    }
}
