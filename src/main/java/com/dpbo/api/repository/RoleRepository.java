package com.dpbo.api.repository;

import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.dpbo.api.model.Role;

public interface RoleRepository extends MongoRepository<Role, Long>{
	
	@Query("{'name': '?0'}")
	Role findByName(String name);
	
	@ExistsQuery("{'name': '?0'}")
	boolean existsByName(String name);
}
