package com.mJames.pojo;

public class Payment {
	
	private int userID;
	private int carLicense;
	private double amount;
	private double amountRemaining;
	private int term;
	
	public Payment() {
		super();
	}

	public Payment(int userID, int carLicense, double amount, double amountRemaining, int term) {
		super();
		this.userID = userID;
		this.carLicense = carLicense;
		this.amount = amount;
		this.amountRemaining = amountRemaining;
		this.term = term;
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
