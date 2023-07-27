package com.sourya.hackingspringbootreactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ItemByExampleRepository extends ReactiveMongoRepository<Item, String> {
	

}
