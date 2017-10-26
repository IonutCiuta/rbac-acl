package com.ionut.ciuta.sci1hw.service;

import com.ionut.ciuta.sci1hw.model.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * ionutciuta24@gmail.com on 25.10.2017.
 */
@Component
public class Storage {
    private static Map<String, String> users = new HashMap<>();
    private static Map<String, Resource> resources = new HashMap<>();

    public boolean isUser(String user) {
        return users.containsKey(user);
    }

    public String getPass(String user) {
        return users.get(user);
    }

    public boolean isKnownUser(String user, String pass) {
        return users.containsKey(user) && pass.equals(users.get(user));
    }

    public void addUser(String user, String pass) {
        users.put(user, pass);
    }

    public boolean hasResource(String user) {
        return users.containsKey(user) && resources.get(user) != null;
    }

    public Resource getResource(String user) {
        return resources.get(user);
    }

    public void addResource(String user, Resource resource) {
        resources.put(user, resource);
    }
}
