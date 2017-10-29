package com.ionut.ciuta.sci1hw.service;

import com.ionut.ciuta.sci1hw.model.Folder;
import com.ionut.ciuta.sci1hw.model.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

    public boolean exists(String user, String name, int type) {
        if(type != Resource.Type.FOLDER && type != Resource.Type.FILE) {
            throw new UnsupportedOperationException();
        }

        Resource resource = storage.getResource(user);
        return resource != null && findResource(getPath(name), resource, type) != null;
    }

    public Resource find(String user, String name, int type) {
        if(type != Resource.Type.FOLDER && type != Resource.Type.FILE) {
            throw new UnsupportedOperationException();
        }

        Resource resource = storage.getResource(user);
        return findResource(getPath(name), resource, type);
    }

    private Resource findResource(List<String> segments, Resource resource, int type)  {
        if(segments.isEmpty()) {
            return null;
        }

        String segment = segments.remove(0);
        if(segment.equals(resource.name) && resource.isOfType(type) ) {
            return resource;
        } else {
            if(resource.isFolder()) {
                List<Resource> results =
                        ((Folder) resource).content.stream()
                        .map(r -> findResource(new ArrayList<>(segments), r, type))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());

                if(results.isEmpty()) {
                    return null;
                } else {
                    return results.get(0);
                }
            }

            return null;
        }
    }

    public List<String> getPath(String name) {
        return new ArrayList<>(Arrays.asList(name.split("/")));
    }
}
