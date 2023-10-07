package todo_list.enums;

import todo_list.view.TaskView;

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
		throw new IllegalArgumentException(Integer.toString(value));
	}
}