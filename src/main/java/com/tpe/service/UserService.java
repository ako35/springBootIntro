package com.tpe.service;

import com.tpe.domain.Role;
import com.tpe.domain.User;
import com.tpe.domain.enums.UserRole;
import com.tpe.dto.UserRequest;
import com.tpe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public void saveUser(UserRequest userRequest) {
        User myUser=new User();
        myUser.setFirstName(userRequest.getFirstName());
        myUser.setLastName(userRequest.getLastName());
        myUser.setUserName(userRequest.getUserName());
        // myUser.setPassword(userRequest.getPassword());
        String password= userRequest.getPassword();
        String encodedPassword=passwordEncoder.encode(password);
        myUser.setPassword(encodedPassword);
        // role setlenmeli
        Role role=roleService.getRoleByType(UserRole.ROLE_ADMIN);
        Set<Role> roles=new HashSet<>();
        roles.add(role);
        myUser.setRoles(roles);
    }



}
