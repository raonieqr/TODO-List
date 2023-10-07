package todo_list.entities;

import todo_list.enums.Priority;
import todo_list.enums.Status;

import java.time.LocalDateTime;
import java.util.Comparator;

public class TaskBuilder {
	private String name;
	private String description;
	private String category;
	private Priority priority;
	private Status status;
	private LocalDateTime dateTime;
	private boolean alarm;

	public TaskBuilder(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public TaskBuilder withCategory(String category) {
		this.category = category;
		return this;
	}

	public TaskBuilder withPriority(Priority priority) {
		this.priority = priority;
		return this;
	}

	public TaskBuilder withStatus(Status status) {
		this.status = status;
		return this;
	}

	public TaskBuilder withDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
		return this;
	}

	public TaskBuilder withAlarm(boolean alarm) {
		this.alarm = alarm;
		return this;
	}

	public Task build() {
		int idCounter = 1;

		return new Task(idCounter++, name, description, category, priority, status, dateTime);
	}
}

