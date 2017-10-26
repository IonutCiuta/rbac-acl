package com.ionut.ciuta.sci1hw;

import com.ionut.ciuta.sci1hw.service.AuthService;
import com.ionut.ciuta.sci1hw.service.Storage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.constraints.AssertTrue;

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
    private Storage storage;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void isAuthenticatedShouldPass() throws Exception {
        when(storage.isUser(user)).thenReturn(true);
        when(storage.getPass(user)).thenReturn(pass);

        assertTrue(authService.isAuthenticated(user, pass));
    }

    @Test
    public void isAuthenticatedShouldFail() throws Exception {
        when(storage.isUser(user)).thenReturn(true);
        when(storage.getPass(user)).thenReturn("wrongPass");

        assertFalse(authService.isAuthenticated(user, pass));
    }

    @Test
    public void createUserShouldSucceed() throws Exception {
        when(storage.isUser(user)).thenReturn(false);

        assertTrue(authService.createUser(user, pass));
    }

    @Test
    public void createUserShouldFailWhenUserExists() throws Exception {
        when(storage.isUser(user)).thenReturn(true);

        assertFalse(authService.createUser(user, pass));
    }

    @Test
    public void createUserShouldFailWhenInvalidPass() throws Exception {
        when(storage.isUser(user)).thenReturn(false);

        assertFalse(authService.createUser(user, ""));
    }
}
