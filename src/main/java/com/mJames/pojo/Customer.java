package com.mJames.project1.java.core.pojo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Customer extends User implements Serializable{
	private static final long serialVersionUID = 5399686909346189303L;

	private Set<Car> myCars;
	
	public Customer() {
		super();
		myCars = new HashSet<Car>();
	}
	public Customer(int userNum, String name, String password, CarLot cLot) {
		super(userNum, name, password, cLot);
	}

	@SuppressWarnings("unchecked")
	public Set<Car> getMyCars() {
		if (myCars == null || !(myCars instanceof Set<?>))
			return new HashSet<Car>();
		else 
			return (HashSet<Car>)new HashSet<Car>(myCars).clone();
		}
	
	public Set<Car> addCar(Car c)
	{
		if (myCars == null)
			myCars = new HashSet<Car>();
		
		myCars.add(c);
		return myCars;
	}
}