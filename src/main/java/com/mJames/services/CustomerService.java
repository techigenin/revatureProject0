package com.mJames.services;

import java.util.HashMap;

import com.mJames.pojo.CarLot;
import com.mJames.pojo.Customer;
import com.mJames.pojo.User;

public interface CustomerService {

	HashMap<Integer, String> getCommands(User c);
	
	public String commandNumString(Customer c);

	void makeOffer(CarLot c, Customer cust);

	void viewCars(CarLot cl, Customer cust);
	
	void viewPayments(CarLot cl, Customer cust);
	
	void makePayment(CarLot cl, Customer cust);
}