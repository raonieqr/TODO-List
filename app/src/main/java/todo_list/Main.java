package todo_list;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import todo_list.bot.BotUpdater;
import todo_list.bot.TelegramBot;
import todo_list.utils.FileManager;
import todo_list.utils.InputValidator;
import todo_list.view.TaskView;
import todo_list.entities.Task;

import java.util.*;

import static java.lang.System.exit;

public class Main
{
		public static void main(String[] args) throws InterruptedException {

			System.out.println("Bem vindo a TODO-List");

			ArrayList<Task> tasks = new ArrayList<>();
			ArrayList<Task> taskWithAlarm = new ArrayList<>();
			FileManager fileManager = new FileManager();
			Scanner sc = new Scanner(System.in);
			int id = 0;

			try {
				TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

				int option = 0;
				while (true) {
					TelegramBot telegramBot = BotUpdater.updateBot(tasks, telegramBotsApi);

					TaskView.showMenu();

					option = InputValidator.promptForIntegerInput("Digite o número: ");
					switch (option) {
						case 1:
							Task task = TaskView.createTaskFromUserInput(++id);

							TaskView.createTaskAlarm(task, tasks, taskWithAlarm);

							telegramBot.sendAutomaticMessage(tasks);

							break;
						case 2:
							TaskView.showTask(tasks);

							break;
						case 3:
							TaskView.createAlarm(taskWithAlarm);

							break;
						case 4:
							TaskView.editTaskStatusById(tasks);

							break;
						case 5:
							TaskView.deleteTaskById(tasks);

							break;
						case 6:
							sc.close();

							fileManager.createFile(tasks);

							exit(0);

							break;
					}
				}

			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
}