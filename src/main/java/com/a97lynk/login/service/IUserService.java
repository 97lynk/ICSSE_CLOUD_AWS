/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.a97lynk.login.service;

import com.a97lynk.login.persistence.entity.Privilege;
import com.a97lynk.login.persistence.entity.Role;
import com.a97lynk.login.persistence.entity.User;

/**
 * @author 97lynk
 */
public interface IUserService {


    User getUserByEmail(String email);

    Role getRoleByName(String name);

    Privilege getPrivilegeByName(String name);

}
