package com.ionut.ciuta.sci1hw.model;

/**
 * ionutciuta24@gmail.com on 25.10.2017.
 */
public abstract class Resource {
    public static class Type {
        public static final int FOLDER = 0;
        public static final int FILE = 1;
    }

    public static class Permission {
        public static final String NONE = "";
        public static final String R = "r";
        public static final String W = "w";
        public static final String RW = "rw";
    }

    public String name;
    public int type;
    public String permission;
    public String owner;

    public Resource(String name, int type, String permission, String owner) {
        this.name = name;
        this.type = type;
        this.permission = permission;
        this.owner = owner;
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
