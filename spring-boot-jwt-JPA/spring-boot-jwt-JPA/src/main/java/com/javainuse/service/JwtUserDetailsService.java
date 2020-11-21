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

import com.javainuse.dao.BedDao;
import com.javainuse.dao.HealthDetailsDao;
import com.javainuse.dao.HospitalDetailsDao;
import com.javainuse.dao.UserDao;
import com.javainuse.dao.UserDetailsDao;

import antlr.collections.List;

@Service
public class JwtUserDetailsService implements UserDetailsService{
	

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
	
	@Autowired
	private BedDao bedDao;


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
	
	public ArrayList<AvailableUserDetails> giveUserDetails(String name) {
		ArrayList<AvailableUserDetails> availableUsers = new ArrayList<AvailableUserDetails>(100);
		ArrayList<DAOUser> savedUser = userDao.selectbyRecord(name);
		for(int i = 0; i < savedUser.size(); i++) {
			String username  = (String)savedUser.get(i).getUsername();
			String name1 = (String)savedUser.get(i).getName();
			DAOUserDetails savedUserDetails = userDetailsDao.selectbyRecord(username);
			DAOHospitalDetails savedHospitalDetails = hospitalDetailsDao.selectbyRecord(username);
			DAOHealthDetails savedHealthDetails = healthDetailsDao.selectbyRecord(username);
			AvailableUserDetails useravailable = new AvailableUserDetails();
			useravailable.setAddress(savedUserDetails.getAddress());
			useravailable.setPhone(savedUserDetails.getPhone());
			useravailable.setEmail(username);
			useravailable.setName(name1);
			useravailable.setBlood_group(savedHealthDetails.getBloodgroup());
			useravailable.setOrgan_donor(savedHealthDetails.getOrgandonor());
			useravailable.setBed(savedHospitalDetails.getBed());
			useravailable.setSymptoms(savedHospitalDetails.getSymptoms());
			useravailable.setStatus(savedHospitalDetails.getStatus());
			availableUsers.add(i,useravailable);
		}
		return availableUsers;
	}
	public DAOUserDetails savedetails(UserDetailsDTO userDetails) {
		DAOUserDetails savedUser = new DAOUserDetails();
		savedUser.setAddress(userDetails.getAddress());
		savedUser.setPhone(userDetails.getPhone());
		savedUser.setUsername(userDetails.getUsername());
		return userDetailsDao.save(savedUser);
		
	}
	
	public ArrayList<DAOHospitalDetails> getAllHospitalDetails(){
		return hospitalDetailsDao.selectAllRecords();
	}
	
	public ArrayList<DAOBed> getavailableBeds(String status, String ward){
		char ward1 = ward.charAt(0);
		System.out.println(ward1);
		ArrayList<DAOBed> availableBeds = bedDao.getAvailableBed(status,ward1);
		return availableBeds;
	}
	
	public boolean updateBedStatus(String status, long id) {
		boolean message = false;
		System.out.println(status);
		DAOBed returnedBed = bedDao.findById(id);
		if(id == returnedBed.getId()) {
			returnedBed.status = status;
			bedDao.save(returnedBed);
			message = true;
		}
		return message;
	}
	
	public ArrayList<DAOHealthDetails> getAllHealthDetails(){
		return healthDetailsDao.selectAllRecords();
	}
	
	public ArrayList<DAOHospitalDetails> getrecoveredpatients(){
		ArrayList<DAOHospitalDetails> allrecoveredpatients;
		String status  = "recovered";
		allrecoveredpatients = hospitalDetailsDao.selectbyOneRecord(status);
		return allrecoveredpatients;
	}
	
	public ArrayList<DAOHospitalDetails> getdeceasedpatients(){
		ArrayList<DAOHospitalDetails> allrecoveredpatients;
		String status  = "deceased";
		allrecoveredpatients = hospitalDetailsDao.selectbyOneRecord(status);
		return allrecoveredpatients;
	}
	
	public ArrayList<DAOHospitalDetails> getmildpatients(){
		ArrayList<DAOHospitalDetails> allrecoveredpatients;
		String status  = "mild";
		allrecoveredpatients = hospitalDetailsDao.selectbyOneRecord(status);
		return allrecoveredpatients;
	}
	
	public ArrayList<DAOHospitalDetails> getcriticalpatients(){
		ArrayList<DAOHospitalDetails> allrecoveredpatients;
		String status  = "critical";
		allrecoveredpatients = hospitalDetailsDao.selectbyOneRecord(status);
		return allrecoveredpatients;
	}
	
	public boolean updateStatus(String username, String status) {
		boolean message= false;
		System.out.println(username);
		DAOHospitalDetails alreadyPresentUser = hospitalDetailsDao.selectbyRecord(username);
		if(alreadyPresentUser.getUsername().equalsIgnoreCase(username)) {
			alreadyPresentUser.status = status;
			hospitalDetailsDao.save(alreadyPresentUser);
			message = true;
		}
		return message;
	}
	public boolean updateSymptoms(String username, String symptoms) {
		boolean message = false;
		DAOHospitalDetails alreadyavailableUser = hospitalDetailsDao.selectbyRecord(username);
		if(alreadyavailableUser.getUsername().equalsIgnoreCase(username)) {
			alreadyavailableUser.symptoms = symptoms;
			hospitalDetailsDao.save(alreadyavailableUser);
			message = true;
		}
		return message;
	}
	
	public boolean updateBed(String username, String bed) {
		boolean message = false;
		System.out.println(username);
		DAOHospitalDetails PresentUser = hospitalDetailsDao.selectbyRecord(username);
		if(PresentUser.getUsername().equalsIgnoreCase(username)) {
			PresentUser.bed = bed;
			hospitalDetailsDao.save(PresentUser);
			message = true;
		}
		return message;
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