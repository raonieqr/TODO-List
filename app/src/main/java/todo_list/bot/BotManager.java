package todo_list.bot;

import todo_list.entities.Task;

import java.util.List;

public class BotManager implements IBot{
	String userName;
	String token;
	List<Task> tasks;

	public BotManager(String userName, String token, List<Task> tasks) {
		this.userName = userName;
		this.token = token;
		this.tasks = tasks;
	}

	@Override
	public IBot createBot(String token, String userName, List<Task> tasks) {
		return new BotManager(token, userName, tasks);
	}
}
