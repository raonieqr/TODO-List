package todo_list;

import todo_list.entities.TaskBuilder;
import todo_list.utils.FileManager;
import todo_list.utils.InputValidator;
import todo_list.view.PriorityView;
import todo_list.view.StatusView;
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
		public static void main(String[] args) throws InterruptedException {

		System.out.println("Bem vindo a TODO-List");

		ArrayList<Task> tasks = new ArrayList<>();
		ArrayList<Task> taskWithAlarm = new ArrayList<>();
		Scanner sc = new Scanner(System.in);

		int option = 0;
		int idCounter = 1;

		while (option != 6) {

			TaskView.showMenu();

			option = InputValidator.promptForIntegerInput("Digite o número: ");
			if (option == 1) {
				String name = InputValidator.promptForUserInput("Qual nome da tarefa? ");
				String description = InputValidator.promptForUserInput("Qual a descrição?");
				String category = InputValidator.promptForUserInput("Qual categoria?");
				Priority priority = PriorityView.getPriorityFromUser(sc);
				Status status = StatusView.getStatusFromUser(sc);
				LocalDateTime dateTime = InputValidator.promptForDateInput();

				System.out.println("Deseja colocar alarme para esta tarefa?");

				String alarmTest;
				boolean alarm;
				while (true) {
						alarmTest = InputValidator.promptForUserInput("1 - Sim\n2 - Não");
						if (alarmTest.matches("^1|2$")) {
							alarm = alarmTest.equals("1");
							break;
						}
						System.out.println("Erro: Entrada inválida. Tente novamente");
				}

				Task task = new TaskBuilder(name, description)
						.withCategory(category)
						.withPriority(priority)
						.withStatus(status)
						.withDateTime(dateTime)
						.withAlarm(alarm)
						.build();

				tasks.add(task);

				if (alarm) {
					taskWithAlarm.add(task);
					System.out.println("Criado alarme para a tarefa");
				}
				if (tasks.size() > 1)
					tasks.sort(Comparator.comparing(Task::getDateTime).thenComparing(Task::getPriority));
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


}