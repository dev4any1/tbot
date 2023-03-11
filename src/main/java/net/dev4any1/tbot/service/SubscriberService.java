package net.dev4any1.tbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.dev4any1.tbot.dao.SubscriberRepository;
import net.dev4any1.tbot.model.Subscriber;

@Service
public class SubscriberService {

	@Autowired
	private SubscriberRepository repository;

	public Subscriber create(String gsmNo) {
		return repository.save(new Subscriber(gsmNo));
	}

	public Subscriber getByGsmNo(String gsmNo) {
		return repository.findByGsmNo(gsmNo);
	}
}
