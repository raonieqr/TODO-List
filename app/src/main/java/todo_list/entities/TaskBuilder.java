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
	private int id;

	public TaskBuilder(int id, String name, String description) {
		this.id = id;
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
		return new Task(id, name, description, category, priority, status, dateTime, alarm);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

