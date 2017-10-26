package com.ionut.ciuta.sci1hw.service;

import com.ionut.ciuta.sci1hw.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Queue;

/**
 * ionutciuta24@gmail.com on 26.10.2017.
 */
@Service
public class ResourceService {
    @Autowired
    private Storage storage;

    public boolean exists(String user, String name) {
        Resource resource = storage.getResource(user);
        if(resource == null) {
            return true;
        }



        return false;
    }

    /*public boolean checkResource(List<String> remaining, Resource resource) {
        if(remaining.isEmpty()) {
            return true;
        }

        if(resource.name.equals(remaining.get(0))) {

        }
    }*/

    public List<String> getPath(String name) {
        return Arrays.asList(name.split("/"));
    }
}
