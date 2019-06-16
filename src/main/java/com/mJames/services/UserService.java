package com.mJames.services;

import java.util.ArrayList;
import java.util.HashMap;

import com.mJames.pojo.Car;
import com.mJames.pojo.CarLot;
import com.mJames.pojo.User;

public interface UserService {

	boolean checkPassword(String pWord, User u);

	HashMap<Integer, String> getCommands(User u);

	String commandNumString(User u);

	void printListOfCarsWithHeading(CarLot c);

	ArrayList<Car> getListOfCars(CarLot carLot);

}