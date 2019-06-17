package com.mJames.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mJames.pojo.Employee;
import com.mJames.util.Logging;

public class EmployeeDaoImpl implements EmployeeDao {

private Connection conn;
	
	@Override
	public void createEmployee(Employee c) {
		try {
			Statement stmt = conn.createStatement();
			
			String sql = "Insert INTO Employee "
					+ " (Employeeid, firstname, lastname) " 
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
	public void updateEmployeePassword(String p, Employee c) {
		try {
			Statement stmt = conn.createStatement();
			String query = "update Employee set password = " + p 
							+ " where Employeeid = " + c.getUserNum();
		
			stmt.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteEmployee(Employee c) {
		try {
			Statement stmt = conn.createStatement();
			String query = "delete from Employee where Employeeid = " + c.getUserNum();
			stmt.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Employee getEmployeeByUserID(Integer userID) {
		try {
			Statement stmt = conn.createStatement();
			
			String sql = "select * from Employee where Employeeid = " + userID;
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next())
			{
				return buildEmployeeFromRS(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Logging.errorLog("Nonexistent Employee requested");
		return null;
	}
	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> EmployeeList = new ArrayList<Employee>();
		
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "select * from Employee";
			ResultSet rs = stmt.executeQuery(sql);
		
			while(rs.next())
				EmployeeList.add(buildEmployeeFromRS(rs));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return EmployeeList;
	}
	
	private Employee buildEmployeeFromRS(ResultSet rs) throws SQLException {
		Integer userID = rs.getInt("lotid"); 
		String firstName = rs.getString("make");
		String lastName = rs.getString("model");
		String password = rs.getString("color");
		
		return new Employee(userID, firstName, lastName, password);
	}

}
