package com.mJames.project1.java.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class User extends Logging implements Serializable{

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + userNum;
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
		User other = (User) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (userNum != other.userNum)
			return false;
		return true;
	}
	
	public int getIdNumber() {
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
		
		String name = carLot.getResponse("Please enter the users name", "[A-Za-z' ]{0,50}");
		String password = carLot.getResponse("Please enter a password for the new user (alphanumeric, no spaces)", "[A-Za-z0-9']{0,50}");
		
		carLot.addUser(uNum + carLot.getEMPLOYEENUMMAX(), new Customer(uNum, name, password, carLot));
		
		System.out.println("User " + name + " added. User number is: " + uNum);
	}
	
	public boolean checkPassword(String pWord) 
	{
		return password.equals(pWord);	
	}
	
	public HashMap<Integer, String> getCommandString() 
	{
		return new HashMap<Integer, String>();
	}
	
	public String commandNumString()
	{
		String ret = "[";
		Set<Integer> cNums = getCommandString().keySet();
		
		for (Integer s : cNums)
		{
			//if (s > 0)
				ret = ret + s + "";
		}
		
		return ret + "\\-]{0,2}";
	}
	
	public void printListOfCars(Map<Integer, Car> carMap)
	{
		System.out.println("The following cars are on the lot.");
		
		getListOfCars(carMap);
	}
	public void getListOfCars(Map<Integer, Car> carMap)
	{
		List<Car> cars = new ArrayList<Car>(carMap.values()); 
		
		if (cars.size() > 0)
		{	
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
