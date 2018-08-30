package com.ionut.ciuta.posd1.model;

import com.ionut.ciuta.posd1.Values;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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
    public String permission;
    public String owner;
    //Access control list: user -> role
    public Map<String, String> acl;

    public Resource(String name, int type, String permission, String owner) {
        this.name = name;
        this.type = type;
        this.permission = permission;
        this.owner = owner;
        this.acl = new HashMap<>();
        acl.put(owner, Values.OWNER);
    }

    public Resource(String name, int type, String permission, String owner, Map<String, String> acl) {
        this.name = name;
        this.type = type;
        this.permission = permission;
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
}