package com.ionut.ciuta.posd1.service;

import com.ionut.ciuta.posd1.exception.ResourceInConflict;
import com.ionut.ciuta.posd1.exception.ResourceNotFound;
import com.ionut.ciuta.posd1.exception.ResourceOperationNotPermitted;
import com.ionut.ciuta.posd1.exception.UnauthorizedUser;
import com.ionut.ciuta.posd1.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceAccessService {
    @Autowired
    private AuthService authService;

    @Autowired
    private ResourceService resourceService;

    public String read(String user, String pass, String file) {
        if(!authService.isAuthenticated(user, pass)) {
            throw new UnauthorizedUser();
        }

        Resource resource = resourceService.find(file);

        if(resource == null) {
            throw new ResourceNotFound();
        }

        if(resource.owner.equals(user)) {
            return getContent(resource);
        } else {
            if(resource.permission.equals(Permission.R) ||
                    resource.permission.equals(Permission.RW)) {
                return getContent(resource);
            } else {
                throw new ResourceOperationNotPermitted();
            }
        }
    }

    public void write(String user, String pass, String filename, String newContent) {
        if(!authService.isAuthenticated(user, pass)) {
            throw new UnauthorizedUser();
        }

        Resource resource = resourceService.find(filename);

        if(resource == null || resource.type != Resource.Type.FILE) {
            throw new ResourceNotFound();
        }

        File file = (File)resource;

        if(file.owner.equals(user) || file.permission.contains(Permission.W)) {
           file.content = newContent;
        } else {
            throw new ResourceOperationNotPermitted();
        }
    }

    private String getContent(Resource resource) {
        switch (resource.type) {
            case Resource.Type.FOLDER:
                return getFolderContent((Folder)resource);

            case Resource.Type.FILE:
                return ((File)resource).content;

            default:
                throw new UnsupportedOperationException();
        }
    }

    private String getFolderContent(Folder folder) {
        StringBuilder sb = new StringBuilder();

        folder.content.forEach(r -> {
            switch (r.type) {
                case Resource.Type.FILE:
                    sb.append(((File)r).name);
                    break;

                case Resource.Type.FOLDER:
                    sb.append(r.name).append("/");
                    break;
            }

            sb.append(" ");
        });

        return sb.toString().trim();
    }

    public void changeRights(String user, String pass, String name, String permissions) {
        if(!authService.isAuthenticated(user, pass)) {
            throw new UnauthorizedUser();
        }

        Resource resource = resourceService.find(name);

        if(resource == null) {
            throw new ResourceNotFound();
        }

        if(resource.owner.equals(user) || resource.permission.contains(Permission.W)) {
            resource.permission = permissions;
        } else {
            throw new ResourceOperationNotPermitted();
        }
    }

    public void create(String user, String pass, String name, String content) {
        if(!authService.isAuthenticated(user, pass)) {
            throw new UnauthorizedUser();
        }

        Resource resource = resourceService.findRootForFile(name);

        if(resource == null) {
            throw new ResourceNotFound();
        }

        if(resourceService.exists(name)) {
            throw new ResourceInConflict();
        } else {
            InsertionPoint insertionPoint = resourceService.findParent(name, resource);
            Folder hook = insertionPoint.hook;

            if(hook.owner.equals(user) || hook.permission.contains(Permission.W)) {
                Resource newNode = resourceService.createResourceFromPath(
                        insertionPoint.chain,
                        content,
                        insertionPoint.hook.permission,
                        user
                );
                insertionPoint.hook.content.add(newNode);
            } else {
                throw new ResourceOperationNotPermitted();
            }
        }
    }
}
