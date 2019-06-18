package com.mJames.services;

import com.mJames.dao.PaymentDao;
import com.mJames.dao.PaymentDaoImpl;
import com.mJames.pojo.CarLot;
import com.mJames.pojo.Offer;
import com.mJames.pojo.Payment;

public class PaymentServiceImpl implements PaymentService {

	@Override
	public void makeFirstPayment(CarLot cl, Offer o, double amt) {
		Payment p = new Payment(cl.getNumberPayments() + 1, o.getUserID(), o.getCarLicense(), 
				amt, o.getValue()-amt, true);
		cl.addPayment(p);
	}

	@Override
	public void makePayment(int ownerID, int license) {
		PaymentDao pd = new PaymentDaoImpl();
		pd.createPayment(ownerID, license);
	}
}
