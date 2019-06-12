package com.mJames.project1.java.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Driver{
	public static void main(String[] args) {

		Logging.infoLog("Application Started");
		
		System.out.println("Welcome to the Carlot.");
		System.out.println();
		
		String fileName = "carLot.ser";
		
		CarLot carLot = new CarLot();

		try
		{
			Logging.infoLog("Opening file");
			// Reading the object from a file 
            FileInputStream file = new FileInputStream(fileName); 
            ObjectInputStream in = new ObjectInputStream(file); 
              
            // Method for deserialization of object 
            carLot = (CarLot)in.readObject(); 
			
			in.close();
			file.close();
			Logging.infoLog("Opened Successfully");
		}
		catch (IOException e){
			Logging.warnLog("Open failed, creating new file");
			//e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			System.out.println(e.getStackTrace());
			e.printStackTrace();
		}
		
		carLot.run();
		
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
}
