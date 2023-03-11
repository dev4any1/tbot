package net.dev4any1.tbot.bot;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

	private static final Logger log = LoggerFactory.getLogger(Bot.class);

	private String botName;

	public Bot(DefaultBotOptions options, String botToken, String name) {
		super(options, botToken);
		this.botName = name;
	}

	@Override
	public void onUpdateReceived(Update update) {
		log.debug("update " + update.getClass() + " toStr: " + update.toString());
		if (update.hasMessage() && update.getMessage().hasText()) {
			sendTextMessage(update.getMessage().getChatId().toString(), "echo " + update.getMessage().getText());
		}
	}

	public Optional<Message> sendTextMessage(String chatId, String text) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(chatId);
		sendMessage.setText(text);
		try {
			return Optional.of(execute(sendMessage));
		} catch (TelegramApiException e) {
			log.error("wtf: ", e);
			return Optional.empty();
		}
	}

	@Override
	public String getBotUsername() {
		return botName;
	}
}
