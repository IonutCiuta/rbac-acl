package com.ionut.ciuta.posd1.service;

import com.ionut.ciuta.posd1.exception.ResourceOperationNotPermitted;
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
    private UserRepository userRepository;

    public boolean isAuthenticated(String user, String pass) {
        User dbUser = userRepository.findByName(user);
        return dbUser != null && dbUser.getName().equals(user) && dbUser.getPass().equals(pass);
    }
}
