package com.javainuse.service;

import com.javainuse.model.*;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.javainuse.dao.HealthDetailsDao;
import com.javainuse.dao.HospitalDetailsDao;
import com.javainuse.dao.UserDao;
import com.javainuse.dao.UserDetailsDao;
import com.javainuse.model.DAOUser;
import com.javainuse.model.UserDTO;

import antlr.collections.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private HospitalDetailsDao hospitalDetailsDao;
	
	@Autowired
	private HealthDetailsDao healthDetailsDao;
	
	@Autowired
	private UserDetailsDao userDetailsDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		DAOUser user = userDao.findByUsername(username);
		UserBuilder builder = null;
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		builder = org.springframework.security.core.userdetails.User.withUsername(username);
	    builder.password(user.getPassword());
	    builder.roles(user.getRole());
		return builder.build();
	}
	
	public ArrayList<AvailableUserDetails> giveUserDetails(String username){
		ArrayList<AvailableUserDetails> availableUsers = new ArrayList<AvailableUserDetails>();
		ArrayList<DAOUser> savedUser = userDao.selectbyRecord(username);
		ArrayList<DAOUserDetails> savedUserDetails = userDetailsDao.selectbyRecord(username);
		ArrayList<DAOHospitalDetails> savedHospitalDetails = hospitalDetailsDao.selectbyRecord(username);
		ArrayList<DAOHealthDetails> savedHealthDetails = healthDetailsDao.selectbyRecord(username);
		System.out.print(savedUser.get(0));
		System.out.println(availableUsers.get(0));
		return availableUsers;
	}
	public DAOUserDetails savedetails(UserDetailsDTO userDetails) {
		DAOUserDetails savedUser = new DAOUserDetails();
		savedUser.setAddress(userDetails.getAddress());
		savedUser.setPhone(userDetails.getPhone());
		savedUser.setUsername(userDetails.getUsername());
		return userDetailsDao.save(savedUser);
		
	}
	
	public DAOHospitalDetails savehospitalDetails(HospitalDetailsDTO hospitalDetails) {
		DAOHospitalDetails savedHospitalDetails = new DAOHospitalDetails();
		savedHospitalDetails.setBed(hospitalDetails.getBed());
		savedHospitalDetails.setStatus(hospitalDetails.getStatus());
		savedHospitalDetails.setSymptoms(hospitalDetails.getSymptoms());
		savedHospitalDetails.setUsername(hospitalDetails.getUsername());
		return hospitalDetailsDao.save(savedHospitalDetails);
	}
	public DAOHealthDetails savehealthDetails(HealthDetailsDTO healthDetails) {
		DAOHealthDetails savedHealthDetails = new DAOHealthDetails();
		savedHealthDetails.setBloodgroup(healthDetails.getBloodgroup());
		savedHealthDetails.setOrgandonor(healthDetails.getOrgandonor());
		savedHealthDetails.setUsername(healthDetails.getUsername());
		return healthDetailsDao.save(savedHealthDetails);
	}
	public DAOUser save(UserDTO user) {
		DAOUser newUser = new DAOUser();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setRole(user.getRole());
		newUser.setName(user.getName());
		return userDao.save(newUser);
	}
}