package com.mastercard.billingsearch.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRoles implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String role;
	private String elements;
	private String asFields;
	private char enable;


	public UserRoles() {
		super();
	}


	
}