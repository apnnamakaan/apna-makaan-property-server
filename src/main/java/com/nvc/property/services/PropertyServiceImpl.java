package com.nvc.property.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.nvc.property.exceptions.ResourceNotFoundException;
import com.nvc.property.models.Property;
import com.nvc.property.models.PropertyDetails;
import com.nvc.property.models.User;
import com.nvc.property.repository.PropertyRepository;
import com.nvc.property.responses.ApiResponse;

@Service
public class PropertyServiceImpl implements PropertyService {
	
	@Autowired
	private PropertyRepository propertyRepository; 
	
	@Override
	public List<Property> getPropertiesByUserEmail(String email) {
		List<Property> properies = this.propertyRepository.findByUser(email);
		return properies;
	}

	@Override
	public List<Property> getPropertysByFilter(Integer bed, Integer bath, Integer garage, String city, Double min, Double max) {
		
		if(bed==null && bath==null && garage==null && city==null && min==null && max==null) {
			new ResourceNotFoundException("please select all");
		}
		
		List<Property> properies = this.propertyRepository.findByBedAndBathAndGarageAndCityAndPriceBetween(bed, bath, garage, city, min, max);
		return properies;
	}

	@Override
	public void deactiveListingPropertyById(Long id) {
		Property property = this.propertyRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("property not found"));
		property.setActive(false);
		this.propertyRepository.save(property);
	}

	@Override
	public void uploadProperty(Property property) {
		property.setId(null);
		property.setActive(true);
		this.propertyRepository.save(property);
	}

	@Override
	public void deleteProperty(Long id) {
		Property property = this.propertyRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("property not found"));
		this.propertyRepository.delete(property);
	}

	@Override
	public PropertyDetails getPropertyById(Long id,String token) {
		
		Property property = this.propertyRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("property not found"));
		System.out.println(property);
		String uri = "http://localhost:5000/user/?email=" + property.getUser();
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("authorization", token);
		
		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		try {
			ResponseEntity<User> response = (ResponseEntity<User>) restTemplate.exchange(uri,  HttpMethod.GET, request, User.class);
			User user = response.getBody();
			
			PropertyDetails propertyDetails = new PropertyDetails();
			propertyDetails.setProperty(property);
			propertyDetails.setUser(user);
			
			return propertyDetails;
					
		}catch(HttpClientErrorException e) {
			
			ApiResponse body =  e.getResponseBodyAs(ApiResponse.class);
			throw new ResourceNotFoundException(body.getMessage());
		}
		
	
		
	}

	@Override
	public List<Property> getProperties() {
		return this.propertyRepository.findAll();
	}

}
