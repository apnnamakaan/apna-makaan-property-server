package com.nvc.property.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nvc.property.models.Favourite;

public interface FavouriteRepository extends JpaRepository<Favourite,Long>{
	
List<Favourite> findByUser(String email);
	
Favourite findByUserAndProperty(String email,Long id);
	
}
