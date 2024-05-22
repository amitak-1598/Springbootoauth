/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.authserver.AuthenticationServer.dto;

import com.authserver.AuthenticationServer.model.Customer;
import java.util.Collection;
import java.util.Collections;
import static org.apache.tomcat.jni.User.username;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class CustomUserDetails implements UserDetails {

    private Customer customer;
    // Add other properties as needed

    public CustomUserDetails(Customer customer) {
        this.customer = customer;
    }

    // Constructors, getters, and setters
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return a collection of authorities if needed
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return customer.getPassword();
    }

    @Override
    public String getUsername() {
        return customer.getUsername();
    }

    // Other methods...
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
