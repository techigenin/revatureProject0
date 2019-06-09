package com.mJames.project1.java.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class User implements Serializable{

	private static final long serialVersionUID = -6755446065224781856L;
	protected int userNum;
	protected String name;
	protected String password;
	protected CarLot carLot;
	
	public User()
	{
		super();
	}
	
	public User(CarLot cLot)
	{
		this.carLot = cLot;
	}
	
	public User(int userNum, String name, String password, CarLot  cLot) {
		super();
		this.userNum = userNum;
		this.name = name;
		this.password = password;
		this.carLot = cLot;
	}

	public int getUserNum() {
		return userNum;
	}
	public String getUserName()	{
		return name;
	}
	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public void addCustomer()
	{
		System.out.println("Creating a new customer account.");
		
		int uNum = carLot.getEMPLOYEENUMMAX() + 1;
		uNum = carLot.firstFree(uNum);
		
		String name = carLot.getResponse("Please enter the users name", "[A-Za-z ]{0,50}");
		String password = carLot.getResponse("Please enter a password for the new user (alphanumeric, no spaces)", "[A-Za-z0-9]{0,50}");
		
		carLot.addUser(uNum + carLot.getEMPLOYEENUMMAX(), new Customer(uNum, name, password, carLot));
		
		System.out.println("User " + name + " added. User number is: " + uNum);
	}
	
	public boolean checkPassword(String pWord) 
	{
		return password.equals(pWord);	
	}
	
	public HashMap<Integer, String> getCommands() 
	{
		return new HashMap<Integer, String>();
	}
	
	public String commandNumString()
	{
		String ret = "[";
		Set<Integer> cNums = getCommands().keySet();
		
		for (Integer s : cNums)
		{
			ret = ret + s + "";
		}
		
		return ret + "]";
	}

	public void printListOfCars(Map<Integer, Car> carMap)
	{
		List<Car> cars = new ArrayList<Car>(carMap.values()); 
		
		if (cars.size() > 0)
		{	
			System.out.println("The following cars are on the lot.");
			System.out.printf("%-6s%-11s%-8s%-16s\n", 
					"ID", 
					"Color", 
					"Price($)", 
					" License Number");
			for (Car c : cars)
			{
				System.out.printf("%6d%11s%9.2f%14s\n", 
						c.getIdNumber(), 
						c.getColor(), 
						c.getPrice(), 
						c.getLicenseString());
			}
		}
		else
			System.out.println("There are no cars on the lot");
	}
}
