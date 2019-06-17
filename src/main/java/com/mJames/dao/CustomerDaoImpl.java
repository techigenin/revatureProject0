package com.mJames.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mJames.dao.CustomerDao;
import com.mJames.pojo.Customer;
import com.mJames.util.Logging;

public class CustomerDaoImpl implements CustomerDao {

	private Connection conn;
	
	@Override
	public void createCustomer(Customer c) {
		try {
			Statement stmt = conn.createStatement();
			
			String sql = "Insert INTO customer "
					+ " (customerid, firstname, lastname) " 
					+ "VALUES (" 
					+ c.getUserNum()+ ", "
					+ c.getFirstName()+ ", "
					+ c.getLastName() + ")";
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void updateCustomerPassword(String p, Customer c) {
		try {
			Statement stmt = conn.createStatement();
			String query = "update Customer set password = " + p 
							+ " where customerid = " + c.getUserNum();
		
			stmt.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteCustomer(Customer c) {
		try {
			Statement stmt = conn.createStatement();
			String query = "delete from Customer where customerid = " + c.getUserNum();
			stmt.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Customer getCustomerByUserID(Integer userID) {
		try {
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
		Integer userID = rs.getInt("lotid"); 
		String firstName = rs.getString("make");
		String lastName = rs.getString("model");
		String password = rs.getString("color");
		
		return new Customer(userID, firstName, lastName, password);
	}
}
