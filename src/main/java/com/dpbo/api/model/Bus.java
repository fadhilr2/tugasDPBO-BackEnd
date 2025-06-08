package com.dpbo.api.model;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("buses")
public class Bus {
	@Id
	private String id;
	private String busName;
	private int price;
	private int numOfSeats;
	private LocalDate yearMade;
	private String busType;
	private ArrayList<String> img;
	
	public Bus() {}
	
	
	public Bus(String busName, int price, int numOfSeats, LocalDate yearMade, String busType,  ArrayList<String> img) {
		this.busName = busName;
		this.price = price;
		this.numOfSeats = numOfSeats;
		this.yearMade = yearMade;
		this.busType = busType;
		this.img = img;
	}

	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getBusName() {
		return busName;
	}


	public void setBusName(String busName) {
		this.busName = busName;
	}


	public int getNumOfSeats() {
		return numOfSeats;
	}


	public void setNumOfSeats(int numOfSeats) {
		this.numOfSeats = numOfSeats;
	}


	public LocalDate getYearMade() {
		return yearMade;
	}


	public void setYearMade(LocalDate yearMade) {
		this.yearMade = yearMade;
	}


	public String getBusType() {
		return busType;
	}


	public void setBusType(String busType) {
		this.busType = busType;
	}


	public ArrayList<String> getImg() {
		return img;
	}


	public void setImg(ArrayList<String> img) {
		this.img = img;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	@Override
	public String toString() {
		return "Bus [id=" + id + ", busName=" + busName + ", price=" + price + ", numOfSeats=" + numOfSeats
				+ ", yearMade=" + yearMade + ", busType=" + busType + ", img=" + img + "]";
	}


	
	
	
	
	
	
}
