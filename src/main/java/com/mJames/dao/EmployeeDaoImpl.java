package com.mJames.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mJames.pojo.Employee;
import com.mJames.util.ConnectionFactory;
import com.mJames.util.Logging;

public class EmployeeDaoImpl implements EmployeeDao {

private Connection conn;

	@Override
	public boolean employeeExists(Employee e) {
		try {
			conn = ConnectionFactory.getConnection();
			Statement stmt = conn.createStatement();
			
			String sql = "select * from Employee where Employeeid = " + e.getUserNum();
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next())
				return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	@Override
	public boolean createEmployee(Employee c) {
		try {
			conn = ConnectionFactory.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("Insert INTO employee (employeeid, firstname, lastname) VALUES (?, ?, ?)");
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
	public boolean updateEmployeePassword(String p, Employee c) {
		try {
			conn = ConnectionFactory.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("update employee set password = ? where employeeid = ?");
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
	public boolean deleteEmployee(Employee c) {
		try {
			conn = ConnectionFactory.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"delete from employee where employeeid = ?;");
			pstmt.setInt(1, c.getUserNum());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Employee getEmployeeByUserID(Integer userID) {
		try {
			conn = ConnectionFactory.getConnection();
			Statement stmt = conn.createStatement();
			
			String sql = "select * from Employee where Employeeid = " 
						+ userID + ";";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next())
			{
				return buildEmployeeFromRS(rs);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		Logging.errorLog("Nonexistent Employee requested");
		return null;
	}
	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> EmployeeList = new ArrayList<Employee>();
		
		conn = ConnectionFactory.getConnection();
		Statement stmt;
		try {
			conn = ConnectionFactory.getConnection();
			stmt = conn.createStatement();
			String sql = "select * from employee;";
			ResultSet rs = stmt.executeQuery(sql);
		
			while(rs.next())
				EmployeeList.add(buildEmployeeFromRS(rs));
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		return EmployeeList;
	}
	
	private Employee buildEmployeeFromRS(ResultSet rs) throws SQLException {
		Integer userID = rs.getInt("employeeid"); 
		String firstName = rs.getString("firstname");
		String lastName = rs.getString("lastname");
		String password = rs.getString("password");
		
		return new Employee(userID, firstName, lastName, password);
	}

}
