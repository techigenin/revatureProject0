package com.mJames.project1.java.core.pojo;

import java.io.Serializable;

public class Offer implements Serializable{
	private static final long serialVersionUID = 7260038008843297384L;
	private Car car;
	private Customer customer;
	private double offer;
	private int term;
	
	public Offer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Offer(Car car, Customer customer, double offer, int term) {
		super();
		this.car = car;
		this.customer = customer;
		this.offer = offer;
		this.term = term;
	}
//	public Offer(Car car, Customer customer, double offer) {
//		super();
//		this.car = car;
//		this.customer = customer;
//		this.offer = offer;
//		this.term = 24; // Default term is 24 months
//	}
	public Offer(Car car, Customer cust)
	{
		super();
		this.car = car;
		this.customer = cust;
		this.offer = 0.00;
		this.term = 0;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((car == null) ? 0 : car.hashCode());
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Offer other = (Offer) obj;
		if (car == null) {
			if (other.car != null)
				return false;
		} else if (!car.equals(other.car))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		return true;
	}
	public Car getCar() {
		return car;
	}
	public Customer getCustomer() {
		return customer;
	}
	public double getOffer() {
		return offer;
	}
	public int getTerm() {
		return term;
	}
	public double getPayment(){
		return offer/term;
	}
}
