package com.javainuse.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.javainuse.model.DAOHealthDetails;
import com.javainuse.model.DAOHospitalDetails;
import com.javainuse.model.DAOUserDetails;

@Repository
public interface HospitalDetailsDao extends CrudRepository<DAOHospitalDetails,Long> {
	DAOHospitalDetails findByUsername(String username);
}
