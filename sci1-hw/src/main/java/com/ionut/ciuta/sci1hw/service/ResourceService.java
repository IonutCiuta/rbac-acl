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

    private Resource findResource(List<String> segments, Resource resource)  {
        /* If there are no more segments to explore, the resource could not be found */
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
        return new ArrayList<>(Arrays.asList(name.split("/")));
    }
}
