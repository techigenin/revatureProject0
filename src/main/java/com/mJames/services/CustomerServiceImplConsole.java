package com.mJames.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.mJames.pojo.Car;
import com.mJames.pojo.CarLot;
import com.mJames.pojo.Customer;
import com.mJames.pojo.Offer;
import com.mJames.pojo.User;
import com.mJames.ui.IOUtil;

	public class CustomerServiceImplConsole extends UserServiceImplConsole implements CustomerService{
		
	public HashMap<Integer, String> getCommands(User c) {
		HashMap<Integer, String> commands = new HashMap<Integer, String>();
		
		commands.put(1, "1. View cars on lot");
		commands.put(2, "2. View current offers");
		commands.put(3, "3. Make offer");
		commands.put(4, "4. View my cars");
		commands.put(5, "5. View remaining payments");
		commands.put(0, "0. Logout");
		
		return commands;
	}
	@Override
	public String commandNumString(Customer c)
	{
		String ret = "[";
		Set<Integer> cNums = getCommands(c).keySet();
		
		for (Integer s : cNums)
		{
			ret += s + "";
		}
		
		return ret + "\\-]{0,2}";
	}	
	@Override
	public void makeOffer(CarLot c, Customer cust)
	{
		CarLotService cs = new CarLotServiceImplConsole(c);
		cs.printActiveCars();
		int whichCar = Integer.parseInt(IOUtil.getResponse(
				"Please select a car to make an offer on", "[0-9]{4,6}"));
		double offer = Double.parseDouble(IOUtil.getResponse(
				"Please enter your offer, only highest will be stored.", 
				"[0-9]{0,6}[.]{1,2}[0-9]{1,3}|[0-9]{0,6}"));
		int months = Integer.parseInt(IOUtil.getResponse(
				"Payments will be made over how many months?", 
				"[0-9]{0,3}"));
		
		cs.updateOffers(new Offer(c.getCars().get(whichCar).getLicenseNumber(), cust.getUserNum(), offer, months, "Active"));
	}

	@Override
	public void viewCars(CarLot cl, Customer cust) {
		
		Set<Car> custCars = getCustomerCars(cl, cust);
		
		if (custCars.size() == 0)
		{
			IOUtil.messageToUser("I don't have any cars");
		}
		else
		{
			IOUtil.messageToUser("I have the following cars");
			IOUtil.messageToUser("%10s%8s\n", "License #", "Color");
			
			for (Car car : custCars)
			{
				IOUtil.messageToUser("%10s%8s%8s%8s\n", 
						car.getLicenseString(), 
						car.getColor(),
						car.getMake(),
						car.getModel());
			}
		}
	}
	private Set<Car> getCustomerCars(CarLot cl, Customer cust) {
		Set<Car> custCars = new HashSet<Car>();
		
		for (Car c : cl.getCars().values())
		{
			if (c.statusSold())
			{
				if (c.getOwnerID() == cust.getUserNum())
				{
					custCars.add(c);
				}
			}
		}
		return custCars;
	}
	
	public void viewPayments(CarLot cl, Customer cust) {
		System.out.printf("%8s%8s%8s%8s\n", "License", "Offer", "Term", "Payment");
		
		Set<Car> myCars = getCustomerCars(cl, cust);
		
		for (Car car : myCars)
		{
			System.out.printf("%8s%8.2f%8d%8.2f\n", 
					car.getLicenseString(), 
					car.getPrice());
		}
	}
}
