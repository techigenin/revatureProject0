package com.mJames.project1.java.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Driver{
	
	
	
	public static void main(String[] args) {

		System.out.println("Welcome to the Carlot.");
		System.out.println();
		
		String fileName = "carLot.ser";
		
		CarLot carLot = new CarLot();

		try
		{
			// Reading the object from a file 
            FileInputStream file = new FileInputStream(fileName); 
            ObjectInputStream in = new ObjectInputStream(file); 
              
            // Method for deserialization of object 
            carLot = (CarLot)in.readObject(); 
			
			in.close();
			file.close();
		}
		catch (IOException e){
			System.out.println("Creating new file");
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
			FileOutputStream file = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(file);
			
			out.writeObject(carLot);
			
			out.close();
			file.close();
		}
		catch (IOException e)
		{
			System.out.println("IOException occured");
			e.printStackTrace();
		}
	}
}
