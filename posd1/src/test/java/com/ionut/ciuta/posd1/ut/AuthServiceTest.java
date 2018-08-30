package com.ionut.ciuta.posd1.ut;

import com.ionut.ciuta.posd1.model.File;
import com.ionut.ciuta.posd1.model.Permission;
import com.ionut.ciuta.posd1.model.Resource;
import com.ionut.ciuta.posd1.model.sql.Role;
import com.ionut.ciuta.posd1.model.sql.User;
import com.ionut.ciuta.posd1.repository.UserRepository;
import com.ionut.ciuta.posd1.service.AuthService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * ionutciuta24@gmail.com on 26.10.2017.
 */
public class AuthServiceTest {
    private final String user = "user";
    private final String pass = "pass";
    private final String role = "role";

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private User mockDbUser;

    @Mock
    private Role mockDbRole;

    @Mock
    private File mockFile;

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

    @Test
    public void isOwnerReturnsTrueForOwner() {
        Resource r = new File("file", "empty", user);
        assertTrue(authService.isOwner(user, r));
    }

    @Test
    public void isOwnerReturnsFalseForNonOwner() {
        Resource r = new File("file", Permission.NONE, "empty", null);
        assertFalse(authService.isOwner(user, r));
    }

    @Ignore
    @Test
    public void hasPermissionShouldReturnTrueForUserWithRights() {
        when(userRepository.findByName(user)).thenReturn(mockDbUser);
        when(mockDbUser.getRoles()).thenReturn(Set.of(mockDbRole));
        when(mockDbRole.getName()).thenReturn(role);
        when(mockDbRole.getPermissions()).thenReturn("r");
        assertTrue(authService.hasPermission(user, mockFile, "r"));
    }

    @Test
    public void hasPermissionShouldReturnFalseForUserWrongRights() {
        when(userRepository.findByName(user)).thenReturn(mockDbUser);
        when(mockDbUser.getRoles()).thenReturn(Set.of(mockDbRole));
        when(mockDbRole.getName()).thenReturn(role);
        when(mockDbRole.getPermissions()).thenReturn("r");
        assertFalse(authService.hasPermission(user, mockFile, "w"));
    }
}
