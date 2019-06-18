package com.mJames.dao;

import java.util.List;

import com.mJames.pojo.Payment;

public interface PaymentDao {
	
	public boolean paymentExists(Payment p);
	
	public boolean createPayment(Payment p);
	
	public boolean updatePaymentAmount(Payment p);
	
	public boolean updatePaymentAmountRemaining(Payment p);
	
	public boolean deletePayment(Payment p);
	
	public List<Payment> getAllPayments();

	Payment getPaymentbyPaymentNumber(Integer paymentNum);

	boolean createPayment(Integer custNum, Integer license);

}
