package com.ionut.ciuta.sci1hw.model;

import java.util.List;

/**
 * ionutciuta24@gmail.com on 31.10.2017.
 */
public class InsertionPoint {
    public Folder hook;
    public List<String> chain;

    public InsertionPoint() {
    }

    public InsertionPoint(Folder hook, List<String> chain) {
        this.hook = hook;
        this.chain = chain;
    }
}
