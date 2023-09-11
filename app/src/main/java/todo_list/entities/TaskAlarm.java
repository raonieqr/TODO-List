package todo_list.entities;

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
				if (task.getDateTime().isBefore(taskMax.getDateTime()))
					taskMin = task;
			}

			int sleepTimeMin = taskMin.getDateTime().getMinute() - LocalDateTime.now().getMinute();
			int sleepTimeMax = taskMax.getDateTime().getMinute() - LocalDateTime.now().getMinute();

			if (sleepTimeMin <= 0 && sleepTimeMax <= 0) {
				System.out.println("Aguardando...");
				checkMessage(taskMax);
				tasks.remove(taskMax);
			}
			else if (sleepTimeMin == sleepTimeMax && taskMax.getPriority().getValue() > taskMin.getPriority().getValue()) {
				System.out.println("Aguardando...");
				Thread.sleep(sleepTimeMax * 60000);
				checkMessage(taskMax);
				tasks.remove(taskMax);
				checkMessage(taskMin);
				tasks.remove(taskMin);
			}
			else if (sleepTimeMin <= sleepTimeMax && taskMax.getPriority().getValue() > taskMin.getPriority().getValue()) {
				System.out.println("Aguardando...");
				Thread.sleep(sleepTimeMin * 60000);
				checkMessage(taskMin);
				tasks.remove(taskMin);
			}
			else {
				System.out.println("Aguardando...");
				Thread.sleep(sleepTimeMax * 60000);
				checkMessage(taskMax);
				tasks.remove(taskMax);
			}
		}
	}

	public void checkMessage(Task task) {
		System.out.println("Aguardando...");
		if (task.getStatus().getValue() == 1)
			System.out.println("Olá, foi feita a tarefa " + task.getName());
		else if (task.getStatus().getValue() == 2)
			System.out.println("Olá, está em andamento a tarefa " + task.getName());
		else
			System.out.println("Olá, está na hora de fazer sua tarefa " + task.getName());
	}
}
