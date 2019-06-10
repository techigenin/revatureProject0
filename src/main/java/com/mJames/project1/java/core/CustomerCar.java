package com.mJames.project1.java.core;

import java.io.Serializable;
import java.util.Set;

public class CustomerCar extends Car implements Serializable {
	private static final long serialVersionUID = -8445167553611737901L;
	double offer;
	int term;
	
	public CustomerCar(int idNumber, double price, String color, Set<Integer> licences, double offer, int term) {
		super(idNumber, price, color, licences);
		this.offer = offer;
		this.term = term;
	}
	public CustomerCar(Car c) {
		super(c);
	}
	public CustomerCar(Car c, double offer, int term) {
		super(c);
		this.offer = offer;
		this.term = term;
	}
	public CustomerCar(int idNumber, double price, String color, Set<Integer> licences) {
		super(idNumber, price, color, licences);
	}

	public double getOffer() {
		return offer;
	}
	public void setOffer(double offer) {
		this.offer = offer;
	}
	public int getTerm() {
		return term;
	}
	public void setTerm(int term) {
		this.term = term;
	}
	public double getPayment()	{
		return offer/term;
	}
}
