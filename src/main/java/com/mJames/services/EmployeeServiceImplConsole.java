package com.mJames.services;

import java.util.ArrayList;
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
import com.mJames.util.Logging;

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
//		if (u.getUserNum() == 0)
//			commands.put(-1, "-1. Remove user");
//		
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
				"Please enter the employees last name", 
				"[A-Za-z' ]{0,50}");
		String password = IOUtil.getResponse(
				"Please enter a password for the new user (alphanumeric, no spaces)", 
				"[A-Za-z0-9']{0,50}");
		
		Employee newEmployee = new Employee(uNum, firstName, lastName, password, true);
		
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
		String make = IOUtil.getResponse("Please enter the make", "[A-Za-z]{0,20}");
		String model = IOUtil.getResponse("Please enter the model", "[A-Za-z0-9]{0,20}");
		Double price = Double.parseDouble(IOUtil.getResponse("Please enter a price:", "[0-9]{0,5}[.]{1}[0-9]{1,2}|[0-9]{0,5}"));
		
		cs.addCar(new Car(cNum, price, color, make, model, null, "Active", c.getKnownLicenses()));
	}	
	@Override
	public void removeCar(CarLot c)
	{
		CarLotService cs = new CarLotServiceImplConsole(c);
		cs.printActiveCars();
		
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
	public void acceptOffer(CarLot c, Employee e) {
		CarLotService cs = new CarLotServiceImplConsole(c);
		int[] nums = offerBusiness(c);
		
		int carNum = nums[0];
		int custNum = nums[1];
		
		if (custNum != -1)
		{
			// Add the car to the customers inventory
			Customer cust = (Customer) c.getUsers().get(custNum);
			Car car = c.getCars().get(carNum);
			Set<Offer> offers = c.getOffers();
			
			for (Offer o : offers)
			{
				if (o.getUserID() == custNum)
				{
					cs.removeCar(car);
					car.setOwnerID(cust.getUserNum());
					car.setStatusSold();
					o.setStatusAccepted();
					
					cs.acceptOffer(o);
					o.setAcceptedBy(e.getUserNum());
					break;
				}
			}
			
			Logging.infoLog("Car " + car.getLicenseString() + " has been sold to user number " + cust.getUserNum());
		}
	}
	@Override
	public void rejectOffer(CarLot c) {
		int[] nums = offerBusiness(c);
		if (nums[0] == -1)
			return;
		int carNum = nums[0];
		int custNum = nums[1];
		Car car = c.getCars().get(carNum);
		
		for (Offer o : c.getOffers())
		{
			if (o.getUserID() == custNum && o.getCarLicense() == car.getLicenseNumber())
			{
				o.setStatusRejected();
				return;	
			}
		}
	}
	@Override
	public void viewPayments(CarLot c) {
		// TODO This method.  After adding the ability for users to make payments
	}

	private int[] offerBusiness(CarLot c) {
		CarLotService cs = new CarLotServiceImplConsole(c);
		 // Customer
		int carNum = -1;
		int custNum = -1;
		
		HashMap<Integer, ArrayList<Integer>> carsAndCusts = new HashMap<Integer, ArrayList<Integer>>();
		for (Offer o : c.getOffers())
		{
			if (o.statusActive())
			{
				Integer lotID = c.getLotID(o.getCarLicense());
				
				if (!carsAndCusts.containsKey(lotID))
					carsAndCusts.put(lotID, new ArrayList<Integer>());
				
				carsAndCusts.get(lotID).add(o.getUserID());
			}
		}
		
		// List offers
		if(cs.printActiveOffers())
		{
			System.out.println("Please select a car");
			
			// Accept one of the selections as input
			while (!carsAndCusts.containsKey(carNum))
			{
				carNum = Integer.parseInt(IOUtil.getResponse("","[0-9]{0,6}"));
			}
			
			// List offers on the car, and select one
			//cs.printCarOffers(carNum);
			
			OfferService os = new OfferServiceImplConsole();
			
			Set<Offer> activeOffers = os.getActiveOffers(c.getOffers());
			if(activeOffers.size() != 0)
			{
				Set<Integer> cNums = new HashSet<Integer>();
				for (Offer o : activeOffers)
				{
					cNums.add(o.getUserID());
				}
				
				IOUtil.messageToUser("Please select a customer");
				
				while (!carsAndCusts.get(carNum).contains(custNum))
				{
					custNum = Integer.parseInt(
							IOUtil.getResponse("", "[0-9]{0,6}"));
				}
			}	
			else
				System.out.println("There are no offers on this car.");
		}	
		int[] nums = new int[2]; // 0 = carNum, 1 = custNum 
		nums[0] = carNum; // Car
		nums[1] = custNum; // Cust
		
		return nums;
	}
}
