package com.mJames.project1.java.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class CarLot implements Serializable {
	private static final long serialVersionUID = 3645297906547539714L;
	
	private final int EMPLOYEENUMMAX = 99;
	private final int CUSTOMERMAX = 999;
	
	private Map<Integer, Car> carMap;
	private Set<Integer> knownLicenses;
	private Map<Integer, User> userMap;
	// Offer should be a map of carID to maps of userIDs to offers
	//private Map<Integer, Map<Integer, Double>> offers;
	private Set<Offer> offers;
	private static Scanner sc;
	private User currentUser;
	private boolean keepRunning;
		
	public CarLot() 
	{
		super();
		sc = new Scanner(System.in);
		userMap = new HashMap<Integer, User>();
		carMap = new HashMap<Integer, Car>();
		knownLicenses = new HashSet<Integer>();
		offers = new HashSet<Offer>();
		
		// Add the super user
		if (!userMap.containsKey(0))
			userMap.put(0, new Employee(0, "Jean Luc", "Picard", this));

		String[] colors = {"green", "blue", "red", "yellow", "orange"};
		
		// Add a couple starter cars
		for (int i = 0; i < 5; i++)
		{
			int cNum = firstFree(CUSTOMERMAX + 1);
			carMap.put(cNum, new Car(cNum, 2000, colors[i], knownLicenses));
		}
	}
	public CarLot(Map<Integer, Car> cars, Map<Integer, User> users, Scanner sc) {
		super();
		this.carMap = cars;
		this.userMap = users;
		CarLot.sc = sc;
		
		knownLicenses = new HashSet<Integer>();
		
		for (Car c : cars.values())
		{
			knownLicenses.add(c.getLicenseNumber());
		}
	}	
	
	public Map<Integer, Car> getCars() {
		return carMap;
	}
	public int getEMPLOYEENUMMAX() {
		return EMPLOYEENUMMAX;
	}
	public int getCUSTOMERMAX() {
		return CUSTOMERMAX;
	}
	public int getCurrentUserNum()
	{
		return currentUser.getIdNumber();
	}
	public Map<Integer, User> getUsers() {
		return userMap;
	}
	public Set<Offer> getOffers() {
		return offers;
	}
	
	public void run()
	{
		keepRunning = true;
		
		while(keepRunning)
		{
			String response = getResponse("Login (1), Create new customer acount (2), or Quit(0)", "[0-2]{1,2}"); 
			if (response.contentEquals("1"))
			{
				login();
				executeCommand();
			}
			else if (response.contentEquals("2"))
			{
				User newUser = new User(this);
				newUser.addCustomer();
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
				userNum = new Integer(Integer.parseInt(getResponse("Please enter your user ID (0-99999)", "[0-9]{1,6}")));
				
				if (userMap.keySet().contains(userNum))
					userGood = true;
				else
					System.out.println("User ID unknown, please re-enter");
			}
			
			user = userMap.get(userNum);
		
			password = getResponse("Please enter your password (Alphanumeric, < 50 characters)", "[A-Z0-9a-z]{0,50}");
			
			if (user.checkPassword(password))
				passGood = true;
			else
			{
				System.out.println("Incorrect password. Please login again.");
				userGood = false;
			}
		}
		
		currentUser = user;
		
		System.out.println("Logged in. Current user is : " + currentUser.getIdNumber());
		System.out.println("Hello " + currentUser.getUserName());
	}
	public void executeCommand()
	{
		boolean loggedIn = true;
		
		while(loggedIn)
		{
			System.out.println("Please select a command.");
			
			// run selected command.
			if (currentUser instanceof Employee)
			{
				Employee emp = (Employee) currentUser;
				
				printChoices(currentUser.getCommandString());
				String response = getResponse("", emp.commandNumString());
				
				switch(Integer.parseInt(response))
				{
				case 1:
					emp.viewOffers();
					break;
				case 2:
					emp.acceptOffer();
					break;
				case 3:
					emp.rejectOffer();
					break;
				case 4:
					emp.addCar();
					break;
				case 5:
					emp.removeCar();
					break;
				case 6: 
					break;
				case 7:
					emp.addCustomer();
					break;
				case 8:
					emp.addEmployee();
					break;
				case 9:
					emp.listUsers();
					break;
				case 10:
					emp.printListOfCars(carMap);
					break;
				case -1:
					emp.removeUser();
					break;
				case 0:
					loggedIn = false;
					break;
				default:
					break;
				}
			}
			else if (currentUser instanceof Customer)
			{
				Customer cust = (Customer) currentUser;
				
				printChoices(cust.getCommandString());
				String response = getResponse("", cust.commandNumString());
				
				switch(Integer.parseInt(response))
				{
				case 1:
					cust.printListOfCars(carMap);
					break;
				case 2:
					cust.viewOffers();
					break;
				case 3:
					cust.makeOffer();
					break;
				case 4:
					cust.viewCars();
					break;
				case 5:
					cust.viewPayments();
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
	public String getResponse(String message, String acceptable) {
		System.out.println(message);

		String string = "";
		boolean good = false;
		
		while(!good)
		{
			string = new String(sc.nextLine());
			
			if (!string.matches(acceptable))
			{
				System.out.println("Response must be in the given range.");
			}
			else if (string == "")
			{
				continue;
			}
			else
				good = true;
		}
		
		return string;
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
		Set<Integer> numbers = new HashSet<Integer>(userMap.keySet());
		numbers.addAll(carMap.keySet());
		
		while(elementExists(start, numbers)) {start += 1;}
		return start;
	}
	
	public boolean userExists(Integer uNum) {
		return (elementExists(uNum, userMap.keySet()));
	}
	public void addUser(int callingUserNum, User newUser)
	{
		
			userMap.put(newUser.getIdNumber(), newUser);
			System.out.printf("User %d, %s, added%n", newUser.getIdNumber(), newUser.getUserName());
	}
	public void removeUser(int callingUserNum, int uNum)
	{
		User user = userMap.get(uNum);
		
		if (callingUserNum == 0)
		{
			if (userExists(uNum) && uNum != 0)
			{	
				userMap.remove(uNum);
				System.out.printf("User %d, %s, removed%n", uNum, user.getUserName());
			}
			else
				System.out.println("There is no user " + uNum);
		}
		else
			System.out.println("Only manager can remove users.");
	}
		
	public void addCar(Car car)
	{
		carMap.put(car.getIdNumber(), car);
	}
	public void removeCar(Car car)
	{
		int cID = car.getIdNumber();
		Set<Offer> offerSet = new HashSet<Offer>();
		
		for (Offer o : offers)
			if (o.getCar().getIdNumber() == cID)
				offerSet.add(o);
		for (Offer o : offerSet)
			removeOffer(o);
		
		carMap.remove(car.getIdNumber());
	}
	
	public Set<Integer> getKnownLicenses() {
		return knownLicenses;
	}

	public void printOffers()
	{
		if (offers.size() > 0)
		{
			System.out.println("The following offers exist");
			System.out.printf("%8s%8s%8s%8s%12s\n", "carID", "Color", "Price", "Offer", "Customer");
			
			for (Offer o : offers)
			{
				Car car = o.getCar();
				
				System.out.printf("%8d%8s%8.2f%8.2f%12d\n", 
					car.getIdNumber(), 
					car.getColor(), 
					car.getPrice(), 
					o.getOffer(), 
					o.getCustomer().getIdNumber());
			}
		}
		else
			System.out.println("There are currently no offers");
				
	}	
	public void printCustomerOffers(int userNum) {
		boolean good = false;
		
		Set<Offer> userOffers = new HashSet<Offer>();
		
		for(Offer o : offers)
		{
			if (o.getCustomer().getIdNumber() == userNum)
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
		
		for (Offer o : offers)
		{
			if (o.getCar().getIdNumber() == cID)
				offerSet.add(o);
		}
		
		if (offerSet.size() != 0)
		{
			System.out.println("The following offers exist");
			System.out.printf("%12s%8s%8s%8s\n", "Customer", "Color", "Price", "Offer");
			
			Car car = carMap.get(cID);
			
			for (Offer o : offerSet)
			{
				System.out.printf("%12d%8s%8.2f%8.2f\n", 
						o.getCustomer().getIdNumber(), 
						car.getColor(), 
						car.getPrice(), 
						o.getOffer());
			}
		}
		else
			System.out.println("There are no offers on this car");
	}
	public void updateOffers(Offer newOffer) {
		// First, the car exists
		if (carMap.keySet().contains(newOffer.getCar()))
		{
			// Check to see if an existing offer exists
			Offer oldOffer = null;
			
			for (Offer o : offers)
			{
				if (o.equals(newOffer))
						oldOffer = newOffer;
			}
			
			if (oldOffer != null)
			{
				if (newOffer.getOffer() > oldOffer.getOffer())
				{
					offers.remove(oldOffer);
					offers.add(newOffer);
					System.out.println("Offer has been placed.");
				}
				else 
					System.out.println("New offers must exceed old offers.");
			}
			else
			{
				offers.add(newOffer);
			}
		}
		else // The car doesn't exist
			System.out.println("Car does not exist.");
	}
	public void removeOffer(Offer oldOffer)
	{
		for (Offer o : offers)
		{
			if (o.equals(oldOffer))
			{
				offers.remove(o);
				System.out.println("Offer on car " 
					+ o.getCar() 
					+ " by customer " 
					+ o.getCar() 
					+ " has been removed.");
				return;
			}
		}
		
		System.out.println("No such offer exists");
	}	
	private Double getCurrentOffer(int whichCar, int userNum) {
		for (Offer o : offers)
		{
			if (o.getCar().getIdNumber() == whichCar 
					&& o.getCustomer().getIdNumber() == userNum)
				return o.getOffer();
		}
		System.out.println("No offer by this user exists");
		
		return -1.0;
	}
	
	public void listUsers()
	{
		boolean employees = true;
		
		System.out.println("Employees");
		
		for (User u : getUsers().values())
		{
			if (u.getIdNumber() > EMPLOYEENUMMAX && employees)
			{
				employees = false;
				System.out.println("Customers");
			}
			if(u.getIdNumber() > 0) // Doesn't list manager
				System.out.printf("%8d%15s\n", u.getIdNumber(), u.getUserName());
		}
	}
}
