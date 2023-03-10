package net.dev4any1.tbot.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.dev4any1.tbot.model.Subscriber;

public interface SubscriberRepository extends MongoRepository<Subscriber, String> {
	public Subscriber findByGsmNo(String gsmNo);
}
