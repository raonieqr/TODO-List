package todo_list.view;

import todo_list.controller.TaskController;
import todo_list.entities.Task;
import todo_list.entities.TaskBuilder;
import todo_list.enums.Priority;
import todo_list.enums.Status;
import todo_list.utils.InputValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskView {
	public static void showMessageList() {
		System.out.println("Comandos para listar as atividades:");
		System.out.println("1 - Consultar por status");
		System.out.println("2 - Consultar por categoria");
		System.out.println("3 - Consultar por prioridade");
		System.out.println("4 - Consultar geral");
		System.out.println("5 - Para sair dessa opção");
	}

	public static void showTypeStatus() {
		System.out.println("Escolha o status:");
		System.out.println("1 - Feito");
		System.out.println("2 - Em andamento");
		System.out.println("3 - A fazer");
	}

	public static void showListStatus(ArrayList<Task> tasks) {
		showTypeStatus();
		Scanner sc = new Scanner(System.in);
		int option;
		while (true) {
			try {
				option = Integer.parseInt(sc.nextLine());
				if (option >= 1 && option <= 3)
					break;
				System.out.println("Error: Opção inválida. Escolha uma opção entre 1 e 3.");
			} catch (NumberFormatException e) {
				System.out.println("Error: Entrada inválida. Digite um número válido.");
			}
		}
		int counter = 0;
		for (Task task : tasks) {
			if (task.getStatus().getValue() == option) {
				System.out.println(task);
				counter++;
			}
		}
		if (counter == 0)
			System.out.println("Não existe tarefa com esse status");
		if (counter > 0)
			System.out.println("Total de tarefas: " + counter);
	}

	public static void showListCategory(ArrayList<Task> tasks) {
		if (tasks.isEmpty()) {
			System.out.println("A lista está vazia");
			return;
		}

		Scanner sc = new Scanner(System.in);
		System.out.println("Escreva o nome da categoria: ");
		String option = sc.nextLine().trim().toLowerCase();

		int counter = 0;
		for (Task task : tasks) {
			String category = task.getCategory();
			if (category != null && category.equals(option)) {
				System.out.println(task);
				counter++;
			}
		}

		if (counter == 0)
			System.out.println("A categoria não existe");
		if (counter > 0)
			System.out.println("Total de tarefas: " + counter);
	}

	public static void showMenu() {
		System.out.println("Escolha uma opções abaixo");
		System.out.println("1 - Criar tarefa");
		System.out.println("2 - Visualizar tarefas");
		System.out.println("3 - Aguardar alarme");
		System.out.println("4 - Editar status da tarefa");
		System.out.println("5 - Deletar tarefa");
		System.out.println("6 - Sair");
	}
	public static void showTypePriority() {
		System.out.println("Qual a prioridade ?");
		System.out.println("1 - Muito baixa");
		System.out.println("2 - Baixa");
		System.out.println("3 - Média");
		System.out.println("4 - Alta");
		System.out.println("5 - Muito Alta");
	}

	public static void showListPriority(ArrayList<Task> tasks) {
		showTypePriority();

		Scanner sc = new Scanner(System.in);
		int option = InputValidator.getOption();

		int counter = 0;
		for (Task task : tasks) {
			if (task.getPriority().getValue() == option) {
				System.out.println(task);
				counter++;
			}
		}

		if (counter == 0)
			System.out.println("Não existe tarefas com essa prioridade");
		if (counter > 0)
			System.out.println("Total de tarefas: " + counter);
	}

	public static void showTask(ArrayList<Task> tasks) {
		showMessageList();

		int option = InputValidator.getOption();

		switch (option) {
			case 1:
				showListStatus(tasks);
				break;
			case 2:
				showListCategory(tasks);
				break;
			case 3:
				showListPriority(tasks);
				break;
			case 4:
				tasks.forEach(System.out::println);
				break;
		}
	}

	public static Task createTaskFromUserInput(Scanner sc){
		String name = InputValidator.promptForUserInput("Qual nome da tarefa? ");
		String description = InputValidator.promptForUserInput("Qual a descrição?");
		String category = InputValidator.promptForUserInput("Qual categoria?");
		Priority priority = PriorityView.getPriorityFromUser(sc);
		Status status = StatusView.getStatusFromUser(sc);
		LocalDateTime dateTime = InputValidator.promptForDateInput();

		boolean alarm = InputValidator.promptForAlarmInput();

		return new TaskBuilder(name, description).withCategory(category).withPriority(priority).withStatus(status)
				.withDateTime(dateTime).withAlarm(alarm).build();

	}
	public static void createTaskAlarm(Task task, List<Task> tasks, List<Task> taskWithAlarm) {
		TaskController.addTaskAndHandleAlarms(task, tasks, taskWithAlarm);
	}
}
