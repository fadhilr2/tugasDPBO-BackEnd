package com.dpbo.api.payload;

import java.time.LocalDate;
import java.util.ArrayList;



public class BusDto {
	private String busName;
	private int price;
	private int numOfSeats;
	private LocalDate yearMade;
	private String busType;
	private ArrayList<String> img;
	
	public BusDto(String busName, int price, int numOfSeats, LocalDate yearMade, String busType, ArrayList<String> img) {
		this.busName = busName;
		this.price = price;
		this.numOfSeats = numOfSeats;
		this.yearMade = yearMade;
		this.busType = busType;
		this.img = img;
	}

	public String getBusName() {
		return busName;
	}

	public int getNumOfSeats() {
		return numOfSeats;
	}

	public LocalDate getYearMade() {
		return yearMade;
	}

	public String getBusType() {
		return busType;
	}

	public ArrayList<String> getImg() {
		return img;
	}

	public int getPrice() {
		return price;
	}

	
	
	
}
