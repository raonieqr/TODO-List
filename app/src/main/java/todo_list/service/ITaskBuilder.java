package todo_list.service;

import todo_list.entities.Task;
import todo_list.enums.Priority;
import todo_list.enums.Status;

import java.time.LocalDateTime;

public interface ITaskBuilder {
	ITaskBuilder withCategory(String category);
	ITaskBuilder withPriority(Priority priority);
	ITaskBuilder withStatus(Status status);
	ITaskBuilder withDateTime(LocalDateTime dateTime);
	ITaskBuilder withAlarm(boolean alarm);
	Task build();
}
