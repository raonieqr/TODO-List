package todo_list.view;

import todo_list.enums.Priority;
import todo_list.utils.InputValidator;

import java.util.Scanner;

public class PriorityView {
	public static Priority getPriorityFromUser(Scanner sc) {
		TaskView.showTypePriority();

		int priorityValue;
		while (true) {
				priorityValue = InputValidator.promptForIntegerInput("Digite o número: ");
				if (priorityValue >= 1 && priorityValue <= 5)
					break;
				System.out.println("Erro: Prioridade inválida. Escolha um número entre 1 e 5.");
		}

		return Priority.fromValue(priorityValue);
	}
}
