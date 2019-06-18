package com.mJames.dao;

import java.util.List;

import com.mJames.pojo.Employee;

public interface EmployeeDao {
	
	public boolean employeeExists(Employee e);

	public boolean createEmployee(Employee e);
	
	public boolean updateEmployeePassword(String p, Employee c);
	
	public boolean deleteEmployee(Employee e);
	
	public Employee getEmployeeByUserID(Integer id);
	
	public List<Employee> getAllEmployees();
}
