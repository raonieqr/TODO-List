package todo_list.entities;

import todo_list.enums.Priority;
import todo_list.enums.Status;
import todo_list.service.ITaskBuilder;

import java.time.LocalDateTime;

public class TaskBuilder implements ITaskBuilder {
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

	public ITaskBuilder withCategory(String category) {
		this.category = category;
		return this;
	}

	public ITaskBuilder withPriority(Priority priority) {
		this.priority = priority;
		return this;
	}

	public ITaskBuilder withStatus(Status status) {
		this.status = status;
		return this;
	}

	public ITaskBuilder withDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
		return this;
	}

	public ITaskBuilder withAlarm(boolean alarm) {
		this.alarm = alarm;
		return this;
	}

	public Task build() {
		int idCounter = 1;

		return new Task(idCounter++, name, description, category, priority, status, dateTime, alarm);
	}
}

