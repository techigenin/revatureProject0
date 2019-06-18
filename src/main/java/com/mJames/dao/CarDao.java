package com.mJames.dao;

import java.util.List;

import com.mJames.pojo.Car;

public interface CarDao {
	
	public boolean carExists(Car c) ;
	public boolean createCar(Car c);
	
	public boolean updateCarLotID(Car c);
	public boolean updateCarOwnerID(Car c);
	public boolean updateCarStatus(Car c);
	
	public boolean deleteCar(Car c);
	
	public Car getCarByLicense(Integer license);
	
	public List<Car> getAllCars();
}
