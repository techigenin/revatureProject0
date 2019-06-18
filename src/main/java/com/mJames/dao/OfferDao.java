package com.mJames.dao;

import java.util.List;

import com.mJames.pojo.Offer;

public interface OfferDao {
	
	public boolean createOffer(Offer u);
	
	public boolean updateOfferAcceptedBy(Offer u);

	public boolean updateOfferStatus(Offer u);
	
	public boolean deleteOffer(Offer u);
	
	public Offer getOfferByCustIDAndLicense(Integer userID, Integer license);
	
	public List<Offer> getAllOffers();

	public boolean offerExists(Offer o);

}
