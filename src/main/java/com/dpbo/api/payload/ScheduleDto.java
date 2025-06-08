package com.dpbo.api.payload;

import java.time.LocalDate;
import java.util.ArrayList;

public class ScheduleDto {
	private String busId;
	private ArrayList<LocalDate> schedules;
	
	public ScheduleDto(String busId, ArrayList<LocalDate> schedules) {
		this.busId = busId;
		this.schedules = schedules;
	}
	public String getBusId() {
		return busId;
	}
	public ArrayList<LocalDate> getSchedules() {
		return schedules;
	}
	
	
}
