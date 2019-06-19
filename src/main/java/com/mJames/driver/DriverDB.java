package com.mJames.driver;

import java.sql.Connection;
import java.sql.SQLException;

import com.mJames.pojo.CarLot;
import com.mJames.services.CarLotService;
import com.mJames.services.CarLotServiceImplConsole;
import com.mJames.ui.IOUtil;
import com.mJames.util.CheckCreateTables;
import com.mJames.util.ConnectionFactory;
import com.mJames.util.Logging;

public class DriverDB {

	public static void main(String[] args) {
		Logging.infoLog("Application Started");
		IOUtil.messageToUser("Welcome to CRAZY DENNY'S AUTO EMPORIUM.\n");
		
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
			carlot.rebuildFromDB();
			CarLotService cs = new CarLotServiceImplConsole(carlot);
				
			while (true) {
				if (cs.run() == 0)
					break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
