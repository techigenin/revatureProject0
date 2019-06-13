package com.mJames.project1.java.core.pojo;

import java.io.Serializable;

public class _CustomerCar extends Car implements Serializable {
	public _CustomerCar(Car c) {
		super(c);
		// TODO Auto-generated constructor stub
	}
	private static final long serialVersionUID = -8445167553611737901L;
	double sellPrice;
	int term;
	
//	public CustomerCar(int idNumber, double price, String color, Set<Integer> licences, double offer, int term) {
//		super(idNumber, price, color, licences);
//		this.sellPrice = offer;
//		this.term = term;
//	}
//	public CustomerCar(Car c) {
//		super(c);
//	}
//	public CustomerCar(Car c, double offer, int term) {
//		super(c);
//		this.sellPrice = offer;
//		this.term = term;
//	}
//	public CustomerCar(int idNumber, double price, String color, Set<Integer> licences) {
//		super(idNumber, price, color, licences);
//	}

	public double getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(double offer) {
		this.sellPrice = offer;
	}
	public int getTerm() {
		return term;
	}
	public void setTerm(int term) {
		this.term = term;
	}
	public double getPayment()	{
		return sellPrice/term;
	}
}
