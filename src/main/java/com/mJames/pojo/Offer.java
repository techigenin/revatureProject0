package com.mJames.pojo;

import java.io.Serializable;

import com.mJames.util.DataUpdate;
import com.mJames.util.Logging;

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
		setStatus(status);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerid == null) ? 0 : customerid.hashCode());
		result = prime * result + ((license == null) ? 0 : license.hashCode());
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
		if (customerid == null) {
			if (other.customerid != null)
				return false;
		} else if (!customerid.equals(other.customerid))
			return false;
		if (license == null) {
			if (other.license != null)
				return false;
		} else if (!license.equals(other.license))
			return false;
		return true;
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
	private void setOffer(double oVal) {
		offer = oVal;
		DataUpdate.saveOffer(this);
	}
	public boolean updateOffer(double oVal) {
		if (oVal > offer)
		{
			setOffer(oVal);
			return true;
		}
		else return false;
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

	public boolean statusActive() {
		return status.equals("Active");
	}
	public boolean statusRejected() {
		return status.equals("Rejected");
	}
	public boolean statusAccepted() {
		return status.equals("Accepted");
	}
	public boolean statusPending() {
		return status.equals("Pending");
	}
	private void setStatus(String status) {
		this.status = status;
		DataUpdate.saveOffer(this);
	}
	public void setStatusActive() {
		setStatus("Active");
		Logging.infoLog("Status of offer by " + customerid + " for car " + license + " is  set to Active");
	}	
	public void setStatusRejected() {
		setStatus("Rejected");
		Logging.infoLog("Status of offer by " + customerid + " for car " + license + " is  set to Rejected");
	}		
	public void setStatusAccepted() {
		setStatus("Accepted");
		Logging.infoLog("Status of offer by " + customerid + " for car " + license + " is  set to Accepted");
	}	
	public void setStatusPending() {
		setStatus("Pending");
		Logging.infoLog("Status of offer by " + customerid + " for car " + license + " is  set to Pending");
	}
}
