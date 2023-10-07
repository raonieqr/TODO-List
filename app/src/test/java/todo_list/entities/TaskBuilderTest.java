package todo_list.entities;

import org.junit.jupiter.api.Test;
import todo_list.enums.Priority;
import todo_list.enums.Status;
import todo_list.service.ITaskBuilder;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class TaskBuilderTest {

	@Test
	public void testBuildTaskWithAlarm() {
		ITaskBuilder taskBuilder = new TaskBuilder("task1", "task1")
				.withCategory("tarefas")
				.withPriority(Priority.MEDIA)
				.withStatus(Status.TODO)
				.withDateTime(LocalDateTime.of(2023, 10, 7, 1, 20))
				.withAlarm(true);

		Task task = taskBuilder.build();

		assertTrue(task.isAlarm());
	}

	@Test
	public void testBuildTaskWithoutAlarm() {
		ITaskBuilder taskBuilder = new TaskBuilder("task2", "task2")
				.withCategory("outras tarefas")
				.withPriority(Priority.ALTA)
				.withStatus(Status.DOING)
				.withDateTime(LocalDateTime.of(2023, 10, 8, 14, 30))
				.withAlarm(false);

		Task task = taskBuilder.build();

		assertFalse(task.isAlarm());
	}

	@Test
	public void testBuildTaskWithDefaultValues() {
		TaskBuilder taskBuilder = new TaskBuilder("task3", "task3");

		Task task = taskBuilder.build();

		assertNull(task.getCategory());
		assertNull(task.getPriority());
		assertNull(task.getStatus());
		assertNull(task.getDateTime());
		assertFalse(task.isAlarm());
	}
}
