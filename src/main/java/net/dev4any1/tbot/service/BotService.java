package net.dev4any1.tbot.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.polls.PollOption;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import net.dev4any1.tbot.bot.Bot;
import net.dev4any1.tbot.dao.UpdateRepository;

@Service
public class BotService {

	private static final Logger log = LoggerFactory.getLogger(BotService.class);

	@Value("${this.bot.name}")
	private String name;

	@Value("${this.bot.token}")
	private String token;

	@Value("${this.bot.list}")
	private String list;

	@Value("${this.bot.question}")
	private String question;

	@Value("${this.bot.answers}")
	private String answers;

	@Autowired
	private UpdateRepository upr;

	private Bot bot;

	public void serviceDefault() {
		if (bot == null) {
			bot = new Bot(new DefaultBotOptions(), token, name, upr);
		}
		try {
			TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
			telegramBotsApi.registerBot(bot);
		} catch (TelegramApiException e) {
			log.error("reconnecting in 5 sec due to: " + e.getMessage());
			try {
				Thread.sleep(Duration.ofSeconds(5).toMillis());
				serviceDefault();
			} catch (InterruptedException intEx) {
				log.error("wtf: ", intEx);
				return;
			}
		}
		log.info("bot is good");
	}
	
	public void startPollToList() {
		List<String> gsmNos = Arrays.asList(list.split(" "));
		List<PollOption> options = new ArrayList<>();
		for (String option: Arrays.asList(answers.split(" "))) {
			options.add(new PollOption(option, 0));
		}
		bot.sendPoll(question, options, gsmNos);
	}

}
