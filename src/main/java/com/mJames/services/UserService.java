package com.mJames.services;

import java.util.ArrayList;

import com.mJames.pojo.Car;
import com.mJames.pojo.CarLot;
import com.mJames.pojo.User;

public interface UserService {

	boolean checkPassword(String pWord, User u);

	void printListOfCarsWithHeading(CarLot c);

	ArrayList<Car> getListOfCars(CarLot carLot);

}