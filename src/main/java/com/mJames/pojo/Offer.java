package com.mJames.pojo;

import java.io.Serializable;

public class Offer implements Serializable{
	private static final long serialVersionUID = 7260038008843297384L;
	private Integer license;
	private Integer customerid;
	private double offer;
	private int term;
	private Integer acceptedBy;
	private String status;
	
	public Offer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Offer(Integer license, Integer customerid, double offer, int term, String status) {
		super();
		this.setLicense(license);
		this.customerid = customerid;
		this.offer = offer;
		this.term = term;
		this.status = status;
	}
	public Offer(Integer license, Integer customerid, double offer, int term, int acceptedBy, String status) {
		super();
		this.license = license;
		this.customerid = customerid;
		this.offer = offer;
		this.term = term;
		this.acceptedBy = acceptedBy;
		this.status = status;
	}
	public Offer(Integer license, Integer cust)
	{
		super();
		this.setLicense(license);
		this.customerid = cust;
		this.offer = 0.00;
		this.term = 0;
	}
	
	public Integer getLicense() {
		return license;
	}
	public void setLicense(Integer license) {
		this.license = license;
	}
	public Integer getCustomerId() {
		return customerid;
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
	public Integer getAcceptedBy() {
		return acceptedBy;
	}
	public void setAcceptedBy(Integer acceptedBy) {
		this.acceptedBy = acceptedBy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
