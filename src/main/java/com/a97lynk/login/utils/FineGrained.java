package com.a97lynk.login.utils;

import com.a97lynk.login.persistence.entity.Privilege;
import com.a97lynk.login.persistence.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 97lynk
 */
@Component
public class FineGrained {

    public Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    public List<String> getPrivileges(Collection<Role> roles) {
//        List<String> privilegeNames = new ArrayList<>();
//        List<Privilege> privileges = new ArrayList<>();
//        for (Role role : roles) {
//            privileges.addAll(role.getPrivileges());
//        }
//
//        for (Privilege item : privileges) {
//            privilegeNames.add(item.getName());
//        }
//        return privilegeNames;
        return roles.stream().flatMap(r -> r.getPrivileges().stream())
                .map(Privilege::getName)
                .collect(Collectors.toList());
    }

    public List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (String privilege : privileges) {
//            authorities.add(new SimpleGrantedAuthority(privilege));
//        }
//        return authorities;
        return privileges.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
