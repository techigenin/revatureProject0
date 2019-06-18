package com.mJames.util;

import com.mJames.dao.CarDao;
import com.mJames.dao.CarDaoImpl;
import com.mJames.dao.CustomerDao;
import com.mJames.dao.CustomerDaoImpl;
import com.mJames.dao.EmployeeDao;
import com.mJames.dao.EmployeeDaoImpl;
import com.mJames.dao.OfferDao;
import com.mJames.dao.OfferDaoImpl;
import com.mJames.pojo.Car;
import com.mJames.pojo.CarLot;
import com.mJames.pojo.Customer;
import com.mJames.pojo.Employee;
import com.mJames.pojo.Offer;
import com.mJames.pojo.Payment;

public interface DataUpdate {

	public static boolean saveCar(Car car) {
		CarDao carDao = new CarDaoImpl();
		
		if (!carDao.carExists(car))
			carDao.createCar(car);
		
		return true;
	}
	public static boolean saveCarLotID(Car car) {
		CarDao carDao = new CarDaoImpl();
		
		if (!carDao.carExists(car))
			carDao.createCar(car);
		
		carDao.updateCarLotID(car);
		
		return true;
	}
	public static boolean saveCarOwnerID(Car car) {
		CarDao carDao = new CarDaoImpl();
		
		if (!carDao.carExists(car))
			carDao.createCar(car);
		
		carDao.updateCarOwnerID(car);
		
		return true;
	}
	public static boolean saveCarStatus(Car car) {
		CarDao carDao = new CarDaoImpl();
		
		if (!carDao.carExists(car))
			carDao.createCar(car);
		
		carDao.updateCarStatus(car);

		return true;
	}
	
	public static boolean saveEmployee(Employee e, String p) {
		EmployeeDao eDao = new EmployeeDaoImpl();
		
		if (!eDao.employeeExists(e))
			eDao.createEmployee(e);
		
		return true;
	}
	public static boolean saveEmployeePassword(Employee e, String p) {
			EmployeeDao eDao = new EmployeeDaoImpl();
			
			if (!eDao.employeeExists(e))
				eDao.createEmployee(e);
			
			eDao.updateEmployeePassword(p, e);

			return true;
	}
	public static boolean saveCustomer(Customer c, String p) {
		CustomerDao cDao = new CustomerDaoImpl();
		
		if (!cDao.customerExists(c))
			cDao.createCustomer(c);
		
		return true;
	}
	public static boolean saveCustomerPassword(Customer c, String p) {
			CustomerDao cDao = new CustomerDaoImpl();
			
			if (!cDao.customerExists(c))
				cDao.createCustomer(c);
			
			cDao.updateCustomerPassword(p, c);
		
			return true;
	}
	
	public static boolean saveOffer(Offer o) {
		OfferDao oDao = new OfferDaoImpl();
		
		if (!oDao.offerExists(o))
			oDao.createOffer(o);
		
		return true;
	}
	public static boolean saveOfferAcceptedBy(Offer o) {
		OfferDao oDao = new OfferDaoImpl();
		
		if (!oDao.offerExists(o))
			oDao.createOffer(o);
		
		oDao.updateOfferAcceptedBy(o);
		
		return true;
	}
	public static boolean saveOfferStatus(Offer o) {
		OfferDao oDao = new OfferDaoImpl();
		
		if (!oDao.offerExists(o))
			oDao.createOffer(o);
		
		oDao.updateOfferStatus(o);
	
		return true;
	}
	
	public static boolean savePayment(Payment payment) {
		return true;
	}
	
	public static boolean saveCarLot(CarLot carlot) {
		return true;
		//return Serialization.Serialize(carlot);
	}
}
