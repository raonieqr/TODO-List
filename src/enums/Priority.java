package enums;

import entities.PrintLog;

import java.util.Scanner;

public enum Priority {
	MUITO_BAIXA("muito baixa", 1),
	BAIXA("baixa", 2),
	MEDIA("média", 3),
	ALTA("alta", 4),
	MUITO_ALTA("muito alta", 5);

	private String name;
	private int value;

	Priority(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	public static Priority fromValue(int value) {
		for (Priority priority : Priority.values()) {
			if (priority.value == value) {
				return priority;
			}
		}
		throw new IllegalArgumentException("Valor de Prioridade inválido: " + value);
	}

	public static Priority getPriorityFromUser(Scanner sc) {
		PrintLog.showTypePriority();
		int priorityValue;
		while (true) {
			try {
				priorityValue = Integer.parseInt(sc.nextLine());
				if (priorityValue >= 1 && priorityValue <= 5)
					break;
				System.out.println("Error: Prioridade inválida. Escolha um número entre 1 e 5.");
			} catch (NumberFormatException e) {
				System.out.println("Error: Entrada inválida. Digite um número válido.");
		}
	}

		return Priority.fromValue(priorityValue);
	}

}