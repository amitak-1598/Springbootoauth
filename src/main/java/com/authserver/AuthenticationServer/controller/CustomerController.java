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
@RequestMapping("/api/v1")
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

	// Signup
	@PostMapping("/auth/signup")
	public ResponseEntity<Object> signup(@RequestBody Customer customer) {
		try {
			Customer customerFromDb = customerRepository.findByUsername(customer.getUsername());
			if (customerFromDb != null) {
				return ResponseHandler.generateResponse("UserName Already Exist", HttpStatus.BAD_REQUEST, null);
			}
			customer.setPassword(passwordEncoder.encode(customer.getPassword()));
			System.out.println(customer);
			customerRepository.save(customer);
			return ResponseHandler.generateResponse("Customer created successfully", HttpStatus.CREATED, customer);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseHandler.generateResponse("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR, null);
		}
	}

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

	// Login
	@PostMapping("/auth/login")
	public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
		try {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					loginRequest.getUsername(), loginRequest.getPassword());

			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			OAuth2Request oAuth2Request = new OAuth2Request(null, "client_id", null, true,
					new HashSet<>(Arrays.asList("read", "write")), null, null, null, null);
			OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
			

			OAuth2AccessToken token = tokenServices.createAccessToken(oAuth2Authentication);


			return ResponseHandler.generateResponse("Login successful", HttpStatus.OK, token);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseHandler.generateResponse("Invalid username or password", HttpStatus.UNAUTHORIZED, null);
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

	// Refresh
	@PostMapping("/auth/refresh")
	public ResponseEntity<Object> refresh(@RequestParam("refresh_token") String refreshToken) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;

			TokenRequest tokenRequest = new TokenRequest(new HashMap<>(),
					oAuth2Authentication.getOAuth2Request().getClientId(),
					oAuth2Authentication.getOAuth2Request().getScope(), "refresh_token");

			OAuth2AccessToken accessToken = tokenServices.refreshAccessToken(refreshToken, tokenRequest);
			return ResponseHandler.generateResponse("Token refreshed successfully", HttpStatus.OK, accessToken);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseHandler.generateResponse("Invalid refresh token", HttpStatus.UNAUTHORIZED, null);
		}
	}
	
	//RefreshSecond
	@PostMapping("/auth/refresh2")
    public ResponseEntity<Object> refreshSecondWay(@RequestParam("refresh_token") String refreshToken) {
        try {
          
            String clientId = "client_id"; 
            Set<String> scopes = new HashSet<>(Arrays.asList("read","write")); 

            TokenRequest tokenRequest = new TokenRequest(new HashMap<>(), clientId, scopes, "refresh_token");

            OAuth2AccessToken accessToken = tokenServices.refreshAccessToken(refreshToken, tokenRequest);
            return ResponseHandler.generateResponse("Token refreshed successfully", HttpStatus.OK, accessToken);
        } catch (InvalidTokenException e) {
            return ResponseHandler.generateResponse("Invalid refresh token", HttpStatus.UNAUTHORIZED, null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
	
	
    // Logout
	@PostMapping("/auth/logout")
	public ResponseEntity<Object> logout(HttpServletRequest request) {
		try {
			
			new SecurityContextLogoutHandler().logout(request, null, null);

			
			String authorizationHeader = request.getHeader("Authorization");
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				String accessToken = authorizationHeader.substring(7);			
				OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(accessToken);
				if (oAuth2AccessToken != null) {
					tokenStore.removeAccessToken(oAuth2AccessToken);
				}
			}

			return ResponseHandler.generateResponse("Logout successful", HttpStatus.OK, null);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseHandler.generateResponse("Error occurred during logout", HttpStatus.INTERNAL_SERVER_ERROR,
					null);
		}
	}
	
	
	// CurrentUser
	@GetMapping("/current-user")
	public String getLoggedInUser(Principal principal) {
		return principal.getName();
	}
	


}
