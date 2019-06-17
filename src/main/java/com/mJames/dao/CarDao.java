package com.mJames.dao;

import java.util.List;

import com.mJames.pojo.Car;

public interface CarDao {
	
	public void createCar(Car c);
	
	public void updateCarLotID(Car c);
	public void updateCarOwnerID(Car c);
	public void updateCarStatus(Car c);
	
	public void deleteCar(Car c);
	
	public Car getCarByLicense(Integer license);
	
	public List<Car> getAllCars();
}
