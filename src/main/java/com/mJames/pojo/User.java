package com.mJames.project1.java.core.pojo;

import java.io.Serializable;

import com.mJames.project1.java.core.Logging;

public class User extends Logging implements Serializable{

	private static final long serialVersionUID = -6755446065224781856L;
	private int userNum;
	private String name;
	private String password;
	private CarLot carLot;
	
	public User()
	{
		super();
	}
	public User(CarLot cLot)
	{
		this.carLot = cLot;
	}
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
	public String getName()	{
		return name;
	}
	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setCarLot(CarLot cLot)
	{
		carLot = cLot;
	}
	public CarLot getCarLot()
	{
		return carLot;
	}
	public boolean checkPassword(String pWord) {
		
		return pWord.contentEquals(password);
	}
}
