package com.nvc.property.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	private String id;
	private String name;
	private String email;
	private long phone;
	
}
