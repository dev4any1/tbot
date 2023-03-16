package net.dev4any1.tbot.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import net.dev4any1.tbot.TestMongoConfig;
import net.dev4any1.tbot.dao.PollRepository;
import net.dev4any1.tbot.model.PollUpdateEntity;

@SpringBootTest(classes = { PollRepository.class })
@ExtendWith(SpringExtension.class)
@Import({ TestMongoConfig.class })

public class PollRepositoryTest {

	@Autowired
	PollRepository repo;

	@Test
	public void tesCreateAndGet() throws Exception {
		PollUpdateEntity puePersisted = repo.save(new PollUpdateEntity("test-poll-id", "test-poll"));
		List<PollUpdateEntity> pueReturned = repo.findByPollId("test-poll-id");
		assertTrue(!pueReturned.isEmpty());
		assertEquals(pueReturned.size(), 1);
		assertEquals(puePersisted, pueReturned.get(0));
		repo.deleteAll();
	}
}
