package net.dev4any1.tbot.dao;

import net.dev4any1.tbot.model.PollUpdate;
import org.springframework.data.mongodb.repository.MongoRepository;

import net.dev4any1.tbot.model.UpdateDoc;

public interface UpdateRepository extends MongoRepository<PollUpdate, String> {

}
