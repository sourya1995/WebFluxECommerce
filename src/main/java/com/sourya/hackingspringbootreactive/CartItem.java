package com.sourya.hackingspringbootreactive;

import java.util.Objects;

public class CartItem {
    private Item item;
    private int quantity;
    
    private CartItem() {
	
	}
    
   
	CartItem(Item item, int quantity) {
		
		this.item = item;
		this.quantity = 1;
	}
	public CartItem(Item item) {
		this.item = item;
	}


	public Item getItem() {
		return item;
	}


	public void setItem(Item item) {
		this.item = item;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public void increment(){
        this.quantity++;
    }


	@Override
	public int hashCode() {
		return Objects.hash(item, quantity);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartItem other = (CartItem) obj;
		return Objects.equals(item, other.item) && quantity == other.quantity;
	}


	@Override
	public String toString() {
		return "CartItem [item=" + item + ", quantity=" + quantity + "]";
	}
	
	
	
	
}
