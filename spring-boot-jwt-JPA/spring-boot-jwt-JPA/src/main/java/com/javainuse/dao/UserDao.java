package com.javainuse.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.javainuse.model.DAOHealthDetails;
import com.javainuse.model.DAOHospitalDetails;
import com.javainuse.model.DAOUser;
import com.javainuse.model.DAOUserDetails;

@Repository
public interface UserDao extends CrudRepository<DAOUser,Long> {
	
	DAOUser findByUsername(String username);
	
	/*@Query("SELECT id,name, role FROM user WHERE username = :username AND role = :role")
	DAOUser getId(@Param("username") String username, @Param("role") String role);*/

}