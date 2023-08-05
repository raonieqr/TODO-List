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
				option = sc.nextInt();
				if (option >= 1 && option <= 4)
					break;
				System.out.println("Error: Opção inválida. Escolha uma opção entre 1 e 4.");
			} catch (InputMismatchException e) {
				System.out.println("Error: Entrada inválida. Digite um número válido.");
				sc.next();
			}
		}
		sc.nextLine();
		if (option == 1) {
			System.out.println("Qual nome da tarefa? ");
			String name = sc.nextLine();
			System.out.println("Qual a descrição?");
			String description = sc.nextLine();
			System.out.println("Qual categoria?");
			String category = sc.nextLine();
			System.out.println("Qual a prioridade ?");
			System.out.println("1 - Muito baixa");
			System.out.println("2 - Baixa");
			System.out.println("3 - Média");
			System.out.println("4 - Alta");
			System.out.println("5 - Muito Alta");
			int priorityValue;
			while (true) {
				try {
					priorityValue = sc.nextInt();
					if (priorityValue >= 1 && priorityValue <= 5)
						break;
					System.out.println("Error: Prioridade inválida. Escolha um número entre 1 e 5.");
				} catch (InputMismatchException e) {
					System.out.println("Error: Entrada inválida. Digite um número válido.");
					sc.next();
				}
			}
			sc.nextLine();
			Priority priority = null;
			if (priorityValue == 1)
				priority = Priority.MUITO_BAIXA;
			if (priorityValue == 2)
				priority = Priority.BAIXA;
			if (priorityValue == 3)
				priority = Priority.MEDIA;
			if (priorityValue == 4)
				priority = Priority.ALTA;
			if(priorityValue == 5)
				priority = Priority.MUITO_ALTA;

			Task task = new Task(1, "Comer", "comida japonesa", "alimento", priority, Status.TODO, LocalDateTime.now());
			ArrayList<Task> tasks = new ArrayList<>();
			tasks.add(task);
			PrintLog.showTask(tasks);
		}
	}
}