package com.javainuse.dao;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.javainuse.model.*;

@Repository
public interface BedDao extends JpaRepository<DAOBed, Integer> {
	
	DAOBed findById(long id);
	
	@Query("from DAOBed where status = :name and ward = :ward order by id desc")
	ArrayList<DAOBed> getAvailableBed(@Param("name") String status, @Param("ward") char ward1);
}
