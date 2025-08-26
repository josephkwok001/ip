package waguri.task;

import waguri.task.Todo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {

    @Test
    public void testTodoCreation() {
        Todo todo = new Todo("Read book");
        assertEquals("[T][ ] Read book", todo.toString());
    }

    @Test
    public void testTodoMarkAsDone() {
        Todo todo = new Todo("Read book");
        todo.markAsDone();
        assertEquals("[T][X] Read book", todo.toString());
    }

    @Test
    public void testDeadlineCreation() {
        // This test might need mocking DateParser if it's complex
        Deadline deadline = new Deadline("Submit assignment",
                java.time.LocalDateTime.of(2024, 12, 25, 0, 0));
        assertEquals("[D][] Submit assignment (by: 2024 Dec 25)", deadline.toString());
    }
}