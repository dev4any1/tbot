package net.dev4any1.tbot;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import net.dev4any1.tbot.dao.SubscriberRepository;
import net.dev4any1.tbot.service.BotService;

@Configuration
@EnableAutoConfiguration(exclude = { EmbeddedMongoAutoConfiguration.class })
@ComponentScan
public class App implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(App.class);

	@Autowired
	SubscriberRepository repo;
	@Autowired
	BotService bs;

	@Override
	public void run(String... args) throws Exception {
		log.info("starting tbot for " + repo.count() + " subscribers");
		bs.serviceDefault();
		try (Scanner scan = new Scanner(System.in)) {
			while (scan.hasNext()) {
				if (scan.next().equals("exit")) {
					break;
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(App.class, args);
	}
}
