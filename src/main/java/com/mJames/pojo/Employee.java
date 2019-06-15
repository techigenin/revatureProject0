package com.mJames.project1.java.core.pojo;

import java.io.Serializable;

public class Employee extends User implements Serializable{
	
	private static final long serialVersionUID = -5213133353068171726L;

	public Employee() {
		super();
	}
	
	public Employee(int userNum, String name, String password, CarLot cLot) {
		super(userNum, name, password, cLot);
	}

}