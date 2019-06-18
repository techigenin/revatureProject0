package com.mJames.pojo;

import com.mJames.util.DataUpdate;

public class Payment {
	
	private Integer userID;
	private Integer carLicense;
	private Double amount;
	private Double amountRemaining;
	private Integer term;
	
	// Amount and ammountRemaining change
	
	public Payment() {
		super();
	}

	// Used for DB reconstruction
	public Payment(int userID, int carLicense, double amount, double amountRemaining, int term) {
		super();
		this.userID = userID;
		this.carLicense = carLicense;
		this.amount = amount;
		this.amountRemaining = amountRemaining;
		this.term = term;
	}
	public Payment(int userID, int carLicense, double amount, double amountRemaining, int term, boolean isNew) {
		this(userID, carLicense, amount, amountRemaining, term);
		
		if (isNew)
			DataUpdate.savePayment(this);
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getAmountRemaining() {
		return amountRemaining;
	}

	public void setAmountRemaining(double amountRemaining) {
		this.amountRemaining = amountRemaining;
	}

	public int getUserID() {
		return userID;
	}

	public int getCarLicense() {
		return carLicense;
	}

	public int getTerm() {
		return term;
	}

	
}
