package com.mJames.project1.java.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CarLot {
	public final int EMPLOYEENUMMAX = 99;
	public final int USERMAX = 999;
	
	private List<Car> cars;
	private Map<Integer, User> userMap = new HashMap<Integer, User>();
	private Scanner sc;
	
	public CarLot() 
	{
		super();
		sc = new Scanner(System.in);
		userMap.put(0, new Employee(0, "Jean Luc", "makeItSo", this));
	}
	public CarLot(List<Car> cars, Map<Integer, User> users, Scanner sc) {
		super();
		this.cars = cars;
		this.userMap = users;
		this.sc = sc;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + EMPLOYEENUMMAX;
		result = prime * result + ((cars == null) ? 0 : cars.hashCode());
		result = prime * result + ((userMap == null) ? 0 : userMap.hashCode());
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
		CarLot other = (CarLot) obj;
		if (EMPLOYEENUMMAX != other.EMPLOYEENUMMAX)
			return false;
		if (cars == null) {
			if (other.cars != null)
				return false;
		} else if (!cars.equals(other.cars))
			return false;
		if (userMap == null) {
			if (other.userMap != null)
				return false;
		} else if (!userMap.equals(other.userMap))
			return false;
		return true;
	}
	public List<Car> getCars() {
		return cars;
	}
	public Map<Integer, User> getUsers() {
		return userMap;
	}
	
	public int registerNewUser(int userNum)
	{
		return 0;
	}
	
	public int login() {
		boolean userGood = false;
		boolean passGood = false;
		int userNum = -1;
		String password;
		
		while(!passGood)
		{	
			while (!userGood)
			{
				userNum = new Integer(Integer.parseInt(getResponse("Please enter your user ID (0-99999)", "[0-9]{1,6}")));
				
				if (userMap.keySet().contains(userNum))
					userGood = true;
				else
					System.out.println("User ID unknown, please re-enter");
			}
			
			User user = userMap.get(userNum);
		
			password = getResponse("Please enter your password (Alphanumeric, < 50 characters", "[A-Z0-9a-z]{1,50}");
			
			if (user.checkPassword(password))
				passGood = true;
			else
			{
				System.out.println("Incorrect password. Please login again.");
				userGood = false;
			}
		}
		
		return userNum;
	}

	public void printChoices(HashMap<Integer, String> map)
	{
		for(String s : map.values())
		{
			System.out.println(s);
		}
	}
	private String getResponse(String message, String acceptable) {
		System.out.println(message);

		String string = "";
		boolean good = false;
		
		while(!good)
		{
			string = new String(sc.nextLine());
			
			if (!string.matches(acceptable))
			{
				System.out.println("Response must be in the given range.");
			}
			else
				good = true;
		}
		
		return string;
	}
	
	public boolean userExits(int u)
	{
		return (userMap.containsKey(u));
	}

}
