package com.authserver.AuthenticationServer;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.authserver.AuthenticationServer.controller.AuthenticationController;
//import com.authserver.AuthenticationServer.model.Customer;
//import com.authserver.AuthenticationServer.repository.CustomerRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.Arrays;
//import java.util.List;
//
//@SpringBootTest
//@AutoConfigureMockMvc
public class AuthenticationControllerTest{

//	
//
//	    @Autowired
//	    private MockMvc mockMvc;
//
//	    @MockBean
//	    private CustomerRepository customerRepository;
//
//	    @InjectMocks
//	    private AuthenticationController authenticationController;
//
//	    private ObjectMapper objectMapper = new ObjectMapper();
//
//	    @BeforeEach
//	    public void setup() {
//	        MockitoAnnotations.initMocks(this);
//	        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
//	    }

//	    @Test
//	    public void testGetAllUsersSuccess() throws Exception {
//	        // Mock Customer data
//	        Customer customer1 = new Customer(); // Add appropriate fields and values
//	        Customer customer2 = new Customer(); // Add appropriate fields and values
//	        List<Customer> customers = Arrays.asList(customer1, customer2);
//
//	        // Mock repository call
//	        when(customerRepository.findAll()).thenReturn(customers);
//
//	        // Perform the GET request and verify the response
//	        mockMvc.perform(get("/api/v1/user/all")
//	                .contentType(MediaType.APPLICATION_JSON))
//	                .andExpect(status().isOk())
//	                .andExpect(jsonPath("$.message").value("Users Fetched Successfully"))
//	                .andExpect(jsonPath("$.data[0]").exists()) // Check if data array is not empty
//	                .andExpect(jsonPath("$.data[1]").exists()); // Check if data array has at least 2 elements
//	    }

//	    @Test
//	    public void testGetAllUsersFailure() throws Exception {
//	        // Mock repository to throw an exception
//	        when(customerRepository.findAll()).thenThrow(new RuntimeException("Database error"));
//
//	        // Perform the GET request and verify the response
//	        mockMvc.perform(get("/api/v1/user/all")
//	                .contentType(MediaType.APPLICATION_JSON))
//	                .andExpect(status().isInternalServerError())
//	                .andExpect(jsonPath("$.message").value("Something went wrong"));
//	    }
	}





