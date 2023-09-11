package todo_list.enums;

import todo_list.entities.PrintLog;

import java.util.Scanner;

public enum Status {
	DONE("done", 1),
	DOING("doing", 2),
	TODO("todo", 3);

	String name;
	int value;

	Status(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	public static Status fromValue(int value) {
		for (Status status : Status.values()) {
			if (status.value == value) {
				return status;
			}
		}
		throw new IllegalArgumentException("Error: Status inválido: " + value);
	}

	public static Status getStatusFromUser(Scanner sc) {
		PrintLog.showTypeStatus();
		int statusValue;
		while (true) {
			try {
				statusValue = Integer.parseInt(sc.nextLine());
				if (statusValue >= 1 && statusValue <= 3)
					break;
				System.out.println("Error: Status inválido. Escolha um número entre 1 e 3.");
			} catch (NumberFormatException e) {
				System.out.println("Error: Entrada inválida. Digite um número válido.");
			}
		}
		return Status.fromValue(statusValue);
	}

}
