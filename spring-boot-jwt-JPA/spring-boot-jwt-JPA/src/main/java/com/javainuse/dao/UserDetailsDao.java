package com.javainuse.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.javainuse.model.DAOUserDetails;

@Repository
public interface UserDetailsDao extends CrudRepository<DAOUserDetails,Long> {
	DAOUserDetails findByUsername(String username);
}
