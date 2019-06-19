package com.mJames.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mJames.pojo.Car;
import com.mJames.util.ConnectionFactory;
import com.mJames.util.Logging;

public class CarDaoImpl implements CarDao {

	private static Connection conn = ConnectionFactory.getConnection();
	
	@Override
	public boolean carExists(Car c) {
		try {
			//conn = ConnectionFactory.getConnection();
			Statement stmt = conn.createStatement();
			
			String sql = "select * from car where licensenum = " + c.getLicenseNumber();
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next())
				return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean createCar(Car c) {
		try {
			//conn = ConnectionFactory.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement(
					"Insert INTO car VALUES (?,?,?,?,?,?,?,?);"); 
				pstmt.setInt(1, c.getLicenseNumber());
				if (c.getOwnerID() == null)
					pstmt.setNull(2, java.sql.Types.INTEGER);
				else
					pstmt.setInt(2, c.getOwnerID());
				pstmt.setString(3, c.getMake());
				pstmt.setString(4, c.getModel());
				pstmt.setString(5, c.getColor());
				pstmt.setDouble(6, c.getPrice());
				if (c.getLotID() == null)
					pstmt.setNull(7, java.sql.Types.INTEGER);
				else
					pstmt.setInt(7, c.getLotID());
				pstmt.setString(8, c.getStatus());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean updateCarLotID(Car c) {
		try {
			//conn = ConnectionFactory.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("update car set LotID = ? where LicenseNum = ?;");
			if (c.getLotID() == null)
				pstmt.setNull(1, java.sql.Types.INTEGER);
			else
				pstmt.setInt(1, c.getLotID());
			pstmt.setInt(2, c.getLicenseNumber());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return false;
		
	}

	@Override
	public boolean updateCarOwnerID(Car c) {
		try {
			//conn = ConnectionFactory.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"update car set ownerid = ? "
					+ "where licensenum = ?;");
			if (c.getOwnerID() == null)
				pstmt.setNull(1, java.sql.Types.INTEGER);
			else
				pstmt.setInt(1, c.getOwnerID());
			pstmt.setInt(2, c.getLicenseNumber());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateCarStatus(Car c) {
		try {
			//conn = ConnectionFactory.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("update car set status = ? where LicenseNum = ?;");
			pstmt.setString(1, c.getStatus());
			pstmt.setInt(2, c.getLicenseNumber());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteCar(Car c) {
		try {
			//conn = ConnectionFactory.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("delete from car where licenseNum = ?;");
			pstmt.setInt(1, c.getLicenseNumber());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Car getCarByLicense(Integer license) {
		try {
			//conn = ConnectionFactory.getConnection();
			Statement stmt = conn.createStatement();
			
			String sql = "select * from car where licensenum = " + license;
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next())
			{
				return buildCarFromRS(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Logging.errorLog("Nonexistent car requested");
		return null;
	}
	@Override
	public List<Car> getAllCars() {
		//conn = ConnectionFactory.getConnection();
		List<Car> carList = new ArrayList<Car>();
		
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "select * from car";
			ResultSet rs = stmt.executeQuery(sql);
		
			while(rs.next())
				carList.add(buildCarFromRS(rs));
		} catch (SQLException e) {
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
