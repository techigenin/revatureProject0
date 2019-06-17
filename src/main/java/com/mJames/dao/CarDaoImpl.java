package com.mJames.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mJames.pojo.Car;
import com.mJames.util.Logging;

public class CarDaoImpl implements CarDao {

	private Connection conn;
	
	@Override
	public void createCar(Car c) {
		try {
			Statement stmt = conn.createStatement();
			
			String sql = "Insert INTO car " 
						+ "VALUES (" 
						+ c.getLicenseNumber()+ ", "
						+ c.getOwnerID()+ ", "
						+ c.getMake()+ ", "
						+ c.getModel()+ ", "
						+ c.getColor()+ ", "
						+ c.getPrice()+ ", "
						+ c.getLotID()+ ", "
						+ c.getStatus() + ")";
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void updateCarLotID(Car c) {
		try {
			Statement stmt = conn.createStatement();
			String query = "update car set LotID = " + c.getLotID() 
							+ " where LicenseNum = " + c.getLicenseNumber();
		
			stmt.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateCarOwnerID(Car c) {
		try {
			Statement stmt = conn.createStatement();
			String query = "update car set OwnerID = " + c.getOwnerID() 
							+ " where LicenseNum = " + c.getLicenseNumber();
		
			stmt.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateCarStatus(Car c) {
		try {
			Statement stmt = conn.createStatement();
			String query = "update car set status = " + c.getStatus() 
							+ " where LicenseNum = " + c.getLicenseNumber();
			stmt.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteCar(Car c) {
		try {
			Statement stmt = conn.createStatement();
			String query = "delete from car where licenseNum = " + c.getLicenseNumber();
			
			stmt.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Car getCarByLicense(Integer license) {
		try {
			Statement stmt = conn.createStatement();
			
			String sql = "select * from car where licensenum = " + license;
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next())
			{
				return buildCarFromRS(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Logging.errorLog("Nonexistent car requested");
		return null;
	}
	@Override
	public List<Car> getAllCars() {
		List<Car> carList = new ArrayList<Car>();
		
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "select * from car";
			ResultSet rs = stmt.executeQuery(sql);
		
			while(rs.next())
				carList.add(buildCarFromRS(rs));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return carList;
	}
	
	private Car buildCarFromRS(ResultSet rs) throws SQLException {
		Integer lotID = rs.getInt("lotid"); 
		int licenseNumber = rs.getInt("licensenum"); 
		Integer ownerID = rs.getInt("ownerid");
		String make = rs.getString("make");
		String model = rs.getString("model");
		String color = rs.getString("color");
		double price = rs.getDouble("price");
		String status = rs.getString("status");
		
		return new Car(lotID, licenseNumber, ownerID, make, model, color, price, status);
	}

}
