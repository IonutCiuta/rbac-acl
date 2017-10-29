package com.ionut.ciuta.sci1hw.model;

import java.util.ArrayList;
import java.util.List;

/**
 * ionutciuta24@gmail.com on 25.10.2017.
 */
public class Folder extends Resource {
    public List<Resource> content = new ArrayList<>();

    public Folder(String name, String permission) {
        super(name, Type.FOLDER, permission);
    }
}
