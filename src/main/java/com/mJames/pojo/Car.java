package com.mJames.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;

public class Car implements Serializable{

	private static final long serialVersionUID = -1720761779763343820L;
	private Integer lotID; // Identifier Unique to carlot
	private int licenseNumber; // Universally unique Identifier
	private double price;
	private String color;
	private Integer ownerID;
	private String make;
	private String model;
	private String status;

	public Car()
	{
		super();
	}
	
	public Car(int lotID, double price, String color, String make, String model, Integer ownerID, String status, Set<Integer> licenses) {
		super();
		
		int lNumber = generateLicenseNumber();
		while(licenses.contains(lNumber))
			lNumber = generateLicenseNumber();
		
		this.licenseNumber = lNumber;
		this.lotID = lotID; 
		this.price = price;
		this.color = color;
		this.make = make;
		this.model = model;
		this.ownerID = ownerID;
		this.status = status;
	}
	
	public Car(int lotID, int licenseNumber, double price, String color, Integer ownerID, String make, String model) {
		super();
		this.lotID = lotID;
		this.licenseNumber = licenseNumber;
		this.price = price;
		this.color = color;
		this.ownerID = ownerID;
		this.make = make;
		this.model = model;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + licenseNumber;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car other = (Car) obj;
		if (licenseNumber != other.licenseNumber)
			return false;
		return true;
	}

	private int generateLicenseNumber(){
		Random rand = new Random();
		rand.setSeed(LocalDateTime.now().getNano());
		return rand.nextInt(0xffffff + 1);
	}	
	public String getLicenseString()
	{
		String licString = String.format("%06X", licenseNumber);
		return licString.substring(0,3) + " " + licString.substring(3);
	}

	public Integer getLotID() {
		return lotID;
	}

	public void setLotID(Integer lotID) {
		this.lotID = lotID;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(Integer ownerID) {
		this.ownerID = ownerID;
	}

	public int getLicenseNumber() {
		return licenseNumber;
	}

	public String getColor() {
		return color;
	}

	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
