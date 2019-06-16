package com.mJames.driver;

import java.sql.Connection;
import java.sql.SQLException;

import com.mJames.util.ConnectionFactory;

public class DriverDB {

	public static void main(String[] args) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try (Connection conn = ConnectionFactory.getConnection()){
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("done");
	}
}
