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
	static Task task2;

	static ArrayList<Task> tasks;
	static ArrayList<Task> taskWithAlarm;

	@BeforeAll
	public static void createTask() {
		int id = 0;
		task = new TaskBuilder(++id,"task1", "task1")
				.withCategory("tarefas")
				.withPriority(Priority.MEDIA)
				.withStatus(Status.TODO)
				.withDateTime(LocalDateTime.of(2023, 10, 7, 1, 22))
				.withAlarm(true)
				.build();

		task2 = new TaskBuilder(++id,"task1", "task1")
				.withCategory("tarefas")
				.withPriority(Priority.MEDIA)
				.withStatus(Status.TODO)
				.withDateTime(LocalDateTime.of(2023, 10, 7, 1, 22))
				.withAlarm(true)
				.build();

		tasks = new ArrayList<>();
		taskWithAlarm = new ArrayList<>();

	}

	@Test
	public void testIdTask() {
		assertNotEquals(task.getId(), task2.getId());
	}
	@Test
	public void testAddTaskAndHandleAlarms() {
		TaskController.addTaskAndHandleAlarms(task, tasks, taskWithAlarm);

		assertFalse(tasks.isEmpty());
		assertFalse(taskWithAlarm.isEmpty());
	}

	@Test
	public void testCreateTaskAlarm() throws InterruptedException {

		Thread taskAlarmThread;

		taskAlarmThread = new Thread(() -> {
			try {
				TaskController.createTaskAlarm(taskWithAlarm);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		taskAlarmThread.start();

		assertTrue(taskAlarmThread.isAlive());;
	}

}