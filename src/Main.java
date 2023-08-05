import entities.PrintLog;
import entities.Task;
import enums.Priority;
import enums.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main
{
	public static void main(String[] args) {
		System.out.println("Bem vindo a TODO-List");
		System.out.println("Escolha uma opções abaixo");
		System.out.println("1 - Criar tarefa");
		System.out.println("2 - Atualizar tarefa");
		System.out.println("3 - Visualizar tarefas");
		System.out.println("4 - Sair");
		Scanner sc = new Scanner(System.in);
		int option;
		while (true) {
			try {
				option = Integer.parseInt(sc.nextLine());
				if (option >= 1 && option <= 4)
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

			Priority priority = Priority.getPriorityFromUser(sc);
			Status status = Status.getStatusFromUser(sc);

			Task task = new Task(1, name, description, category, priority, status, LocalDateTime.now());
			ArrayList<Task> tasks = new ArrayList<>();
			tasks.add(task);
			PrintLog.showTask(tasks);
		}
	}
}