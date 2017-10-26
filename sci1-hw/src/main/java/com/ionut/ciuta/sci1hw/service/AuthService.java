package com.ionut.ciuta.sci1hw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ionutciuta24@gmail.com on 26.10.2017.
 */
@Service
public class AuthService {
    @Autowired
    private Storage storage;

    public boolean isAuthenticated(String user, String pass) {
        return storage.isUser(user) && pass.equals(storage.getPass(user));
    }

    public boolean createUser(String user, String pass) {
        if(!storage.isUser(user) && pass != null && !pass.isEmpty()) {
            storage.addUser(user, pass);
            return true;
        }

        return false;
    }
}
