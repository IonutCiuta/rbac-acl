package com.ionut.ciuta.posd1.service;

import com.ionut.ciuta.posd1.model.Folder;
import com.ionut.ciuta.posd1.model.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ionutciuta24@gmail.com on 25.10.2017.
 */
@Component
public class Storage {
    private Map<String, String> users = new ConcurrentHashMap<>();
    private Map<String, Resource> resources = new ConcurrentHashMap<>();

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

    @PostConstruct
    public void setupStorage() {
        final String bob = "bob", alice = "alice";

        users.put(bob, bob);
        users.put(alice, alice);

        resources.put(bob, new Folder(bob, Resource.Permission.RW, bob));
        resources.put(alice, new Folder(alice, Resource.Permission.RW, alice));
    }
}