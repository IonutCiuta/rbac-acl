package com.ionut.ciuta.sci1hw.service;

import com.ionut.ciuta.sci1hw.exception.ResourceNotFound;
import com.ionut.ciuta.sci1hw.exception.ResourceOpertaionNotPermitted;
import com.ionut.ciuta.sci1hw.exception.UnauthorizedUser;
import com.ionut.ciuta.sci1hw.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessService {
    @Autowired
    private AuthService authService;

    @Autowired
    private ResourceService resourceService;

    public Resource read(String user, String pass, String file) {
        if(!authService.isAuthenticated(user, pass)) {
            throw new UnauthorizedUser();
        }

        Resource resource = resourceService.find(file);

        if(resource == null) {
            throw new ResourceNotFound();
        }

        if(resource.owner.equals(user)) {
            return resource;
        } else {
            if(resource.permission.equals(Resource.Permission.R) ||
                    resource.permission.equals(Resource.Permission.RW)) {
                return resource;
            } else {
                throw new ResourceOpertaionNotPermitted();
            }
        }
    }
}
