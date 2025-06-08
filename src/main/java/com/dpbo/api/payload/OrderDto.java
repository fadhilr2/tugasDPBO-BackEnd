package com.dpbo.api.payload;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

public class OrderDto {
	@Id
	private String userId;
	private Object bus;
	private LocalDate orderDate;
	
	public OrderDto(String userId, Object bus, LocalDate orderDate) {
		this.userId = userId;
		this.bus = bus;
		this.orderDate = orderDate;
	}
	public Object getBus() {
		return bus;
	}
	public LocalDate getOrderDate() {
		return orderDate;
	}
	public String getUserId() {
		return userId;
	}

	
	
	
}
