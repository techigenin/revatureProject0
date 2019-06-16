package com.mJames.services;

import java.util.HashSet;
import java.util.Set;

import com.mJames.pojo.Offer;

public class OfferServiceImplConsole implements OfferService {

	@Override
	public Set<Offer> getActiveOffers(Set<Offer> offers) {
		Set<Offer> activeOffers = new HashSet<Offer>();
		
		for(Offer o : offers)
		{
			if (o.getStatus() == "Active")
				activeOffers.add(o);
		}
		
		return activeOffers;
	}

}
