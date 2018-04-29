/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.a97lynk.login.config;

import com.a97lynk.login.persistence.entity.User;
import com.a97lynk.login.service.UserService;
import com.a97lynk.login.utils.FineGrained;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

//import com.google.appengine.api.users.UserService;
/**
 * @author 97lynk
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserService service;

    @Autowired
    private FineGrained fineGrained;

    private static final Logger logger
            = Logger.getLogger(MyUserDetailsService.class.getName());

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        logger.log(Level.INFO, ">> Load user from database");

        User user = service.getUserByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException("");
        logger.log(Level.INFO, ">> Found: {0}", user.getEmail());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), user.isEnabled(), true, true,
                true, fineGrained.getAuthorities(user.getRoles()));
    }


}
