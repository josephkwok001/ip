package waguri.task;

/**
 * Extends the base Task class to provide a basic task type that only
 * requires a description.  @see Task
 */
public class Todo extends Task {

    /**
     * Constructs a new Todo task with the specified description.
     * The task is initially marked as not completed.
     *
     * @param description the text description of the todo task
     */
    public Todo(String description){
        super(description, false);
    }

    /**
     * Returns a formatted string representation of the todo task.
     * The format includes the task type identifier [T] followed by
     * the string representation from the parent Task class.
     *
     * @return a formatted string showing the task type and details in the format "[T] [status] description"
     */
    @Override
    public String toString(){
        return "[T]" + super.toString();
    }
}