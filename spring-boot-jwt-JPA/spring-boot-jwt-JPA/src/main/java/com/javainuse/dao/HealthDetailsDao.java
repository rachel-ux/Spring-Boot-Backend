package com.javainuse.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.javainuse.model.DAOHealthDetails;
import com.javainuse.model.DAOHospitalDetails;

@Repository
public interface HealthDetailsDao extends  CrudRepository<DAOHealthDetails, Long> {
	DAOHealthDetails findByUsername(String username);
}
