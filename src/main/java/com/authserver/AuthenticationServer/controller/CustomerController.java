package com.authserver.AuthenticationServer.controller;


import com.authserver.AuthenticationServer.model.Customer;
import com.authserver.AuthenticationServer.repository.CustomerRepository;
import com.authserver.AuthenticationServer.response.ResponseHandler;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v2")
public class CustomerController {

	@Autowired
	CustomerRepository customerRepository;
	
	
	
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
