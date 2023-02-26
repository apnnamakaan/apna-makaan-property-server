package com.nvc.property.models;

import java.security.Timestamp;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "properties")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property {
	
	  	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	  	
	  	private String name;
	  	
	  	private String user;
	  	
	  	private Double area;
	  	
	  	private Integer bed;
	  	
	  	private Integer bath;
	  	
	  	private Integer garage;
	  	
	  	private Double price;
	  	
	  	private String city;
	  	
	  	private String image;
	  	
	  	@CreationTimestamp
	  	@Column(name="created_at",nullable = false,updatable = false)
	  	private Date createdAt;
	  	
	  	@UpdateTimestamp
		@Column(name="update_at")
	  	private Date updateAt;
	  	
	  	@Column(columnDefinition ="boolean default true" )
	  	private Boolean active;
	  	
	  	
	  	

}
