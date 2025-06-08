package com.dpbo.api.repository;

import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.dpbo.api.model.User;

public interface UserRepository extends MongoRepository<User, Long>{
	@Query("{ 'id' : '?0' }")
	User findById(String id);
	
	@Query("{ 'phoneNumber' : '?0' }")
	User findByPhoneNumber(String phoneNumber);
	
	@Query("{ 'fullName' : '?0' }")
	User findByFullName(String fullName);
	
	@ExistsQuery("{ 'phoneNumber': '?0' }")
	boolean existsByPhoneNumber(String phoneNumber);
	@ExistsQuery("{ 'fullName': '?0' }")
	boolean existsByFullName(String fullName);
	@ExistsQuery("{ 'id': '?0' }")
	boolean existsById(String id);
}
