package com.nvc.property.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nvc.property.models.Favourite;
import com.nvc.property.models.Property;
import com.nvc.property.responses.ApiResponse;
import com.nvc.property.services.FavouriteService;

@RestController
public class FavouriteController {
	
	@Autowired
	private FavouriteService favouriteService;

	@GetMapping("/favourite")
	ResponseEntity<List<Property>> getFavouritePropertiesByUserEmail(@RequestParam(name = "email") String email){
		List<Property> properties =  this.favouriteService.getFavouritePropertiesByUserEmail(email);
		return new ResponseEntity<>(properties,HttpStatus.OK);
	}
	
	@PostMapping("/favourite")
	ResponseEntity<ApiResponse> addFavouritePropertiesByUserEmail(@RequestBody Favourite favourite){
		this.favouriteService.addFavouritePropertiesByUserEmail(favourite.getUser(), favourite.getProperty());
		return new ResponseEntity<>( new ApiResponse("true","new favourite added successfully"),HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/favourite")
	ResponseEntity<ApiResponse> deleteFavouritePropertiesByUserEmail(@RequestBody Favourite favourite){
		this.favouriteService.deleteFavouritePropertiesByUserEmail(favourite.getUser(), favourite.getProperty());
		return new ResponseEntity<>( new ApiResponse("true","delete favourite successfully"),HttpStatus.ACCEPTED);
	}
	
}
