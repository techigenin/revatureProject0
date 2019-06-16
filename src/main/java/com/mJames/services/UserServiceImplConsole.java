package com.mJames.services;

import java.util.ArrayList;

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
	public void printListOfCarsWithHeading(CarLot c)
	{
		IOUtil.messageToUser("The following cars are on the lot.");
		ArrayList<Car> cars = getListOfCars(c);
		if (cars.size() > 0)
		{	
			IOUtil.messageToUser("%-6s%-11s%-11s%-11s%-8s%-16s\n", 
					"ID", 
					"Color", 
					"Make", 
					"Model",
					"Price($)", 
					" License Number");
			for (Car car : cars)
			{
				IOUtil.messageToUser("%6d%11s%11s%11s%8.2f%16s\n", 
						car.getLotID(), 
						car.getColor(), 
						car.getMake(),
						car.getModel(),
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
