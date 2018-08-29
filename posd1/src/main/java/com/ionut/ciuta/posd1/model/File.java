package com.ionut.ciuta.posd1.model;

/**
 * ionutciuta24@gmail.com on 25.10.2017.
 */
public class File extends Resource {
    public String content;

    public File(String name, String permission, String content, String owner) {
        super(name, Type.FILE, permission, owner);
        this.content = content;
    }

    public File(String name, String permission, String content) {
        super(name, Type.FILE, permission, "");
        this.content = content;
    }
}
