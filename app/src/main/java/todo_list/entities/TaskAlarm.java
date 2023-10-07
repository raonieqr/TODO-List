package todo_list.entities;

import java.time.LocalDateTime;
import java.util.List;
public class TaskAlarm implements Runnable {
	private List<Task> tasks;
	private boolean isRunning = true;
	private boolean isPaused = false;

	public TaskAlarm(List<Task> tasks) {
		this.tasks = tasks;
	}

	@Override
	public void run() {
		while (isRunning) {
			synchronized (this) {
				while (isPaused) {
					while (isPaused || tasks.isEmpty()) {
						try {
								this.wait();
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
						}
					}
				}

				if (!tasks.isEmpty()) {
					Task taskMax = tasks.get(0);
					Task taskMin = tasks.get(0);

					for (Task task : tasks) {
						if (task.getPriority().getValue() > taskMax.getPriority().getValue())
							taskMax = task;

						if (task.getDateTime().isBefore(taskMax.getDateTime()))
							taskMin = task;
					}

					System.out.println("Aguardando...");

					try {
						checkTimeAndRemoveTasks(taskMax, taskMin, tasks);
					}
					catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
				else
					isRunning = false;
			}
		}
	}

	public void removeMax(Task taskMax, List<Task> tasks){
		checkMessage(taskMax);
		tasks.remove(taskMax);
	}

	public void removeMin(Task taskMin, List<Task> tasks){
		checkMessage(taskMin);
		tasks.remove(taskMin);
	}

	public void checkTimeAndRemoveTasks(Task taskMax, Task taskMin, List<Task> tasks) throws InterruptedException {
		int sleepTimeMin = taskMin.getDateTime().getMinute() - LocalDateTime.now().getMinute();
		int sleepTimeMax = taskMax.getDateTime().getMinute() - LocalDateTime.now().getMinute();

		if (sleepTimeMin <= 0 && sleepTimeMax <= 0)
			removeMax(taskMax, tasks);
		else if (sleepTimeMin == sleepTimeMax && taskMax.getPriority().getValue() > taskMin.getPriority().getValue()) {
			Thread.sleep(sleepTimeMax * 60000);
			removeMax(taskMax, tasks);
			removeMin(taskMin, tasks);
		}
		else if (sleepTimeMin <= sleepTimeMax && taskMax.getPriority().getValue() > taskMin.getPriority().getValue()) {
			Thread.sleep(sleepTimeMin * 60000);
			removeMin(taskMin, tasks);
		}
		else {
			Thread.sleep(sleepTimeMax * 60000);
			removeMax(taskMax, tasks);
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