package com.ionut.ciuta.sci1hw.service;

import com.ionut.ciuta.sci1hw.model.File;
import com.ionut.ciuta.sci1hw.model.Folder;
import com.ionut.ciuta.sci1hw.model.Resource;

/**
 * ionutciuta24@gmail.com on 26.10.2017.
 */
public class ResourceBuilder {
    public static Resource build(String name, String content, String permission, int type) {
        switch (type) {
            case Resource.Type.FILE:
                return new File(content, permission, content);

            case Resource.Type.FOLDER:
                return new Folder(content, permission);

            default:
                throw new UnsupportedOperationException();
        }
    }
}
