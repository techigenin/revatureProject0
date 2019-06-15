package com.mJames.project1.java.core.driver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.mJames.project1.java.core.Logging;
import com.mJames.project1.java.core.pojo.Car;
import com.mJames.project1.java.core.pojo.CarLot;
import com.mJames.project1.java.core.pojo.Employee;
import com.mJames.project1.java.core.services.CarLotService;
import com.mJames.project1.java.core.ui.IOUtil;

public class Driver{
	private static CarLotService cs; //= new CarLotService();
	
	public static void main(String[] args) {
		
		Logging.infoLog("Application Started");
		
		IOUtil.messageToUser("Welcome to the Carlot.\n");
		
		String fileName = "carLot.ser";
		
		CarLot carLot = new CarLot();

		try
		{
			Logging.infoLog("Opening file");
			// Reading the object from a file 
            FileInputStream file = new FileInputStream(fileName); 
            ObjectInputStream in = new ObjectInputStream(file); 
              
            // Method for de-serialization of object 
            carLot = (CarLot)in.readObject(); 
			
			in.close();
			file.close();
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
			cs = new CarLotService(carLot);
		
		cs.run();
		
		try
		{
			Logging.infoLog("Saving file");
			FileOutputStream file = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(file);
			
			out.writeObject(carLot);
			
			out.close();
			file.close();
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
		cs = new CarLotService(carLot);
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
