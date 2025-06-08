package com.dpbo.api.repository;

import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.dpbo.api.model.Schedule;

public interface ScheduleRepository extends MongoRepository<Schedule, Long> {
	
	@Query(" { 'busId': '?0' }")
	Schedule findSchedulesByBusId(String busId);
	
//	@Query(value=" { 'busId': '?0' }", delete=true)
//	Schedule deleteScheduleByBusId(String busId);
	
	@ExistsQuery(" { 'busId': '?0' } ")
	boolean schedulesExistById(String busId);
}
