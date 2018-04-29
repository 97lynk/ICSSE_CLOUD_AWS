package com.a97lynk.login.service;

import com.a97lynk.login.persistence.entity.Privilege;
import com.a97lynk.login.persistence.entity.Role;
import com.a97lynk.login.persistence.entity.User;
import com.a97lynk.login.persistence.respository.PrivilegeRepository;
import com.a97lynk.login.persistence.respository.RoleRepository;
import com.a97lynk.login.persistence.respository.UserRepository;
import com.a97lynk.login.utils.FineGrained;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PrivilegeRepository privilegeRepository;

    @Autowired
    FineGrained fineGrained;


    private static final Logger logger
            = Logger.getLogger(UserService.class.getName());


    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Privilege getPrivilegeByName(String name) {
        return privilegeRepository.findByName(name);
    }


}
