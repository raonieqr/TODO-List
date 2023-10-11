package todo_list.bot;

public class BotManager implements IBot{
	@Override
	public IBot createBot(String token, String userName) {
		return (IBot) new TelegramBot();
	}
}
