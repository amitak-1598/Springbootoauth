/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.authserver.AuthenticationServer.service;

import com.authserver.AuthenticationServer.dto.CustomUserDetails;
import com.authserver.AuthenticationServer.model.Customer;
import com.authserver.AuthenticationServer.repository.CustomerRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class CustomerDetailsService implements UserDetailsService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(username);
        System.out.println("Customer details fetched successfully " + customer);
        if (customer != null) {
            Customer customerNew = new Customer();
            customerNew.setUsername(customer.getUsername());
            customerNew.setPassword(customer.getPassword());
            return new CustomUserDetails(customerNew);
        }
        throw new UsernameNotFoundException(username);
    }
}
