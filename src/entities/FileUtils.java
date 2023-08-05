package entities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileUtils {
	public static void writeToFile(ArrayList<?> tasks, String filePath) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
			}

			for (Object item : tasks) {
				writer.write(item.toString());
				writer.newLine();
			}
			writer.close();
			System.out.println("Conteúdo salvo em " + filePath);
		} catch (IOException e) {
			System.out.println("Error: Não foi possível gravar o arquivo " + filePath);
		}
	}
}
