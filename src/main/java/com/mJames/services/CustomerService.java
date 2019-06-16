package com.mJames.services;

import java.util.HashMap;

import com.mJames.pojo.CarLot;
import com.mJames.pojo.Customer;
import com.mJames.pojo.User;

public interface CustomerService {

	HashMap<Integer, String> getCommands(User c);

	void makeOffer(CarLot c, Customer cust);

	void viewCars(Customer c);

	void viewPayments(Customer c);

}