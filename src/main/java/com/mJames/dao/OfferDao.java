package com.mJames.dao;

import java.util.List;

import com.mJames.pojo.Offer;

public interface OfferDao {
	
	public void createOffer(Offer u);
	
	public void updateOfferAcceptedBy(Offer u);

	public void updateOfferStatus(Offer u);
	
	public void deleteOffer(Offer u);
	
	public Offer getOfferByCustIDAndLicense(Integer userID, Integer license);
	
	public List<Offer> getAllOffers();

}
