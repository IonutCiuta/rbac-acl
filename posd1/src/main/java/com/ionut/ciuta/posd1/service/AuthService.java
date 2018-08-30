package com.ionut.ciuta.posd1.service;

import com.ionut.ciuta.posd1.POSD1;
import com.ionut.ciuta.posd1.Values;
import com.ionut.ciuta.posd1.exception.ResourceOperationNotPermitted;
import com.ionut.ciuta.posd1.model.Permission;
import com.ionut.ciuta.posd1.model.Resource;
import com.ionut.ciuta.posd1.model.sql.Role;
import com.ionut.ciuta.posd1.model.sql.User;
import com.ionut.ciuta.posd1.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ionutciuta24@gmail.com on 26.10.2017.
 */
@Service
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    public boolean isAuthenticated(String user, String pass) {
        User dbUser = userRepository.findByName(user);
        return dbUser != null && dbUser.getName().equals(user) && dbUser.getPass().equals(pass);
    }

    public boolean isOwner(String user, Resource resource) {
        log.info("isOwner() {} {}", user, resource);
        return resource != null
                && resource.owner != null
                && resource.owner.equals(user);
    }

    public boolean hasPermission(String user, Resource resource, String permission) {
        log.info("hasPermission() {} {}", user, resource);

        /* If owner, user can do anything with resource */
        if(isOwner(user, resource))
            return true;

        /* Sanity checks */
        if(user == null || resource == null || resource.acl == null)
            return false;

        User dbUser = userRepository.findByName(user);
        log.info("hasPermission(): dbUser -> {}", dbUser);

        /* Sanity checks */
        if(dbUser == null || dbUser.getRoles() == null)
            return false;

        return isInAcl(dbUser, resource, permission);
    }

    private boolean isInAcl(User user, Resource resource, String permission) {
        /* Handle negative cases */
        if(user == null || user.getRoles() == null || resource.acl == null)
            return false;

        /* Count matching dbRole - acl roles */
        long count = user.getRoles().stream().filter(dbRole ->
                resource.acl.contains(dbRole.getName()) && dbRole.getPermissions().contains(permission)).count();

        log.info("isInAcl() {} {} {} {}", count > 0 ? "yes" : "no", user, resource, permission);
        return count > 0;
    }

    public boolean canRead(String user, Resource resource) {
        return hasPermission(user, resource, Permission.R);
    }

    public boolean canWrite(String user, Resource resource) {
        return hasPermission(user, resource, Permission.W);
    }
}
