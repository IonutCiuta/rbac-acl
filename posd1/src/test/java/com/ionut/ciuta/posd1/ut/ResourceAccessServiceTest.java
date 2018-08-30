package com.ionut.ciuta.posd1.ut;

import com.ionut.ciuta.posd1.exception.ResourceNotFound;
import com.ionut.ciuta.posd1.exception.ResourceOperationNotPermitted;
import com.ionut.ciuta.posd1.exception.UnauthorizedUser;
import com.ionut.ciuta.posd1.model.*;
import com.ionut.ciuta.posd1.model.sql.Role;
import com.ionut.ciuta.posd1.model.sql.User;
import com.ionut.ciuta.posd1.service.AuthService;
import com.ionut.ciuta.posd1.service.ResourceAccessService;
import com.ionut.ciuta.posd1.service.ResourceService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class ResourceAccessServiceTest {
    private final String userBob = "bob";
    private final String userAlice = "alice";
    private final String userBobPass = "bob";
    private final String userBobFile = "file.bob";
    private final String userAliceFile = "file.alice";

    private final User dbUserBob = new User(userBob, userBob);
    private final User dbUserAlice = new User(userAlice, userAlice);
    private final String readRole = "readRole";
    private final String writeRole = "writeRole";
    private final Role dbReadRole = new Role(readRole, Permission.R);
    private final Role dbWriteRole = new Role(writeRole, Permission.W);

    @InjectMocks
    private ResourceAccessService resourceAccessService;

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

    @Test(expected = ResourceOperationNotPermitted.class)
    public void readFolderShouldFailForNoPermissions() throws Exception {
        Folder folder = new Folder(userAlice, "", userAlice);

        when(authService.isAuthenticated(any(), any())).thenReturn(true);
        when(resourceService.find(any())).thenReturn(folder);

        resourceAccessService.read(userBob, userBobPass, userAlice);
    }

    @Test(expected = ResourceOperationNotPermitted.class)
    public void readFileShouldFailForNoPermissions() throws Exception {
        Folder folder = new Folder(userAlice, "", userAlice);
        File file = new File(userAliceFile, "", "", userAlice);
        folder.content.add(file);

        when(authService.isAuthenticated(any(), any())).thenReturn(true);
        when(resourceService.find(any())).thenReturn(folder);

        resourceAccessService.read(userBob, userBobPass, userAliceFile);
    }

    @Test(expected = ResourceOperationNotPermitted.class)
    public void readFolderShouldFailForInsufficientPermissions() throws Exception {
        Folder folder = new Folder(userAlice, Permission.W, userAlice);

        when(authService.isAuthenticated(any(), any())).thenReturn(true);
        when(resourceService.find(any())).thenReturn(folder);

        resourceAccessService.read(userBob, userBobPass, userAlice);
    }

    @Test(expected = ResourceOperationNotPermitted.class)
    public void readFileShouldFailForInsufficienPermissions() throws Exception {
        Folder folder = new Folder(userAlice, "", userAlice);
        File file = new File(userAliceFile, Permission.W, "", userAlice);
        folder.content.add(file);

        when(authService.isAuthenticated(any(), any())).thenReturn(true);
        when(resourceService.find(any())).thenReturn(folder);

        resourceAccessService.read(userBob, userBobPass, userAliceFile);
    }

    @Test
    public void readEmptyFolderShouldPassForReadPermissions() throws Exception {
        Folder folder = new Folder(userAlice, Permission.R, userAlice);
        folder.acl.put(userBob, readRole);

        when(authService.isAuthenticated(any(), any())).thenReturn(true);
        when(authService.isOwner(userBob, folder)).thenReturn(false);
        when(authService.canRead(userBob, folder)).thenReturn(true);
        when(resourceService.find(any())).thenReturn(folder);

        assertEquals("", resourceAccessService.read(userBob, userBobPass, userAlice));
    }

    @Test
    public void readFolderShouldPassForReadPermissions() throws Exception {
        Folder folder = new Folder(userAlice, Permission.R, userAlice);
        Folder subfolder = new Folder(userBob, Permission.R, userAlice);
        File file = new File(userAliceFile, Permission.R, userAliceFile, userAlice);
        folder.content.add(subfolder);
        folder.content.add(file);

        when(authService.isAuthenticated(any(), any())).thenReturn(true);
        when(authService.isOwner(userBob, folder)).thenReturn(false);
        when(authService.canRead(userBob, folder)).thenReturn(true);
        when(resourceService.find(any())).thenReturn(folder);

        assertEquals(userBob.concat("/ ").concat(userAliceFile).concat(""), resourceAccessService.read(userBob, userBobPass, userAlice));
    }

    @Test
    public void readFileShouldPassForReadPermissions() throws Exception {
        Folder folder = new Folder(userAlice, "", userAlice);
        File file = new File(userAliceFile, Permission.R, userAliceFile, userAlice);
        folder.content.add(file);

        when(authService.isAuthenticated(any(), any())).thenReturn(true);
        when(authService.isOwner(userBob, file)).thenReturn(false);
        when(authService.canRead(userBob, file)).thenReturn(true);
        when(resourceService.find(any())).thenReturn(file);

        assertEquals(file.content, resourceAccessService.read(userBob, userBobPass, userAlice));
    }

    @Test(expected = UnauthorizedUser.class)
    public void writeShouldFailWithUnauthorizedUser() throws Exception {
        when(authService.isAuthenticated(any(), any())).thenReturn(false);

        resourceAccessService.write("", "", "", "");
    }

    @Test(expected = ResourceNotFound.class)
    public void writeResourceShouldFailWithResourceNotFound() throws Exception {
        when(authService.isAuthenticated(any(), any())).thenReturn(true);
        when(resourceService.find(any())).thenReturn(null);

        resourceAccessService.write(userBob, userBobPass, userBobFile, "");
    }

    @Test(expected = ResourceNotFound.class)
    public void writeFolderShouldFailWithResourceNotFound() throws Exception {
        when(authService.isAuthenticated(any(), any())).thenReturn(true);
        when(resourceService.find(any())).thenReturn(new Folder("", "", ""));

        resourceAccessService.write(userBob, userBobPass, userBobFile, "");
    }

    @Test(expected = ResourceOperationNotPermitted.class)
    public void writeShouldFailForNoPermissions() throws Exception {
        File file = new File(userAlice, Permission.R, userAliceFile, userAlice);

        when(authService.isAuthenticated(any(), any())).thenReturn(true);
        when(resourceService.find(any())).thenReturn(file);

        resourceAccessService.write(userBob, userBobPass, userAlice, userBobFile);
    }

    @Test
    public void writeShouldPassForWritePermission() throws Exception {
        File file = new File(userAlice, Permission.RW, userAliceFile, userAlice);

        when(authService.isAuthenticated(any(), any())).thenReturn(true);
        when(authService.isOwner(userBob, file)).thenReturn(false);
        when(authService.canWrite(userBob, file)).thenReturn(true);
        when(authService.canRead(userBob, file)).thenReturn(true);
        when(resourceService.find(any())).thenReturn(file);

        resourceAccessService.write(userBob, userBobPass, userAlice, userBobFile);
        assertEquals(userBobFile, resourceAccessService.read(userBob, userBobPass, userAlice));
    }

    @Ignore
    @Test
    public void writeShouldPassForUserFile() throws Exception {
        File file = new File(userBob, Permission.W, userBobFile, userBob);

        when(authService.isAuthenticated(any(), any())).thenReturn(true);
        when(authService.isOwner(userBob, file)).thenReturn(false);
        when(authService.canWrite(userBob, file)).thenReturn(true);
        when(resourceService.find(any())).thenReturn(file);

        resourceAccessService.write(userBob, userBobPass, userAlice, userBobFile);
        assertEquals(userBobFile, resourceAccessService.read(userBob, userBobPass, userBob));
    }

    @Test(expected = UnauthorizedUser.class)
    public void changeRightsShouldFailWithUnauthorizedUser() throws Exception {
        when(authService.isAuthenticated(any(), any())).thenReturn(false);

        resourceAccessService.changeRights("", "", "", "");
    }

    @Test(expected = ResourceNotFound.class)
    public void changeRightsShouldFailWithResourceNotFound() throws Exception {
        when(authService.isAuthenticated(any(), any())).thenReturn(true);
        when(resourceService.find(any())).thenReturn(null);

        resourceAccessService.changeRights("", "", "", "");
    }

    @Test(expected = ResourceOperationNotPermitted.class)
    public void changeRightsShouldFailForNoPermissions() throws Exception {
        File file = new File(userAliceFile, Permission.NONE, userAliceFile, userAlice);

        when(authService.isAuthenticated(any(), any())).thenReturn(true);
        when(resourceService.find(any())).thenReturn(file);

        resourceAccessService.changeRights(userBob, userBobPass, userAliceFile, Permission.R);
    }

    @Ignore
    @Test
    public void changeRightsShouldPassForUserFile() throws Exception {
        File file = new File(userAliceFile, Permission.W, userAliceFile, userAlice);

        when(authService.isAuthenticated(any(), any())).thenReturn(true);
        when(resourceService.find(any())).thenReturn(file);

        resourceAccessService.changeRights(userBob, userBobPass, userAliceFile, Permission.RW);
        assertEquals(userAliceFile, resourceAccessService.read(userBob, userBobPass, userAliceFile));
    }

    @Test
    @Ignore
    public void createShouldPassForTheRightPermissionsAndPropeNewFile() throws Exception {
        Folder folder = new Folder("root", Permission.R, userBob);
        Folder subfolder = new Folder("folder", Permission.R, userBob);
        folder.content.add(subfolder);

        InsertionPoint insertionPoint = new InsertionPoint(subfolder, Collections.singletonList("newFile"));
        when(authService.isAuthenticated(any(), any())).thenReturn(true);
        when(resourceService.exists(any())).thenReturn(false);
        when(resourceService.find("root/folder/newFile")).thenReturn(folder);
        when(resourceService.findParent(any(), any())).thenReturn(insertionPoint);
        when(resourceService.createResourceFromPath(any(), any(), any(), any())).thenReturn(new File("newFile", "rw", "newFileContent"));

        resourceAccessService.create(userBob, userBobPass, "root/folder/newFile", "newFileContent");
        assertEquals("newFileContent", ((File)((Folder)folder.content.get(0)).content.get(0)).content);
    }
}
