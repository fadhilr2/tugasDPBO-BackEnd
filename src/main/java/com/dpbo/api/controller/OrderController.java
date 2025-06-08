package com.dpbo.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dpbo.api.model.Order;
import com.dpbo.api.payload.OrderDto;
import com.dpbo.api.repository.OrderRepository;
import com.dpbo.api.repository.UserRepository;


@RestController
@RequestMapping("/api/order")
public class OrderController {
	@Autowired
	OrderRepository orderRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@GetMapping("/getOrder")
	public ResponseEntity<?> getOrder(@RequestParam String userId){
		ArrayList<Order> orders = orderRepo.findAllByUserID(userId);
		if(orders.isEmpty()) {
			return new ResponseEntity<>("Order not found", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(orders, HttpStatus.OK);
		
	}
	/* application/json
	 * 
	 * -----orderDto-----
	 * userId: String
	 * bus: Object
	 * orderDate: LocalDate
	 */
	@PostMapping("/createOrder")
	public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto){
		HashMap<String, Object> responseBody = new HashMap<String, Object>();
		
		if(!userRepo.existsById(orderDto.getUserId())) {
			return new ResponseEntity<>("USER NOT FOUND", HttpStatus.BAD_REQUEST);
		}
		
		Order order = new Order();
		String uuid = UUID.randomUUID().toString();
		order.setId(uuid);
		order.setUserId(orderDto.getUserId());
		order.setBus(orderDto.getBus());
		order.setOrderDate(orderDto.getOrderDate());
		
		orderRepo.save(order);
		
		responseBody.put("message", "ORDER CREATED");
		responseBody.put("order", order);
		
		return new ResponseEntity<>(responseBody, HttpStatus.OK);
	}
	
	@PostMapping("/deleteOrder")
	public ResponseEntity<?> deleteOrder(@RequestParam String orderId){
		if(!orderRepo.existById(orderId)) {
			// 400
			return new ResponseEntity<>("ORDER NOT FOUND", HttpStatus.BAD_REQUEST);
		}
		
		orderRepo.deleteById(orderId);
		
		return new ResponseEntity<>("ORDER DELETED", HttpStatus.OK);
	}
}
