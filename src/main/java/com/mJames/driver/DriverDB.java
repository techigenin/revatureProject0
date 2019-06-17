package com.mJames.driver;

import java.sql.Connection;
import java.sql.SQLException;

import com.mJames.pojo.CarLot;
import com.mJames.ui.IOUtil;
import com.mJames.util.ConnectionFactory;
import com.mJames.util.Logging;

public class DriverDB {

	public static void main(String[] args) {
		Logging.infoLog("Application Started");
		IOUtil.messageToUser("Welcome to the Carlot.\n");
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try (Connection conn = ConnectionFactory.getConnection()){
			CheckCreateTables.checkCarTable(conn);
			CheckCreateTables.checkCustomerTable(conn);
			CheckCreateTables.checkEmployeeTable(conn);
			CheckCreateTables.checkOfferTable(conn);
			CheckCreateTables.checkPaymentTable(conn);
			
			CarLot carlot = new CarLot();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
