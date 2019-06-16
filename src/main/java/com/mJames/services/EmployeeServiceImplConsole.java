package com.mJames.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.mJames.pojo.Car;
import com.mJames.pojo.CarLot;
import com.mJames.pojo.Customer;
import com.mJames.pojo.Employee;
import com.mJames.pojo.Offer;
import com.mJames.pojo.User;
import com.mJames.ui.IOUtil;
import com.mJames.util.Serialization;

public class EmployeeServiceImplConsole extends UserServiceImplConsole implements EmployeeService {
	@Override
	public HashMap<Integer, String> getCommands(User u) {
		HashMap<Integer, String> commands = new HashMap<Integer, String>();
		
		commands.put(1, "1. View active offers");
		commands.put(2, "2. Accept offer");
		commands.put(3, "3. Reject offer");
		commands.put(4, "4. Add car to lot");
		commands.put(5, "5. Remove car from lot");
		commands.put(6, "6. View payments on car");
		commands.put(7, "7. Add new customer");
		commands.put(8, "8. Add new employee");
		commands.put(9, "9. List users");
		commands.put(10, "10. View cars on lot");
		commands.put(0, "0. Logout");
		if (u.getUserNum() == 0)
			commands.put(-1, "-1. Remove user");
		
		return commands;
	}

	@Override
	public String commandNumString(Employee e)
	{
		String ret = "[";
		Set<Integer> cNums = getCommands(e).keySet();
		
		for (Integer s : cNums)
		{
			ret += s + "";
		}
		
		return ret + "\\-]{0,2}";
	}	
	
	@Override
	public void listUsers(CarLot carLot)
	{
			CarLotService cs = new CarLotServiceImplConsole(carLot);
		cs.listUsers();
	}
	@Override
	public Employee addEmployee(CarLot c)
	{
		CarLotService cs = new CarLotServiceImplConsole(c);
		
		int uNum = 0;
		uNum = cs.firstFree(uNum);
		
		String firstName = IOUtil.getResponse(
				"Please enter the employees first name", 
				"[A-Za-z' ]{0,50}");
		String lastName = IOUtil.getResponse(
				"Please enter the employees lsat name", 
				"[A-Za-z' ]{0,50}");
		String password = IOUtil.getResponse(
				"Please enter a password for the new user (alphanumeric, no spaces)", 
				"[A-Za-z0-9']{0,50}");
		
		Employee newEmployee = new Employee(uNum, firstName, lastName, password);
		
		cs.addUser(newEmployee);
		
		return newEmployee;
	}
	@Override
	public Map<Integer, User> removeUser(CarLot c, Employee e)
	{
		CarLotService cs = new CarLotServiceImplConsole(c);
		
		int uNum = -1;
		
		while (!cs.userExists(uNum) || uNum < 1)
		{	
			IOUtil.messageToUser("Please select a user to remove. (-1 to quit)");
			cs.listUsers();
			uNum = Integer.parseInt(IOUtil.getResponse("", "[0-9\\-]{1,6}"));
			
			if (uNum == -1)
				return c.getUsers();
			else if (uNum == 0)
				IOUtil.messageToUser("Cannot remove manager");
		}
		
		cs.removeUser(e.getUserNum(), uNum);
		
		return c.getUsers();
	}	
	
	@Override
	public void addCar(CarLot c)
	{
		CarLotService cs = new CarLotServiceImplConsole(c);
		
		int cNum = c.getCUSTOMERMAX() + 1;
		cNum = cs.firstFree(cNum);
		
		String color = IOUtil.getResponse("Please enter a color", "[A-Za-z]{0,20}");
		Double price = Double.parseDouble(IOUtil.getResponse("Please enter a price:", "[0-9]{0,5}[.]{1}[0-9]{1,2}|[0-9]{0,5}"));
		String make = IOUtil.getResponse("Please enter the make", "[A-Za-z]{0,20}");
		String model = IOUtil.getResponse("Please enter the model", "[A-Za-z]{0,20}");
		Integer year = Integer.parseInt(IOUtil.getResponse("Please enter a year of release:", "[0-9]{0,5}"));
		
		cs.addCar(new Car(cNum, price, color, make, model, year, "Active", c.getKnownLicenses()));
	}	
	@Override
	public void removeCar(CarLot c)
	{
		CarLotService cs = new CarLotServiceImplConsole(c);
		printListOfCarsWithHeading(c);
		
		boolean good = false;
		
		while(!good)
		{
			String response = IOUtil.getResponse("Please select a car to remove, by ID. Press (q) to quit.", "[0-9]{3,6}|[q]{1,2}");
			
			if (response.equals("q"))
				break;
			else
			{
				int intResp = Integer.parseInt(response);
				
				if (c.getCars().keySet().contains(intResp))
				{
					cs.removeCar(c.getCars().get(intResp));
					System.out.println("Car " + intResp + " removed.");
					good = true;
				}
			}
		}
	}

	@Override
	public void viewActiveOffers(CarLot c) {
		CarLotService cs= new CarLotServiceImplConsole(c);
		cs.printActiveOffers();
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
	@Override
	public void acceptOffer(CarLot c) {
		CarLotService cs = new CarLotServiceImplConsole(c);
		int[] nums = offerBusiness(c);
		
		if (nums[1] != -1)
		{
			// Add the car to the customers inventory
			Customer cust = (Customer) c.getUsers().get(nums[1]);
			Car car = c.getCars().get(nums[0]);
			Set<Offer> offers = c.getOpenOffers(nums[0]);
			
			for (Offer o : offers)
			{
				if (o.getCustomer().getUserNum() == nums[1])
				{
					cust.addCar(new Car(car, o.getOffer(), o.getTerm()));
					c.addClosedOffer(o);
					break;
				}
			}
			
			//TODO Logging
			
			// remove the car from the lots inventory
			cs.removeCar(car);
			Serialization.Serialize(c);
		}
	}
	@Override
	public void rejectOffer(CarLot c) {
		CarLotService cs = new CarLotServiceImplConsole(c);
		int[] nums = offerBusiness(c);
		
		if (nums[1] != -1)
			cs.rejectSingleOffer(new Offer(c.getCars().get(nums[0]), (Customer)c.getUsers().get(nums[1])));
	}
	@Override
	public void viewPayments(CarLot c) {
		Set<Offer> offers = c.getClosedOffers();
		Set<String> offerLic = new HashSet<String>();
		int licNumber;
		
		for (Offer o : offers)
		{
			offerLic.add(o.getCar().getLicenseString());
		}
		
		String resp = "";

		System.out.println("Please select a license number");
		for (Offer o : offers)
		{
			System.out.printf("%8s%8s%8s%8s\n", "License", "Offer", "Term", "Payment");
			System.out.printf("%8s%8.2f%8d%8.2f", 
						o.getCar().getLicenseString(), 
						o.getOffer(), 
						o.getTerm(), 
						o.getPayment());
		}
		while(!offerLic.contains(resp))
		{
			resp = IOUtil.getResponse("", "[A-F0-9\\s]{6,8}");
		}
		
		resp = resp.split(" ")[0] + resp.split(" ")[1];
		
		licNumber = Integer.parseInt(resp, 16);
		
		System.out.println("Payments on the vehicle are: ");
		for (Offer o : c.getClosedOffers())
		{
			if (o.getCar().getLicenseNumber() == licNumber)
			{
				System.out.printf("%8s%8s%8s%8s\n", "License", "Offer", "Term", "Payment");
				System.out.printf("%8s%8.2f%8d%8.2f\n", 
							o.getCar().getLicenseString(), 
							o.getOffer(), 
							o.getTerm(), 
							o.getPayment());
			}
		}
	}
	
	// Returns the car number in int[0] and the customer number in int[1]
	// TODO fix this mess. I hate how this works currently.
	private int[] offerBusiness(CarLot c) {
		CarLotService cs = new CarLotServiceImplConsole(c);
		int[] nums = new int[2]; // 0 = nums[0], 1 = nums[1] 
		nums[0] = -1;
		nums[1] = -1;
		
		// List offers
		System.out.println("Please select a car");
		getListOfCars(c);
		
		// Accept one of the selections as input
		nums[0] = -1;
		
		while (!c.getCars().containsKey(nums[0]) 
				&& !(nums[0] > c.getCUSTOMERMAX()))
		{
			nums[0] = Integer.parseInt(IOUtil.getResponse("","[0-9]{0,6}"));
		}
		
		// List offers on the car, and select one
		cs.printCarOffers(nums[0]);
		
		Set<Offer> offerSet = c.getOpenOffers(nums[0]);
		if(offerSet.size() != 0)
		{
			Set<Integer> cNums = new HashSet<Integer>();
			for (Offer o : offerSet)
			{
				cNums.add(o.getCustomer().getUserNum());
			}
			nums[1] = -1;
			
			while (!cNums.contains(nums[1]) 
					&& (nums[1] < c.getEMPLOYEENUMMAX() 
					|| nums[1] > c.getCUSTOMERMAX()))
			{
				nums[1] = Integer.parseInt(
						IOUtil.getResponse("Please select a customer", "[0-9]{0,6}"));
			}
		}	
		else
			System.out.println("There are no offers on this car.");
		return nums;
	}
}
