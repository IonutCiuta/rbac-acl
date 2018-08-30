package com.ionut.ciuta.posd1.service;

import com.ionut.ciuta.posd1.model.sql.User;
import com.ionut.ciuta.posd1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ionutciuta24@gmail.com on 26.10.2017.
 */
@Service
public class AuthService {
    @Autowired
    private Storage storage;

    @Autowired
    private UserRepository userRepository;

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

    public boolean isRoot(String user, String pass) {
        User dbUser = userRepository.findByName("root");
        return dbUser.getName().equals(user) && dbUser.getPass().equals(pass);
    }
}
