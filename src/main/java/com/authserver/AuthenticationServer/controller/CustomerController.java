/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.authserver.AuthenticationServer.controller;

import com.authserver.AuthenticationServer.dto.LoginRequest;
import com.authserver.AuthenticationServer.model.Customer;
import com.authserver.AuthenticationServer.repository.CustomerRepository;
import com.authserver.AuthenticationServer.response.ResponseHandler;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v2")
public class CustomerController {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

//	@Autowired
//	private AuthorizationServerTokenServices tokenServices;
	
	@Autowired
    private DefaultTokenServices tokenServices;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenStore tokenStore;

	

	// Get ALL user
	@GetMapping("/user/all")
	public ResponseEntity<Object> getAllUsers() {
		try {
			List<Customer> customers = customerRepository.findAll();
			return ResponseHandler.generateResponse("Users Fetched Successfully", HttpStatus.OK, customers);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseHandler.generateResponse("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR, null);
		}
	}

	
	// Test
	@GetMapping("/user/")
	public ResponseEntity<Object> testAPI() {
		try {
			System.out.println("Welcome to payvang");

			return ResponseHandler.generateResponse("Users Fetched Successfully", HttpStatus.OK, null);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseHandler.generateResponse("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR, null);
		}
	}


	

	     // CurrentUser
		@GetMapping("/current-user")
		public String getLoggedInUser(Principal principal) {
			return principal.getName();
		}
		
   
	
	
	
	


}
