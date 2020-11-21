package com.javainuse.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final Object role;
	private final String username;

	public String getUsername() {
		return username;
	}

	public Object getRole() {
		return role;
	}

	public JwtResponse(String jwttoken, Object roles, String username) {
		this.jwttoken = jwttoken;
		this.role = roles;
		this.username = username;
	}

	public String getToken() {
		return this.jwttoken;
	}
}