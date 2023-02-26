package com.nvc.property.services;

import java.util.List;

import com.nvc.property.models.Property;
import com.nvc.property.models.PropertyDetails;

public interface PropertyService {
	
	List<Property> getPropertiesByUserEmail(String email);
	List<Property> getProperties();
	List<Property> getPropertysByFilter(Integer bed, Integer bath, Integer garage, String city, Double min, Double max);
	
	void deactiveListingPropertyById(Long id);
	void uploadProperty(Property property);
	void deleteProperty(Long id);
	
	PropertyDetails getPropertyById(Long id, String token);
}
