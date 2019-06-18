package com.mJames.services;

import java.util.Collection;
import java.util.HashMap;

import com.mJames.pojo.Car;
import com.mJames.pojo.CarLot;
import com.mJames.pojo.Offer;
import com.mJames.pojo.User;

public interface CarLotService {

	int run();

	void login();

	void executeCommand();

	void printChoices(HashMap<Integer, String> map);

	boolean elementExists(int u, Collection<Integer> c);

	int firstFree(int start);

	boolean userExists(Integer uNum);

	Car addCar(Car car);

	void removeCar(Car car);

	User addUser(User user);

	void removeUser(int callingUserNum, int uNum);

	boolean printActiveOffers();
	
	void printActiveCars();

	void printCustomerOffers(int userNum);

	void printCarOffers(int cID);

	void updateOffers(Offer newOffer);

	void rejectSingleOffer(Offer oldOffer);

	void listUsers();

	void addCustomer();

	void acceptOffer(Offer o);
	
	void viewPayments();
	
	double calculatePayment(Offer o);
}