package com.nbs.utils;

import org.springframework.util.Base64Utils;

import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * @Package : com.nbs.utils
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/28-15:53
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class BaseURLUtil {

    private static File getJarFile(){
        String jarPath = BaseURLUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        try {
            jarPath = java.net.URLDecoder.decode(jarPath,"UTF-8");
        } catch (UnsupportedEncodingException e) {

        }
        return new File(jarPath);
    }

    public static String getAppJarPath(){
        return getJarFile().getParent();
    }

    public static String getAppBaseDir(){
       if(getJarFile().getParentFile().getName().equalsIgnoreCase("lib")){
           return getJarFile().getParentFile().getParentFile().getParent();
       }else {
           return getJarFile().getParentFile().getParent();
       }
    }

    public static void main(String[] args){
        System.out.println(getJarFile().getAbsolutePath());
        System.out.println(getAppJarPath());
        System.out.println(getAppBaseDir());
        System.out.println(">>>>>>>>>>..");
    }
}
