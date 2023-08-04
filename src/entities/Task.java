package entities;

import enums.Priority;
import enums.Status;

import java.time.LocalDateTime;

public class Task {
	private Integer id;
	private String name;
	private String description;
	private String category;
	private Priority priority;
	private Status status;
	private LocalDateTime dateTime;

	public Task(Integer id, String name, String description, String category, Priority priority, Status status, LocalDateTime dateTime)
	{
		this.id = id;
		this.name = name;
		this.description = description;
		this.category = category;
		this.priority = priority;
		this.status = status;
		this.dateTime = dateTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	public String toString()
	{
		return "Task: " + "id=" + id + ", name='" + name + '\'' + ", description='" + description + '\'' + ", " +
				"category='" + category + '\'' + ", priority=" + priority + ", status=" + status + ", date=" + dateTime;
	}
}
