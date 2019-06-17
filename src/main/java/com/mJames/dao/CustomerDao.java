package com.mJames.dao;

import java.util.List;

import com.mJames.pojo.Customer;

public interface CustomerDao {
	
	public void createCustomer(Customer u);
	
	public void updateCustomerPassword(String p, Customer c);
	
	public void deleteCustomer(Customer u);
	
	public Customer getCustomerByUserID(Integer userID);
	
	public List<Customer> getAllCustomers();
}
