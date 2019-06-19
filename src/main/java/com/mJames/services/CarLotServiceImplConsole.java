package com.mJames.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.HashMap;
import java.util.Set;

import com.mJames.pojo.Car;
import com.mJames.pojo.CarLot;
import com.mJames.pojo.Customer;
import com.mJames.pojo.Employee;
import com.mJames.pojo.Offer;
import com.mJames.pojo.Payment;
import com.mJames.pojo.User;
import com.mJames.ui.IOUtil;
import com.mJames.util.DataUpdate;
import com.mJames.util.Logging;

public class CarLotServiceImplConsole implements CarLotService {
	private static UserService us = new UserServiceImplConsole();
	private static EmployeeService es = new EmployeeServiceImplConsole();
	private static CustomerService cs = new CustomerServiceImplConsole();
	private CarLot cl;

	public CarLotServiceImplConsole(CarLot c)
	{
		super();
		this.cl = c;
	}
	
	@Override
	public int run()
	{
		String response = IOUtil.getResponse(
				"Login (1), Create new customer acount (2), or Quit(0)", 
				"[0-2]{1,2}"); 
		if (response.contentEquals("1"))
		{
			login();
			cl.rebuildFromDB();
			executeCommand();
			return 1;
		}
		else if (response.contentEquals("2"))
		{
			addCustomer();
			return 2;
		}
		else // response 0
		{
			System.out.println("Goodbye!");
			return 0;
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
		Logging.infoLog((user.getUserNum() > cl.getEMPLOYEENUMMAX()) ? "Customer " : "Employee " + 
				user.getUserNum() + " has logged in.");
		
		IOUtil.messageToUser("Logged in. Current user is : " + user.getUserNum());
		IOUtil.messageToUser("Hello " + user.getFirstName());
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
					printActiveOffers();
					break;
				case 2:
					es.acceptOffer(cl, emp);
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
					viewPayments();
					break;
				case 7:
					addCustomer();
					break;
				case 8:
					es.addEmployee(cl);
					break;
				case 9:
					es.listUsers(cl);
					break;
				case 10:
					printActiveCars();
					break;
//				case -1:
//					es.removeUser(cl, emp);
//					break;
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
				String response = IOUtil.getResponse("", cs.commandNumString(cust));
				
				switch(Integer.parseInt(response))
				{
				case 1:
					printActiveCars();
					break;
				case 2:
					printCustomerOffers(cust.getUserNum());
					break;
				case 3:
					cs.makeOffer(cl, cust);
					break;
				case 4:
					cs.viewCars(cl, cust);
					break;
				case 5:
					cs.viewPayments(cl, cust);
					break;
				case 6:
					cs.makePayment(cl, cust);
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
		Set<Integer> numbers = getCarLotIDs();
		
		while(elementExists(start, numbers)) {start += 1;}
		return start;
	}

	public Set<Integer> getCarLotIDs() {
		Set<Integer> numbers = new HashSet<Integer>(cl.getUsers().keySet());

		for (Car c : cl.getCars())
			if (c.statusActive())
				numbers.add(c.getLotID());
		return numbers;
	}	
	@Override
	public boolean userExists(Integer uNum) {
		return (elementExists(uNum, cl.getUsers().keySet()));
	}
	@Override
	public Car addCar(Car car) {
		cl.addCar(car);
		DataUpdate.saveCarLot(cl);
		return car;
	}
	@Override
	public void removeCar(Car car)
	{
		rejectAllOffers(car);
		
		car.setLotID(null);
		car.setStatusRemoved();
		DataUpdate.saveCarLot(cl);
	}

	@Override
	public User addUser(User user)
	{
		IOUtil.messageToUser("User %d, %s %s, added%n", 
				user.getUserNum(), 
				user.getFirstName(),
				user.getLastName());
		
		cl.addUser(user);
		DataUpdate.saveCarLot(cl);
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
				DataUpdate.saveCarLot(cl);
			}
			else
				System.out.println("There is no user " + uNum);
		}
		else
			System.out.println("Only manager can remove users.");
	}
	
	@Override
	public boolean printActiveOffers()
	{
		ArrayList<Offer> activeOffers = new ArrayList<Offer>();
		
		for (Offer o : cl.getOffers())
		{
			if (o.statusActive())
				activeOffers.add(o);
		}
		
		if (activeOffers.size() > 0)
		{
			IOUtil.messageToUser("The following active offers exist");
			IOUtil.messageToUser("%8s%8s%8s%8s%8s%8s%8s%12s\n", "carID", "Color", "Make", "Model", "Price", "Offer", "Months",  "Customer");
			
			for (Offer o : activeOffers)
			{
				Car car = getCarByLicense(o.getCarLicense());
				
				IOUtil.messageToUser("%8d%8s%8s%8s%8.2f%8.2f%8d%12d\n", 
					car.getLotID(), 
					car.getColor(), 
					car.getMake(),
					car.getModel(),
					car.getPrice(), 
					o.getValue(), 
					o.getTerm(),
					o.getUserID());
			}
			return true;
		}
		else
		{
			IOUtil.messageToUser("There are currently no active offers");	
			return false;
		}	
	}

	@Override
	public void printActiveCars()
	{
		ArrayList<Car> cars = new ArrayList<Car>();
				
		for (Car c : cl.getCars())
		{
			if (c.statusActive())
				cars.add(c);
		}
		if (cars.size() > 0)
		{
			IOUtil.messageToUser("The following cars are on the lot.");
			
			IOUtil.messageToUser("%-6s%-11s%-11s%-15s%-10s%-16s\n", 
					"ID", 
					"Color", 
					"Make", 
					"Model",
					"Price($)", 
					" License Number");
			
			Collections.sort(cars);
			for (Car car : cars)
			{
				IOUtil.messageToUser("%6d%11s%11s%15s%10.2f%16s\n", 
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
	public void printCustomerOffers(int userNum) {
		boolean good = false;
		
		Set<Offer> userOffers = new HashSet<Offer>();
		
		for(Offer o : cl.getOffers())
		{
			if (o.getUserID() == userNum && o.statusActive())
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
		System.out.printf("%-6s%-10s%-9s%-9s%-9s\n", "ID", "Color", "Make", "Model", "Price", "Offer", "Term");
		
		for (Offer o: userOffers)
		{
			Car c = getCarByLicense(o.getCarLicense());
			System.out.printf("%6d%10s%10s%10s%9.2f%9.2f%9d\n", 
					c.getLotID(), 
					c.getColor(), 
					c.getMake(),
					c.getModel(),
					c.getPrice(), 
					o.getValue(),
					o.getTerm());
		}
	}
	@Override
	public void printCarOffers (int cID)
	{	
		Set<Offer> offerSet = new HashSet<Offer>();
		
		for (Offer o : cl.getOffers())
		{
			if (getCarByLicense(o.getCarLicense()).getLotID() == cID)
				offerSet.add(o);
		}
		
		if (offerSet.size() != 0)
		{
			System.out.println("The following offers exist");
			System.out.printf("%8s%8s%8s%8s%8s%12s\n", "carID", "Color", "Price", "Offer", "Months",  "Customer");
			
			Car car = new Car();
			
			for (Car c : cl.getCars())
			{
				if (c.getLotID() == cID)
				{
					car = c;
					break;
				}
			}
			
			for (Offer o : offerSet)
			{
				System.out.printf("%8d%8s%8.2f%8.2f%8d%12d\n", 
						car.getLotID(), 
						car.getColor(), 
						car.getPrice(), 
						o.getValue(), 
						o.getTerm(),
						o.getUserID());
			}
		}
		else
			System.out.println("There are no offers on this car");
	}
	@Override
	public void updateOffers(Offer newOffer) {
		// First, the car exists
		if (cl.getCars().contains(getCarByLicense(newOffer.getCarLicense())))
		{
			Set<Offer> activeOffers = new HashSet<Offer>();
			
			for (Offer o  : cl.getOffers())
				if (o.statusActive())
					activeOffers.add(o);
			
			// Check to see if an existing offer exists
			for (Offer o : activeOffers)
			{
				if (o.equals(newOffer)) {
					if (o.updateOffer(newOffer.getValue()))
					{	
						IOUtil.messageToUser("Offer has been placed.");
						return;
					}
					else 
					{
						IOUtil.messageToUser("New offers must exceed old offers.");
						return;
					}
				}
			}
			// Otherwise, add the offer
			cl.addOffer(newOffer);
			DataUpdate.saveCarLot(cl);
		}
		else // The car doesn't exist
			IOUtil.messageToUser("Car does not exist.");
	}
	@Override
	public void rejectSingleOffer(Offer offer)
	{
		for (Offer o : cl.getOffers())
		{
			if (o.equals(offer))
			{
				o.setStatusRejected();
				
				Customer cust = (Customer) getCustomerByID(o.getUserID());
				IOUtil.messageToUser("Offer on car " 
					+ getCarByLicense(o.getCarLicense()).getLotID()
					+ " by customer " 
					+ cust.getFirstName()
					+ " "
					+ cust.getLastName()
					+ " has been removed.");
				return;
			}
		}
		
		IOUtil.messageToUser("No such offer exists");
		DataUpdate.saveCarLot(cl);
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
				System.out.printf("%8d%25s\n", u.getUserNum(), u.getFirstName() + " " + u.getLastName());
			}
		}
		
		if (customers.size() > 0)
		{
			System.out.println("Customers");
			for(Integer i : customers)
			{
				User u = cl.getUsers().get(i);
				System.out.printf("%8d%25s\n", u.getUserNum(), u.getFirstName() + " " + u.getLastName());
			}
		}
	}
	@Override
	public void addCustomer()
	{
		IOUtil.messageToUser("Creating a new customer account.");
		
		int uNum = cl.getEMPLOYEENUMMAX() + 1;
		uNum = firstFree(uNum);
		
		String firstName = IOUtil.getResponse(
				"Please enter the users first name", 
				"[A-Za-z' ]{0,50}");
		String lastName = IOUtil.getResponse(
				"Please enter the users last name", 
				"[A-Za-z' ]{0,50}");
		String password = IOUtil.getResponse(
				"Please enter a password for the new user (alphanumeric, no spaces)", 
				"[A-Za-z0-9']{0,50}");
		
		addUser(new Customer(uNum, firstName, lastName, password, true));
		
		IOUtil.messageToUser("User " + firstName + " added. User number is: " + uNum);
	}

	@Override
	public void acceptOffer(Offer offer) {
		DataUpdate.saveCarLot(cl);
	}

	private void rejectAllOffers(Car car)
	{
		for (Offer o : cl.getOffers())
		{
			if (o.getCarLicense() == car.getLicenseNumber())
				o.setStatusRejected();
		}
		
		IOUtil.messageToUser("All remaining offers for " 
				+ car.getLotID() 
				+ " have been rejected.");
		
		DataUpdate.saveCarLot(cl);
	}
	private User getCustomerByID(Integer customerId) {
		return cl.getUsers().get(customerId);
	}
	@Override
	public  Car getCarByLicense(Integer lic) {
		for (Car c : cl.getCars())
		{
			if (c.getLicenseNumber().equals(lic))
				return c;
		}
		return null;
	}

	@Override
	public double calculatePayment(Offer o) {
		return o.getValue()/o.getTerm();
	}

	@Override
	public void viewPayments() {
		IOUtil.messageToUser("%-12s%-10s%-10s%-18s\n", "License", "OwnerID", "Amount", "Amount Remaining");

		List<Payment> payments = new ArrayList<Payment>();
		
		payments.addAll(cl.getPayments());
		
		Collections.sort(payments);
		
		for (Payment p : payments)
		{
			IOUtil.messageToUser("%10s%10d%10.2f%18.2f\n", 
					Car.getLicenseString(p.getCarLicense()),
					p.getUserID(), p.getAmount(), 
					p.getAmountRemaining());
		}
	}

	@Override
	public Car getCarByLotID(Integer LotID) {
		for (Car c : cl.getCars())
			if (c.getLotID().equals(LotID))
				return c;
		
		return new Car();
	}


}