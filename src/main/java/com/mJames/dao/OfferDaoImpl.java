package com.mJames.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mJames.pojo.Offer;
import com.mJames.util.Logging;

public class OfferDaoImpl implements OfferDao {

	private Connection conn;
	
	@Override
	public void createOffer(Offer o) {
		try {
			Statement stmt = conn.createStatement();
			
			String sql = "Insert INTO Offer "
					+ " (userid, car_license, value, term, accepted_by, status) " 
					+ "VALUES (" 
					+ o.getUserID()+ ", "
					+ o.getCarLicense()+ ", "
					+ o.getValue()+ ", "
					+ o.getTerm() + ", "
					+ o.getAcceptedBy() + ", "
					+ o.getStatus() + ");";
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateOfferAcceptedBy(Offer o) {
		try {
			Statement stmt = conn.createStatement();
			String query = "update Offer set accepted_by = " + o.getAcceptedBy() 
							+ " where userid = " + o.getUserID()
							+ " and car_license = " + o.getCarLicense() + ";";
		
			stmt.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void updateOfferStatus(Offer o) {
		try {
			Statement stmt = conn.createStatement();
			String query = "update Offer set status = " + o.getStatus() 
							+ " where userid = " + o.getUserID()
							+ " and car_license = " + o.getCarLicense() + ";";
		
			stmt.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteOffer(Offer o) {
		try {
			Statement stmt = conn.createStatement();
			String query = "delete from Offer "
					+ "where userid = " + o.getUserID()
					+ "and car_license = " + o.getCarLicense() + ";";
			stmt.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Offer getOfferByCustIDAndLicense(Integer userID, Integer license) {
		try {
			Statement stmt = conn.createStatement();
			
			String sql = "select * from Offer where userid = " + userID
						+ " and car_license = " + license + ";";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next())
			{
				return buildOfferFromRS(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Logging.errorLog("Nonexistent Offer requested");
		return null;
	}
	@Override
	public List<Offer> getAllOffers() {
		List<Offer> OfferList = new ArrayList<Offer>();
		
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "select * from Offer";
			ResultSet rs = stmt.executeQuery(sql);
		
			while(rs.next())
				OfferList.add(buildOfferFromRS(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return OfferList;
	}

	private Offer buildOfferFromRS(ResultSet rs) throws SQLException {
		Integer userID = rs.getInt("lotid"); 
		Integer license = rs.getInt("car_license");
		Double value = rs.getDouble("value");
		Integer term = rs.getInt("term");
		Integer acceptedBy = rs.getInt("accepted_by");
		String status = rs.getString("status");
		
		return new Offer(license, userID, value, term, status, acceptedBy);
	}
}
