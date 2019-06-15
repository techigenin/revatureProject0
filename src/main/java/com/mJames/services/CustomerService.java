package com.mJames.project1.java.core.services;

import java.util.HashMap;
import java.util.Set;

import com.mJames.project1.java.core.pojo.Car;
import com.mJames.project1.java.core.pojo.CarLot;
import com.mJames.project1.java.core.pojo.Customer;
import com.mJames.project1.java.core.pojo.Offer;
import com.mJames.project1.java.core.pojo.User;
import com.mJames.project1.java.core.ui.IOUtil;

	public class CustomerService extends UserService{
		
		@Override
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
	public void makeOffer(CarLot c, Customer cust)
	{
		CarLotService cs = new CarLotService(c);
		printListOfCarsWithHeading(c);
		int whichCar = Integer.parseInt(IOUtil.getResponse(
				"Please select a car to make an offer on", "[0-9]{4,6}"));
		double offer = Double.parseDouble(IOUtil.getResponse(
				"Please enter your offer, only highest will be stored.", 
				"[0-9]{0,6}[.]{1,2}[0-9]{1,3}|[0-9]{0,6}"));
		int months = Integer.parseInt(IOUtil.getResponse(
				"Payments will be made over how many months?", 
				"[0-9]{0,3}"));
		cs.updateOffers(new Offer(c.getCars().get(whichCar), 
				cust, 
				offer, 
				months));
	}
	public void viewCars(Customer c) {
		System.out.println("I have the following cars");
		System.out.printf("%10s%8s\n", "License #", "Color");
		
		Set<Car> myCars = c.getMyCars();
		
		if (myCars.size() == 0)
		{
			System.out.println("I don't have any cars");
		}
		else
		{
			for (Car car : myCars)
			{
				System.out.printf("%10s%8s\n", 
						car.getLicenseString(), 
						car.getColor());
			}
		}
	}
	public void viewPayments(Customer c) {
		System.out.printf("%8s%8s%8s%8s\n", "License", "Offer", "Term", "Payment");
		
		Set<Car> myCars = c.getMyCars();
		
		for (Car car : myCars)
		{
			System.out.printf("%8s%8.2f%8d%8.2f\n", 
					car.getLicenseString(), 
					car.getSellPrice(), 
					car.getTerm(), 
					car.getPayment());
		}
	}
}
