package com.ionut.ciuta.posd1.service;

import com.ionut.ciuta.posd1.Values;
import com.ionut.ciuta.posd1.model.Folder;
import com.ionut.ciuta.posd1.model.Permission;
import com.ionut.ciuta.posd1.model.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
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

        Map<String, String> aliceAcl = new HashMap<>();
        aliceAcl.put(alice, Values.OWNER);
        resources.put(alice, new Folder(alice, Permission.NONE, alice, aliceAcl));

        Map<String, String> bobAcl = new HashMap<>();
        aliceAcl.put(bob, Values.OWNER);
        resources.put(bob, new Folder(bob, Permission.NONE, bob, bobAcl));
    }

    public Map<String, String> getUsers() {
        return users;
    }

    public Map<String, Resource> getResources() {
        return resources;
    }
}
