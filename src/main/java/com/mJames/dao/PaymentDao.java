package com.mJames.dao;

import java.util.List;

import com.mJames.pojo.Payment;

public interface PaymentDao {
	
	public boolean paymentExists(Payment p);
	
	public boolean createPayment(Payment p);
	
	public boolean updatePaymentAmmount(Payment p);
	
	public boolean updatePaymentAmmountRemaining(Payment p);
	
	public boolean deletePayment(Payment p);
	
	public Payment getPaymentByUserIDAndLicense(Integer license, Integer id);
	
	public List<Payment> getAllPayments();

}
