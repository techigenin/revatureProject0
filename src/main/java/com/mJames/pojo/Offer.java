package com.mJames.pojo;

import java.io.Serializable;

public class Offer implements Serializable{
	private static final long serialVersionUID = 7260038008843297384L;
	private Integer license;
	private Customer customer;
	private double offer;
	private int term;
	private int acceptedBy;
	private String status;
	
	public Offer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Offer(Integer license, Customer customer, double offer, int term, String status) {
		super();
		this.setLicense(license);
		this.customer = customer;
		this.offer = offer;
		this.term = term;
		this.setStatus(status);
	}
	public Offer(Integer license, Customer cust)
	{
		super();
		this.setLicense(license);
		this.customer = cust;
		this.offer = 0.00;
		this.term = 0;
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
	public int getAcceptedBy() {
		return acceptedBy;
	}
	public void setAcceptedBy(int acceptedBy) {
		this.acceptedBy = acceptedBy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getLicense() {
		return license;
	}
	public void setLicense(Integer license) {
		this.license = license;
	}
}
