package com.mJames.driver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.mJames.pojo.Car;
import com.mJames.pojo.CarLot;
import com.mJames.pojo.Employee;
import com.mJames.services.CarLotService;
import com.mJames.services.CarLotServiceImplConsole;
import com.mJames.ui.IOUtil;
import com.mJames.util.Logging;

public class DriverSerializable{
	private static CarLotService cs; //= new CarLotService();
	
	public static void main(String[] args) {
		
		Logging.infoLog("Application Started");
		
		IOUtil.messageToUser("Welcome to the Carlot.\n");
		
		String fileName = "carLot.ser";
		
		CarLot carLot = new CarLot();

		try (FileInputStream file = new FileInputStream(fileName))
		{
			Logging.infoLog("Opening file");
			// Reading the object from a file 
            ObjectInputStream in = new ObjectInputStream(file); 
              
            // Method for de-serialization of object 
            carLot = (CarLot)in.readObject(); 
			
			in.close();
			Logging.infoLog("Opened Successfully");
		}
		catch (IOException e){
			Logging.warnLog("Open failed, creating new file");
			
			carLot = newCarLot();
			//e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			Logging.errorLog(e.getStackTrace().toString());
			e.printStackTrace();
		}
		
		if (cs == null)
			cs = new CarLotServiceImplConsole(carLot);
		
		cs.run();
		
		try(FileOutputStream file = new FileOutputStream(fileName))
		{
			Logging.infoLog("Saving file");
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(carLot);
			
			out.close();
			Logging.infoLog("Saved Successfully");
		}
		catch (IOException e)
		{
			Logging.errorLog("Saving failed");
			e.printStackTrace();
		}
		
		Logging.infoLog("Application Ended");
	}

	private static CarLot newCarLot() {
		CarLot carLot;
		carLot = new CarLot();
		cs = new CarLotServiceImplConsole(carLot);
		// Add the super user
		carLot.addUser(new Employee(0, "Jean Luc", "Picard", carLot));

		String[] colors = {"green", "blue", "red", "yellow", "orange"};
		
		// Add a couple starter cars
		for (int i = 0; i < 5; i++)
		{
			int cNum = cs.firstFree(carLot.getCUSTOMERMAX() + 1);
			Car newCar = new Car(cNum, 2000, colors[i], carLot.getKnownLicenses());
			carLot.getCars().put(cNum, newCar);
			carLot.getKnownLicenses().add(newCar.getLicenseNumber());
		}
		return carLot;
	}
}
