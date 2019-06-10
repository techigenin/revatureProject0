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
	
	public Car(int idNumber, double price, String color, Set<Integer> licences) {
		super();
		
		int lNumber = generateLicenseNumber();
		while(licences.contains(this.licenseNumber))
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
