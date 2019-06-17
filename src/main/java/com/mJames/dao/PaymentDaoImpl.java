package com.mJames.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mJames.pojo.Payment;
import com.mJames.util.Logging;

public class PaymentDaoImpl implements PaymentDao {

	private Connection conn;
	
	@Override
	public void createPayment(Payment p) {
		try {
			Statement stmt = conn.createStatement();
			
			String sql = "Insert INTO Payment "
					+ " (userid, car_license, ammount, ammount_remaining, term) " 
					+ "VALUES (" 
					+ p.getUserID()+ ", "
					+ p.getCarLicense()+ ", "
					+ p.getAmount()+ ", "
					+ p.getAmountRemaining() + ", "
					+ p.getTerm() + ");";
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updatePaymentAmmount(Payment p) {
		try {
			Statement stmt = conn.createStatement();
			String query = "update Payment set ammount = " + p.getAmount() 
							+ " where userid = " + p.getUserID()
							+ " and car_license = " + p.getCarLicense() + ";";
		
			stmt.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void updatePaymentAmmountRemaining(Payment p) {
		try {
			Statement stmt = conn.createStatement();
			String query = "update Payment set ammount_remaining = " + p.getAmountRemaining() 
							+ " where userid = " + p.getUserID()
							+ " and car_license = " + p.getCarLicense() + ";";
		
			stmt.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deletePayment(Payment o) {
		try {
			Statement stmt = conn.createStatement();
			String query = "delete from Payment "
					+ "where userid = " + o.getUserID()
					+ "and car_license = " + o.getCarLicense() + ";";
			stmt.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	// TODO This won't work.  Need some other primary key, can have multiple payments per users+carLicense
	public Payment getPaymentByUserIDAndLicense(Integer userID, Integer carLicense) {
		try {
			Statement stmt = conn.createStatement();
			
			String sql = "select * from Payment where userid = " + userID
						+ " and car_license = " + carLicense + ";";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next())
			{
				return buildPaymentFromRS(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Logging.errorLog("Nonexistent Payment requested");
		return null;
	}
	@Override
	public List<Payment> getAllPayments() {
		List<Payment> PaymentList = new ArrayList<Payment>();
		
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "select * from Payment";
			ResultSet rs = stmt.executeQuery(sql);
		
			while(rs.next())
				PaymentList.add(buildPaymentFromRS(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return PaymentList;
	}

	private Payment buildPaymentFromRS(ResultSet rs) throws SQLException {
		Integer userID = rs.getInt("lotid"); 
		Integer carLicense = rs.getInt("car_license");
		Double amount = rs.getDouble("value");
		Double amountRemaining = rs.getDouble("amount_remaining");
		Integer term = rs.getInt("term");
		
		return new Payment(userID, carLicense, amount, amountRemaining, term);
	}

}
