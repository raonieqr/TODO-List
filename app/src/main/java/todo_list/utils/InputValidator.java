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
}
