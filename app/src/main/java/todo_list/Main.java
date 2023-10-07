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
		FileManager fileManager = new FileManager();
		Scanner sc = new Scanner(System.in);

		int option = 0;
		while (option != 6) {

			TaskView.showMenu();

			option = InputValidator.promptForIntegerInput("Digite o número: ");
			switch (option) {
				case 1:
					Task task = TaskView.createTaskFromUserInput(sc);

					TaskView.createTaskAlarm(task, tasks, taskWithAlarm);

					System.out.println("Tarefa criada");

					break;
				case 2:
					if (tasks.isEmpty())
						System.out.println("A lista de tarefas está vazia");
					else
						TaskView.showTask(tasks);

					break;
				case 3:
					TaskAlarm taskAlarm = new TaskAlarm();
					taskAlarm.run(taskWithAlarm);

					break;
				case 4:
					if (tasks.isEmpty()) {
						System.out.println("Erro: Lista vazia. Tente novamente");
						return;
					}
					tasks.forEach(System.out::println);
					System.out.println("Escolha o id da tarefa que gostaria de editar:");
					int index = Integer.parseInt(sc.nextLine());
					for (Task selectedTask : tasks) {
						if (selectedTask.getId() == index) {
							System.out.println("Qual o novo status da tarefa? ");
							System.out.println("1 - Feito");
							System.out.println("2 - Em andamento");
							System.out.println("3 - A fazer");
							String status = sc.nextLine();
							while (! status.matches("^[1-3]$")) {
								System.out.println("Erro: Status inválido. Escolha um número entre 1 e 3.");
								status = sc.nextLine();
							}
							selectedTask.setStatus(Status.fromValue(Integer.parseInt(status)));
						}
					}
					break;
				case 5:
					if (tasks.isEmpty()) {
						System.out.println("Erro: Lista vazia. Tente novamente");
						return;
					}

					tasks.forEach(System.out::println);

					System.out.println("Digite o ID da tarefa que gostaria de deletar:");
					int idDelete = Integer.parseInt(sc.nextLine());

					if (idDelete >= 1 && idDelete <= tasks.size()) {
						tasks.remove(idDelete - 1);
						System.out.println("Tarefa removida com sucesso.");
					}
					else
						System.out.println("Erro: Índice não encontrado");
					break;

			}
		}
		fileManager.createFile(tasks);
	}
}