package todo_list.bot;

import todo_list.entities.Task;

import java.util.List;

public interface IBot {
	Object createBot(String token, String userName, List<Task> tasks);
}
