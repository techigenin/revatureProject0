package com.mJames.pojo;

import java.io.Serializable;

import com.mJames.util.DataUpdate;

public class Customer extends User implements Serializable{
	private static final long serialVersionUID = 5399686909346189303L;

	public Customer() {
		super();
	}
	// Used for DB reconstruction
	public Customer(int userNum, String firstName, String lastName, String password) {
		super(userNum, firstName, lastName, password);
	}
	public Customer(int userNum, String firstName, String lastName, String password, boolean isNew) {
		this(userNum, firstName, lastName, password);
		
		if (isNew)
			DataUpdate.saveCustomerPassword(this, password);
	}
}