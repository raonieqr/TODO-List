package todo_list.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import todo_list.entities.Task;
import todo_list.enums.Priority;
import todo_list.enums.Status;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class FileManagerTest {
	private static FileManager fileManager;
	private static String testFilePath;

	@BeforeAll
	public static void setUp() {
		fileManager = new FileManager();
		testFilePath = "test.txt";
	}

	@Test
	public void testWriteToFile() throws IOException {
		List<String> testStrings = new ArrayList<>();
		testStrings.add("Line 1");
		testStrings.add("Line 2");

		fileManager.writeToFile(testStrings, testFilePath);

		List<String> fileContents = Files.readAllLines(Paths.get(testFilePath));
		assertEquals(testStrings, fileContents);

		Files.delete(Paths.get(testFilePath));
	}

	@Test
	public void testCreateFile() throws IOException {
		List<Task> tasks = new ArrayList<>();

		Task task1 = new Task(1, "Task 1", "Description 1", "Category 1", Priority.BAIXA,
				Status.TODO, null, false);

		Task task2 = new Task(2, "Task 2", "Description 2", "Category 2", Priority.ALTA,
				Status.DOING, null, true);

		tasks.add(task1);
		tasks.add(task2);

		fileManager.createFile(tasks);

		File testFile = new File("tasks.txt");
		assertTrue(testFile.exists());

		testFile.delete();
	}
}
