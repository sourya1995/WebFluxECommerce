package com.sourya.hackingspringbootreactive;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

@Component
public class TemplateDatabaseLoader {
	
	CommandLineRunner initialize(MongoOperations mongo) {
		return args -> {
			mongo.save(new Item("Cricket Bat", 2000));
			mongo.save(new Item("Shin Guard", 1000));
		};
	}
}
