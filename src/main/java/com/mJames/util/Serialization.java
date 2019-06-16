package com.mJames.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.mJames.pojo.CarLot;

public class Serialization {
	
	public static void Serialize(CarLot c) {
		String fileName = "carLot.ser";
		try
		{
			Logging.infoLog("Saving file");
			FileOutputStream file = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(file);
			
			out.writeObject(c);
			
			out.close();
			file.close();
			Logging.infoLog("Saved Successfully");
		}
		catch (IOException e)
		{
			Logging.errorLog("Saving failed");
			e.printStackTrace();
		}
	}
}
