package com.mJames.pojo;

import java.io.Serializable;

import com.mJames.util.DataUpdate;
import com.mJames.util.Logging;

public class Offer implements Serializable{
	private static final long serialVersionUID = 7260038008843297384L;
	private Integer carLicense;
	private Integer userID;
	private Double value;
	private Integer term;
	private Integer acceptedBy;
	private String status;
	
	// accepedBy and status change
	
	public Offer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Offer(Integer license, Integer customerid, Double value, Integer term, String status, Integer acceptedBy) {
		super();
		this.setCarLicense(license);
		this.userID = customerid;
		this.value = value;
		this.term = term;
		setStatus(status);
		this.acceptedBy = acceptedBy;
	}
	// Used for DB reconstruction
	public Offer(Integer carLicense, Integer userNum, Double value, Integer months, String status) {
		this(carLicense, userNum, value, months, status, null);
	}	
	public Offer(Integer carLicense, Integer userNum, Double value, Integer months, String status, boolean isNew) {
		super();
		this.setCarLicense(carLicense);
		this.userID = userNum;
		this.value = value;
		this.term = months;
		this.status = status;
		this.acceptedBy = null;
		
		DataUpdate.saveOffer(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userID == null) ? 0 : userID.hashCode());
		result = prime * result + ((carLicense == null) ? 0 : carLicense.hashCode());
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
		if (userID == null) {
			if (other.userID != null)
				return false;
		} else if (!userID.equals(other.userID))
			return false;
		if (carLicense == null) {
			if (other.carLicense != null)
				return false;
		} else if (!carLicense.equals(other.carLicense))
			return false;
		return true;
	}

	public Integer getCarLicense() {
		return carLicense;
	}
	public void setCarLicense(Integer license) {
		this.carLicense = license;
	}
	public Integer getUserID() {
		return userID;
	}
	public double getValue() {
		return value;
	}
	private void setValue(double oVal) {
		value = oVal;
	}
	public boolean updateOffer(double oVal) {
		if (oVal > value)
		{
			setValue(oVal);
			return true;
		}
		else return false;
	}
	public int getTerm() {
		return term;
	}
	public double getPayment(){
		return value/term;
	}
	public Integer getAcceptedBy() {
		return acceptedBy;
	}
	public void setAcceptedBy(Integer acceptedBy) {
		this.acceptedBy = acceptedBy;
		DataUpdate.saveOfferAcceptedBy(this);
	}

	public String getStatus() {
		return status;
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
		DataUpdate.saveOfferStatus(this);
	}
	public void setStatusActive() {
		setStatus("Active");
		Logging.infoLog("Status of offer by " + userID + " for car " + carLicense + " is  set to Active");
	}	
	public void setStatusRejected() {
		setStatus("Rejected");
		Logging.infoLog("Status of offer by " + userID + " for car " + carLicense + " is  set to Rejected");
	}		
	public void setStatusAccepted() {
		setStatus("Accepted");
		Logging.infoLog("Status of offer by " + userID + " for car " + carLicense + " is  set to Accepted");
	}	
	public void setStatusPending() {
		setStatus("Pending");
		Logging.infoLog("Status of offer by " + userID + " for car " + carLicense + " is  set to Pending");
	}
}
