package com.mJames.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.mJames.pojo.User;
import com.mJames.util.Logging;

public class CarLot extends Logging implements Serializable {
	private static final long serialVersionUID = 3645297906547539714L;
	
	private final int EMPLOYEENUMMAX = 99;
	private final int CUSTOMERMAX = 999;
	
	private User currentUser;
	private Map<Integer, Car> cars;
	private Set<Integer> knownLicenses;
	private Map<Integer, User> users;
	private Set<Offer> offers;
		
	public CarLot() 
	{
		super();
		users = new HashMap<Integer, User>();
		cars = new HashMap<Integer, Car>();
		knownLicenses = new HashSet<Integer>();
		offers = new HashSet<Offer>();
	}
	public CarLot(Map<Integer, Car> cars, Map<Integer, User> users, Scanner sc) {
		super();
		this.cars = cars;
		this.users = users;
		
		knownLicenses = new HashSet<Integer>();
		
		for (Car c : cars.values())
		{
			knownLicenses.add(c.getLicenseNumber());
		}
	}	
	
	public Car addCar(Car car)
	{
		String idNum = ""  + car.getLotID();
		String license = "" + car.getLicenseNumber();
		Logging.infoLog("Car " + idNum + ", license " + license + ", created.");
		
		cars.put(car.getLotID(), car);
		
		return car;
	}
	public Map<Integer, Car> removeCar(int license) {
		Car car = new Car();
		
		for (Car c : cars.values())
		{
			if (c.getLicenseNumber() == license)
			{
				car = c;
				break;
			}
		}
		
		cars.remove(car.getLotID());
		int idNum = car.getLotID();
		
		Logging.infoLog("Car " + idNum + ", license " + license + ", removed.");
		
		return cars;
	}
	public Map<Integer, Car> getCars() {
		return cars;
	}
	public int getEMPLOYEENUMMAX() {
		return EMPLOYEENUMMAX;
	}
	public int getCUSTOMERMAX() {
		return CUSTOMERMAX;
	}
	
	public Map<Integer, User> getUsers() {
		return users;
	}
	public Set<Offer> getOffers() {
		return offers;
	}
	public Set<Integer> getKnownLicenses() {
		return knownLicenses;
	}

	
	public User setCurrentUser(User u)
	{
		currentUser = u;
		return currentUser;
	}
	public User getCurrentUser() {
		return currentUser;
	}
	public User addUser(User user)
	{
		String userNum = ""  + user.getUserNum();
		String userName = user.getFirstName();
		Logging.infoLog("New User " + userNum + ", " + userName + " created.");
		
		users.put(user.getUserNum(), user);
		return user;
	}
	public void removeUser(User user) {
		Logging.infoLog("Removing user number " + user.getUserNum() + ".");
		users.remove(user.getUserNum());
	}

}
