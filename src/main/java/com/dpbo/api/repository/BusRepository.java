package com.dpbo.api.repository;

import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.dpbo.api.model.Bus;

public interface BusRepository extends MongoRepository<Bus, Long> {
	
	@Query("{'busName': '?0'}")
	Bus findByBusName(String name);
	
	@Query(value=" {'busName': '?0' } ", delete=true)
	Bus deleteByBusName(String name);
	
	@ExistsQuery("{'busName': '?0'}")
	boolean existsByBusName(String name);
	
	@ExistsQuery("{'id': '?0'}")
	boolean existsById(String Id);
}
