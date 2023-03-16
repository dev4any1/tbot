package net.dev4any1.tbot.service;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import net.dev4any1.tbot.bot.Bot;
import net.dev4any1.tbot.dao.PollRepository;
import net.dev4any1.tbot.dao.UpdateRepository;

@Service
public class BotService {

	private static final Logger log = LoggerFactory.getLogger(BotService.class);

	@Value("${this.bot.name}")
	private String name;

	@Value("${this.bot.token}")
	private String token;

	@Autowired
	private UpdateRepository updateRepository;

	@Autowired
	private PollRepository pollRepository;

	private Bot bot;

	public void serviceDefault() {
		if (bot == null) {
			bot = new Bot(new DefaultBotOptions(), token, name, updateRepository, pollRepository);
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
}
