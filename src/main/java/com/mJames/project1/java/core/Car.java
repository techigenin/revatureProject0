package com.mJames.project1.java.core;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;

public class Car implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1720761779763343820L;
	private int idNumber; // Identifier Unique to carlot
	private int licenseNumber; // Universally unique Identifier
	private double price;
	private String color;
	
	public Car(int idNumber, double price, String color, Set<Integer> licenses) {
		super();
		
		int lNumber = generateLicenseNumber();
		while(licenses.contains(lNumber))
			lNumber = generateLicenseNumber();
		
		this.licenseNumber = lNumber;
		this.idNumber = idNumber; 
		this.price = price;
		this.color = color;
	}
	public Car(Car c)

	
	{
		this.idNumber = c.idNumber;
		this.licenseNumber = c.licenseNumber;
		this.color = c.color;
		this.price = c.price;
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
	public int getIdNumber() {
		return idNumber;
	}
	public int getLicenseNumber() {
		return licenseNumber;
	}
	public double getPrice() {
		return price;
	}
	public String getColor() {
		return color;
	}
}
