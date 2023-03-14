package net.dev4any1.tbot.bot;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.polls.PollOption;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import net.dev4any1.tbot.dao.UpdateRepository;
import net.dev4any1.tbot.model.UpdateDoc;

public class Bot extends TelegramLongPollingBot {

	private static final Logger log = LoggerFactory.getLogger(Bot.class);

	private String botName;
	private UpdateRepository upr;

	public Bot(DefaultBotOptions options, String botToken, String name, UpdateRepository upr) {
		super(options, botToken);
		this.botName = name;
		this.upr = upr;
	}

	@Override
	public void onUpdateReceived(Update update) {
		upr.save(new UpdateDoc(update.toString()));
		if (update.hasMessage() && update.getMessage().hasText()) {
			sendTextMessage(update.getMessage().getChatId().toString(), getGooAnswer(update.getMessage().getText()));
		}
	}

	public String getGooAnswer(String query) {
		Collection<SearchResult> searches = Helper.Searcher.search(query, 1);
		if (!searches.isEmpty()) {
			SearchResult sr = searches.iterator().next();
			return "[" + sr.getTitle() + "](" + sr.getLink() + ")";
		} else {
			return "uh oh..";
		}
	}

	public Optional<Message> sendTextMessage(String chatId, String text) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(chatId);
		sendMessage.setText(text);
		sendMessage.setParseMode("Markdown");
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

	public void sendPoll(String question, List<PollOption> options, List<String> gsmNos) {
		// TODO Auto-generated method stub

	}
}