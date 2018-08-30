package com.ionut.ciuta.posd1.service;

import com.ionut.ciuta.posd1.exception.ResourceNotFound;
import com.ionut.ciuta.posd1.model.File;
import com.ionut.ciuta.posd1.model.Folder;
import com.ionut.ciuta.posd1.model.InsertionPoint;
import com.ionut.ciuta.posd1.model.Resource;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ionutciuta24@gmail.com on 26.10.2017.
 */
@Service
public class ResourceService {

    @Autowired
    private Storage storage;

    public boolean exists(String name) {
        List<String> path = getPath(name);
        Resource resource = storage.getResource(path.get(0));
        return resource != null && findResource(path, resource) != null;
    }

    public Resource find(String name) {
        List<String> path = getPath(name);
        Resource resource = storage.getResource(path.get(0));
        return findResource(path, resource);
    }

    public Resource findRootForFile(String name) {
        List<String> path = getPath(name);
        return storage.getResource(path.get(0));
    }

    private Resource findResource(List<String> segments, Resource resource)  {
        /* If there are no more segments to explore, the resource could not be found */
        if(resource == null) {
            throw new ResourceNotFound();
        }

        if(segments.isEmpty()) {
            return null;
        }

        /* Current segment */
        String segment = segments.remove(0);

        if(resource.isFolder()) {
            /* If it's the last folder and it has the correct name, then return it*/
            if(segment.equals(resource.name) && segments.isEmpty()) {
                    return resource;
            }

            /* Not all criteria were matched so we explore subfolders */
            List<Resource> results =
                    ((Folder) resource).content.stream()
                            .map(r -> findResource(new ArrayList<>(segments), r))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());

            /* Return null is nothing matched the search */
            return results.isEmpty() ? null : results.get(0);

        } else {
            /* If we search for a file and the name is a match, return it*/
            if(segment.equals(resource.name)) {
                return resource;
            } else {
                return null;
            }
        }
    }


    public List<String> getPath(String name) {
        return new ArrayList<>(Arrays.asList(name
                .replace('/', ' ')
                .trim()
                .split(" ")
        ));
    }

    public InsertionPoint findParent(String file, Resource rootFolder) {
        List<String> segments = getPath(file);
        List<Resource> resources = Collections.singletonList(rootFolder);

        Folder parent = null;
        boolean match;
        int i = 0;

        for(; i < segments.size(); i++) {
            match = false;
            for(Resource resource : resources) {
                if(resource.name.equals(segments.get(i)) && resource.isFolder()) {
                    parent = (Folder) resource;
                    resources = parent.content;
                    match = true;
                    break;
                }
            }

            if(!match) {
                break;
            }
        }

        return new InsertionPoint(parent, segments.subList(i, segments.size()));
    }

    public Resource createResourceFromPath(List<String> path, String content, String rights, String owner, Map<String, String> acl) {
        Resource hook = null;
        Resource resource = null;
        int type = (content == null || content.isEmpty()) ? Resource.Type.FOLDER : Resource.Type.FILE;

        if (path.isEmpty()) {
            return null;
        }

        boolean withHook = false;
        if (path.size() > 1) {
            hook = new Folder(path.get(0), rights, owner);
            resource = hook;
            withHook = true;
        }

        int i = withHook ? 1 : 0;
        for (; i < path.size() - 1; i++) {
            Folder newFolder = new Folder(path.get(i), rights, owner, acl);
            if (resource != null) {
                ((Folder) resource).content.add(newFolder);
            }
            resource = newFolder;
        }

        Resource newResource;
        if (type == Resource.Type.FOLDER) {
            newResource = new Folder(path.get(i), rights, owner, acl);
        } else {
            newResource = new File(path.get(i), rights, content, owner, acl);
        }

        if (hook == null) {
            hook = newResource;
        } else {
            ((Folder) resource).content.add(newResource);
        }

        return hook;
    }
}
