package io.nbs.client.ui.filters;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * @Package : io.ipfs.nbs.ui.filters
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/30-2:20
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ImageFileFilter extends FileFilter {
    /**
     * 50MB
     */
    public static final long MAX_SIZE = 1024*1024*50;

    @Override
    public boolean accept(File f) {
        if(f==null||f.isDirectory())return false;
        int pos =  f.getName().lastIndexOf(".");
        if(pos<0)return false;
        String suffix = f.getName().substring(pos);
        return ImageTypes.checkedInTypes(suffix);
    }

    @Override
    public String getDescription() {
        return ".png";
    }

    public static enum ImageTypes {
        png,jpg,git,jpeg,bmp;

        static boolean checkedInTypes(String suffix){
            for(ImageTypes t : ImageTypes.values()){
                if(t.name().equalsIgnoreCase(suffix))return true;
            }
            return false;
        }
    }
}
