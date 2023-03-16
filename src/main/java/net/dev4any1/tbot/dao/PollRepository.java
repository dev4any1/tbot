package net.dev4any1.tbot.dao;

import net.dev4any1.tbot.model.PollUpdate;
import net.dev4any1.tbot.model.Subscriber;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PollRepository extends MongoRepository<PollUpdate, String> {

    public PollUpdate findByPollId(String pollId);
    public List<PollUpdate> getAllByPollId(String pollId);
}
