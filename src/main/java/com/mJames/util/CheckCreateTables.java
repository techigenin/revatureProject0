package com.mJames.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mJames.dao.EmployeeDao;
import com.mJames.dao.EmployeeDaoImpl;
import com.mJames.pojo.Employee;
import com.mJames.ui.IOUtil;

public class CheckCreateTables {
	
	public static void checkCarTable(Connection c) {
		DatabaseMetaData dbm;
		try {
			dbm = c.getMetaData();
		
			ResultSet rs;
			rs = dbm.getTables(null, null, "car", null);

			if (rs.next()) {
			}
			else 
			{
				Statement stmt = null;
			
				stmt = c.createStatement();
				
				String sql = "CREATE TABLE Car " +
						"(LicenseNum INTEGER,"
						+ "OwnerID INTEGER,"
						+ "Make VARCHAR(20),"
						+ "Model VARCHAR(20),"
						+ "Color VARCHAR(20),"
						+ "price numeric(8,2),"
						+ "LotID INTEGER,"
						+ "Status VARCHAR(20), "
						+ "PRIMARY KEY (LicenseNum))";
				
				stmt.executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void checkCustomerTable(Connection c) {
		DatabaseMetaData dbm;
		try {
			dbm = c.getMetaData();
		
			ResultSet rs;
			rs = dbm.getTables(null, null, "customer", null);

			if (!rs.next()) {
				Statement stmt = null;
			
				stmt = c.createStatement();
				
				String sql = "CREATE TABLE customer " +
						"(customerid INTEGER,"
						+ "firstname VARCHAR(20),"
						+ "lastname VARCHAR(20),"
						+ "password VARCHAR(20),"
						+ "PRIMARY KEY (customerid))";
				
				stmt.executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void checkEmployeeTable(Connection c) {
		DatabaseMetaData dbm;
		try {
			dbm = c.getMetaData();
		
			ResultSet rs;
			rs = dbm.getTables(null, null, "employee", null);

			if (!rs.next()) {
				Statement stmt = null;
			
				stmt = c.createStatement();
				
				String sql = "CREATE TABLE employee " +
						"(employeeid INTEGER,"
						+ "firstname VARCHAR(20),"
						+ "lastname VARCHAR(20),"
						+ "password VARCHAR(20),"
						+ "PRIMARY KEY (employeeid))";
				
				stmt.executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public static void checkOfferTable(Connection c) {
		DatabaseMetaData dbm;
		try {
			dbm = c.getMetaData();
		
			ResultSet rs;
			rs = dbm.getTables(null, null, "offer", null);

			if (!rs.next()){
				Statement stmt = null;
			
				stmt = c.createStatement();
				
				String sql = "CREATE TABLE offer " +
						"(userid INTEGER,"
						+ "car_license INTEGER,"
						+ "value NUMERIC(8,2),"
						+ "term INTEGER,"
						+ "accepted_by INTEGER,"
						+ "status VARCHAR(20), "
						+ "PRIMARY KEY (userid, car_license))";
				
				stmt.executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void checkPaymentTable(Connection c) {
		DatabaseMetaData dbm;
		try {
			dbm = c.getMetaData();
		
			ResultSet rs;
			rs = dbm.getTables(null, null, "payment", null);

			if (!rs.next()) 
			{
				Statement stmt = null;
			
				stmt = c.createStatement();
				
				String sql = "CREATE TABLE payment " +
						"(userid INTEGER,"
						+ "car_license INTEGER,"
						+ "ammount NUMERIC(8,2),"
						+ "ammount_remaining NUMERIC(8,2),"
						+ "term INTEGER,"
						+ "PRIMARY KEY (userid, car_license))";
				
				stmt.executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
