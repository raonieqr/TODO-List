package todo_list.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {

	@Override
	public String getBotUsername() {
		return BotCredentials.BOT_USER_NAME;
	}

	@Override
	public String getBotToken() {
		return BotCredentials.BOT_TOKEN;
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

		}
		else
			reply = "Não entendi!\nDigite /help para ver os comandos disponíveis.";

		return SendMessage.builder().text(reply).chatId(chatId).build();
	}

}
