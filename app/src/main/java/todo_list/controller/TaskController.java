package todo_list.controller;

import todo_list.entities.Task;
import todo_list.entities.TaskAlarm;

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

	public static void createTaskAlarm(List<Task> taskWithAlarm) throws InterruptedException {

		TaskAlarm taskAlarm = new TaskAlarm(taskWithAlarm);
		Thread taskAlarmThread = new Thread(taskAlarm);
		taskAlarmThread.start();
	}
}
