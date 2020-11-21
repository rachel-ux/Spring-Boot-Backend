package com.javainuse.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.javainuse.model.AvailableUserDetails;
import com.javainuse.model.DAOHealthDetails;
import com.javainuse.model.DAOHospitalDetails;
import com.javainuse.model.DAOUser;
import com.javainuse.model.DAOUserDetails;

@Repository
public interface UserDao extends JpaRepository<DAOUser,Long> {
	
	DAOUser findByUsername(String username);
	
	ArrayList<DAOUser> findByName(String name);
	
	@Query("from DAOUser where name = :name order by id desc")
	ArrayList<DAOUser> selectbyRecord(@Param("name") String name);

}