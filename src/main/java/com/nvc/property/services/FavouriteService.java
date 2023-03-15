package com.nvc.property.services;

import java.util.List;

import com.nvc.property.models.Property;

public interface FavouriteService {
	List<Property> getFavouritePropertiesByUserEmail(String email);
	
	void deleteFavouritePropertiesByUserEmail(String email,Long id);
	
	void addFavouritePropertiesByUserEmail(String email,Long id);
}
