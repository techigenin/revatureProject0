package com.mJames.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.mJames.pojo.Car;
import com.mJames.pojo.CarLot;
import com.mJames.pojo.User;
import com.mJames.ui.IOUtil;

public class UserServiceImplConsole implements UserService {
	@Override
	public boolean checkPassword(String pWord, User u) 
	{
			return u.checkPassword(pWord);	
	}
	@Override
	public HashMap<Integer, String> getCommands(User u) { return new HashMap<Integer, String>();} 
	@Override
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
	@Override
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
						car.getLotID(), 
						car.getColor(), 
						car.getPrice(), 
						car.getLicenseString());
			}
		}
		else
			System.out.println("There are no cars on the lot");
	}
	@Override
	public ArrayList<Car> getListOfCars(CarLot carLot)
	{
		return new ArrayList<Car>(carLot.getCars().values()); 
	}
}
