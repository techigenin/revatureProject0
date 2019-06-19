package com.mJames.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mJames.pojo.Offer;
import com.mJames.util.ConnectionFactory;
import com.mJames.util.Logging;

public class OfferDaoImpl implements OfferDao {

	private static Connection conn = ConnectionFactory.getConnection();	
	@Override
	public boolean offerExists(Offer o) {
		try {
//			conn = ConnectionFactory.getConnection();
			Statement stmt = conn.createStatement();
			
			String sql = "select * from offer where userid = "
				 + o.getUserID() + " and car_license = " + o.getCarLicense() + ";";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next())
				return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean createOffer(Offer o) {
		try {
//			conn = ConnectionFactory.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("Insert INTO Offer "
					+ "(userid, car_license, value, term, accepted_by, status) " 
					+ "VALUES (?, ?, ?, ?, ?, ?);");
			 
			pstmt.setInt(1, o.getUserID());
			pstmt.setInt(2, o.getCarLicense());
			pstmt.setDouble(3, o.getValue());
			pstmt.setInt(4, o.getTerm());
			if (o.getAcceptedBy() == null)
				pstmt.setNull(5, java.sql.Types.INTEGER);
			else
				pstmt.setInt(5, o.getAcceptedBy());
			pstmt.setString(6, o.getStatus());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateOfferAcceptedBy(Offer o) {
		try {
//			conn = ConnectionFactory.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"update Offer set accepted_by = ? "
						+ "where userid = ? "
						+ "and car_license = ? ;");
			if (o.getAcceptedBy() == null)
				pstmt.setNull(1, java.sql.Types.INTEGER);
			else
				pstmt.setInt(1, o.getAcceptedBy());
			pstmt.setInt(2, o.getUserID());
			pstmt.setInt(3, o.getCarLicense());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public boolean updateOfferStatus(Offer o) {
		try {
//			conn = ConnectionFactory.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"update Offer set status = ? "
						+ "where userid = ? "
						+ "and car_license = ?;");
			pstmt.setString(1, o.getStatus());
			pstmt.setInt(2, o.getUserID());
			pstmt.setInt(3, o.getCarLicense());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteOffer(Offer o) {
		try {
//			conn = ConnectionFactory.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"delete from Offer "
						+ "where userid = ? "
						+ "and car_license = ?;");
			pstmt.setInt(1, o.getUserID());
			pstmt.setInt(2, o.getCarLicense());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Offer getOfferByCustIDAndLicense(Integer userID, Integer license) {
		try {
//			conn = ConnectionFactory.getConnection();
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
		
//		conn = ConnectionFactory.getConnection();
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
		Integer userID = rs.getInt("userID"); 
		Integer license = rs.getInt("car_license");
		Double value = rs.getDouble("value");
		Integer term = rs.getInt("term");
		Integer acceptedBy = rs.getInt("accepted_by");
		String status = rs.getString("status");
		
		return new Offer(license, userID, value, term, status, acceptedBy);
	}
}
