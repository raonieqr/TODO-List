package todo_list;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import todo_list.bot.BotCredentials;
import todo_list.bot.BotManager;
import todo_list.bot.TelegramBot;
import todo_list.entities.TaskBuilder;
import todo_list.utils.FileManager;
import todo_list.utils.InputValidator;
import todo_list.view.PriorityView;
import todo_list.view.StatusView;
import todo_list.view.TaskView;
import todo_list.entities.Task;
import todo_list.entities.TaskAlarm;
import todo_list.enums.Priority;
import todo_list.enums.Status;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main
{
		public static void main(String[] args) throws InterruptedException {

			System.out.println("Bem vindo a TODO-List");

			ArrayList<Task> tasks = new ArrayList<>();
			ArrayList<Task> taskWithAlarm = new ArrayList<>();
			FileManager fileManager = new FileManager();
			Scanner sc = new Scanner(System.in);

			try {
				TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
				BotManager botManager = new BotManager(BotCredentials.BOT_USER_NAME, BotCredentials.BOT_TOKEN, tasks);
				TelegramBot telegramBot = (TelegramBot) botManager
						.createBot(BotCredentials.BOT_USER_NAME, BotCredentials.BOT_TOKEN, tasks);

				telegramBotsApi.registerBot(telegramBot);

				int option = 0;
				while (option != 6) {

					TaskView.showMenu();

					option = InputValidator.promptForIntegerInput("Digite o número: ");
					switch (option) {
						case 1:
							Task task = TaskView.createTaskFromUserInput();

							TaskView.createTaskAlarm(task, tasks, taskWithAlarm);

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
					}
				}

				sc.close();

				fileManager.createFile(tasks);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
}