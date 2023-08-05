import entities.FileUtils;
import entities.PrintLog;
import entities.Task;
import enums.Priority;
import enums.Status;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main
{
	public static void main(String[] args) {
		System.out.println("Bem vindo a TODO-List");
		ArrayList<Task> tasks = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		int option = 0;
		int idCounter = 1;
		while (option != 3) {
			System.out.println("Escolha uma opções abaixo");
			System.out.println("1 - Criar tarefa");
			System.out.println("2 - Visualizar tarefas");
			System.out.println("3 - Sair");
			while (true) {
				try {
					option = Integer.parseInt(sc.nextLine());
					if (option >= 1 && option <= 3)
						break;
					System.out.println("Error: Opção inválida. Escolha uma opção entre 1 e 3.");
				} catch (Exception e) {
					System.out.println("Error: Entrada inválida. Digite um número válido.");
				}
			}
			if (option == 1) {
				System.out.println("Qual nome da tarefa? ");
				String name = sc.nextLine();
				System.out.println("Qual a descrição?");
				String description = sc.nextLine();
				System.out.println("Qual categoria?");
				String category = sc.nextLine();

				Priority priority = Priority.getPriorityFromUser(sc);
				Status status = Status.getStatusFromUser(sc);

				System.out.println("Qual data e hora da tarefa? Passe no formato dd/MM/yyyy HH:mm:ss");
				String date = sc.nextLine();
				LocalDateTime dateTime = null;

				try {
					String pattern = "dd/MM/yyyy HH:mm:ss";
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
					dateTime = LocalDateTime.parse(date, formatter);
				} catch (Exception e) {
					System.out.println("Error: Formato inválido. Formato correto dd/MM/yyyy HH:mm:ss");
					continue;
				}
				Task task = new Task(idCounter++, name, description, category, priority, status, dateTime);
				tasks.add(task);
				if (tasks.size() > 1)
					Collections.sort(tasks, Comparator.comparing(Task::getDateTime).thenComparing(Task::getPriority));
				System.out.println("Tarefa criada");
			}
			if (option == 2) {
				if (tasks.isEmpty())
					System.out.println("A lista de tarefas está vazia");
				else
					PrintLog.showTask(tasks);
			}
		}
		File currentDirectory = new File(".");
		if (currentDirectory.getParent() == null)
			FileUtils.writeToFile(tasks, new File(currentDirectory, "tasks.txt").getPath());
		else {
			File parentDirectory = currentDirectory.getParentFile();
			FileUtils.writeToFile(tasks, new File(parentDirectory, "tasks.txt").getPath());
		}
	}
}