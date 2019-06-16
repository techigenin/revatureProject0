package com.mJames.pojo;

import java.io.Serializable;

public class Customer extends User implements Serializable{
	private static final long serialVersionUID = 5399686909346189303L;

	public Customer() {
		super();
	}
	public Customer(int userNum, String firstName, String lastName, String password) {
		super(userNum, firstName, lastName, password);
	}
}