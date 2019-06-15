package com.mJames.project1.java.core.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;

import com.mJames.project1.java.core.Serialization;
import com.mJames.project1.java.core.pojo.Car;
import com.mJames.project1.java.core.pojo.CarLot;
import com.mJames.project1.java.core.pojo.Customer;
import com.mJames.project1.java.core.pojo.Employee;
import com.mJames.project1.java.core.pojo.Offer;
import com.mJames.project1.java.core.pojo.User;
import com.mJames.project1.java.core.ui.IOUtil;

public class CarLotService {
	private static UserService us = new UserService();
	private static EmployeeService es = new EmployeeService();
	private static CustomerService cs = new CustomerService();
	private boolean keepRunning;
	private CarLot c;
	
	public CarLotService(CarLot c)
	{
		super();
		this.c = c;
	}
	
	public void run()
	{
		keepRunning = true;
		
		while(keepRunning)
		{
			String response = IOUtil.getResponse(
					"Login (1), Create new customer acount (2), or Quit(0)", 
					"[0-2]{1,2}"); 
			if (response.contentEquals("1"))
			{
				login();
				executeCommand();
			}
			else if (response.contentEquals("2"))
			{
				//Customer newCust = new Customer(c);
				addCustomer(c);
			}
			else if (response.contentEquals("0"))
			{
				System.out.println("Goodbye!");
				keepRunning = false;
			}
		}
	}
	public void login() {
		boolean userGood = false;
		boolean passGood = false;
		int userNum = -1;
		String password;
		User user = new User();
		
		while(!passGood)
		{	
			while (!userGood)
			{
				userNum = new Integer(Integer.parseInt(IOUtil.getResponse(
						"Please enter your user ID (0-99999)", 
						"[0-9]{1,6}")));
				
				if (c.getUsers().keySet().contains(userNum))
					userGood = true;
				else
					IOUtil.messageToUser("User ID unknown, please re-enter");
			}
			
			user = c.getUsers().get(userNum);
		
			password = IOUtil.getResponse(
					"Please enter your password (Alphanumeric, < 50 characters)", 
					"[A-Z0-9a-z]{0,50}");
			
			if (us.checkPassword(password, user))
				passGood = true;
			else
			{
				IOUtil.messageToUser("Incorrect password. Please login again.");
				userGood = false;
			}
		}
		
		c.setCurrentUser(user);
		
		IOUtil.messageToUser("Logged in. Current user is : " + c.getCurrentUser().getUserNum());
		IOUtil.messageToUser("Hello " + c.getCurrentUser().getName());
	}
	
	public void executeCommand()
	{
		boolean loggedIn = true;
		
		while(loggedIn)
		{
			IOUtil.messageToUser("Please select a command.");
			
			// run selected command.
			if (c.getCurrentUser() instanceof Employee)
			{
				Employee emp = (Employee) c.getCurrentUser();
				
				printChoices(es.getCommands(c.getCurrentUser()));
				String response = IOUtil.getResponse("", es.commandNumString(emp));
				
				switch(Integer.parseInt(response))
				{
				case 1:
					es.viewOffers(c);
					break;
				case 2:
					es.acceptOffer(c);
					break;
				case 3:
					es.rejectOffer(c);
					break;
				case 4:
					es.addCar(c);
					break;
				case 5:
					es.removeCar(c);
					break;
				case 6: 
					es.viewPayments(c);
					break;
				case 7:
					addCustomer(c);
					break;
				case 8:
					es.addEmployee(c);
					break;
				case 9:
					es.listUsers(c);
					break;
				case 10:
					es.printListOfCarsWithHeading(c);
					break;
				case -1:
					es.removeUser(c, emp);
					break;
				case 0:
					loggedIn = false;
					break;
				default:
					break;
				}
			}
			else if (c.getCurrentUser() instanceof Customer)
			{
				Customer cust = (Customer) c.getCurrentUser();
				
				printChoices(cs.getCommands(cust));
				String response = IOUtil.getResponse("", cs.commandNumString(cust));
				
				switch(Integer.parseInt(response))
				{
				case 1:
					us.printListOfCarsWithHeading(c);
					break;
				case 2:
					printCustomerOffers(cust.getUserNum());
					break;
				case 3:
					cs.makeOffer(c, cust);
					break;
				case 4:
					cs.viewCars(cust);
					break;
				case 5:
					cs.viewPayments(cust);
					break;
				case 0:
					loggedIn = false;
					break;
				default:
					break;
				}
			}
		}
	}
	public void printChoices(HashMap<Integer, String> map)
	{
		for(String s : map.values())
		{
			System.out.println(s);
		}
	}
	public boolean elementExists(int u, Collection<Integer> c)
	{
		return (c.contains(u));
	}
	public int firstFree(int start) 
	{
		Set<Integer> numbers = new HashSet<Integer>(c.getUsers().keySet());
		numbers.addAll(c.getCars().keySet());
		
		while(elementExists(start, numbers)) {start += 1;}
		return start;
	}	
	public boolean userExists(Integer uNum) {
		return (elementExists(uNum, c.getUsers().keySet()));
	}
	public Car addCar(Car car) {
		c.addCar(car);
		Serialization.Serialize(c);
		return car;
	}
	public void removeCar(Car car)
	{
		removeAllOffers(car);
		c.removeCar(car.getLicenseNumber());
		Serialization.Serialize(c);
	}
	public User addUser(User user)
	{
		IOUtil.messageToUser("User %d, %s, added%n", 
				user.getUserNum(), 
				user.getName());
		
		c.addUser(user);
		Serialization.Serialize(c);
		return user;
	}
	public void removeUser(int callingUserNum, int uNum)
	{
		User user = c.getUsers().get(uNum);
		
		if (callingUserNum == 0)
		{
			if (userExists(uNum) && uNum != 0)
			{	
				c.removeUser(user);
				System.out.printf("User %d, %s, removed%n", uNum, user.getName());
				Serialization.Serialize(c);
			}
			else
				System.out.println("There is no user " + uNum);
		}
		else
			System.out.println("Only manager can remove users.");
	}
	
	public void printOffers()
	{
		if (c.getOpenOffers().size() > 0)
		{
			IOUtil.messageToUser("The following offers exist");
			IOUtil.messageToUser("%8s%8s%8s%8s%8s%12s\n", "carID", "Color", "Price", "Offer", "Months",  "Customer");
			
			for (Offer o : c.getOpenOffers())
			{
				Car car = o.getCar();
				
				IOUtil.messageToUser("%8d%8s%8.2f%8.2f%8d%12d\n", 
					car.getIdNumber(), 
					car.getColor(), 
					car.getPrice(), 
					o.getOffer(), 
					o.getTerm(),
					o.getCustomer().getUserNum());
			}
		}
		else
			IOUtil.messageToUser("There are currently no open offers");	
	}	
	public void printCustomerOffers(int userNum) {
		boolean good = false;
		
		Set<Offer> userOffers = new HashSet<Offer>();
		
		for(Offer o : c.getOpenOffers())
		{
			if (o.getCustomer().getUserNum() == userNum)
			{
				userOffers.add(o);
				good = true;
			}
		}
		
		if (!good)
		{
			System.out.println("User has no offers");
			return;
		}
		
		System.out.println("User has the following offers");
		System.out.printf("%-6s%-10s%-9s%-9s\n", "ID", "Color", "Price", "Offer");
		
		for (Offer o: userOffers)
		{
			Car c = o.getCar();
			System.out.printf("%6d%10s%9.2f%9.2f\n", 
					c.getIdNumber(), 
					c.getColor(), 
					c.getPrice(), 
					o.getOffer());
		}
	}
	public void printCarOffers (int cID)
	{	
		Set<Offer> offerSet = new HashSet<Offer>();
		
		for (Offer o : c.getOpenOffers())
		{
			if (o.getCar().getIdNumber() == cID)
				offerSet.add(o);
		}
		
		if (offerSet.size() != 0)
		{
			System.out.println("The following offers exist");
			System.out.printf("%8s%8s%8s%8s%8s%12s\n", "carID", "Color", "Price", "Offer", "Months",  "Customer");
			
			Car car = c.getCars().get(cID);
			
			for (Offer o : offerSet)
			{
				System.out.printf("%8d%8s%8.2f%8.2f%8d%12d\n", 
						car.getIdNumber(), 
						car.getColor(), 
						car.getPrice(), 
						o.getOffer(), 
						o.getTerm(),
						o.getCustomer().getUserNum());
			}
		}
		else
			System.out.println("There are no offers on this car");
	}
	public void updateOffers(Offer newOffer) {
		// First, the car exists
		if (c.getCars().values().contains(newOffer.getCar()))
		{
			// Check to see if an existing offer exists
			Offer oldOffer = null;
			
			for (Offer o : c.getOpenOffers())
			{
				if (o.equals(newOffer))
						oldOffer = newOffer;
			}
			
			if (oldOffer != null)
			{
				if (newOffer.getOffer() > oldOffer.getOffer())
				{
					c.getOpenOffers().remove(oldOffer);
					c.getOpenOffers().add(newOffer);
					System.out.println("Offer has been placed.");
				}
				else 
					System.out.println("New offers must exceed old offers.");
			}
			else
			{
				c.getOpenOffers().add(newOffer);
			}
		}
		else // The car doesn't exist
			System.out.println("Car does not exist.");
	}
	public void removeSingleOffer(Offer oldOffer)
	{
		for (Offer o : c.getOpenOffers())
		{
			if (o.equals(oldOffer))
			{
				c.getOpenOffers().remove(o);
				System.out.println("Offer on car " 
					+ o.getCar().getIdNumber()
					+ " by customer " 
					+ o.getCustomer().getName()
					+ " has been removed.");
				return;
			}
		}
		
		System.out.println("No such offer exists");
	}
	public void removeAllOffers(Car car)
	{
		Set<Offer> matching = new HashSet<Offer>();
		
		for (Offer o : c.getOpenOffers())
		{
			if (o.getCar().equals(car))
				matching.add(o);
		}
		
		c.getOpenOffers().removeAll(matching);
		
		System.out.println("All remaining offers for " 
				+ car.getIdNumber() 
				+ " have been removed.");
	}
	
	public void listUsers()	{
		Set<Integer> employees = new HashSet<Integer>();
		Set<Integer> customers = new HashSet<Integer>();
		
		for (Integer i : c.getUsers().keySet())
		{
			if (i > 0 && i <= c.getEMPLOYEENUMMAX())
				employees.add(i);
			else if (i > c.getEMPLOYEENUMMAX() && i < c.getCUSTOMERMAX())
				customers.add(i);
		}
		
		if (employees.size() == 0 && customers.size() == 0)
		{
			System.out.println("There are no users");
			return;
		}
		
		if (employees.size() > 0)
		{
			System.out.println("Employees");
			for(Integer i : employees)
			{
				User u = c.getUsers().get(i);
				System.out.printf("%8d%15s\n", u.getUserNum(), u.getName());
			}
		}
		
		if (customers.size() > 0)
		{
			System.out.println("Customers");
			for(Integer i : customers)
			{
				User u = c.getUsers().get(i);
				System.out.printf("%8d%15s\n", u.getUserNum(), u.getName());
			}
		}
	}
	public void addCustomer(CarLot c)
	{
		IOUtil.messageToUser("Creating a new customer account.");
		
		int uNum = c.getEMPLOYEENUMMAX() + 1;
		uNum = firstFree(uNum);
		
		String name = IOUtil.getResponse(
				"Please enter the users name", 
				"[A-Za-z' ]{0,50}");
		String password = IOUtil.getResponse(
				"Please enter a password for the new user (alphanumeric, no spaces)", 
				"[A-Za-z0-9']{0,50}");
		
		addUser(new Customer(uNum, name, password, c));
		
		System.out.println("User " + name + " added. User number is: " + uNum);
	}
}