package net.dev4any1.tbot.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.dev4any1.tbot.model.PollUpdateEntity;

public interface PollRepository extends MongoRepository<PollUpdateEntity, String> {
	public List<PollUpdateEntity> findByPollId(String pollId);
}
