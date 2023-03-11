package net.dev4any1.tbot.service;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import net.dev4any1.tbot.bot.Bot;

@Service
public class BotService {

	private static final Logger log = LoggerFactory.getLogger(BotService.class);

	@Value("${this.bot.name}")
	private String name;

	@Value("${this.bot.token}")
	private String token;

	private Bot bot;

	public void serviceDefault() {
		if (bot == null) {
			bot = new Bot(new DefaultBotOptions(), token, name);
		}
		try {
			TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
			telegramBotsApi.registerBot(bot);
			log.info("bot is good");
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
	}

}
