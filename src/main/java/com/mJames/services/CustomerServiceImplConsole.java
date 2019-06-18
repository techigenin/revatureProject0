package com.mJames.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.mJames.dao.PaymentDao;
import com.mJames.dao.PaymentDaoImpl;
import com.mJames.pojo.Car;
import com.mJames.pojo.CarLot;
import com.mJames.pojo.Customer;
import com.mJames.pojo.Offer;
import com.mJames.pojo.Payment;
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
		commands.put(6, "6. Make a payment");
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
		
		return ret + "\\-]{1,2}";
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

		cs.updateOffers(new Offer(cs.getCarByLotID(whichCar).getLicenseNumber(), cust.getUserNum(), offer, months, "Active"));
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
		
		for (Car c : cl.getCars())
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
		CarLotService cs = new CarLotServiceImplConsole(cl);
		
		ArrayList<Payment> pl = new ArrayList<Payment>();
		
		for(Payment p : cl.getPayments())
			if (p.getUserID() == cust.getUserNum())
				pl.add(p);
		
		Collections.sort(pl);
		Collections.reverse(pl);
		
		System.out.printf("%10s%10s%10s%10s%15s\n", "License", "Color", "Make", "Model", "Remaining");
		
		Set<Car> carsWeHaveSeen = new HashSet<Car>();
		
		for (Payment p : pl)
		{
			Car c = cs.getCarByLicense(p.getCarLicense());
			if (!carsWeHaveSeen.contains(c))
				System.out.printf("%10s%10s%10s%10s%15.2f\n", Car.getLicenseString(p.getCarLicense()), 
						c.getColor(), c.getMake(), c.getModel(), p.getAmountRemaining());
			carsWeHaveSeen.add(c);
		}
	}
	@Override
	public void makePayment(CarLot cl, Customer cust) {
		PaymentService ps = new PaymentServiceImpl();
		
		Map<Integer, Car> userCars = new HashMap<Integer, Car>();
		int i = 1;
		
		for (Car c : cl.getCars())
		{
			if (c.statusSold() &&c.getOwnerID() == cust.getUserNum())
			{
				userCars.put(i++, c);
			}
		}
		
		
		if (userCars.size() > 0)
		{
			IOUtil.messageToUser("User has the following cars:");
			IOUtil.messageToUser("%6s%10s%10s%10s%10s\n", "", "License", "Make", "Model", "Color");
			
			i = 1;
			
			for (Car c : userCars.values())
			{
				IOUtil.messageToUser("%5d.%10s%10s%10s%10s\n", i++, c.getLicenseString(),
						c.getMake(), c.getModel(), c.getColor());	
			}
			
			i = Integer.parseInt(IOUtil.getResponse("Please select a car number" , "[0-9]{1,3}"));
			
			Car c = userCars.get(i);
			
			PaymentDao pd = new PaymentDaoImpl();
			
			if (pd.getPaymentAmountRemaining(cust.getUserNum(), c.getLicenseNumber()) > 0)
				ps.makePayment(cust.getUserNum(), c.getLicenseNumber(), cl);
			else
				IOUtil.messageToUser("No more payments on this car");
		}
	}
}
