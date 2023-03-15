package com.nvc.property.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.nvc.property.models.Property;
import com.nvc.property.models.PropertyDetails;
import com.nvc.property.responses.ApiResponse;
import com.nvc.property.services.PropertyService;
import com.nvc.property.exceptions.UnauthorizeException;
import com.nvc.property.responses.VerifyResponse;

@RestController
public class PropertyController {
	
	@Autowired
	private PropertyService propertyService;
	
	VerifyResponse verifyToken(String token) {
		String uri = "http://localhost:5000/auth/verify";
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("authorization", token);
		
		HttpEntity<String> request = new HttpEntity<String>(headers);
		ResponseEntity<VerifyResponse> response = restTemplate.exchange(uri,  HttpMethod.POST, request, VerifyResponse.class);
		return response.getBody();
	}
	
	@GetMapping("/all/")
	public ResponseEntity<List<Property>> getPropertysByEmail(@RequestParam(name = "email") String email) {
		
		List<Property> properties = this.propertyService.getPropertiesByUserEmail(email);
		return new ResponseEntity<>(properties,HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<PropertyDetails> getPropertyById(@RequestHeader("authorization") String token,@RequestParam(name = "id") Long id){
		PropertyDetails propertyDetails = this.propertyService.getPropertyById(id,token);
		
		return new ResponseEntity<>(propertyDetails,HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/new/")
	public ResponseEntity<ApiResponse> uploadProperty(@RequestHeader("authorization") String token,@RequestBody Property property){
		
		VerifyResponse response =  this.verifyToken(token);
		
		if(!(response.getEmail().equals(property.getUser()) || response.isAdmin())) throw new UnauthorizeException("you are not authorized");
		
		this.propertyService.uploadProperty(property);
		return new ResponseEntity<>( new ApiResponse("true","new property added successfully"),HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete/") // only admin can delete
	public ResponseEntity<ApiResponse> deletePropertyById(@RequestHeader("authorization") String token,@RequestParam(name = "id") Long id){
		
		VerifyResponse response =  this.verifyToken(token);
		
		if(!(response.isAdmin())) throw new UnauthorizeException("you are not authorized");
		
		this.propertyService.deletePropertyById(id);
		return new ResponseEntity<>( new ApiResponse("true","delete property successfully"),HttpStatus.ACCEPTED);
	}
	
	@PatchMapping("/deactive/")
	public ResponseEntity<ApiResponse> deactiveListingPropertyById(@RequestHeader("authorization") String token,@RequestParam(name = "id") Long id) {

		this.propertyService.deactivePropertyById(id);
		return new ResponseEntity<>( new ApiResponse("true","deactive successfully"),HttpStatus.OK);
	}
	
	@GetMapping("/search/") 
	public ResponseEntity<List<Property>> getPropertysByFilter(
			@RequestParam(name = "bed") Integer bed ,
			@RequestParam(name = "bath") Integer bath ,
			@RequestParam(name = "garage") Integer garage ,
			@RequestParam(name = "city") String city, 
			@RequestParam(name = "min") Double min,
			@RequestParam(name = "max" ) Double max
			) {
		
			List<Property> propertys = this.propertyService.getPropertysByFilter(bed, bath, garage, city, min, max);
			return new ResponseEntity<>(propertys,HttpStatus.OK);	
	}
	
	@GetMapping("/search/all/") 
	public ResponseEntity<List<Property>> getProperties() {
		
			List<Property> propertys = this.propertyService.getProperties();
			return new ResponseEntity<>(propertys,HttpStatus.OK);	
	}
	

	
}
