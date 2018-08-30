package com.ionut.ciuta.posd1.ut;

import com.ionut.ciuta.posd1.model.sql.User;
import com.ionut.ciuta.posd1.repository.UserRepository;
import com.ionut.ciuta.posd1.service.AuthService;
import com.ionut.ciuta.posd1.service.Storage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * ionutciuta24@gmail.com on 26.10.2017.
 */
public class AuthServiceTest {
    private final String user = "user";
    private final String pass = "pass";

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void isAuthenticatedShouldPass() throws Exception {
        when(userRepository.findByName(user)).thenReturn(new User(user, pass));

        assertTrue(authService.isAuthenticated(user, pass));
    }

    @Test
    public void isAuthenticatedShouldFailForWrongPass() throws Exception {
        when(userRepository.findByName(user)).thenReturn(new User(user, "wrongPass"));
        assertFalse(authService.isAuthenticated(user, pass));
    }

    @Test
    public void isAuthenticatedShouldFailForNonExistingUser() throws Exception {
        when(userRepository.findByName(user)).thenReturn(null);
        assertFalse(authService.isAuthenticated(user, pass));
    }
}
