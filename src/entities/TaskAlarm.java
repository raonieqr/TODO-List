package entities;

import java.time.Duration;
import java.util.ArrayList;
import java.time.LocalDateTime;
public class TaskAlarm {
	public void run(ArrayList<Task> tasks) throws InterruptedException {
		while (!tasks.isEmpty()) {
			Task taskMax = tasks.get(0);
			Task taskMin = tasks.get(0);

			for (Task task : tasks) {
				if (task.getPriority().getValue() > taskMax.getPriority().getValue())
					taskMax = task;
				if (task.getDateTime().isBefore(taskMin.getDateTime()))
					taskMin = task;
			}

			LocalDateTime now = LocalDateTime.now();
			long sleepTimeMin = Duration.between(now, taskMin.getDateTime()).toMillis();
			long sleepTimeMax = Duration.between(now, taskMax.getDateTime()).toMillis();

			if (sleepTimeMin <= 0 && sleepTimeMax <= 0) {
				System.out.println("Olá, está na hora de fazer sua tarefa " + taskMin.getName());
				tasks.remove(taskMin);
			}
			else if (sleepTimeMin <= 0) {
				System.out.println("Olá, está na hora de fazer sua tarefa " + taskMin.getName());
				tasks.remove(taskMin);
				Thread.sleep(sleepTimeMax);
			}
			else {
				System.out.println("Olá, está na hora de fazer sua tarefa " + taskMax.getName());
				tasks.remove(taskMax);
				Thread.sleep(sleepTimeMin);
			}
		}
	}
}
