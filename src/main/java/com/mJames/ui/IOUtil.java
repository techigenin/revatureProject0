package com.mJames.project1.java.core.ui;

import java.util.Scanner;

public class IOUtil {
	private static final Scanner sc = new Scanner(System.in);
	
	public static String getResponse(String message, String acceptable) {
		messageToUser(message);
		return getResponse(acceptable);
	}

	private static String getResponse(String acceptable) {
		String string = "";
		boolean good = false;
		
		while(!good)
		{
			string = new String(sc.nextLine());
			
			if (!string.matches(acceptable))
			{
				messageToUser("Response must be in the given range.");
			}
			else if (string == "")
			{
				continue;
			}
			else
				good = true;
		}
		
		return string;
	}

	public static void messageToUser(String message) {
		System.out.println(message);
	}
	
	public static void messageToUser(String message, Object... format) {
		System.out.printf(message, format);
	}
	
}
