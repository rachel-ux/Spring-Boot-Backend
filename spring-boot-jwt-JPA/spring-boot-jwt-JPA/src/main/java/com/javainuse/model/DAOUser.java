package com.javainuse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;

@Entity
@Table(name = "user")
public class DAOUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	
	@Column(name = "username")
	public String username;
	
	@Column
	@JsonIgnore
	public String password;
	
	@Column(nullable = false, name = "role")
	public  String role;
	
	@Column(nullable = false, name = "name")
	public String name;
	
	

    /*private DAOUserDetails userDetails;
    
   
    private DAOHealthDetails healthDetails;
    

    private DAOHospitalDetails hospitalDetails;*/
    
	
	public long getId() {
		return id;
	}

	/*public DAOUserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(DAOUserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public DAOHealthDetails getHealthDetails() {
		return healthDetails;
	}

	public void setHealthDetails(DAOHealthDetails healthDetails) {
		this.healthDetails = healthDetails;
	}

	public DAOHospitalDetails getHospitalDetails() {
		return hospitalDetails;
	}

	public void setHospitalDetails(DAOHospitalDetails hospitalDetails) {
		this.hospitalDetails = hospitalDetails;
	}*/

	public void setId(long id) {
		this.id = id;
	}

	

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getUsername() {
		return username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}