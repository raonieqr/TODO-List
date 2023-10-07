package todo_list;

import todo_list.utils.FileManager;
import todo_list.view.TaskView;
import todo_list.entities.Task;
import todo_list.entities.TaskAlarm;
import todo_list.enums.Priority;
import todo_list.enums.Status;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main
{
	public static boolean validateFields(String name, String description, String category) {
		return ! name.isEmpty() && ! description.isEmpty() && ! category.isEmpty();
	}

	public static void main(String[] args) throws InterruptedException {

		System.out.println("Bem vindo a TODO-List");

		ArrayList<Task> tasks = new ArrayList<>();
		ArrayList<Task> taskWithAlarm = new ArrayList<>();
		Scanner sc = new Scanner(System.in);

		int option = 0;
		int idCounter = 1;

		while (option != 6) {
			TaskView.showMenu();
			option = promptForIntegerInput("Digite o número: ");
			if (option == 1) {
				System.out.println("Qual nome da tarefa? ");
				String name = sc.nextLine();
				System.out.println("Qual a descrição?");
				String description = sc.nextLine();
				System.out.println("Qual categoria?");
				String category = sc.nextLine();

				if(!validateFields(name, description, category)) {
					System.out.println("Erro: Você passou um valor inválido. Tente novamente");
					continue;
				}
				Priority priority = Priority.getPriorityFromUser(sc);
				Status status = Status.getStatusFromUser(sc);

				System.out.println("Qual data e hora da tarefa? Passe no formato dd/MM/yyyy HH:mm");
				String date = sc.nextLine();
				LocalDateTime dateTime = null;

				try {
					String pattern = "dd/MM/yyyy HH:mm";
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
					dateTime = LocalDateTime.parse(date, formatter);
				} catch (Exception e) {
					System.out.println("Erro: Formato inválido. Formato correto dd/MM/yyyy HH:mm");
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
						System.out.println("Erro: Entrada inválida. Tente novamente");
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
					TaskView.showTask(tasks);
			}
			if (option == 3) {
				TaskAlarm taskAlarm = new TaskAlarm();
				taskAlarm.run(taskWithAlarm);
			}
			if (option == 4) {
				if (tasks.isEmpty()) {
					System.out.println("Erro: Lista vazia. Tente novamente");
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
							System.out.println("Erro: Status inválido. Escolha um número entre 1 e 3.");
							status = sc.nextLine();
						}
						task.setStatus(Status.fromValue(Integer.parseInt(status)));
					}
				}
			}
			if(option == 5) {
				if (tasks.isEmpty()) {
					System.out.println("Erro: Lista vazia. Tente novamente");
					continue;
				}
				for(Task task: tasks)
					System.out.println(task);
				System.out.println("Digite o id da tarefa que gostaria de deletar:");
				int index = Integer.parseInt(sc.nextLine());
				if (index >= 0 && (index - 1) <= tasks.size())
					tasks.remove((index - 1));
				else
					System.out.println("Erro: Index não encontrado");
			}
		}
		if (!tasks.isEmpty()) {
			File currentDirectory = new File(".");
			if (currentDirectory.getParent() == null)
				FileManager.writeToFile(tasks, new File(currentDirectory, "tasks.txt").getPath());
			else {
				File parentDirectory = currentDirectory.getParentFile();
				FileManager.writeToFile(tasks, new File(parentDirectory, "tasks.txt").getPath());
			}
		}
	}

	static int promptForIntegerInput(String prompt) {

		Scanner sc = new Scanner(System.in);

		System.out.println(prompt);

		String input = sc.nextLine();
		while (!isNumber(input)) {

			System.out.println("Erro: Entrada inválida. Tente novamente.");
			System.out.println(prompt);

			input = sc.nextLine();
		}
		return Integer.parseInt(input);
	}

	public static boolean isNumber(String input) {
		for (char c : input.toCharArray()) {
			if (Character.isDigit(c))
				return true;
		}
		return false;
	}
}