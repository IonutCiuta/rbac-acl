package com.ionut.ciuta.posd1.service;

import com.ionut.ciuta.posd1.exception.CustomException;
import com.ionut.ciuta.posd1.exception.ExceptionWithStatusCode;
import com.ionut.ciuta.posd1.exception.ResourceNotFound;
import com.ionut.ciuta.posd1.exception.ResourceOperationNotPermitted;
import com.ionut.ciuta.posd1.model.Permission;
import com.ionut.ciuta.posd1.model.sql.Role;
import com.ionut.ciuta.posd1.model.sql.User;
import com.ionut.ciuta.posd1.repository.RoleRepository;
import com.ionut.ciuta.posd1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ManagementService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void createRole(String user, String pass, String role) {
        if(isRoot(user, pass)) {
            roleRepository.save(new Role(role, Permission.NONE));
        }
    }

    public void changeRights(String user, String pass, String role, String rights) {
        if(isRoot(user, pass)) {
            Role dbRole = roleRepository.findByName(role);
            if(dbRole == null)
                throw new CustomException(HttpStatus.NOT_FOUND, "Role not found: " + role);
            dbRole.setPermissions(rights);
            roleRepository.save(dbRole);
        }
    }

    public void assignRole(String user, String pass, String assignee, String role) {
        if(isRoot(user, pass)) {
            User dbUser = userRepository.findByName(assignee);
            Role dbRole = roleRepository.findByName(role);
            if(dbUser == null)
                throw new CustomException(HttpStatus.NOT_FOUND, "User not found: " + assignee);
            if(dbRole == null)
                throw new CustomException(HttpStatus.NOT_FOUND, "Role not found: " + role);
            dbUser.getRoles().add(dbRole);
            userRepository.save(dbUser);
        }
    }

    private boolean isRoot(String user, String pass) {
        if(!user.equals("root") || !pass.equals("root"))
            throw new ResourceOperationNotPermitted();
        return true;
    }
}
