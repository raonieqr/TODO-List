package todo_list.enums;

import todo_list.view.TaskView;

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
		throw new IllegalArgumentException(Integer.toString(value));
	}
}
