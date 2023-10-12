package todo_list.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import todo_list.entities.Task;
import todo_list.utils.JsonFormatter;

import java.util.List;

public class TelegramBot extends TelegramLongPollingBot {

	private List<Task> tasks;
	String userName = "";
	String token = "";

	public TelegramBot(String userName, String token, List<Task> tasks) {
		this.tasks = tasks;
		this.userName = userName;
		this.token = token;
	}

	@Override
	public String getBotUsername() {
		return this.userName;
	}

	@Override
	public String getBotToken() {
		return this.token;
	}

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			SendMessage message = answer(update);
			try {
				execute(message);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
	}

	private SendMessage answer(Update update) {
		String chatMessage = update.getMessage().getText().toLowerCase();
		String chatId = update.getMessage().getChatId().toString();

		String reply = "";

		if (chatMessage.startsWith("/help"))
			reply = "Comandos disponíveis:\nrelatorio\nhelp";
		else if(chatMessage.startsWith("relatorio") || chatMessage.startsWith("relatório")) {
			JsonFormatter jsonFormatter = new JsonFormatter();
			reply = jsonFormatter.convertToJson(tasks);
		}
		else
			reply = "Não entendi!\nDigite /help para ver os comandos disponíveis.";

		return SendMessage.builder().text(reply).chatId(chatId).build();
	}

}
