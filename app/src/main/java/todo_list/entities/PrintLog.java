package todo_list.entities;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PrintLog {
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
		System.out.println("Escreva o número da prioridade: ");
		int option;

		while (true) {
			try {
				option = Integer.parseInt(sc.nextLine());
				if (option >= 1 && option <= 5)
					break;
				System.out.println("Error: Opção inválida. Escolha uma opção entre 1 e 5.");
			} catch (NumberFormatException e) {
				System.out.println("Error: Entrada inválida. Digite um número válido.");
			}
		}
		int counter = 0;
		for (Task task : tasks) {
			if (task.getPriority().getValue() == option) {
				System.out.println(task);
				counter++;
			}
		}
		if (counter == 0)
			System.out.println("Não existe tarefas com essa prioridade");
	}

	public static void showTask(ArrayList<Task> tasks) {
		showMessageList();
		Scanner sc = new Scanner(System.in);
		System.out.print("Digite o número escolhido: ");

		int option;
		while (true) {
			try {
				String input = sc.nextLine();
				option = Integer.parseInt(input);
				if (option >= 1 && option <= 5)
					break;
				System.out.println("Error: Opção inválida. Tente um número de 1 a 5");
			} catch (NumberFormatException e) {
				System.out.println("Error: Entrada inválida. Digite um número válido.");
			}
		}

		if (option == 1)
			showListStatus(tasks);
		else if (option == 2)
			showListCategory(tasks);
		else if (option == 3)
			showListPriority(tasks);
		else if (option == 4) {
			for (Task task : tasks)
					System.out.println(task);
			System.out.println("Total de tarefas " + tasks.size());
		}
		else
			return;
	}

}