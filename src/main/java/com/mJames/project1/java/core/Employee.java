package com.mJames.project1.java.core;

import java.util.HashMap;

public class Employee extends User {

	public Employee(int userNum, String name, String password, CarLot cLot) {
		super(userNum, name, password, cLot);
		// TODO Auto-generated constructor stub
	}

	@Override
	public HashMap<Integer, String> getListOfCommands() {
		HashMap<Integer, String> commands = new HashMap<Integer, String>();
		
		commands.put(1, "1. Add new customer");
		commands.put(2, "2. Add new employee");
		commands.put(3, "3. Accept offer");
		commands.put(4, "4. Reject offer");
		commands.put(5, "5. Remove car from lot");
		commands.put(6, "6. View all payments");
		commands.put(0, "0. Logout");
		if (this.getUserNum() == 0)
			commands.put(7, "7. Remove user");
		
		return commands;
	}
	
	private void addUser()
	{
		int i = 0;
		
		// TODO - This while(1) nonsense must go.
		while(carLot.userExits(i++)){} // Find first available user number
		
	}
}
