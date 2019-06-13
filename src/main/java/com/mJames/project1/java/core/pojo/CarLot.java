package com.mJames.project1.java.core.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.mJames.project1.java.core.Logging;
import com.mJames.project1.java.core.pojo.User;

public class CarLot extends Logging implements Serializable {
	private static final long serialVersionUID = 3645297906547539714L;
	
	private final int EMPLOYEENUMMAX = 99;
	private final int CUSTOMERMAX = 999;
	
	private User currentUser;
	private Map<Integer, Car> cars;
	private Set<Integer> knownLicenses;
	private Map<Integer, User> users;
	private Set<Offer> openOffers;
	private Set<Offer> closedOffers;
		
	public CarLot() 
	{
		super();
		users = new HashMap<Integer, User>();
		cars = new HashMap<Integer, Car>();
		knownLicenses = new HashSet<Integer>();
		openOffers = new HashSet<Offer>();
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
		String idNum = ""  + car.getIdNumber();
		String license = "" + car.getLicenseNumber();
		Logging.infoLog("Car " + idNum + ", license " + license + ", created.");
		
		cars.put(car.getIdNumber(), car);
		
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
		
		cars.remove(car.getIdNumber());
		int idNum = car.getIdNumber();
		
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
	public Set<Offer> getOpenOffers() {
		return openOffers;
	}
	public Set<Offer> getClosedOffers() {
		return closedOffers;
	}
	public Set<Integer> getKnownLicenses() {
		return knownLicenses;
	}
	public Set<Offer> getOpenOffers(int cID) {
		Set<Offer> carOffers = new HashSet<Offer>();
		
		for (Offer o : openOffers)
		{
			if (o.getCar().getIdNumber() == cID)
				carOffers.add(o);
		}
		
		return carOffers;
	}
	public void addClosedOffer(Offer o) {
		if (closedOffers == null)
			closedOffers = new HashSet<Offer>(0);
		closedOffers.add(o);
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
		String userName = user.getName();
		Logging.infoLog("New User " + userNum + ", " + userName + " created.");
		
		users.put(user.getUserNum(), user);
		return user;
	}
	public void removeUser(User user) {
		Logging.infoLog("Removing user number " + user.getUserNum() + ".");
		users.remove(user.getUserNum());
	}
}
