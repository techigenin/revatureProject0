package com.mJames.services;

import java.util.Set;

import com.mJames.pojo.Offer;

public interface OfferService {

	Set<Offer> getActiveOffers(Set<Offer> offers);
}
