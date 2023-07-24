package com.sourya.hackingspringbootreactive;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.result.view.Rendering;

import reactor.core.publisher.Mono;

@Controller
public class HomeController {
	private ItemRepository itemRepository;
	private CartRepository cartRepository;

	public HomeController(ItemRepository itemRepository, CartRepository cartRepository) {
		super();
		this.itemRepository = itemRepository;
		this.cartRepository = cartRepository;
	}

	@GetMapping
	Mono<Rendering> home() {
		return Mono.just(Rendering.view("home.html").modelAttribute("items", this.itemRepository.findAll())
				.modelAttribute("cart", this.cartRepository.findById("My Cart").defaultIfEmpty(new Cart("My Cart")))
				.build());
	}

	@PostMapping("/add/{id}")
	Mono<String> addToCart(@PathVariable String id) { // <2>
		return this.cartRepository.findById("My Cart") //
				.defaultIfEmpty(new Cart("My Cart")) // <3>
				.flatMap(cart -> cart.getCartItems().stream() // <4>
						.filter(cartItem -> cartItem.getItem() //
								.getId().equals(id)) //
						.findAny() //
						.map(cartItem -> {
							cartItem.increment();
							return Mono.just(cart);
						}) //
						.orElseGet(() -> { // <5>
							return this.itemRepository.findById(id) //
									.map(item -> new CartItem(item)) //
									.map(cartItem -> {
										cart.getCartItems().add(cartItem);
										return cart;
									});
						}))
				.flatMap(cart -> this.cartRepository.save(cart)) // <6>
				.thenReturn("redirect:/"); // <7>
	}

	/*
	 * Let's try iterative programming
	 * 
	 * boolean found = false; for(CartItem cartItem : cart.getCartItems()){
	 * if(cartItem.getItem().getId().equals("5"){ found = true; } }
	 * 
	 * if(found){ qty++; }
	 * 
	 * else { new CartItem(item) }
	 * 
	 * 
	 * The functional style is
	 * 
	 * if(cart.getCartItems().stream() .anyMatch(cartItem ->
	 * cartItem.getItem().getId().equals("5"))){
	 * 
	 * } else {
	 * 
	 * }
	 */
}
