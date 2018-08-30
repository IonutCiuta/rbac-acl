package com.ionut.ciuta.posd1.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ionutciuta24@gmail.com on 25.10.2017.
 */
public class Folder extends Resource {
    public List<Resource> content = new ArrayList<>();

    public Folder(String name, String permission, String owner) {
        super(name, Type.FOLDER, permission, owner);
    }

    public Folder(String name, String permission, String owner, Map<String, String> acl) {
        super(name, Type.FOLDER, permission, owner, acl);
    }

    public Folder(String name, String permission) {
        super(name, Type.FOLDER, permission, "");
    }

    @Override
    public String toString() {
        return "Folder{" +
                "content=" + content +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", permission='" + permission + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }
}
