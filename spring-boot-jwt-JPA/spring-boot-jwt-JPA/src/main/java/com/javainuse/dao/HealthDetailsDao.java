package com.javainuse.dao;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.javainuse.model.DAOHealthDetails;
import com.javainuse.model.DAOHospitalDetails;
import com.javainuse.model.DAOUser;

@Repository
public interface HealthDetailsDao extends JpaRepository<DAOHealthDetails, Long> {
	DAOHealthDetails findByUsername(String username);
	
	@Query("from DAOHealthDetails where username = :name ")
	DAOHealthDetails selectbyRecord(@Param("name")  String name);
}
