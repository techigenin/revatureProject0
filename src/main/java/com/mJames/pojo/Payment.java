package com.mJames.pojo;

import com.mJames.util.DataUpdate;

public class Payment implements Comparable<Payment> {
	
	private Integer userID;
	private Integer carLicense;
	private Double amount;
	private Double amountRemaining;
	private Integer paymentNumber;
	
	// Amount and amountRemaining change
	
	public Payment() {
		super();
	}

	// Used for DB reconstruction
	public Payment(int paymentNum, int userID, int carLicense, double amount, double amountRemaining) {
		super();
		this.userID = userID;
		this.carLicense = carLicense;
		this.amount = amount;
		this.amountRemaining = amountRemaining;
		this.paymentNumber = paymentNum;
	}
	public Payment(int paymentNum, int userID, int carLicense, double amount, double amountRemaining,  boolean isNew) {
		this(paymentNum, userID, carLicense, amount, amountRemaining);
		
		if (isNew)
			DataUpdate.savePayment(this);
	}
	
	@Override
	public int compareTo(Payment p) {
		if (this.getCarLicense() != p.getCarLicense())
			return this.getCarLicense().compareTo(p.getCarLicense());
		else
			return this.getAmountRemaining().compareTo(p.getAmountRemaining());
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Double getAmountRemaining() {
		return amountRemaining;
	}

	public void setAmountRemaining(double amountRemaining) {
		this.amountRemaining = amountRemaining;
	}

	public Integer getUserID() {
		return userID;
	}

	public Integer getCarLicense() {
		return carLicense;
	}
	
	public Integer getPaymentNumber() {
		return paymentNumber;
	}
}
