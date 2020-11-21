package com.javainuse.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "hospital_details")
public class DAOHospitalDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	//@OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL.ALL, orphanRemoval = true)
	//@JoinColumn(name = "user_id",  referencedColumnName = "id")
    //@MapsId
	@Column(name = "username")
	private String username;
	
	
	@Column(name = "symptoms")
	public String symptoms;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	@Column(name = "bed" )
	public String bed;
	
	@Column(name = "status")
	public String status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public String getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}

	public String getBed() {
		return bed;
	}

	public void setBed(String bed) {
		this.bed = bed;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
