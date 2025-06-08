package com.dpbo.api.repository;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.dpbo.api.model.Order;

public interface OrderRepository extends MongoRepository<Order, Long>{
	@Query("{'id': '?0'}")
	Order findById(String id);
	
	@Query(value="{'id' : '?0'}", delete=true)
	Order deleteById(String id);
	
	@Query(value="{'userId': '?0'}")
	ArrayList<Order> findAllByUserID(String id);
	

	
	@ExistsQuery("{'id' : '?0'}")
	boolean existById(String id);
}
