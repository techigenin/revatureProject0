package com.mJames.dao;

import java.util.List;

import com.mJames.pojo.Payment;

public interface PaymentDao {
	
	public void createPayment(Payment u);
	
	public void updatePaymentAmmount(Payment u);
	
	public void updatePaymentAmmountRemaining(Payment u);
	
	public void deletePayment(Payment u);
	
	public Payment getPaymentByUserIDAndLicense(Integer license, Integer id);
	
	public List<Payment> getAllPayments();

}
