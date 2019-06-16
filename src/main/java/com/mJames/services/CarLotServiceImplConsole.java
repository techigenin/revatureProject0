package com.mJames.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;

import com.mJames.pojo.Car;
import com.mJames.pojo.CarLot;
import com.mJames.pojo.Customer;
import com.mJames.pojo.Employee;
import com.mJames.pojo.Offer;
import com.mJames.pojo.User;
import com.mJames.ui.IOUtil;
import com.mJames.util.Serialization;

public class CarLotServiceImplConsole implements CarLotService {
	private static UserService us = new UserServiceImplConsole();
	private static EmployeeService es = new EmployeeServiceImplConsole();
	private static CustomerService cs = new CustomerServiceImplConsole();
	private boolean keepRunning;
	private CarLot cl;

	public CarLotServiceImplConsole(CarLot c)
	{
		super();
		this.cl = c;
	}
	
	@Override
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
				addCustomer(cl);
			}
			else if (response.contentEquals("0"))
			{
				System.out.println("Goodbye!");
				keepRunning = false;
			}
		}
	}
	@Override
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
				
				if (cl.getUsers().keySet().contains(userNum))
					userGood = true;
				else
					IOUtil.messageToUser("User ID unknown, please re-enter");
			}
			
			user = cl.getUsers().get(userNum);
		
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
		
		cl.setCurrentUser(user);
		
		IOUtil.messageToUser("Logged in. Current user is : " + cl.getCurrentUser().getUserNum());
		IOUtil.messageToUser("Hello " + cl.getCurrentUser().getFirstName());
	}
	
	@Override
	public void executeCommand()
	{
		boolean loggedIn = true;
		
		while(loggedIn)
		{
			IOUtil.messageToUser("Please select a command.");
			
			// run selected command.
			if (cl.getCurrentUser() instanceof Employee)
			{
				Employee emp = (Employee) cl.getCurrentUser();
				
				printChoices(es.getCommands(cl.getCurrentUser()));
				String response = IOUtil.getResponse("", es.commandNumString(emp));
				
				switch(Integer.parseInt(response))
				{
				case 1:
					es.viewActiveOffers(cl);
					break;
				case 2:
					es.acceptOffer(cl);
					break;
				case 3:
					es.rejectOffer(cl);
					break;
				case 4:
					es.addCar(cl);
					break;
				case 5:
					es.removeCar(cl);
					break;
				case 6: 
					es.viewPayments(cl);
					break;
				case 7:
					addCustomer(cl);
					break;
				case 8:
					es.addEmployee(cl);
					break;
				case 9:
					es.listUsers(cl);
					break;
				case 10:
					us.printListOfCarsWithHeading(cl);
					break;
				case -1:
					es.removeUser(cl, emp);
					break;
				case 0:
					loggedIn = false;
					break;
				default:
					break;
				}
			}
			else if (cl.getCurrentUser() instanceof Customer)
			{
				Customer cust = (Customer) cl.getCurrentUser();
				
				printChoices(cs.getCommands(cust));
				String response = IOUtil.getResponse("", us.commandNumString(cust));
				
				switch(Integer.parseInt(response))
				{
				case 1:
					us.printListOfCarsWithHeading(cl);
					break;
				case 2:
					printCustomerOffers(cust.getUserNum());
					break;
				case 3:
					cs.makeOffer(cl, cust);
					break;
				case 4:
					cs.viewCars(cust);
					break;
				case 5:
					viewPayments(cust);
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
	
	private void viewPayments(Customer cust) {
		System.out.printf("%8s%8s%8s%8s\n", "License", "Offer", "Term", "Payment");
		
		Set<Car> myCars = getCustomerCars(cust);
		
		for (Car car : myCars)
		{
			System.out.printf("%8s%8.2f%8d%8.2f\n", 
					car.getLicenseString(), 
					car.getPrice());
		}
	}

	private Set<Car> getCustomerCars(Customer cust) {
		Set<Car> custCars = new HashSet<Car>();
		
		for (Car c : cl.getCars().values())
		{
			if (c.getOwnerID() == cust.getUserNum())
				custCars.add(c);
		}
		
		return custCars;
	}

	@Override
	public void printChoices(HashMap<Integer, String> map)
	{
		for(String s : map.values())
		{
			System.out.println(s);
		}
	}
	@Override
	public boolean elementExists(int u, Collection<Integer> c)
	{
		return (c.contains(u));
	}
	@Override
	public int firstFree(int start) 
	{
		Set<Integer> numbers = new HashSet<Integer>(cl.getUsers().keySet());
		numbers.addAll(cl.getCars().keySet());
		
		while(elementExists(start, numbers)) {start += 1;}
		return start;
	}	
	@Override
	public boolean userExists(Integer uNum) {
		return (elementExists(uNum, cl.getUsers().keySet()));
	}
	@Override
	public Car addCar(Car car) {
		cl.addCar(car);
		Serialization.Serialize(cl);
		return car;
	}
	@Override
	public void removeCar(Car car)
	{
		removeAllOffers(car);
		cl.removeCar(car.getLicenseNumber());
		Serialization.Serialize(cl);
	}
	@Override
	public User addUser(User user)
	{
		IOUtil.messageToUser("User %d, %s, added%n", 
				user.getUserNum(), 
				user.getFirstName());
		
		cl.addUser(user);
		Serialization.Serialize(cl);
		return user;
	}
	@Override
	public void removeUser(int callingUserNum, int uNum)
	{
		User user = cl.getUsers().get(uNum);
		
		if (callingUserNum == 0)
		{
			if (userExists(uNum) && uNum != 0)
			{	
				cl.removeUser(user);
				System.out.printf("User %d, %s, removed%n", uNum, user.getFirstName());
				Serialization.Serialize(cl);
			}
			else
				System.out.println("There is no user " + uNum);
		}
		else
			System.out.println("Only manager can remove users.");
	}
	
	@Override
	public void printActiveOffers()
	{
		Set<Offer> activeOffers = new HashSet<Offer>();
		
		for (Offer o : cl.getOffers())
		{
			if (o.getStatus() == "Active")
				activeOffers.add(o);
		}
		
		if (activeOffers.size() > 0)
		{
			IOUtil.messageToUser("The following active offers exist");
			IOUtil.messageToUser("%8s%8s%8s%8s%8s%12s\n", "carID", "Color", "Price", "Offer", "Months",  "Customer");
			
			for (Offer o : activeOffers)
			{
				Car car = getCarByLicense(o.getLicense());
				
				IOUtil.messageToUser("%8d%8s%8.2f%8.2f%8d%12d\n", 
					car.getLotID(), 
					car.getColor(), 
					car.getPrice(), 
					o.getOffer(), 
					o.getTerm(),
					o.getCustomer().getUserNum());
			}
		}
		else
			IOUtil.messageToUser("There are currently no active offers");	
	}	
	@Override
	public void printCustomerOffers(int userNum) {
		boolean good = false;
		
		Set<Offer> userOffers = new HashSet<Offer>();
		
		for(Offer o : cl.getOffers())
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
					c.getLotID(), 
					c.getColor(), 
					c.getPrice(), 
					o.getOffer());
		}
	}
	@Override
	public void printCarOffers (int cID)
	{	
		Set<Offer> offerSet = new HashSet<Offer>();
		
		for (Offer o : cl.getOffers())
		{
			if (o.getCar().getLotID() == cID)
				offerSet.add(o);
		}
		
		if (offerSet.size() != 0)
		{
			System.out.println("The following offers exist");
			System.out.printf("%8s%8s%8s%8s%8s%12s\n", "carID", "Color", "Price", "Offer", "Months",  "Customer");
			
			Car car = cl.getCars().get(cID);
			
			for (Offer o : offerSet)
			{
				System.out.printf("%8d%8s%8.2f%8.2f%8d%12d\n", 
						car.getLotID(), 
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
	@Override
	public void updateOffers(Offer newOffer) {
		// First, the car exists
		if (cl.getCars().values().contains(newOffer.getCar()))
		{
			// Check to see if an existing offer exists
			Offer oldOffer = null;
			
			for (Offer o : cl.getOffers())
			{
				if (o.equals(newOffer))
						oldOffer = newOffer;
			}
			
			if (oldOffer != null)
			{
				if (newOffer.getOffer() > oldOffer.getOffer())
				{
					cl.getOffers().remove(oldOffer);
					cl.getOffers().add(newOffer);
					System.out.println("Offer has been placed.");
				}
				else 
					System.out.println("New offers must exceed old offers.");
			}
			else
			{
				cl.getOffers().add(newOffer);
			}
		}
		else // The car doesn't exist
			System.out.println("Car does not exist.");
	}
	@Override
	public void removeSingleOffer(Offer oldOffer)
	{
		for (Offer o : cl.getOffers())
		{
			if (o.equals(oldOffer))
			{
				cl.getOffers().remove(o);
				System.out.println("Offer on car " 
					+ o.getCar().getLotID()
					+ " by customer " 
					+ o.getCustomer().getFirstName()
					+ " has been removed.");
				return;
			}
		}
		
		System.out.println("No such offer exists");
	}
	@Override
	public void removeAllOffers(Car car)
	{
		Set<Offer> matching = new HashSet<Offer>();
		
		for (Offer o : cl.getOffers())
		{
			if (o.getCar().equals(car))
				matching.add(o);
		}
		
		cl.getOffers().removeAll(matching);
		
		System.out.println("All remaining offers for " 
				+ car.getLotID() 
				+ " have been removed.");
	}
	
	@Override
	public void listUsers()	{
		Set<Integer> employees = new HashSet<Integer>();
		Set<Integer> customers = new HashSet<Integer>();
		
		for (Integer i : cl.getUsers().keySet())
		{
			if (i > 0 && i <= cl.getEMPLOYEENUMMAX())
				employees.add(i);
			else if (i > cl.getEMPLOYEENUMMAX() && i < cl.getCUSTOMERMAX())
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
				User u = cl.getUsers().get(i);
				System.out.printf("%8d%15s\n", u.getUserNum(), u.getFirstName());
			}
		}
		
		if (customers.size() > 0)
		{
			System.out.println("Customers");
			for(Integer i : customers)
			{
				User u = cl.getUsers().get(i);
				System.out.printf("%8d%15s\n", u.getUserNum(), u.getFirstName());
			}
		}
	}
	@Override
	public void addCustomer(CarLot c)
	{
		IOUtil.messageToUser("Creating a new customer account.");
		
		int uNum = c.getEMPLOYEENUMMAX() + 1;
		uNum = firstFree(uNum);
		
		String firstName = IOUtil.getResponse(
				"Please enter the users first name", 
				"[A-Za-z' ]{0,50}");
		String lastName = IOUtil.getResponse(
				"Please enter the users lsat name", 
				"[A-Za-z' ]{0,50}");
		String password = IOUtil.getResponse(
				"Please enter a password for the new user (alphanumeric, no spaces)", 
				"[A-Za-z0-9']{0,50}");
		
		addUser(new Customer(uNum, firstName, lastName, password));
		
		System.out.println("User " + firstName + " added. User number is: " + uNum);
	}


	private Car getCarByLicense(int lic) {
		for (Car c : cl.getCars().values())
		{
			if (c.getLicenseNumber() == lic)
				return c;
		}
		return null;
	}
}