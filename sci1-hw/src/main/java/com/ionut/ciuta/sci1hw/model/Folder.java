package com.ionut.ciuta.sci1hw.model;

import java.util.HashSet;
import java.util.Set;

/**
 * ionutciuta24@gmail.com on 25.10.2017.
 */
public class Folder extends Resource {
    public Set<Resource> content = new HashSet<>();

    public Folder(String name, String permission) {
        super(name, Type.FOLDER, permission);
    }
}
