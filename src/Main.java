import entities.FileUtils;
import entities.PrintLog;
import entities.Task;
import entities.TaskAlarm;
import enums.Priority;
import enums.Status;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main
{
	public static boolean validateFields(String name, String description, String category) {
		if (name.isEmpty() || containsNumber(name) || description.isEmpty() || containsNumber(description) ||
				category.isEmpty() || containsNumber(category))
			return false;
		return true;
	}

	public static boolean containsNumber(String input) {
		for (char c : input.toCharArray()) {
			if (Character.isDigit(c))
				return true;
		}
		return false;
	}
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Bem vindo a TODO-List");
		ArrayList<Task> tasks = new ArrayList<>();
		ArrayList<Task> taskWithAlarm = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		int option = 0;
		int idCounter = 1;
		while (option != 6) {
			System.out.println("Escolha uma opções abaixo");
			System.out.println("1 - Criar tarefa");
			System.out.println("2 - Visualizar tarefas");
			System.out.println("3 - Aguardar alarme");
			System.out.println("4 - Editar status da tarefa");
			System.out.println("5 - Deletar tarefa");
			System.out.println("6 - Sair");
			while (true) {
				try {
					option = Integer.parseInt(sc.nextLine());
					if (option >= 1 && option <= 6)
						break;
					System.out.println("Error: Opção inválida. Escolha uma opção entre 1 e 4.");
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

				if(!validateFields(name, description, category)) {
					System.out.println("Error: Você passou um valor inválido. Tente novamente");
					continue;
				}
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
				System.out.println("Deseja colocar alarme para esta tarefa?");
				System.out.println("1 - Sim");
				System.out.println("2 - Não");
				int alarm;
				while (true) {
					try {
						alarm = Integer.parseInt(sc.nextLine());
						if (alarm == 1 || alarm == 2)
							break;
					}
					catch (NumberFormatException e) {
						System.out.println("Error: Entrada inválida. Tente novamente");
					}
				}
				Task task = new Task(idCounter++, name, description, category, priority, status, dateTime);
				tasks.add(task);
				if (alarm == 1) {
					taskWithAlarm.add(task);
					System.out.println("Criado alarme para a tarefa");
				}
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
			if (option == 3) {
				TaskAlarm taskAlarm = new TaskAlarm();
				taskAlarm.run(taskWithAlarm);
			}
			if (option == 4) {
				if (tasks.isEmpty()) {
					System.out.println("Error: Lista vazia. Tente novamente");
					continue;
				}
				for(Task task: tasks)
					System.out.println(task);
				System.out.println("Escolha o id da tarefa que gostaria de editar:");
				int index = Integer.parseInt(sc.nextLine());
				for (Task task: tasks) {
					if (task.getId() == index) {
						System.out.println("Qual o novo status da tarefa? ");
						System.out.println("1 - Feito");
						System.out.println("2 - Em andamento");
						System.out.println("3 - A fazer");
						String status = sc.nextLine();
						while(!status.matches("^[1-3]$")) {
							System.out.println("Error: Status inválido. Escolha um número entre 1 e 3.");
							status = sc.nextLine();
						}
						task.setStatus(Status.fromValue(Integer.parseInt(status)));
					}
				}
			}
			if(option == 5) {
				if (tasks.isEmpty()) {
					System.out.println("Error: Lista vazia. Tente novamente");
					continue;
				}
				for(Task task: tasks)
					System.out.println(task);
				System.out.println("Digite o id da tarefa que gostaria de deletar:");
				int index = Integer.parseInt(sc.nextLine());
				if (index >= 0 && (index - 1) <= tasks.size())
					tasks.remove((index - 1));
				else
					System.out.println("Error: Index não encontrado");
			}
		}
		if (!tasks.isEmpty()) {
			File currentDirectory = new File(".");
			if (currentDirectory.getParent() == null)
				FileUtils.writeToFile(tasks, new File(currentDirectory, "tasks.txt").getPath());
			else {
				File parentDirectory = currentDirectory.getParentFile();
				FileUtils.writeToFile(tasks, new File(parentDirectory, "tasks.txt").getPath());
			}
		}
	}
}