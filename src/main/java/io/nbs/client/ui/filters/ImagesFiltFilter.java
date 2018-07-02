package io.nbs.client.ui.filters;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * @Package : io.nbs.client.ui.filters
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/2-13:23
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ImagesFiltFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        String name = f.getName();
        name = name.toLowerCase();
        return f.isDirectory()|| name.endsWith(".png")||name.endsWith("jpg")||name.endsWith("gif")||name.endsWith("jpeg");
    }

    @Override
    public String getDescription() {
        return ".jpg,.jpeg,.gif,.png";
    }
}
