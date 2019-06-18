package com.mJames.services;

import com.mJames.pojo.CarLot;
import com.mJames.pojo.Offer;

public interface PaymentService {

	void makeFirstPayment(CarLot cl, Offer o, double amt);
	
	void makePayment(int ownerID, int license, CarLot cl);
}
