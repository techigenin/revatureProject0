package com.mJames.project1.java.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Employee extends User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5213133353068171726L;

	public Employee() {
		super();
	}
	
	public Employee(int userNum, String name, String password, CarLot cLot) {
		super(userNum, name, password, cLot);
	}

	@Override
	public HashMap<Integer, String> getCommands() {
		HashMap<Integer, String> commands = new HashMap<Integer, String>();
		
		commands.put(1, "1. View Offers");
		commands.put(2, "2. Accept offer");
		commands.put(3, "3. Reject offer");
		commands.put(4, "4. Add car to lot");
		commands.put(5, "5. Remove car from lot");
		commands.put(6, "6. View all payments");
		commands.put(7, "7. Add new customer");
		commands.put(8, "8. Add new employee");
		commands.put(9, "9. List Users");
		commands.put(10, "10. View cars on lot");
		commands.put(0, "0. Logout");
		if (this.getUserNum() == 0)
			commands.put(-1, "-1. Remove user");
		
		return commands;
	}
	
	public void listUsers()
	{
		for (User u : carLot.getUsers().values())
		{
			if(u.getUserNum() > 0) // Doesn't list manager
				System.out.printf("%d.\t%s\n", u.getUserNum(), u.getUserName());
		}
	}
	public void addEmployee()
	{
		int uNum = 0;
		uNum = carLot.firstFree(uNum);
		
		String name = carLot.getResponse("Please enter the users name", "[A-Za-z ]{0,50}");
		String password = carLot.getResponse("Please enter a password for the new user (alphanumeric, no spaces)", "[A-Za-z0-9]{0,50}");
		
		carLot.addUser(userNum, new Employee(uNum, name, password, carLot));
	}

	public void removeUser()
	{
		listUsers();
		int uNum = -1;
		
		while (!carLot.userExists(uNum) || uNum < 1)
		{	
			System.out.println("Please select a user to remove.");
			listUsers();
			uNum = Integer.parseInt(carLot.getResponse("", "[0-9]{1,6}"));
		}
		
		carLot.removeUser(userNum, uNum);
	}	
	
	public void addCar()
	{
		int cNum = carLot.getCUSTOMERMAX() + 1;
		cNum = carLot.firstFree(cNum);
		
		String color = carLot.getResponse("Please enter a color", "[A-Za-z]{0,20}");
		Double price = Double.parseDouble(carLot.getResponse("Please enter a price:", "[0-9]{0,5}[.]{1}[0-9]{1,2}|[0-9]{0,5}"));
		
		carLot.getCars().put(cNum, new Car(cNum, price, color, carLot.getKnownLicenses()));
	}	

	public void removeCar()
	{
		printListOfCars(carLot.getCars());
		
		boolean good = false;
		
		while(!good)
		{
			String response = carLot.getResponse("Please select a car to remove, by ID. Press (q) to quit.", "[0-9]{3,6}|[q]{1,2}");
			
			if (response.equals("q"))
				break;
			else
			{
				int intResp = Integer.parseInt(response);
				
				if (carLot.getCars().keySet().contains(intResp))
				{
					carLot.removeCar(carLot.getCars().remove(intResp));
					System.out.println("Car " + intResp + " removed.");
					good = true;
				}
			}
		}
	}

	public void viewOffers() {
		System.out.println("The following offers currently exist.");
		
		ArrayList<Integer> offerCars = new ArrayList<Integer>(carLot.getOffers().keySet());
		
		for (Integer i : offerCars)
		{
			for (Integer j : carLot.getOffers().get(i).keySet())
			{
				System.out.printf("%6d%6d%10.2f\n", i, j, carLot.getOffers().get(i).get(j));
			}
		}
	}
	

}
