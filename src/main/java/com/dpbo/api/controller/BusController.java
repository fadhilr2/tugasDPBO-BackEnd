package com.dpbo.api.controller;

import java.util.HashMap;
import java.util.List;
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

import com.dpbo.api.model.Bus;
import com.dpbo.api.payload.BusDto;
import com.dpbo.api.repository.BusRepository;

@RestController
@RequestMapping("/api/bus")
public class BusController {
	@Autowired
	private BusRepository busRepo;
	
	/* application/json
	 * 
	 * --------busDto------
	 * busName: String
	 * price: int
	 * numOfSeats: int
	 * yearMade: LocalDate (YY-MM-DD)
	 * busType: String
	 * img: ArrayList<String>
	 */
	@PostMapping("/registerBus")
	public ResponseEntity<?> registerBus(@RequestBody BusDto busDto){
		HashMap<String, Object> responseBody = new HashMap<String, Object>();
		// BUSNAME ALREADY EXIST HANDLER
		if(busRepo.existsByBusName(busDto.getBusName())) {
			// 400
			return new ResponseEntity<>("Bus name already taken", HttpStatus.BAD_REQUEST);
		}
		
		// INIT BUS
		Bus bus = new Bus();
		String uuid = UUID.randomUUID().toString();
		bus.setId(uuid);
		bus.setBusName(busDto.getBusName());
		bus.setPrice(busDto.getPrice());
		bus.setBusType(busDto.getBusType());
		bus.setNumOfSeats(busDto.getNumOfSeats());
		bus.setYearMade(busDto.getYearMade());
		bus.setImg(busDto.getImg());
				
		// SAVE BUS TO DATABASE
		busRepo.save(bus);
		
		// RETURN RESPONSE
		responseBody.put("message", "Register bus success");
		responseBody.put("bus", bus);
		
		return new ResponseEntity<>(responseBody, HttpStatus.OK);
	}
	
	@PostMapping("/findAll")
	public ResponseEntity<List<Bus>> findAll(){
		List<Bus> buses = busRepo.findAll();
		
		return new ResponseEntity<>(buses, HttpStatus.OK);
	}
	
	@GetMapping("/findBusByName")
	public ResponseEntity<?> findByBusName(@RequestParam String busName){
		//BUSNAME NOT FOUND HANDLER
		if(!busRepo.existsByBusName(busName)) {
			return new ResponseEntity<>("Bus name doesn't exist", HttpStatus.BAD_REQUEST);
		}
		
		//GET BUS FROM DATABASE
		Bus bus = busRepo.findByBusName(busName);
		
		return new ResponseEntity<>(bus, HttpStatus.OK);
	}
	
	@PostMapping("/deleteBusByName")
	public ResponseEntity<?> deleteBusByName(@RequestParam String busName){
		//BUSNAME NOT FOUND HANDLER
		if(!busRepo.existsByBusName(busName)) {
			return new ResponseEntity<>("Bus name doesn't exist", HttpStatus.BAD_REQUEST);
		}

		// DELETE BUS FROM DATABASE
		busRepo.deleteByBusName(busName);
		
		return new ResponseEntity<>("Bus deleted", HttpStatus.OK);
	}
	
}
