package com.ionut.ciuta.posd1.model;

import java.util.Map;

/**
 * ionutciuta24@gmail.com on 25.10.2017.
 */
public class File extends Resource {
    public String content;

    public File(String name, String permission, String content, String owner) {
        super(name, Type.FILE, permission, owner);
        this.content = content;
    }

    public File(String name, String permission, String content, String owner, Map<String, String> acl) {
        super(name, Type.FILE, permission, owner, acl);
        this.content = content;
    }

    public File(String name, String permission, String content) {
        super(name, Type.FILE, permission, "");
        this.content = content;
    }

    @Override
    public String toString() {
        return "File{" +
                "content='" + content + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", permission='" + permission + '\'' +
                ", owner='" + owner + '\'' +
                ", acl=" + acl +
                '}';
    }
}
