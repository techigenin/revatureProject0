package com.mJames.services;

import java.util.HashMap;
import java.util.Map;

import com.mJames.pojo.CarLot;
import com.mJames.pojo.Employee;
import com.mJames.pojo.User;

public interface EmployeeService {

	HashMap<Integer, String> getCommands(User u);

	String commandNumString(Employee e);

	void listUsers(CarLot carLot);

	Employee addEmployee(CarLot c);

	Map<Integer, User> removeUser(CarLot c, Employee e);

	void addCar(CarLot c);

	void removeCar(CarLot c);

	void viewActiveOffers(CarLot c);

	void acceptOffer(CarLot c);

	void rejectOffer(CarLot c);

	void viewPayments(CarLot c);

}