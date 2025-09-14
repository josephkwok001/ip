package waguri.task;

/**
 * Represents a task with a String description and boolean completion status.
 * This is the base class for all types of tasks in the application.
 * Tasks can be marked as done or undone
 * showing their current status.
 */

public class Task {
    /** The description or title of the task */
    private String description;

    /** The completion status of the task (true if completed, false otherwise) */
    private boolean status;

    /**
     * Constructs a new Task with the specified description and initial status.
     *
     * @param description the text description of the task
     * @param status the initial completion status (true for completed, false for pending)
     */
    public Task(String description, boolean status) {
        this.description = description;
        this.status = status;
    }

    /**
     * Marks this task as completed by setting its status to true.
     * This operation is idempotent - calling it multiple times on an already
     * completed task has no additional effect.
     */
    public void markAsDone() {
        this.status = true;
    }

    /**
     * Marks this task as not completed by setting its status to false.
     * This operation is idempotent - calling it multiple times on an already
     * unmarked task has no additional effect.
     */
    public void unmark() {
        this.status = false;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean getStatus() {
        return this.status;
    }

    /**
     * Returns a string representation of the task showing its status and description.
     * The format is "[X] description" for completed tasks and "[] description" for
     * pending tasks, where "description" is the task's description.
     *
     * @return a formatted string representing the task's status and description
     */
    @Override
    public String toString() {
        return this.status ? "[X] " + this.description : "[] " + this.description;
    }



}
