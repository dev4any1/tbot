package net.dev4any1.tbot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import net.dev4any1.tbot.TestMongoConfig;
import net.dev4any1.tbot.model.Subscriber;

@SpringBootTest(classes = { SubscriberService.class })
@ExtendWith(SpringExtension.class)
@Import({ TestMongoConfig.class })

public class SubscriberServiceTest {

	@Autowired
	SubscriberService service;

	@Test
	public void tesCreateAndGet() throws Exception {
		final String gsmN = "0123456789";
		Subscriber sub1 = service.create(gsmN);
		Subscriber sub2 = service.getByGsmNo(gsmN);
		assertEquals(sub1, sub2);
	}
}
