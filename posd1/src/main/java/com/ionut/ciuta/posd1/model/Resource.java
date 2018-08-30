package com.ionut.ciuta.posd1.model;

import com.ionut.ciuta.posd1.Values;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ionutciuta24@gmail.com on 25.10.2017.
 */
public abstract class Resource {
    public static class Type {
        public static final int FOLDER = 0;
        public static final int FILE = 1;
    }

    public String name;
    public int type;
    public String owner;
    public Set<String> acl;

    public Resource(String name, int type, String owner) {
        this.name = name;
        this.type = type;
        this.owner = owner;
        this.acl = new HashSet<>();
    }

    public Resource(String name, int type, String owner, Set<String> acl) {
        this.name = name;
        this.type = type;
        this.owner = owner;
        this.acl = acl;
    }

    public boolean isFolder() {
        return this.type == Type.FOLDER;
    }

    public boolean isFile() {
        return this.type == Type.FILE;
    }

    public boolean isOfType(int type) {
        return this.type == type;
    }

    @Override
    public String toString() {
        return "Resource: " +
                "name  -> "  + name  + '\n' +
                "type  -> "  + type  + '\n' +
                "owner -> "  + owner + '\n' +
                "acl   -> "  + acl   + '\n';
    }
}