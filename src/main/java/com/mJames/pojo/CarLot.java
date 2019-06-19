package com.mJames.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mJames.dao.CarDao;
import com.mJames.dao.CarDaoImpl;
import com.mJames.dao.CustomerDao;
import com.mJames.dao.CustomerDaoImpl;
import com.mJames.dao.EmployeeDao;
import com.mJames.dao.EmployeeDaoImpl;
import com.mJames.dao.OfferDao;
import com.mJames.dao.OfferDaoImpl;
import com.mJames.dao.PaymentDao;
import com.mJames.dao.PaymentDaoImpl;
import com.mJames.pojo.User;
import com.mJames.util.Logging;

public class CarLot extends Logging implements Serializable {
	private static final long serialVersionUID = 3645297906547539714L;
	
	private final int EMPLOYEENUMMAX = 99;
	private final int CUSTOMERMAX = 999;
	
	private User currentUser;
	private List<Car> cars;
	private Set<Integer> knownLicenses;
	private Map<Integer, User> users;
	private Set<Offer> offers;
	private Set<Payment> payments;
	private Map<Integer, Integer> licenceToLotID;
		
	public CarLot() 
	{
		super();
		users = new HashMap<Integer, User>();
		cars = new ArrayList<Car>();
		knownLicenses = new HashSet<Integer>();
		offers = new HashSet<Offer>();
		payments = new HashSet<Payment>();
		licenceToLotID = new HashMap<Integer, Integer>();
	}

	public void rebuildFromDB() {
		CarDao carDao = new CarDaoImpl();
		EmployeeDao eDao = new EmployeeDaoImpl();
		CustomerDao cDao = new CustomerDaoImpl();
		OfferDao oDao = new OfferDaoImpl();
		PaymentDao pDao = new PaymentDaoImpl();
		
		cars.clear();
		users.clear();
		offers.clear();
		payments.clear();
		
		List<User> userList = new ArrayList<User>();
		userList.addAll(eDao.getAllEmployees());
		userList.addAll(cDao.getAllCustomers());
		
		for (User u : userList)
			users.put(u.getUserNum(), u);
		cars.addAll(carDao.getAllCars());
		offers.addAll(oDao.getAllOffers());
		payments.addAll(pDao.getAllPayments());
		
		buildLicToLotID();
	}
	
	public boolean addKnownLicenses(Collection<Integer> licenses) {
		return knownLicenses.addAll(licenses);
	}
	public boolean addUsers(Collection<Customer> custs, Collection<Employee> emps) {
		Map<Integer, User> usrMap = new HashMap<Integer, User>();
		
		for (Customer c : custs)
			usrMap.put(c.getUserNum(), c);
		for (Employee e : emps)
			usrMap.put(e.getUserNum(), e);
		
		int usersSize = users.size();
		
		users.putAll(usrMap);
		
		return (users.size() - usersSize) == usrMap.size();
	}
	public boolean addOffers(Collection<Offer> offs) {
		return offers.addAll(offs);
	}
	public boolean addPayments(Collection<Payment> pmts) {
		return payments.addAll(pmts);
	}
	private void buildLicToLotID() {
		for (Car c : cars)
			licenceToLotID.put(c.getLicenseNumber(), c.getLotID());
	}
	
	public Car addCar(Car car)
	{
		String idNum = ""  + car.getLotID();
		String license = "" + car.getLicenseNumber();
		licenceToLotID.put(car.getLicenseNumber(), car.getLotID());
		Logging.infoLog("Car " + idNum + ", license " + license + ", created.");
		
		cars.add(car);
		knownLicenses.add(car.getLicenseNumber());
		
		licenceToLotID.put(car.getLicenseNumber(), car.getLotID());
		
		return car;
	}
	public Map<Integer, Integer> removeCar(int license) {
		licenceToLotID.put(license, null);
		return licenceToLotID;
	}
	public List<Car> getCars() {
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
	public void addPayment(Payment p) {
		payments.add(p);
	}
	public Set<Payment> getPayments() {
		return payments;
	}
	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
	}
	public void addOffer(Offer offer) {
		offers.add(offer);
	}
	public Integer getLotID(Integer lVal) {
		return licenceToLotID.get(lVal);
	}

	public Integer getNumberPayments() {
		return payments.size();
	}

}
