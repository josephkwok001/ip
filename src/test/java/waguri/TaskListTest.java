package waguri;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import waguri.task.Deadline;
import waguri.task.Event;
import waguri.task.Task;
import waguri.task.TaskList;
import waguri.task.Todo;
import waguri.WaguriException;

public class TaskListTest {

    private TaskList taskList;
    private Todo todo;
    private Deadline deadline;
    private Event event;

    @BeforeEach
    public void setUp() {
        ArrayList<Task> tasks = new ArrayList<>();
        todo = new Todo("Buy groceries");
        deadline = new Deadline("Submit report", LocalDateTime.of(2024, 12, 25, 23, 59));
        event = new Event("Team meeting",
                LocalDateTime.of(2024, 12, 25, 14, 0),
                LocalDateTime.of(2024, 12, 25, 15, 0));

        tasks.add(todo);
        tasks.add(deadline);
        tasks.add(event);
        taskList = new TaskList(tasks);
    }

    @Test
    public void testMarkTask_validIndex_success() throws waguri.WaguriException {
        taskList.markTask(1);
        assertTrue(todo.isDone());
    }

    @Test
    public void testMarkTask_invalidIndex_throwsException() {
        assertThrows(waguri.WaguriException.class, () -> taskList.markTask(0));
        assertThrows(waguri.WaguriException.class, () -> taskList.markTask(4));
    }

    @Test
    public void testUnmarkTask_validIndex_success() throws WaguriException {
        todo.markAsDone();
        taskList.unmarkTask(1);
        assertFalse(todo.isDone());
    }

    @Test
    public void testDeleteTask_validIndex_success() throws WaguriException {
        int initialSize = taskList.getTasks().size();
        taskList.deleteTask(1);
        assertEquals(initialSize - 1, taskList.getTasks().size());
    }

    @Test
    public void testCreateTodo_validDescription_success() throws WaguriException {
        int initialSize = taskList.getTasks().size();
        taskList.createTodo("Read book");
        assertEquals(initialSize + 1, taskList.getTasks().size());
        assertTrue(taskList.getTasks().get(initialSize) instanceof Todo);
    }

    @Test
    public void testCreateTodo_emptyDescription_throwsException() {
        assertThrows(WaguriException.class, () -> taskList.createTodo(""));
        assertThrows(WaguriException.class, () -> taskList.createTodo("   "));
    }

    @Test
    public void testCreateDeadline_validInput_success() throws WaguriException {
        int initialSize = taskList.getTasks().size();
        taskList.createDeadline("Finish project /by 25/12/2024 1800");
        assertEquals(initialSize + 1, taskList.getTasks().size());
        assertTrue(taskList.getTasks().get(initialSize) instanceof Deadline);
    }

    @Test
    public void testCreateDeadline_missingByKeyword_throwsException() {
        assertThrows(WaguriException.class, () -> taskList.createDeadline("Finish project"));
    }

    @Test
    public void testCreateDeadline_emptyFields_throwsException() {
        assertThrows(WaguriException.class, () -> taskList.createDeadline(" /by 25/12/2024"));
        assertThrows(WaguriException.class, () -> taskList.createDeadline("Finish project /by "));
    }

    @Test
    public void testCreateEvent_validInput_success() throws WaguriException {
        int initialSize = taskList.getTasks().size();
        taskList.createEvent("Conference /from 25/12/2024 0900 /to 25/12/2024 1700");
        assertEquals(initialSize + 1, taskList.getTasks().size());
        assertTrue(taskList.getTasks().get(initialSize) instanceof Event);
    }

    @Test
    public void testCreateEvent_missingKeywords_throwsException() {
        assertThrows(WaguriException.class, () -> taskList.createEvent("Conference"));
        assertThrows(WaguriException.class, () -> taskList.createEvent("Conference /from 0900"));
    }

    @Test
    public void testGetDueTasks_matchingDate_returnsTasks() throws WaguriException {
        ArrayList<Task> dueTasks = taskList.getDueTasks("25/12/2024");
        assertEquals(2, dueTasks.size());
        assertTrue(dueTasks.contains(deadline));
        assertTrue(dueTasks.contains(event));
    }

}
