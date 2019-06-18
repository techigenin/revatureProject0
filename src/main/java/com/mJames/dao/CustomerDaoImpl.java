package com.mJames.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mJames.dao.CustomerDao;
import com.mJames.pojo.Customer;
import com.mJames.util.ConnectionFactory;
import com.mJames.util.Logging;

public class CustomerDaoImpl implements CustomerDao {

	private Connection conn;

	@Override
	public boolean customerExists(Customer c) {
		try {
			conn = ConnectionFactory.getConnection();
			Statement stmt = conn.createStatement();
			
			String sql = "select * from customer where customerid = " + c.getUserNum();
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next())
				return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean createCustomer(Customer c) {
		try {
			conn = ConnectionFactory.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("Insert INTO Customer (Customerid, firstname, lastname) VALUES (?, ?, ?)");
			pstmt.setInt(1, c.getUserNum());
			pstmt.setString(2, c.getFirstName());
			pstmt.setString(3, c.getLastName());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public boolean updateCustomerPassword(String p, Customer c) {
		try {
			conn = ConnectionFactory.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("update Customer set password = ? where Customerid = ?");
			pstmt.setString(1, p);
			pstmt.setInt(2, c.getUserNum());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
			return false;
	}

	@Override
	public boolean deleteCustomer(Customer c) {
		try {
			conn = ConnectionFactory.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"delete from Customer where Customerid = ?;");
			pstmt.setInt(1, c.getUserNum());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Customer getCustomerByUserID(Integer userID) {
		try {
			conn = ConnectionFactory.getConnection();
			Statement stmt = conn.createStatement();
			
			String sql = "select * from Customer where customerid = " + userID;
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next())
			{
				return buildCustomerFromRS(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Logging.errorLog("Nonexistent Customer requested");
		return null;
	}
	@Override
	public List<Customer> getAllCustomers() {
		List<Customer> CustomerList = new ArrayList<Customer>();
		
		conn = ConnectionFactory.getConnection();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "select * from Customer";
			ResultSet rs = stmt.executeQuery(sql);
		
			while(rs.next())
				CustomerList.add(buildCustomerFromRS(rs));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return CustomerList;
	}
	
	private Customer buildCustomerFromRS(ResultSet rs) throws SQLException {
		Integer userID = rs.getInt("customerid"); 
		String firstName = rs.getString("firstname");
		String lastName = rs.getString("lastname");
		String password = rs.getString("password");
		
		return new Customer(userID, firstName, lastName, password);
	}
}
