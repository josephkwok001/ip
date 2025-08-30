package waguri.task;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a specific deadline date and time.
 * Extends the base Task class to add deadline functionality.
 * The deadline is stored as a LocalDateTime object and can be formatted
 * for display with different patterns based on whether a time is specified.
 */

public class Deadline extends Task {
    /** The date and time by which this task should be completed */
    private LocalDateTime by;

    /**
     * Constructs a new Deadline task with the specified description and deadline.
     * The task is initially marked as not completed.
     *
     * @param description the text description of the deadline task
     * @param by the date and time by which the task should be completed
     */
    public Deadline(String description, LocalDateTime by) {
        super(description, false);
        this.by = by;
    }

    /**
     * Returns the deadline date and time for this task.
     *
     * @return the LocalDateTime object representing the deadline
     */
    public LocalDateTime getBy() {
        return this.by;
    }

    /**
     * Returns a formatted string representation of the deadline task.
     * The format includes the task type identifier [D], the parent class
     * string representation, and the formatted deadline date.
     *
     * The deadline is formatted differently based on whether a specific time
     * is provided:
     * - If the time is midnight, only the date is displayed (yyyy MMM dd)
     * - If a specific time is provided, both date and time are displayed (yyyy MMM dd HH:mm)
     *
     * @return a formatted string showing the task details and deadline
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter;

        if (by.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            formatter = DateTimeFormatter.ofPattern("yyyy MMM dd");
        } else {
            formatter = DateTimeFormatter.ofPattern("yyyy MMM dd HH:mm");
        }
        return "[D]" + super.toString() + " (by: " + by.format(formatter) + ")";
    }

}
