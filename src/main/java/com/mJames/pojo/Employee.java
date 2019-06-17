package com.mJames.pojo;

import java.io.Serializable;

import com.mJames.util.DataUpdate;

public class Employee extends User implements Serializable{
	
	private static final long serialVersionUID = -5213133353068171726L;

	public Employee() {
		super();
	}
	
	public Employee(int userNum, String firstName, String lastName, String password) {
		super(userNum, firstName, lastName, password);
		DataUpdate.saveUser(this);
	}
	
	// TODO - Consider removing Employee and Customer, since they really are just users

}