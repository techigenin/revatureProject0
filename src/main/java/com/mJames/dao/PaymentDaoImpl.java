package com.mJames.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mJames.pojo.Payment;
import com.mJames.services.PaymentService;
import com.mJames.util.ConnectionFactory;
import com.mJames.util.Logging;

public class PaymentDaoImpl implements PaymentDao {

	private Connection conn;
	
	// TODO needs PreparedStatement updating
	
	@Override
	public boolean paymentExists(Payment p) {
		try {
			conn = ConnectionFactory.getConnection();
			Statement stmt = conn.createStatement();
			
			String sql = "select * from payment where payment_num = " 
					+ p.getPaymentNumber() + ";";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next())
				return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean createPayment(Payment p) {
		try {
			conn = ConnectionFactory.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("Insert INTO payment "
					+ "(userid, car_license, amount, amount_remaining, payment_num) " 
					+ "VALUES (?, ?, ?, ?, ?);");
			 
			pstmt.setInt(1, p.getUserID());
			pstmt.setInt(2, p.getCarLicense());
			pstmt.setDouble(3, p.getAmount());
			pstmt.setDouble(4, p.getAmountRemaining());
			pstmt.setInt(5, p.getPaymentNumber());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updatePaymentAmount(Payment p) {
		try {
			conn = ConnectionFactory.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"update payment set amount = ? "
						+ "where payment_num = ? ");
			pstmt.setDouble(1, p.getAmount());
			pstmt.setInt(2, p.getPaymentNumber());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public boolean updatePaymentAmountRemaining(Payment p) {
		try {
			conn = ConnectionFactory.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"update payment set amount_remaining = ? "
						+ "where payment_num = ? ");
			pstmt.setDouble(1, p.getAmountRemaining());
			pstmt.setInt(2, p.getPaymentNumber());
			pstmt.executeUpdate();
			return true;		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deletePayment(Payment o) {
		try {
			conn = ConnectionFactory.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("delete from Payment "
					+ "where payment_num = ? ");
		pstmt.setInt(1, o.getPaymentNumber());
		pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Payment getPaymentbyPaymentNumber(Integer paymentNum) {
		try {
			conn = ConnectionFactory.getConnection();
			Statement stmt = conn.createStatement();
			
			String sql = "select * from Payment where payment_num = " + paymentNum;
			
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
		
		conn = ConnectionFactory.getConnection();
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
		Integer paymentNum = rs.getInt("payment_num");
		Integer userID = rs.getInt("userid"); 
		Integer carLicense = rs.getInt("car_license");
		Double amount = rs.getDouble("amount");
		Double amountRemaining = rs.getDouble("amount_remaining");
		
		return new Payment(paymentNum, userID, carLicense, amount, amountRemaining);
	}

	@Override
	public boolean createPayment(Integer custNum, Integer license) {
		try {
			//userid = u and car_license
			conn = ConnectionFactory.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "select payment_amount_remaining(" + custNum + ", " + license + ");";
			ResultSet rs = stmt.executeQuery(sql);
		
			while(rs.next()) {
				String[] returnStrings = fixReturnValue(rs.getString(1));
				Integer highestPNum = Integer.parseInt(returnStrings[0]);
				Double amtRem = Double.parseDouble(returnStrings[1]);
				Double amt = Double.parseDouble(returnStrings[2]);
				
				new Payment(highestPNum+1, custNum, license, amt, amtRem - amt, true);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Logging.infoLog("Payment on " + license + " by " + custNum);
		return false;
	}
	
	private String[] fixReturnValue(String retVal) {
		retVal = retVal.substring(1, retVal.length()-1);
		
		return retVal.split(",");
	}
}
