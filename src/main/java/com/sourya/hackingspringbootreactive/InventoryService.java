package com.sourya.hackingspringbootreactive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.mongodb.core.ReactiveFluentMongoOperations;

import reactor.core.publisher.Flux;

public class InventoryService {
	
	private ItemRepository repository;
	@Autowired
	private ItemByExampleRepository exampleRepository;
	private ReactiveFluentMongoOperations fluentOperations;
	
	
	public InventoryService(ItemRepository repository, ItemByExampleRepository exampleRepository,
			ReactiveFluentMongoOperations fluentOperations) {
		super();
		this.repository = repository;
		this.exampleRepository = exampleRepository;
		this.fluentOperations = fluentOperations;
	}
	
	Flux<Item> getItems(){
		return Flux.empty();	
	}

	Flux<Item> search(String partialName, String partialDescription, boolean useAnd){
		if(partialName != null) {
			if(partialDescription != null) {
				if(useAnd) {
					return repository.findByNameContainingAndDescriptionContainingAllIgnoreCase(partialName, partialDescription);
				} else {
					return repository.findByNameContainingOrDescriptionContainingAllIgnoreCase(partialName, partialDescription);
				}
			} else {
				return repository.findByNameContaining(partialName);
			}
		} else {
			if(partialDescription != null) {
				return repository.findByDescriptionContainingIgnoreCase(partialDescription);
			} else {
				return repository.findAll();
			}
		}
	}
	
	Flux<Item> searchByExample(String name, String description, boolean useAnd){
		Item item = new Item(name, description, 0.0);
		
		ExampleMatcher matcher = (useAnd ? 
				ExampleMatcher.matchingAll()
				: ExampleMatcher.matchingAny())
				.withStringMatcher(StringMatcher.CONTAINING)
				.withIgnoreCase()
				.withIgnorePaths("price");
		
		Example<Item> probe = Example.of(item, matcher);
		return exampleRepository.findAll(probe);
	}
	
	Flux<Item> searchByFluentExample(String name, String description){
		return fluentOperations.query(Item.class)
				.matching(query(where("TV tray").is(name).and("Smurf").is(description)))
				.all();
	}
	
	
}
