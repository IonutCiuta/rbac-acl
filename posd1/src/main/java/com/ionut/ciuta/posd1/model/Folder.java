package com.ionut.ciuta.posd1.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * ionutciuta24@gmail.com on 25.10.2017.
 */
public class Folder extends Resource {
    public List<Resource> content = new ArrayList<>();

    public Folder(String name, String owner) {
        super(name, Type.FOLDER, owner);
    }

    public Folder(String name, String owner, Set<String> acl) {
        super(name, Type.FOLDER, owner, acl);
    }

    @Override
    public String toString() {
        return super.toString() + "Folder content -> " + content.toString() + '\n';
    }
}
