package com.mJames.project1.java.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Employee extends User implements Serializable{
	
	private static final long serialVersionUID = -5213133353068171726L;

	public Employee() {
		super();
	}
	
	public Employee(int userNum, String name, String password, CarLot cLot) {
		super(userNum, name, password, cLot);
	}

	@Override
	public HashMap<Integer, String> getCommandString() {
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
		if (this.getIdNumber() == 0)
			commands.put(-1, "-1. Remove user");
		
		return commands;
	}
	
	public void listUsers()
	{
		carLot.listUsers();
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
		int uNum = -1;
		
		while (!carLot.userExists(uNum) || uNum < 1)
		{	
			System.out.println("Please select a user to remove. (-1 to quit)");
			listUsers();
			uNum = Integer.parseInt(carLot.getResponse("", "[0-9\\-]{1,6}"));
			
			if (uNum == -1)
				return;
			else if (uNum == 0)
				System.out.println("Cannot remove manager");
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
		carLot.printOffers();
		/*
		 * System.out.println("The following offers currently exist.");
		 * 
		 * ArrayList<Integer> offerCars = new
		 * ArrayList<Integer>(carLot.getOffers().keySet());
		 * 
		 * for (Integer i : offerCars) { for (Integer j :
		 * carLot.getOffers().get(i).keySet()) { System.out.printf("%6d%6d%10.2f\n", i,
		 * j, carLot.getOffers().get(i).get(j)); } }
		 */
	}
	public void acceptOffer() 
	{
		int[] nums = offerBusiness();
		
		if (nums[1] != -1)
		{
			// Add the car to the customers inventory
			Customer customer = (Customer) carLot.getUsers().get(nums[1]);
			Car car = carLot.getCars().get(nums[0]);
			customer.addCar(new Car(car));
			// remove the car from the lots inventory
			carLot.removeCar(car);
		}
	}
	public void rejectOffer() {
		int[] nums = offerBusiness();
		
		if (nums[1] != -1)
			carLot.removeSingleOffer(new Offer(carLot.getCars().get(nums[0]), (Customer)carLot.getUsers().get(nums[1])));
	}

	// Returns the car number in int[0] and the customer number in int[1]
	private int[] offerBusiness() {
		int[] nums = new int[2]; // 0 = nums[0], 1 = nums[1] 
		nums[0] = -1;
		nums[1] = -1;
		
		// List offers
		System.out.println("Please select a car");
		getListOfCars(carLot.getCars());
		
		// Accept one of the selections as input
		nums[0] = -1;
		
		while (!carLot.getCars().containsKey(nums[0]) 
				&& !(nums[0] > carLot.getCUSTOMERMAX()))
		{
			nums[0] = Integer.parseInt(carLot.getResponse(
					"", 
					"[0-9]{0,6}"));
		}
		
		// List offers on the car, and select one
		carLot.printCarOffers(nums[0]);
		
		Set<Offer> offerSet = carLot.getCarOffers(nums[0]);
		if(offerSet.size() != 0)
		{
			Set<Integer> cNums = new HashSet<Integer>();
			for (Offer o : offerSet)
			{
				cNums.add(o.getCustomer().getIdNumber());
			}
			nums[1] = -1;
			
			while (!cNums.contains(nums[1]) 
					&& (nums[1] < carLot.getEMPLOYEENUMMAX() 
					|| nums[1] > carLot.getCUSTOMERMAX()))
			{
				nums[1] = Integer.parseInt(
						carLot.getResponse("Please select a customer", "[0-9]{0,6}"));
			}
		}	
		else
			System.out.println("There are no offers on this car.");
		return nums;
	}
}