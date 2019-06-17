package com.mJames.dao;

import java.util.List;

import com.mJames.pojo.Employee;

public interface EmployeeDao {

	public void createEmployee(Employee u);
	
	public void updateEmployeePassword(String p, Employee c);
	
	public void deleteEmployee(Employee u);
	
	public Employee getEmployeeByUserID(Integer id);
	
	public List<Employee> getAllEmployees();
}
