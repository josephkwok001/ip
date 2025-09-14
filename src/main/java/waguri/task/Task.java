package waguri.task;

/**
 * Represents a task with a String description and boolean completion isDone.
 * This is the base class for all types of tasks in the application.
 * Tasks can be marked as done or undone
 * showing their current isDone.
 */

public class Task {
    /** The description or title of the task */
    private String description;

    /** The completion isDone of the task (true if completed, false otherwise) */
    private boolean isDone;

    /**
     * Constructs a new Task with the specified description and initial isDone.
     *
     * @param description the text description of the task
     * @param isDone the initial completion isDone (true for completed, false for pending)
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Marks this task as completed by setting its isDone to true.
     * This operation is idempotent - calling it multiple times on an already
     * completed task has no additional effect.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    public boolean isDone() {
        return this.isDone;
    }
    /**
     * Marks this task as not completed by setting its isDone to false.
     * This operation is idempotent - calling it multiple times on an already
     * unmarked task has no additional effect.
     */
    public void unmark() {
        this.isDone = false;
    }

    public String getDescription() {
        return this.description;
    }


    /**
     * Returns a string representation of the task showing its isDone and description.
     * The format is "[X] description" for completed tasks and "[] description" for
     * pending tasks, where "description" is the task's description.
     *
     * @return a formatted string representing the task's isDone and description
     */
    @Override
    public String toString() {
        return this.isDone ? "[X] " + this.description : "[] " + this.description;
    }



}
