package com.ionut.ciuta.sci1hw;

import com.ionut.ciuta.sci1hw.exception.ResourceNotFound;
import com.ionut.ciuta.sci1hw.exception.ResourceOpertaionNotPermitted;
import com.ionut.ciuta.sci1hw.exception.UnauthorizedUser;
import com.ionut.ciuta.sci1hw.model.File;
import com.ionut.ciuta.sci1hw.model.Folder;
import com.ionut.ciuta.sci1hw.model.Resource;
import com.ionut.ciuta.sci1hw.service.AccessService;
import com.ionut.ciuta.sci1hw.service.AuthService;
import com.ionut.ciuta.sci1hw.service.ResourceService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class ResourceAccessServiceTest {
    private final String userBob = "bob";
    private final String userAlice = "alice";
    private final String userBobPass = "bob";
    private final String userBobFile = "file.bob";
    private final String userAliceFile = "file.alice";

    @InjectMocks
    private AccessService resourceAccessService;

    @Mock
    private ResourceService resourceService;

    @Mock
    private AuthService authService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = UnauthorizedUser.class)
    public void readResourceShouldFailWithUnauthorizedUser() throws Exception {
        when(authService.isAuthenticated(any(), any())).thenReturn(false);

        resourceAccessService.read("", "", "");
    }

    @Test(expected = ResourceNotFound.class)
    public void readResourceShouldFailWithResourceNotFound() throws Exception {
        when(authService.isAuthenticated(any(), any())).thenReturn(true);
        when(resourceService.find(any())).thenReturn(null);

        resourceAccessService.read(userBob, userBobPass, userBobFile);
    }

    @Test(expected = ResourceOpertaionNotPermitted.class)
    public void readFolderShouldFailForNoPermissions() throws Exception {
        Folder folder = new Folder(userAlice, "", userAlice);

        when(authService.isAuthenticated(any(), any())).thenReturn(true);
        when(resourceService.find(any())).thenReturn(folder);

        resourceAccessService.read(userBob, userBobPass, userAlice);
    }

    @Test(expected = ResourceOpertaionNotPermitted.class)
    public void readFileShouldFailForNoPermissions() throws Exception {
        Folder folder = new Folder(userAlice, "", userAlice);
        File file = new File(userAliceFile, "", "", userAlice);
        folder.content.add(file);

        when(authService.isAuthenticated(any(), any())).thenReturn(true);
        when(resourceService.find(any())).thenReturn(folder);

        resourceAccessService.read(userBob, userBobPass, userAliceFile);
    }

    @Test(expected = ResourceOpertaionNotPermitted.class)
    public void readFolderShouldFailForInsufficientPermissions() throws Exception {
        Folder folder = new Folder(userAlice, Resource.Permission.W, userAlice);

        when(authService.isAuthenticated(any(), any())).thenReturn(true);
        when(resourceService.find(any())).thenReturn(folder);

        resourceAccessService.read(userBob, userBobPass, userAlice);
    }

    @Test(expected = ResourceOpertaionNotPermitted.class)
    public void readFileShouldFailForInsufficienPermissions() throws Exception {
        Folder folder = new Folder(userAlice, "", userAlice);
        File file = new File(userAliceFile, Resource.Permission.W, "", userAlice);
        folder.content.add(file);

        when(authService.isAuthenticated(any(), any())).thenReturn(true);
        when(resourceService.find(any())).thenReturn(folder);

        resourceAccessService.read(userBob, userBobPass, userAliceFile);
    }

    @Test
    public void readFolderShouldPassForReadPermissions() throws Exception {
        Folder folder = new Folder(userAlice, Resource.Permission.R, userAlice);

        when(authService.isAuthenticated(any(), any())).thenReturn(true);
        when(resourceService.find(any())).thenReturn(folder);

        assertEquals(folder, resourceAccessService.read(userBob, userBobPass, userAlice));
    }

    @Test
    public void readFileShouldPassForReadPermissions() throws Exception {
        Folder folder = new Folder(userAlice, "", userAlice);
        File file = new File(userAliceFile, Resource.Permission.R, "", userAlice);
        folder.content.add(file);

        when(authService.isAuthenticated(any(), any())).thenReturn(true);
        when(resourceService.find(any())).thenReturn(file);

        assertEquals(file, resourceAccessService.read(userBob, userBobPass, userAlice));
    }
}
