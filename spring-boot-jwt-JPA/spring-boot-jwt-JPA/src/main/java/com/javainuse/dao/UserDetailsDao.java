package com.javainuse.dao;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.javainuse.model.DAOUser;
import com.javainuse.model.DAOUserDetails;

@Repository
public interface UserDetailsDao extends CrudRepository<DAOUserDetails,Long> {
	DAOUserDetails findByUsername(String username);
	
	@Query("SELECT username, address, phone FROM DAOUserDetails WHERE name = :name")
	ArrayList<DAOUserDetails> selectbyRecord(@Param("name") String name);
}
