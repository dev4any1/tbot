package net.dev4any1.tbot;

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

@Configuration
@EnableAutoConfiguration(exclude = {EmbeddedMongoAutoConfiguration.class})
@ComponentScan
public class App implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(App.class);

	@Autowired
	SubscriberRepository repo;

	@Override
	public void run(String... args) throws Exception {
		log.info("starting tbot for " + repo.count() + " subscribers");
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(App.class, args);
	}
}
