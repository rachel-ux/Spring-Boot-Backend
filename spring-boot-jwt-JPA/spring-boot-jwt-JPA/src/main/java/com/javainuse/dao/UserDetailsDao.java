package com.javainuse.dao;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.javainuse.model.DAOUser;
import com.javainuse.model.DAOUserDetails;

@Repository
public interface UserDetailsDao extends JpaRepository<DAOUserDetails,Long> {
	DAOUserDetails findByusername(String username);
	
	@Query("from DAOUserDetails where username = :name order by id desc")
	DAOUserDetails selectbyRecord(@Param("name") String username);
}
