package com.ionut.ciuta.posd1.model;

import java.util.Set;

/**
 * ionutciuta24@gmail.com on 25.10.2017.
 */
public class File extends Resource {
    public String content;

    public File(String name, String content, String owner) {
        super(name, Type.FILE, owner);
        this.content = content;
    }

    public File(String name, String content, String owner, Set<String> acl) {
        super(name, Type.FILE, owner, acl);
        this.content = content;
    }

    @Override
    public String toString() {
        return super.toString() + "File content -> " + content + '\n';
    }
}
