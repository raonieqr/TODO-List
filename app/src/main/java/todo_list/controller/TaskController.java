package todo_list.controller;

import todo_list.entities.Task;

import java.util.Comparator;
import java.util.List;

public class TaskController {
	public static void addTaskAndHandleAlarms(Task task, List<Task> tasks, List<Task> taskWithAlarm) {
		tasks.add(task);

		if (task.isAlarm()) {
			taskWithAlarm.add(task);
		}

		if (tasks.size() > 1) {
			tasks.sort(Comparator.comparing(Task::getDateTime).thenComparing(Task::getPriority));
		}
	}
}
