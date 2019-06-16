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
	@Override
	public void makeOffer(CarLot c, Customer cust)
	{
		CarLotService cs = new CarLotServiceImplConsole(c);
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
//	@Override
//	public void viewCars(Customer c) {
//		System.out.println("I have the following cars");
//		System.out.printf("%10s%8s\n", "License #", "Color");
//		
//		Set<Car> myCars = getCustomerCars(c);
//		
//		if (myCars.size() == 0)
//		{
//			System.out.println("I don't have any cars");
//		}
//		else
//		{
//			for (Car car : myCars)
//			{
//				System.out.printf("%10s%8s\n", 
//						car.getLicenseString(), 
//						car.getColor());
//			}
//		}
//	}
//	@Override
//	public void viewPayments(Customer cust, CarLot cl) {
//		System.out.printf("%8s%8s%8s%8s\n", "License", "Offer", "Term", "Payment");
//		
//		Set<Car> myCars = cust.getCustomerCars(cust, cl);
//		
//		for (Car car : myCars)
//		{
//			System.out.printf("%8s%8.2f%8d%8.2f\n", 
//					car.getLicenseString(), 
//					car.getPrice());
//		}
//	}
}
