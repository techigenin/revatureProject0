package com.mJames.project1.java.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Customer extends User implements Serializable{
	private static final long serialVersionUID = 5399686909346189303L;

	Set<Car> myCars;
	
	public Customer() {
		super();
		myCars = new HashSet<Car>();
		// TODO Auto-generated constructor stub
	}


	public Customer(int userNum, String name, String password, CarLot cLot) {
		super(userNum, name, password, cLot);
		// TODO Auto-generated constructor stub
	}


	@Override
	public HashMap<Integer, String> getCommands() {
		HashMap<Integer, String> commands = new HashMap<Integer, String>();
		
		commands.put(1, "1. View cars on lot");
		commands.put(2, "2. View current offers");
		commands.put(3, "3. Make offer");
		commands.put(4, "4. View my cars");
		commands.put(5, "5. View remaining payments");
		commands.put(0, "0. Logout");
		
		return commands;
	}
	
	public void makeOffer()
	{
		printListOfCars(carLot.getCars());
		int whichCar = Integer.parseInt(carLot.getResponse("Please select a car to make an offer on", "[0-9]{4,6}"));
		double offer = Double.parseDouble(carLot.getResponse(
				"Please enter your offer, only highest will be stored.", 
				"[0-9]{0,6}[.]{1,2}[0-9]{1,3}|[0-9]{0,6}"));
		
		carLot.updateOffers(whichCar, userNum, offer);
	}

	public void viewOffers() {
		carLot.viewCustomerOffers(userNum);
		
	}


	public void viewCars() {
		System.out.println("I have " + myCars.size() + " cars");
	}


	public void viewPayments() {
		// TODO Auto-generated method stub
		
	}
	
	public void addCar(Car c)
	{
		if (myCars == null)
			myCars = new HashSet<Car>();
		
		myCars.add(c);
	}
}
