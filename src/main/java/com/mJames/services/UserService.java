package com.mJames.project1.java.core.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.mJames.project1.java.core.pojo.Car;
import com.mJames.project1.java.core.pojo.CarLot;
import com.mJames.project1.java.core.pojo.User;
import com.mJames.project1.java.core.ui.IOUtil;

public class UserService {
	public boolean checkPassword(String pWord, User u) 
	{
			return u.checkPassword(pWord);	
	}
	public HashMap<Integer, String> getCommands(User u) { return new HashMap<Integer, String>();} 
	public String commandNumString(User u)
	{
		String ret = "[";
		Set<Integer> cNums = getCommands(u).keySet();
		
		for (Integer s : cNums)
		{
			ret += s + "";
		}
		
		return ret + "\\-]{0,2}";
	}	
	public void printListOfCarsWithHeading(CarLot c)
	{
		IOUtil.messageToUser("The following cars are on the lot.");
		ArrayList<Car> cars = getListOfCars(c);
		if (cars.size() > 0)
		{	
			IOUtil.messageToUser("%-6s%-11s%-8s%-16s\n", 
					"ID", 
					"Color", 
					"Price($)", 
					" License Number");
			for (Car car : cars)
			{
				IOUtil.messageToUser("%6d%11s%9.2f%14s\n", 
						car.getIdNumber(), 
						car.getColor(), 
						car.getPrice(), 
						car.getLicenseString());
			}
		}
		else
			System.out.println("There are no cars on the lot");
	}
	public ArrayList<Car> getListOfCars(CarLot carLot)
	{
		return new ArrayList<Car>(carLot.getCars().values()); 
	}
}
