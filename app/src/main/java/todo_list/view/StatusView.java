package todo_list.view;

import todo_list.enums.Status;
import todo_list.utils.InputValidator;

import java.util.Scanner;

public class StatusView {
	public static Status getStatusFromUser(Scanner sc) {
		TaskView.showTypeStatus();

		int statusValue;
		while (true) {
				statusValue = InputValidator.promptForIntegerInput("Digite um número: ");
				if (statusValue >= 1 && statusValue <= 3)
					break;
				System.out.println("Erro: Status inválido. Escolha um número entre 1 e 3.");
		}
		return Status.fromValue(statusValue);
	}
}
