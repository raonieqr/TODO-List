package todo_list.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputValidator {
	public static int promptForIntegerInput(String prompt) {

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

	public static LocalDateTime promptForDateInput() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime dateTime = null;

		while (dateTime == null) {
			String input = promptForUserInput("Digite a data e hora (dd/MM/yyyy HH:mm): ");

			if (input.matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}")) {
				dateTime = LocalDateTime.parse(input, formatter);
			} else {
				System.out.println("Erro: Formato inválido. Formato correto dd/MM/yyyy HH:mm");
			}
		}

		return dateTime;
	}

	public static boolean promptForAlarmInput() {
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
		return alarm;
	}

	public static String promptForUserInput(String prompt) {

		Scanner sc = new Scanner(System.in);

		System.out.println(prompt);

		String input = sc.nextLine();
		while (input.trim().isEmpty()) {

			System.out.println("Erro: O campo não pode estar vazio. Tente novamente.");
			System.out.println(prompt);

			input = sc.nextLine();
		}
		return input;
	}

	public static int getOption() {
		int option;

		while (true) {
			option = InputValidator.promptForIntegerInput("Digite o número: ");
			if (option >= 1 && option <= 5)
				break;

			System.out.println("Erro: Opção inválida. Tente um número de 1 a 5");
		}
		return option;
	}
}
