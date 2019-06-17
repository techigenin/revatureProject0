package com.mJames.util;

import com.mJames.pojo.Car;
import com.mJames.pojo.CarLot;
import com.mJames.pojo.Offer;
import com.mJames.pojo.Payment;
import com.mJames.pojo.User;

public interface DataUpdate {

	public static boolean saveCar(Car car) {
		return true;
	}
	
	public static boolean saveUser(User user) {
		return true;
	}
	
	public static boolean saveOffer(Offer offer) {
		return true;
	}
	
	public static boolean savePayment(Payment payment) {
		return true;
	}
	
	public static boolean saveCarLot(CarLot carlot) {
		return Serialization.Serialize(carlot);
	}
}
