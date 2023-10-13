package todo_list.bot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import todo_list.entities.Task;

import java.util.List;

public class BotUpdater {
	public static TelegramBot updateBot(List<Task> tasks, TelegramBotsApi telegramBotsApi) throws TelegramApiException {
		BotManager botManager = new BotManager(BotCredentials.BOT_USER_NAME, BotCredentials.BOT_TOKEN, tasks);

		TelegramBot telegramBot = (TelegramBot) botManager
				.createBot(BotCredentials.BOT_USER_NAME, BotCredentials.BOT_TOKEN, tasks);

		telegramBotsApi.registerBot(telegramBot);

		return telegramBot;
	}

}
