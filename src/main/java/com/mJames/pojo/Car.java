package com.mJames.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;

import com.mJames.util.DataUpdate;
import com.mJames.util.Logging;

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

	// lotID, ownerID, status change
	
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
		
		DataUpdate.saveCar(this);
	}	

	// Used for DB reconstruction
	public Car(Integer lotID, int licenseNumber, Integer ownerID,   
			String make, String model, String color,double price,
			String status)	{ 
		  super(); 
		  this.lotID = lotID;
		  this.licenseNumber = licenseNumber; 
		  this.price = price; 
		  this.color = color;
		  this.ownerID = ownerID; 
		  this.make = make; 
		  this.model = model; 
		  this.status = status;
	  }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + licenseNumber;
		result = prime * result + ((lotID == null) ? 0 : lotID.hashCode());
		result = prime * result + ((make == null) ? 0 : make.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((ownerID == null) ? 0 : ownerID.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (licenseNumber != other.licenseNumber)
			return false;
		if (lotID == null) {
			if (other.lotID != null)
				return false;
		} else if (!lotID.equals(other.lotID))
			return false;
		if (make == null) {
			if (other.make != null)
				return false;
		} else if (!make.equals(other.make))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (ownerID == null) {
			if (other.ownerID != null)
				return false;
		} else if (!ownerID.equals(other.ownerID))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
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
	public static String getLicenseString(Integer licNum)
	{
		String licString = String.format("%06X", licNum);
		return licString.substring(0,3) + " " + licString.substring(3);
	}

	public Integer getLotID() {
		return lotID;
	}

	public void setLotID(Integer lotID) {
		this.lotID = lotID;
		DataUpdate.saveCarLotID(this);
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
		DataUpdate.saveCarOwnerID(this);
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
	public boolean statusActive() {
		return (status.equals("Active"));
	}
	public boolean statusSold() {
		return (status.equals("Sold"));
	}
	public boolean statusPending() {
		return (status.equals("Pending"));
	}
	public boolean statusRemoved() {
		return (status.equals("Removed"));
	}

	private void setStatus(String status) {
		this.status = status;
		DataUpdate.saveCarStatus(this);
	}
	public void setStatusActive() {
		setStatus("Active");
		Logging.infoLog("Status of car " + licenseNumber + " is  set to Active");
	}	
	public void setStatusSold() {
		setStatus("Sold");
		Logging.infoLog("Status of car " + licenseNumber + " is  set to Sold");
	}		
	public void setStatusPending() {
		setStatus("Pending");
		Logging.infoLog("Status of car " + licenseNumber + " is  set to Pending");
	}	
	public void setStatusRemoved() {
		setStatus("Removed");
		Logging.infoLog("Status of car " + licenseNumber + " is  set to Removed");
	}
}
