package com.mJames.project1.java.core;

import java.util.HashMap;

public abstract class User {
	protected int userNum;
	protected String name;
	protected String password;
	protected CarLot carLot;
	
	public User(int userNum, String name, String password, CarLot  cLot) {
		super();
		this.userNum = userNum;
		this.name = name;
		this.password = password;
		this.carLot = cLot;
	}

	public int getUserNum() {
		return userNum;
	}
	public String getUserName()	{
		return name;
	}
	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + userNum;
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
		User other = (User) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userNum != other.userNum)
			return false;
		return true;
	}
	
	/*
	 * Checks what kind of user the given user is.
	 * 0 -> EMPLOYEENUMAX = employee (1)
	 * EMPLOYEENUMMAX + 1 -> USERMAX = customer (2)
	 * otherwise error (0)
	 */
	public int userType(int uNum)
	{
		if (uNum > -1 && uNum <= carLot.EMPLOYEENUMMAX)
			return 1;	
		else if (uNum > carLot.EMPLOYEENUMMAX && uNum <= carLot.USERMAX)
			return 2;
		else
			return 0;
	}

	public boolean checkPassword(String pWord) 
	{
		return password.equals(pWord);	
	}
	
	public abstract HashMap<Integer, String> getListOfCommands();
}
