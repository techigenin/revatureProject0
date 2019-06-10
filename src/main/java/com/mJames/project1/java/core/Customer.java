package com.mJames.project1.java.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Customer extends User implements Serializable{
	private static final long serialVersionUID = 5399686909346189303L;

	private Set<CustomerCar> myCars;
	
	public Customer() {
		super();
		myCars = new HashSet<CustomerCar>();
	}
	public Customer(int userNum, String name, String password, CarLot cLot) {
		super(userNum, name, password, cLot);
	}

	@Override
	public HashMap<Integer, String> getCommandString() {
		HashMap<Integer, String> commands = new HashMap<Integer, String>();
		
		commands.put(1, "1. View cars on lot");
		commands.put(2, "2. View current offers");
		commands.put(3, "3. Make offer");
		commands.put(4, "4. View my cars");
		commands.put(5, "5. View remaining payments");
		commands.put(0, "0. Logout");
		
		return commands;
	}
	
	public void viewOffers() {
		carLot.printCustomerOffers(userNum);	
	}
	public void makeOffer()
	{
		printListOfCars(carLot.getCars());
		int whichCar = Integer.parseInt(carLot.getResponse(
				"Please select a car to make an offer on", "[0-9]{4,6}"));
		double offer = Double.parseDouble(carLot.getResponse(
				"Please enter your offer, only highest will be stored.", 
				"[0-9]{0,6}[.]{1,2}[0-9]{1,3}|[0-9]{0,6}"));
		int months = Integer.parseInt(carLot.getResponse(
				"Payments will be made over how many months?", 
				"[0-9]{0,3}"));
		
		carLot.updateOffers(new Offer(carLot.getCars().get(whichCar), 
				(Customer)carLot.getUsers().get(userNum), 
				offer, 
				months));
	}

	public void viewCars() {
		System.out.println("I have the following cars");
		System.out.printf("%10s%8s\n", "License #", "Color");
		
		if (myCars == null)
			myCars = new HashSet<CustomerCar>();
		
		if (myCars.size() == 0)
		{
			System.out.println("I don't have any cars");
		}
		else
		{
			for (Car c : myCars)
			{
				System.out.printf("%10s%8s\n", c.getLicenseString(), c.getColor());
			}
		}
	}
	public void addCustomerCar(CustomerCar c)
	{
		if (myCars == null)
			myCars = new HashSet<CustomerCar>();
		
		myCars.add(c);
	}

	public void viewPayments() {
		System.out.printf("%8s%8s%8s%8s\n", "License", "Offer", "Term", "Payment");
		
		if (myCars == null)
			myCars = new HashSet<CustomerCar>();
		
		for (CustomerCar c : myCars)
		{
			System.out.printf("%8s%8.2f%8d%8.2f\n", 
					c.getLicenseString(), 
					c.getOffer(), 
					c.getTerm(), 
					c.getPayment());
		}
	}
}