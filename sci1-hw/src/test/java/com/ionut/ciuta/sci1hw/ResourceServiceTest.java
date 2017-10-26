package com.ionut.ciuta.sci1hw;

import com.ionut.ciuta.sci1hw.model.File;
import com.ionut.ciuta.sci1hw.model.Folder;
import com.ionut.ciuta.sci1hw.model.Resource;
import com.ionut.ciuta.sci1hw.service.AuthService;
import com.ionut.ciuta.sci1hw.service.ResourceBuilder;
import com.ionut.ciuta.sci1hw.service.ResourceService;
import com.ionut.ciuta.sci1hw.service.Storage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

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
    public void existsShouldReturnTrue() throws Exception {
        Resource resource = resource();
        when(storage.getResource(user)).thenReturn(resource);

        assertTrue(resourceService.exists(user, name));
    }

    @Test
    public void existsShouldReturnFalseForEmptyResource() throws Exception {
        when(storage.getResource(user)).thenReturn(null);

        assertTrue(resourceService.exists(user, name));
    }

    @Test
    public void checkResourceShouldReturnTrueForRootFolder() throws Exception {
        Folder root = new Folder(user, "");
        when(storage.getResource(user)).thenReturn(root);

        assertTrue(resourceService.checkResource(Collections.singletonList(user), root));
    }

    @Test
    public void checkResourceShouldReturnTrueForEmptyPath() throws Exception {
       // assertTrue(resourceService.checkResource(Collections.emptyList(), null));
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
