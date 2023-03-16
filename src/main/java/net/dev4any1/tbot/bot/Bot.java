package net.dev4any1.tbot.bot;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import net.dev4any1.tbot.dao.PollRepository;
import net.dev4any1.tbot.dao.UpdateRepository;
import net.dev4any1.tbot.model.PollUpdateEntity;
import net.dev4any1.tbot.model.UpdateEntity;

public class Bot extends TelegramLongPollingBot {

	private static final Logger log = LoggerFactory.getLogger(Bot.class);

	private static final String BOT_WELCOME = "Привет! Я бот для опросов. Чтобы начать опрос, введите команду /poll.";
	private static final String POLL_QUESTION = "Какие есть варианты?";
	private static final String POLL_QUESTION_ANONYM = "Какие есть варианты анонимно?";
	private static final List<String> POLL_OPTIONS = List.of("Я", "В костюме", "Без костюма");

	private String botName;
	private UpdateRepository updateRepository;
	private PollRepository pollRepository;

	public Bot(DefaultBotOptions options, String botToken, String name, UpdateRepository updateRepository,
			PollRepository pollRepository) {
		super(options, botToken);
		this.botName = name;
		this.updateRepository = updateRepository;
		this.pollRepository = pollRepository;
	}

	@Override
	public void onUpdateReceived(Update update) {
		log.debug("update " + update.getClass() + " toStr: " + update.toString());
		// saving any update
		updateRepository.insert(new UpdateEntity(Helper.getJson(update).get()));
		// saving non anonymous poll reply
		if (update.hasPollAnswer()) {
			pollRepository.save(new PollUpdateEntity(update.getPollAnswer().getPollId(),
					Helper.getJson(update.getPollAnswer()).get()));
		}
		// saving anonymous poll reply
		if (update.hasPoll()) {
			pollRepository.save(new PollUpdateEntity(update.getPoll().getId(), Helper.getJson(update.getPoll()).get()));
		}
		// handling command
		if (update.hasMessage() && update.getMessage().hasText()) {
			String messageText = update.getMessage().getText();
			String chatId = update.getMessage().getChatId().toString();
			System.out.println("ChatID:  " + chatId);

			switch (messageText) {
			case "/start":
				sendTextMessage(chatId, BOT_WELCOME);
				break;
			case "/poll":
				sendPoll(chatId, POLL_QUESTION, POLL_OPTIONS, false);
				break;
			case "/pollAnonym":
				sendPoll(chatId, POLL_QUESTION_ANONYM, POLL_OPTIONS, true);
				break;
			case "/stats":
				String statistic = pollRepository.findAll().stream().map(PollUpdateEntity::getPoll)
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

	@Override
	public String getBotUsername() {
		return botName;
	}
}
