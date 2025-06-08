package com.dpbo.api.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("orders")
public class Order {
	@Id
	private String id;
	private String userId;
	private Object bus;
	private LocalDate orderDate;
	
	public Order() {
		
	}
	public Order(String id, String userId, Object bus, LocalDate orderDate) {
		this.id = id;
		this.setUserId(userId);
		this.bus = bus;
		this.orderDate = orderDate;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Object getBus() {
		return bus;
	}

	public void setBus(Object bus) {
		this.bus = bus;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
