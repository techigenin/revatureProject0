package com.mJames.project1.java.core;

public class Driver {
	
	
	private static CarLot carlot;

	public static void main(String[] args) {

		System.out.println("Welcome to the Carlot.");
		System.out.println();
		carlot = new CarLot();
		
		int currentUser = carlot.login();
		System.out.println("Logged in. Current user is : " + currentUser);
		System.out.println("Hello " + carlot.getUsers().get(currentUser).getUserName());
		carlot.printChoices(carlot.getUsers().get(currentUser).getListOfCommands());
	}

	

}
