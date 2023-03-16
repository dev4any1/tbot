package net.dev4any1.tbot.bot;

import java.util.*;
import java.util.stream.Collectors;

import net.dev4any1.tbot.dao.PollRepository;
import net.dev4any1.tbot.model.PollUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.polls.PollOption;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import org.telegram.telegrambots.meta.api.objects.polls.PollAnswer;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import net.dev4any1.tbot.dao.UpdateRepository;
import net.dev4any1.tbot.model.UpdateDoc;

public class Bot extends TelegramLongPollingBot {

	private static final Logger log = LoggerFactory.getLogger(Bot.class);

	private String botName;
	private UpdateRepository upr;
	private PollRepository pollRepository;

	public Bot(DefaultBotOptions options, String botToken, String name, UpdateRepository upr, PollRepository pollRepository) {
		super(options, botToken);
		this.botName = name;
		this.upr = upr;
		this.pollRepository = pollRepository;
	}

	@Override
	public void onUpdateReceived(Update update) {
		log.debug("update " + update.getClass() + " toStr: " + update.toString());

		if (update.hasPollAnswer()) {
			pollRepository.save(new PollUpdate(update.getPollAnswer().getPollId(), update.getPollAnswer().toString()));
		}
		if (update.hasPoll()) {
			pollRepository.save(new PollUpdate(update.getPoll().getId(), update.getPoll().toString()));
		}
		if (update.hasMessage() && update.getMessage().hasText()) {
			String messageText = update.getMessage().getText();
			String chatId = update.getMessage().getChatId().toString();
			System.out.println("ChatID:  "+chatId);

			switch (messageText) {
				case "/start":
					sendTextMessage(chatId, "Привет! Я бот для опросов. Чтобы начать опрос, введите команду /poll.");
					break;
				case "/poll":
					sendPoll(chatId, "Какие есть варианты?",
							List.of("Я", "В костюме", "Без костюма"), false);
					break;
				case "/pollAnswer":
					sendPoll(chatId, "Какие есть варианты анонимно?",
							List.of("Я", "В костюме", "Без костюма"), true);
					break;
				case "/stat":
					String statistic = pollRepository.findAll()
						.stream()
						.map(PollUpdate::getPoll)
						.collect(Collectors.joining(", "));
					sendTextMessage(chatId, statistic);
					break;
			}
		}
	}

	private void sendTextMessage(String chatId, String text) {
		SendMessage message = new SendMessage();
		message.setChatId(chatId);
		message.setText(text);
		try {
			execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void sendPoll(String chatId, String question, List<String> options, boolean isAnonymous) {
		SendPoll poll = new SendPoll();
		poll.setChatId(chatId);
		poll.setQuestion(question);
		poll.enableNotification();
		poll.setOptions(options);
		poll.setIsAnonymous(isAnonymous);
		poll.setType("regular");
		poll.setAllowMultipleAnswers(true);
		poll.setCorrectOptionId(0); // в данном случае не учитываем правильный ответ
		try {
			execute(poll);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	public Map<String, Integer> getPollResults() {
		return null;
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

	@Override
	public String getBotUsername() {
		return botName;
	}
}
