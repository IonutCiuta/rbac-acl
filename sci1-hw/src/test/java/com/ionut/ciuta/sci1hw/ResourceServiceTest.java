package com.ionut.ciuta.sci1hw;

import com.ionut.ciuta.sci1hw.model.File;
import com.ionut.ciuta.sci1hw.model.Folder;
import com.ionut.ciuta.sci1hw.model.Resource;
import com.ionut.ciuta.sci1hw.service.ResourceBuilder;
import com.ionut.ciuta.sci1hw.service.ResourceService;
import com.ionut.ciuta.sci1hw.service.Storage;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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

        assertFalse(resourceService.exists(user, name, 0));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void existsShouldThrowExceptionForUnknowType() throws Exception {
        when(storage.getResource(user)).thenReturn(resource());

        resourceService.exists(user, name, 2);
    }

    @Test
    public void existsShouldReturnTrueForFolder() throws Exception {
        Folder rootFolder = new Folder(root, "");
        Folder childFolder = new Folder(folder, "");
        rootFolder.content.add(childFolder);

        when(storage.getResource(user)).thenReturn(rootFolder);
        assertTrue(resourceService.exists(user, String.join("/", root, folder), Resource.Type.FOLDER));
    }

    @Test
    public void existsShouldReturnFalseForFolder() throws Exception {
        Folder rootFolder = new Folder(root, "");
        Folder childFolder = new Folder("unknown", "");
        rootFolder.content.add(childFolder);

        when(storage.getResource(user)).thenReturn(rootFolder);
        assertFalse(resourceService.exists(user, String.join("/", root, folder), Resource.Type.FOLDER));
    }

    @Test
    public void existsShouldReturnTrueForFile() throws Exception {
        Folder rootFolder = new Folder(user, "");
        Folder childFolder = new Folder(folder, "");
        rootFolder.content.add(childFolder);
        childFolder.content.add(new File(file, "", content));

        when(storage.getResource(user)).thenReturn(rootFolder);
        assertTrue(resourceService.exists(user, String.join("/", user, folder, file), Resource.Type.FILE));
    }

    @Test
    public void existsShouldReturnFalseForFile() throws Exception {
        Folder rootFolder = new Folder(user, "");
        Folder childFolder = new Folder(folder, "");
        rootFolder.content.add(childFolder);
        childFolder.content.add(new File("unknown", "", content));

        when(storage.getResource(user)).thenReturn(rootFolder);
        assertFalse(resourceService.exists(user, String.join("/", user, folder, file), Resource.Type.FILE));
    }

    @Test
    public void findShouldReturnFile() throws Exception {
        Folder rootFolder = new Folder(user, "");
        Folder childFolder = new Folder(folder, "");
        rootFolder.content.add(childFolder);
        childFolder.content.add(new File(file, "", content));

        when(storage.getResource(user)).thenReturn(rootFolder);
        assertEquals(file, resourceService.find(user, String.join("/", user, folder, file), Resource.Type.FILE).name);
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
        assertEquals(file, resourceService.find(user, String.join("/", user, folder, file), Resource.Type.FILE).name);
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
        assertEquals(file, resourceService.find(user, String.join("/", user, folder, file), Resource.Type.FILE).name);
    }

    @Test
    public void findShouldReturnNullWhenSearchingFile() throws Exception {
        Folder rootFolder = new Folder(user, "");
        Folder childFolder = new Folder(folder, "");
        rootFolder.content.add(childFolder);
        childFolder.content.add(new File("unknown", "", content));

        when(storage.getResource(user)).thenReturn(rootFolder);
        assertEquals(null, resourceService.find(user, String.join("/", user, folder, file), Resource.Type.FILE));
    }

    @Test
    public void findShouldReturnFolder() throws Exception {
        Folder rootFolder = new Folder(root, "");
        Folder childFolder = new Folder(folder, "");
        rootFolder.content.add(childFolder);

        when(storage.getResource(user)).thenReturn(rootFolder);
        assertEquals(folder, resourceService.find(user, String.join("/", root, folder), Resource.Type.FOLDER).name);
    }

    @Test
    public void findShouldReturnCorrectSubfolder() throws Exception {
        Folder rootFolder = new Folder(root, "");
        Folder childFolder1 = new Folder(folder, "");
        Folder childFolder2 = new Folder("another", "");
        rootFolder.content.add(childFolder2);
        rootFolder.content.add(childFolder1);

        when(storage.getResource(user)).thenReturn(rootFolder);
        assertEquals(folder, resourceService.find(user, String.join("/", root, folder), Resource.Type.FOLDER).name);
    }

    @Test
    public void findShouldReturnNullWhenSearchingFolder() throws Exception {
        Folder rootFolder = new Folder(user, "");
        Folder childFolder = new Folder("unknown", "");
        rootFolder.content.add(childFolder);

        when(storage.getResource(user)).thenReturn(rootFolder);
        assertEquals(null, resourceService.find(user, String.join("/", user, folder, file), Resource.Type.FOLDER));
    }

    private Resource resource() {
        Folder root = new Folder("user", "");
        Folder subfolder = new Folder("folder", "");
        File file = new File("file.ext", "", "fileContent");

        root.content.add(subfolder);
        subfolder.content.add(file);

        return root;
    }
}
