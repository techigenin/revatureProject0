package com.mJames.dao;

import java.util.List;

import com.mJames.pojo.Customer;

public interface CustomerDao {
	
	public boolean createCustomer(Customer u);
	
	public boolean updateCustomerPassword(String p, Customer c);
	
	public boolean deleteCustomer(Customer u);
	
	public Customer getCustomerByUserID(Integer userID);
	
	public List<Customer> getAllCustomers();

	public boolean customerExists(Customer c);
}
