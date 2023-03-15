package com.nvc.property.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nvc.property.models.Property;

public interface PropertyRepository extends JpaRepository<Property,Long> {
	
	List<Property> findByUser(String email);

	List<Property> findByBedAndBathAndGarageAndCityAndPriceBetweenAndActiveTrue(Integer bed, Integer bath, Integer garage, String city, Double min, Double max);

	List<Property> findByActiveTrue();
	
}
