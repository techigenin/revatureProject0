package com.mJames.pojo;

import java.io.Serializable;

import com.mJames.util.Logging;

public class User extends Logging implements Serializable{

	private static final long serialVersionUID = -6755446065224781856L;
	private int userNum;
	private String firstName;
	private String lastName;
	private String password;
	
	public User()
	{
		super();
	}
	public User(int userNum, String firstName, String lastName, String password) {
		super();
		this.userNum = userNum;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}
	
	public int getUserNum() {
		return userNum;
	}
	public String getFirstName()	{
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean checkPassword(String pWord) {	
		return pWord.contentEquals(password);
	}

}
