package com.ionut.ciuta.sci1hw;

import com.ionut.ciuta.sci1hw.exception.ResourceOperationNotPermitted;
import com.ionut.ciuta.sci1hw.model.File;
import com.ionut.ciuta.sci1hw.model.Folder;
import com.ionut.ciuta.sci1hw.model.InsertionPoint;
import com.ionut.ciuta.sci1hw.model.Resource;
import com.ionut.ciuta.sci1hw.service.ResourceBuilder;
import com.ionut.ciuta.sci1hw.service.ResourceService;
import com.ionut.ciuta.sci1hw.service.Storage;

import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * ionutciuta24@gmail.com on 26.10.2017.
 */
public class ResourceServiceTest {
    private final String user = "user";
    private final String pass = "pass";
    private final String root = "user";
    private final String folder = "folder";
    private final String file = "file.ext";
    private final String name = "user/folder/file.ext";
    private final String content = "fileContent";

    @InjectMocks
    private ResourceService resourceService;

    @Mock
    private Storage storage;

    @Mock
    private ResourceBuilder resourceBuilder;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getPathShouldReturnValidList() throws Exception {
        List<String> path = resourceService.getPath(name);
        assertTrue(path.get(0).equals(root));
        assertTrue(path.get(1).equals(folder));
        assertTrue(path.get(2).equals(file));
    }

    @Test
    public void existsShouldReturnFalseForEmptyResource() throws Exception {
        when(storage.getResource(user)).thenReturn(null);

        assertFalse(resourceService.exists(name));
    }

    @Test
    public void existsShouldReturnTrueForFolder() throws Exception {
        Folder rootFolder = new Folder(root, "");
        Folder childFolder = new Folder(folder, "");
        rootFolder.content.add(childFolder);

        when(storage.getResource(user)).thenReturn(rootFolder);
        assertTrue(resourceService.exists(String.join("/", root, folder)));
    }

    @Test
    public void existsShouldReturnFalseForFolder() throws Exception {
        Folder rootFolder = new Folder(root, "");
        Folder childFolder = new Folder("unknown", "");
        rootFolder.content.add(childFolder);

        when(storage.getResource(user)).thenReturn(rootFolder);
        assertFalse(resourceService.exists(String.join("/", root, folder)));
    }

    @Test
    public void existsShouldReturnTrueForFile() throws Exception {
        Folder rootFolder = new Folder(user, "");
        Folder childFolder = new Folder(folder, "");
        rootFolder.content.add(childFolder);
        childFolder.content.add(new File(file, "", content));

        when(storage.getResource(user)).thenReturn(rootFolder);
        assertTrue(resourceService.exists(String.join("/", user, folder, file)));
    }

    @Test
    public void existsShouldReturnFalseForFile() throws Exception {
        Folder rootFolder = new Folder(user, "");
        Folder childFolder = new Folder(folder, "");
        rootFolder.content.add(childFolder);
        childFolder.content.add(new File("unknown", "", content));

        when(storage.getResource(user)).thenReturn(rootFolder);
        assertFalse(resourceService.exists(String.join("/", user, folder, file)));
    }

    @Test
    public void findShouldReturnFile() throws Exception {
        Folder rootFolder = new Folder(user, "");
        Folder childFolder = new Folder(folder, "");
        rootFolder.content.add(childFolder);
        childFolder.content.add(new File(file, "", content));

        when(storage.getResource(user)).thenReturn(rootFolder);
        assertEquals(file, resourceService.find(String.join("/", user, folder, file)).name);
    }

    @Test
    public void findShouldReturnCorrectFileWhenMultiplePresentInFolder() throws Exception {
        Folder rootFolder = new Folder(user, "");
        Folder childFolder = new Folder(folder, "");
        rootFolder.content.add(childFolder);
        childFolder.content.add(new File(file, "", content));
        childFolder.content.add(new File("file1", "", content));
        childFolder.content.add(new File("file2", "", content));

        when(storage.getResource(user)).thenReturn(rootFolder);
        assertEquals(file, resourceService.find(String.join("/", user, folder, file)).name);
    }

    @Test
    public void findShouldReturnFileFromCorrectFolder() throws Exception {
        Folder rootFolder = new Folder(user, "");
        Folder childFolder = new Folder(folder, "");
        File fileResource = new File(file, "", content);
        rootFolder.content.add(fileResource);
        rootFolder.content.add(childFolder);
        childFolder.content.add(fileResource);

        when(storage.getResource(user)).thenReturn(rootFolder);
        assertEquals(file, resourceService.find(String.join("/", user, folder, file)).name);
    }

    @Test
    public void findShouldReturnNullWhenSearchingFile() throws Exception {
        Folder rootFolder = new Folder(user, "");
        Folder childFolder = new Folder(folder, "");
        rootFolder.content.add(childFolder);
        childFolder.content.add(new File("unknown", "", content));

        when(storage.getResource(user)).thenReturn(rootFolder);
        assertEquals(null, resourceService.find(String.join("/", user, folder, file)));
    }

    @Test
    public void findShouldReturnFolder() throws Exception {
        Folder rootFolder = new Folder(root, "");
        Folder childFolder = new Folder(folder, "");
        rootFolder.content.add(childFolder);

        when(storage.getResource(user)).thenReturn(rootFolder);
        assertEquals(folder, resourceService.find(String.join("/", root, folder)).name);
    }

    @Test
    public void findShouldReturnCorrectSubfolder() throws Exception {
        Folder rootFolder = new Folder(root, "");
        Folder childFolder1 = new Folder(folder, "");
        Folder childFolder2 = new Folder("another", "");
        rootFolder.content.add(childFolder2);
        rootFolder.content.add(childFolder1);

        when(storage.getResource(user)).thenReturn(rootFolder);
        assertEquals(folder, resourceService.find(String.join("/", root, folder)).name);
    }

    @Test
    public void findShouldReturnNullWhenSearchingFolder() throws Exception {
        Folder rootFolder = new Folder(user, "");
        Folder childFolder = new Folder("unknown", "");
        rootFolder.content.add(childFolder);

        when(storage.getResource(user)).thenReturn(rootFolder);
        assertEquals(null, resourceService.find(String.join("/", user, folder, file)));
    }

/*    @Test(expected = ResourceOperationNotPermitted.class)
    public void createResourceShouldFailWhenNoWritePermission() throws Exception {
        Folder rootFolder = new Folder(user, "", "");
        when(storage.getResource(any())).thenReturn(rootFolder);

        resourceService.create(user, file, 0);
    }

    @Test(expected = ResourceOperationNotPermitted.class)
    public void createResourceShouldFailWhenAlreadyExists() throws Exception {
        Folder rootFolder = new Folder(user, Resource.Permission.W, "");
        when(storage.getResource(any())).thenReturn(rootFolder);

        resourceService.create(user, user, 0);
    }

    @Test(expected = ResourceOperationNotPermitted.class)
    public void createResourceShouldSucceedForCorrectPermissions() throws Exception {
        Folder rootFolder = new Folder(user, Resource.Permission.W, "");
        when(storage.getResource(any())).thenReturn(rootFolder);

        resourceService.create(user, user, 0);
    }*/

    @Test
    public void findParentShouldReturnParentFolder() throws Exception {
        Folder rootFolder = new Folder(user, Resource.Permission.RW, user);
        Folder childFolder = new Folder(folder, Resource.Permission.RW, user);
        rootFolder.content.add(childFolder);

        InsertionPoint insertionPoint = resourceService.findParent(name, rootFolder);
        assertEquals(childFolder.name, insertionPoint.hook.name);
        assertEquals(1, insertionPoint.chain.size());
        assertEquals(file, insertionPoint.chain.get(0));
    }

    @Test
    public void findParentShouldSecondParentFolder() throws Exception {
        Folder rootFolder = new Folder(user, Resource.Permission.RW, user);
        Folder childFolder = new Folder(user, Resource.Permission.RW, user);
        rootFolder.content.add(childFolder);

        InsertionPoint insertionPoint = resourceService.findParent("user/user/test/file", rootFolder);
        assertEquals(childFolder.name, insertionPoint.hook.name);
        assertEquals(2, insertionPoint.chain.size());
        assertEquals("file", insertionPoint.chain.get(1));
        assertEquals("test", insertionPoint.chain.get(0));
    }

    @Test
    public void createResourceFromPathShouldReturnValidResource() throws Exception {
        List<String> path = Arrays.asList(user, folder, file);

        Folder result = (Folder) resourceService.createResourceFromPath(path, content, "", "");
        assertEquals(user, result.name);
        assertEquals(folder, result.content.get(0).name);
        assertEquals(file, ((Folder)result.content.get(0)).content.get(0).name);
        assertEquals(content, ((File)((Folder)result.content.get(0)).content.get(0)).content);
    }

    @Test
    public void createResourceFromSingleFolderPathShouldReturnValidResource() throws Exception {
        List<String> path = Arrays.asList(user);

        Folder result = (Folder) resourceService.createResourceFromPath(path, null, "", "");
        assertEquals(user, result.name);
    }

    @Test
    public void createResourceFromDoubleFolderPathShouldReturnValidResource() throws Exception {
        List<String> path = Arrays.asList(user, user);

        Folder result = (Folder) resourceService.createResourceFromPath(path, null, "", "");
        assertEquals(user, result.name);
        assertEquals(user, result.content.get(0).name);
    }
}
