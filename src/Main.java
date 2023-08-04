import entities.PrintLog;
import entities.Task;
import enums.Priority;
import enums.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main
{
	public static void main(String[] args) {
		System.out.println("Bem vindo a TODO-List");
		Task task = new Task(1, "Comer","comida japonesa", "alimento", Priority.MUITO_BAIXA, Status.TODO,
				LocalDateTime.now());
		Task task1 = new Task(2, "Jogar","LOL", "games", Priority.MUITO_BAIXA, Status.TODO,
				LocalDateTime.now());
		ArrayList<Task> tasks = new ArrayList<>();
		tasks.add(task);
		tasks.add(task1);
		PrintLog.showTask(tasks);
	}
}