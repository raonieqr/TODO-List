package todo_list.utils;

import todo_list.entities.Task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileManager {
	public void writeToFile(List<?> tasks, String filePath) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			File file = new File(filePath);
			if (!file.exists())
				file.createNewFile();

			for (Object item : tasks) {
				writer.write(item.toString());
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			System.out.println(filePath);
		}
	}

	public void createFile(List<Task> tasks) {
		if (!tasks.isEmpty()) {
			File currentDirectory = new File(".");
			if (currentDirectory.getParent() == null)
				this.writeToFile(tasks, new File(currentDirectory, "tasks.txt").getPath());
			else {
				File parentDirectory = currentDirectory.getParentFile();
				this.writeToFile(tasks, new File(parentDirectory, "tasks.txt").getPath());
			}
		}
	}
}
