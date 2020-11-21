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
import com.javainuse.model.AvailableName;
import com.javainuse.model.AvailableUserDetails;
import com.javainuse.model.JwtRequest;
import com.javainuse.model.JwtResponse;
import com.javainuse.model.SearchUser;
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
	/*@RequestMapping(value = "/authenticate/search", method = RequestMethod.POST)
		public ArrayList<AvailableUserDetails> giveUserDetails(@RequestBody SearchUser searchUser){
		ArrayList<AvailableUserDetails> availableUser;
		availableUser = userDetailsService.
			return availableUser;
	}*/
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