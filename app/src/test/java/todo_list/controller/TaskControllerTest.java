package todo_list.controller;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import todo_list.entities.Task;
import todo_list.entities.TaskBuilder;
import todo_list.enums.Priority;
import todo_list.enums.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TaskControllerTest {
	static Task task;
	static ArrayList<Task> tasks;
	static ArrayList<Task> taskWithAlarm;

	@BeforeAll
	public static void createTask() {
		task = new TaskBuilder("task1", "task1")
				.withCategory("tarefas")
				.withPriority(Priority.MEDIA)
				.withStatus(Status.TODO)
				.withDateTime(LocalDateTime.of(2023, 10, 7, 1, 20))
				.withAlarm(true)
				.build();

		tasks = new ArrayList<>();
		taskWithAlarm = new ArrayList<>();

	}

	@Test
	public void testAddTaskAndHandleAlarms() {
		TaskController.addTaskAndHandleAlarms(task, tasks, taskWithAlarm);

		assert !tasks.isEmpty();
		assert !taskWithAlarm.isEmpty();
	}

}