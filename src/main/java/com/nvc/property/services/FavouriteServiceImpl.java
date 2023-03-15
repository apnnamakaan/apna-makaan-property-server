package com.nvc.property.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nvc.property.exceptions.ResourceNotFoundException;
import com.nvc.property.models.Favourite;
import com.nvc.property.models.Property;
import com.nvc.property.repository.FavouriteRepository;
import com.nvc.property.repository.PropertyRepository;

@Service
public class FavouriteServiceImpl implements FavouriteService {
	
	@Autowired
	private PropertyRepository propertyRepository; 

	@Autowired
	private FavouriteRepository favouriteRepository; 
	
	@Override
	public List<Property> getFavouritePropertiesByUserEmail(String email) {
		
		List<Property> properties = new ArrayList<Property>();
		
		List<Favourite> favourites = this.favouriteRepository.findByUser(email);
		
		for (Favourite favourite: favourites) {
			
			Property property = this.propertyRepository.findById(favourite.getProperty()).orElse(null);
	
			if(property!=null) {
				properties.add(property);
			}
		}
		
		return properties;
	}

	@Override
	public void deleteFavouritePropertiesByUserEmail(String email, Long id) {
		
	Favourite favouritie = this.favouriteRepository.findByUserAndProperty(email,id);
		
		if(favouritie==null) {
			return;
		}
		this.favouriteRepository.delete(favouritie);

	}

	@Override
	public void addFavouritePropertiesByUserEmail(String email, Long id) {
		
		Property property = this.propertyRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("property not found"));
		
		Favourite favouritie = this.favouriteRepository.findByUserAndProperty(email,property.getId());
		
		if(favouritie != null) {
			return;
		}
		
		Favourite favourite =new Favourite();
		favourite.setUser(email);
		favourite.setProperty(id);
		
		this.favouriteRepository.save(favourite);

	}

}
