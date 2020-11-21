package com.javainuse.dao;

import java.util.ArrayList;
import java.util.List;

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
public interface UserDao extends CrudRepository<DAOUser,Long> {
	
	DAOUser findByUsername(String username);
	
	@Query("SELECT name, username FROM DAOUser WHERE name = :name")
	ArrayList<DAOUser> selectbyRecord(@Param("name") String name);

}