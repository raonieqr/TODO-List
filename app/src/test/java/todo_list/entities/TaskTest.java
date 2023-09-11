package todo_list.entities;

import org.junit.jupiter.api.Test;
import todo_list.enums.Priority;
import todo_list.enums.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

	@Test
	void testAddTask() {

		//Given
		ArrayList<Task> tasks = new ArrayList<>();
		Task task = new Task(1, "task1", "comer", "alimento", Priority.MEDIA, Status.TODO,
				LocalDateTime.of(2023, 10, 23, 22, 00));
		System.out.println("Criando " + task.toString());

		//When
		tasks.add(task);

		//Then
		assertEquals(1, tasks.size());
		System.out.println("Tarefa criada com sucesso!");

	}

	@Test
	void testReadTask() {

		//Given
		Task task = new Task(1, "task1", "comer", "alimento", Priority.MEDIA, Status.TODO,
				LocalDateTime.of(2023, 10, 23, 22, 00));
		System.out.println("Criando " + task.toString());

		//When
		int id = task.getId();
		String name = task.getName();
		String description = task.getDescription();
		String category = task.getCategory();
		Priority priority = task.getPriority();
		Status status = task.getStatus();
		LocalDateTime dateTime = task.getDateTime();

		//Then
		assertEquals(1, id);
		assertEquals("task1", name);
		assertEquals("comer", description);
		assertEquals("alimento", category);
		assertEquals(Priority.MEDIA, priority);
		assertEquals(Status.TODO, status);
		assertEquals(LocalDateTime.of(2023, 10, 23, 22, 00), dateTime);
		System.out.println("Tarefa lida com sucesso!");

	}

	@Test
	void testEditTask() {

		//Given
		ArrayList<Task> tasks = new ArrayList<>();
		Task task = new Task(1, "task1", "comer", "alimento", Priority.MEDIA, Status.TODO,
				LocalDateTime.of(2023, 10, 23, 22, 00));
		System.out.println("Criando " + task.toString());

		int id = 2;
		String name = "task2";
		String description = "trabalhar";
		String category = "trabalho";
		Priority priority = Priority.ALTA;
		Status status = Status.DOING;
		LocalDateTime dateTime = LocalDateTime.of(2023, 11, 2, 7, 00);

		//When
		task.setId(id);
		task.setName(name);
		task.setDescription(description);
		task.setCategory(category);
		task.setPriority(priority);
		task.setStatus(status);
		task.setDateTime(dateTime);

		//Then
		assertEquals(id, task.getId());
		assertEquals(name, task.getName());
		assertEquals(description, task.getDescription());
		assertEquals(category, task.getCategory());
		assertEquals(priority, task.getPriority());
		assertEquals(status, task.getStatus());
		assertEquals(dateTime, task.getDateTime());

		System.out.println("Tarefa editada com sucesso!");
		System.out.println(task.toString());
	}

	@Test
	void testDeleteTask() {

		//Given
		ArrayList<Task> tasks = new ArrayList<>();
		Task task = new Task(1, "task1", "comer", "alimento", Priority.MEDIA, Status.TODO,
				LocalDateTime.of(2023, 10, 23, 22, 00));
		System.out.println("Criando " + task.toString());

		//When
		tasks.add(task);
		tasks.remove(task);

		//Then
		assertTrue(tasks.isEmpty());
		System.out.println("Tarefa deletada com sucesso!");
	}

}