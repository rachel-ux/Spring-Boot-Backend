package com.javainuse.controller;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.javainuse.service.JwtUserDetailsService;
import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;
import com.javainuse.config.JwtTokenUtil;
import com.javainuse.model.AvailableBeds;
import com.javainuse.model.AvailableName;
import com.javainuse.model.AvailableUserDetails;
import com.javainuse.model.BedStatusUpdate;
import com.javainuse.model.BedUpdate;
import com.javainuse.model.DAOBed;
import com.javainuse.model.DAOHealthDetails;
import com.javainuse.model.DAOHospitalDetails;
import com.javainuse.model.HealthDetailsDTO;
import com.javainuse.model.HospitalDetailsDTO;
import com.javainuse.model.JwtRequest;
import com.javainuse.model.JwtResponse;
import com.javainuse.model.SearchUser;
import com.javainuse.model.StatusUpdateDTO;
import com.javainuse.model.SymptomsUpdate;
import com.javainuse.model.UserDTO;
import com.javainuse.model.UserDetailsDTO;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
        final Object[] roles = userDetails.getAuthorities().toArray();
		return ResponseEntity.ok(new JwtResponse(token,roles[0],userDetails.getUsername()));
	}
	
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
		return ResponseEntity.ok(userDetailsService.save(user));
	}
	
	@RequestMapping(value = "/savedetails", method = RequestMethod.POST)
		public ResponseEntity<?> saveUserDetails(@RequestBody UserDetailsDTO userDetails){
			return ResponseEntity.ok(userDetailsService.savedetails(userDetails));
	}
	
	@RequestMapping(value = "/savehospitaldetails", method = RequestMethod.POST)
	public ResponseEntity<?> saveHospitalDetails(@RequestBody HospitalDetailsDTO hospitalDetails){
		return ResponseEntity.ok(userDetailsService.savehospitalDetails(hospitalDetails));
	}
	
	@RequestMapping(value = "/updatestatus", method = RequestMethod.PUT)
	public boolean updateUserStatus(@RequestBody StatusUpdateDTO requiredUpdate){
		boolean message = userDetailsService.updateStatus(requiredUpdate.getUsername(), requiredUpdate.getStatus());
		return message;
	}
	
	@RequestMapping(value = "/updatesymptoms", method = RequestMethod.PUT)
	public boolean updateUserSymptoms(@RequestBody SymptomsUpdate updatedSymptoms) {
		boolean message  = userDetailsService.updateSymptoms(updatedSymptoms.getUsername(), updatedSymptoms.getSymptoms());
		return message;
	}
	
	@RequestMapping(value = "/updatebed", method = RequestMethod.PUT)
	public boolean updateUserBed(@RequestBody BedUpdate bedupdate) {
		boolean message = userDetailsService.updateBed(bedupdate.getUsername(), bedupdate.getBed());
		return message;
	}
	
	@RequestMapping(value = "/gethospitaldetails", method = RequestMethod.GET)
	public ArrayList<DAOHospitalDetails> getallHospitalDetails(){
		ArrayList<DAOHospitalDetails> allHospitalDetails;
		allHospitalDetails = userDetailsService.getAllHospitalDetails();
		return allHospitalDetails;
	}
	
	@RequestMapping(value = "/getrecoveredpatients", method = RequestMethod.GET)
	public ArrayList<DAOHospitalDetails> getallrecoveredpatients(){
		ArrayList<DAOHospitalDetails> allrecoveredpatients;
		allrecoveredpatients = userDetailsService.getrecoveredpatients();
		return allrecoveredpatients;
	}
	
	@RequestMapping(value = "/getdeceasedpatients", method = RequestMethod.GET)
	public ArrayList<DAOHospitalDetails> getalldeceasedpatients(){
		ArrayList<DAOHospitalDetails> allrecoveredpatients;
		allrecoveredpatients = userDetailsService.getdeceasedpatients();
		return allrecoveredpatients;
	}
	@RequestMapping(value = "/getmildpatients", method = RequestMethod.GET)
	public ArrayList<DAOHospitalDetails> getallmildpatients(){
		ArrayList<DAOHospitalDetails> allrecoveredpatients;
		allrecoveredpatients = userDetailsService.getmildpatients();
		return allrecoveredpatients;
	}
	
	@RequestMapping(value = "/getcriticalpatients", method = RequestMethod.GET)
	public ArrayList<DAOHospitalDetails> getallcriticalpatients(){
		ArrayList<DAOHospitalDetails> allrecoveredpatients;
		allrecoveredpatients = userDetailsService.getcriticalpatients();
		return allrecoveredpatients;
	}
	@RequestMapping(value = "/getbedsward", method = RequestMethod.POST)
	public ArrayList<DAOBed> getallavailableBedsAWard(@RequestBody AvailableBeds ward){
		ArrayList<DAOBed> availableBeds;
		String status = "notbooked";
		availableBeds = userDetailsService.getavailableBeds(status,ward.getWard());
		return availableBeds;
	}
	
	@RequestMapping(value = "/updatebedstatus", method = RequestMethod.PUT)
	public boolean updateBedStatus(@RequestBody BedStatusUpdate statusUpdate) {
		boolean message = userDetailsService.updateBedStatus(statusUpdate.getStatus(), statusUpdate.getId()); 
		return message;
	}
	
	
	@RequestMapping(value = "/getHealthDetails", method = RequestMethod.GET)
	public ArrayList<DAOHealthDetails> getallHealthDetails(){
		ArrayList<DAOHealthDetails> allHealthDetails;
		allHealthDetails = userDetailsService.getAllHealthDetails();
		return allHealthDetails;
	}
	@RequestMapping(value = "/savehealthdetails", method = RequestMethod.POST)
	public ResponseEntity<?> saveHealthDetails(@RequestBody HealthDetailsDTO healthDetails ){
		return ResponseEntity.ok(userDetailsService.savehealthDetails(healthDetails));
	}
	@RequestMapping(value = "/search", method = RequestMethod.POST)
		public ArrayList<AvailableUserDetails> giveUserDetails(@RequestBody SearchUser searchUser){
		ArrayList<AvailableUserDetails> availableUser;
		availableUser = userDetailsService.giveUserDetails(searchUser.getName());
			return availableUser;
	}
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}