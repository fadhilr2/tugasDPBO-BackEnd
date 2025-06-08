package com.dpbo.api.model;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("schedules")
public class Schedule {
	@Id
	private String busId;
	private ArrayList<LocalDate> schedules;
	
	public Schedule() {}
	
	public Schedule(String busId, ArrayList<LocalDate> schedules) {
		this.busId = busId;
		this.schedules = schedules;
	}
	public String getBusId() {
		return busId;
	}
	public void setBusId(String busId) {
		this.busId = busId;
	}
	public ArrayList<LocalDate> getSchedules() {
		return schedules;
	}
	public void setSchedules(ArrayList<LocalDate> schedules) {
		this.schedules = schedules;
	}
	
	
}
