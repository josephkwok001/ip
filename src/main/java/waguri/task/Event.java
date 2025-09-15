package waguri.task;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task with a specific start and end date/time.
 * Extends the base Task class to add event scheduling functionality.
 * The event has a defined time period with both start and end times,
 * and provides intelligent formatting for display based on whether
 * specific times are provided.
 */
public class Event extends Task {
    /** The start date and time of the event */
    private LocalDateTime from;
    /** The end date and time of the event */
    private LocalDateTime to;

    /**
     * Constructs a new Event task with the specified description, start time, and end time.
     * The task is initially marked as not completed.
     *
     * @param description the text description of the event
     * @param from the date and time when the event starts
     * @param to the date and time when the event ends
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description, false);
        this.from = from;
        this.to = to;
    }
    public LocalDateTime getFrom() {
        return this.from;
    }

    public LocalDateTime getTo() {
        return this.to;
    }

    /**
     * Returns a formatted string representation of the event task.
     * The format includes the task type identifier [E], the parent class
     * string representation, and the formatted start and end times.
     *
     * The times are formatted differently based on whether specific times
     * are provided:
     * - If the time is midnight, only the date is displayed (yyyy MMM dd)
     * - If a specific time is provided, both date and time are displayed (yyyy MMM dd HH:mm)
     *
     * @return a formatted string showing the task details and event time period
     */
    @Override
    public String toString() {
        DateTimeFormatter fromFormat;
        DateTimeFormatter toFormat;

        if (from.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            fromFormat = DateTimeFormatter.ofPattern("yyyy MMM dd");
        } else {
            fromFormat = DateTimeFormatter.ofPattern("yyyy MMM dd HH:mm");
        }

        if (to.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            toFormat = DateTimeFormatter.ofPattern("yyyy MMM dd");
        } else {
            toFormat = DateTimeFormatter.ofPattern("yyyy MMM dd HH:mm");
        }

        assert fromFormat != null : "from (date) cannot not be null";
        assert toFormat != null : "to (date) cannot not be null";

        return "[E]" + super.toString() + " (from: " + from.format(fromFormat) + " to: " + to.format(toFormat) + ")";
    }
}
