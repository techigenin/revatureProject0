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
	private Map<Integer, Map<Integer, Double>> offers;
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
		offers = new HashMap<Integer, Map<Integer,Double>>();
		
		// Add the super user
		if (!userMap.containsKey(0))
			userMap.put(0, new Employee(0, "Bill", "", this));

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
		
		System.out.println("Logged in. Current user is : " + currentUser.getUserNum());
		System.out.println("Hello " + currentUser.getUserName());
	}
	
	public Map<Integer, User> getUsers() {
		return userMap;
	}
	
	public int getCurrentUserNum()
	{
		return currentUser.getUserNum();
	}
	
	public void addUser(int callingUserNum, User newUser)
	{
		
			userMap.put(newUser.getUserNum(), newUser);
			System.out.printf("User %d, %s, added%n", newUser.getUserNum(), newUser.getUserName());
	}
	
	public void addCar(Car car)
	{
		carMap.put(car.getIdNumber(), car);
	}
	
	public void removeUser(int callingUserNum, int uNum)
	{
		User user = userMap.get(uNum);
		
		if (callingUserNum == 0)
		{
			userMap.remove(uNum);
			System.out.printf("User %d, %s, removed%n", uNum, user.getUserName());
		}
		else
			System.out.println("Only manager can remove users.");
	}

	public void removeCar(Car car)
	{
		carMap.remove(car.getIdNumber());
	}
	
	public Set<Integer> getKnownLicenses() {
		return knownLicenses;
	}
	public void printChoices(HashMap<Integer, String> map)
	{
		for(String s : map.values())
		{
			System.out.println(s);
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
			else
				good = true;
		}
		
		return string;
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
				
				printChoices(currentUser.getCommands());
				String response = getResponse("", currentUser.commandNumString());
				
				switch(Integer.parseInt(response))
				{
				case 1:
					emp.viewOffers();
					break;
				case 2:
					break;
				case 3:
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
				
				printChoices(currentUser.getCommands());
				String response = getResponse("", currentUser.commandNumString());
				
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
//				case 4:
//					emp.addCar();
//					break;
//				case 5:
//					emp.removeCar();
//					break;
				case 0:
					loggedIn = false;
					break;
				default:
					break;
				}
			}
		}
	}
	
	public Map<Integer, Map<Integer, Double>> getOffers() {
		return offers;
	}
	public boolean elementExists(int u, Collection<Integer> c)
	{
		return (c.contains(u));
	}

//	private int userType(int uNum)
//	{
//		if (uNum > -1 && uNum <= EMPLOYEENUMMAX)
//			return 1;	
//		else if (uNum > EMPLOYEENUMMAX && uNum <= CUSTOMERMAX)
//			return 2;
//		else
//			return 0;
//	}
	
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
	public void updateOffers(int whichCar, int userNum, double offer) {
		// First, the car exists
		if (carMap.keySet().contains(whichCar))
		{
			// No other offers exist
			if (!offers.keySet().contains(whichCar))
				offers.put(whichCar, new HashMap<Integer, Double>());
			if (getCurrentOffer(whichCar, userNum) < offer)
			{
				offers.get(whichCar).put(userNum, offer);
				System.out.println("Offer has been placed.");
			}
			else
				System.out.println("New offers must exceed old offers.");
		}
		else // The car doesn't exist
			System.out.println("Car does not exist.");
	}
	private double getCurrentOffer(int whichCar, int userNum) {
		return offers.get(whichCar).get(userNum);
	}
	public void viewCustomerOffers(int userNum) {
		boolean good = false;
		
		HashMap<Integer, Double> userOffers = new HashMap<Integer, Double>();
		
		for(Integer i : offers.keySet())
		{
			if (offers.get(i).containsKey(userNum))
			{
				userOffers.put(i, getCurrentOffer(i, userNum));
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
		
		for (Integer i: userOffers.keySet())
		{
			Car c = carMap.get(i);
			System.out.printf("%6d%10s%9.2f%9.2f\n", i, c.getColor(), c.getPrice(), userOffers.get(i));
		}
	}
}
