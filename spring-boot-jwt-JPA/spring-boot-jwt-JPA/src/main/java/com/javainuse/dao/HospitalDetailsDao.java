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
import com.javainuse.model.DAOUserDetails;

@Repository
public interface HospitalDetailsDao extends JpaRepository<DAOHospitalDetails,Long> {
	
	@Query("from DAOHospitalDetails order by id desc")
	ArrayList<DAOHospitalDetails> selectAllRecords();
	
	@Query("from DAOHospitalDetails where status = :variable order by id desc")
	ArrayList<DAOHospitalDetails> selectbyOneRecord(@Param("variable") String status);
	
	@Query("from DAOHospitalDetails WHERE username = :name")
	DAOHospitalDetails selectbyRecord(@Param("name") String name);
}
