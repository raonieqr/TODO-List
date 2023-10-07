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

		int option = InputValidator.getOptionForThree();
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

		String option = InputValidator.promptForUserInput("Escreva o nome da categoria: ").toLowerCase();

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
		int option = InputValidator.getOptionForFive();

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

		if (tasks.isEmpty()) {
			System.out.println("A lista de tarefas está vazia");
			return ;
		}

		showMessageList();

		int option = InputValidator.getOptionForFive();

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

	public static Task createTaskFromUserInput(){
		String name = InputValidator.promptForUserInput("Qual nome da tarefa? ");
		String description = InputValidator.promptForUserInput("Qual a descrição?");
		String category = InputValidator.promptForUserInput("Qual categoria?");
		Priority priority = PriorityView.getPriorityFromUser();
		Status status = StatusView.getStatusFromUser();
		LocalDateTime dateTime = InputValidator.promptForDateInput();

		boolean alarm = InputValidator.promptForAlarmInput();

		return new TaskBuilder(name, description).withCategory(category).withPriority(priority).withStatus(status)
				.withDateTime(dateTime).withAlarm(alarm).build();

	}

	public static void createTaskAlarm(Task task, List<Task> tasks, List<Task> taskWithAlarm) {
		TaskController.addTaskAndHandleAlarms(task, tasks, taskWithAlarm);

		System.out.println("Tarefa criada");
	}

	public static void editTaskStatusById(List<Task> tasks) {
		if (tasks.isEmpty()) {
			System.out.println("Erro: Lista vazia. Tente novamente");
			return;
		}

		tasks.forEach(System.out::println);
		int status = 0;
		int index = InputValidator.promptForIntegerInput("Escolha o ID da tarefa que gostaria de editar:");

		for (Task selectedTask : tasks) {
			if (selectedTask.getId() == index) {
				TaskView.showTypeStatus();

				status = InputValidator.getOptionForThree();

				selectedTask.setStatus(Status.fromValue(status));

				System.out.println("Tarefa editada!");
			}
		}

		if (status == 0) {
			System.out.println("Erro: ID não localizado");
		}
	}

	public static void deleteTaskById(List<Task> tasks) {
		if (tasks.isEmpty()) {
			System.out.println("Erro: Lista vazia. Tente novamente");
			return;
		}

		tasks.forEach(System.out::println);

		int idDelete = InputValidator.promptForIntegerInput("Digite o ID para deletar: ");

		if (idDelete >= 1 && idDelete <= tasks.size()) {
			tasks.remove(idDelete - 1);
			System.out.println("Tarefa removida com sucesso.");
		}
		else {
			System.out.println("Erro: Índice não encontrado");
		}
	}

	public static void createAlarm(List<Task> taskWithAlarm) throws InterruptedException {
		TaskController.createTaskAlarm(taskWithAlarm);
	}
}
