package com.dpbo.api.controller;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dpbo.api.model.Schedule;
import com.dpbo.api.payload.ScheduleDto;
import com.dpbo.api.repository.BusRepository;
import com.dpbo.api.repository.ScheduleRepository;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
	
	@Autowired
	private ScheduleRepository scheduleRepo;
	
	@Autowired
	private BusRepository busRepo;
	

	@GetMapping("/getSchedule")
	public ResponseEntity<?> getSchedule(@RequestParam String busId){
		HashMap<String, Object> responseBody = new HashMap<String, Object>();
		
		if(!scheduleRepo.schedulesExistById(busId)) {
			// 400
			return new ResponseEntity<>("Schedules doesn't exist", HttpStatus.BAD_REQUEST);
		}
		
		Schedule schedules = scheduleRepo.findSchedulesByBusId(busId);
		responseBody.put("message", "Schedule found");
		responseBody.put("schedule", schedules);
		
		return new ResponseEntity<>(responseBody, HttpStatus.OK);
	}
	
	/* 	application/json
	 * ----scheduleDto-----
	 * 	busId: String ;
	 * 	schedules: ArrayList<LocalDate>;
	 */
	@PostMapping("/addSchedule")
	public ResponseEntity<?> addSchedule(@RequestBody ScheduleDto scheduleDto){
		HashMap<String, Object> responseBody = new HashMap<String, Object>();
		
		if(!busRepo.existsById(scheduleDto.getBusId())) {
			// 400
			return new ResponseEntity<>("Bus doesn't exist", HttpStatus.BAD_REQUEST);
		}
		
		ArrayList<LocalDate> currSchedules = null;
		
		try {
			currSchedules = scheduleRepo.findSchedulesByBusId(scheduleDto.getBusId()).getSchedules();

		} catch(Exception e) {
			ArrayList<LocalDate> schedules = new ArrayList<LocalDate>();
			Schedule schedule = new Schedule(scheduleDto.getBusId(), schedules);
			currSchedules = schedule.getSchedules();
			scheduleRepo.save(schedule);
		} finally {
			for(LocalDate date : scheduleDto.getSchedules()) {
				if(!currSchedules.contains(date)) {
					currSchedules.add(date);
				}
			}
			
			Schedule schedule = new Schedule(scheduleDto.getBusId(), currSchedules);
			responseBody.put("message", "schedule added");
			responseBody.put("schedule", schedule);
			
			scheduleRepo.save(schedule);
		}
		
		// 200
		return new ResponseEntity<>(responseBody, HttpStatus.OK);
	}
	
	/* 	application/json
	 *  ----scheduleDto-----
	 * 	busId: String ;
	 * 	schedules: ArrayList<LocalDate>;
	 */
	@PostMapping("/deleteSchedule")
	public ResponseEntity<?> deleteSchedule(@RequestBody ScheduleDto scheduleDto){
		HashMap<String, Object> responseBody = new HashMap<String, Object>();
		
		if(!scheduleRepo.schedulesExistById(scheduleDto.getBusId())) {
			// 400
			return new ResponseEntity<>("Schedules doesn't exist", HttpStatus.BAD_REQUEST);
		}
		
		Schedule schedule = scheduleRepo.findSchedulesByBusId(scheduleDto.getBusId());
				
		ArrayList<LocalDate> currSchedules = schedule.getSchedules();
		
		for(LocalDate date : scheduleDto.getSchedules()) {
			if(currSchedules.contains(date)) {
				currSchedules.remove(date);
			}
		}
		
		schedule.setSchedules(currSchedules);

		scheduleRepo.save(schedule);
		
		responseBody.put("message", "schedule updated");
		responseBody.put("schedule", schedule);
		
		// 200
		return new ResponseEntity<>(responseBody, HttpStatus.OK);
	}
	
}
