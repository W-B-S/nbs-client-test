package io.ipfs.nbs.cnsts;

/**
 * @Package : io.ipfs.nbs.cnsts
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/29-14:25
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class AppGlobalCnst {
    /**
     * 客户端运行产生文件根目录
     */
    public static final String NBS_ROOT = ".nbs";
    /**
     * 临时文件目录
     */
    public static final String TEMP_FILE = "tmp";

    /**
     * 在用户目录下 : .nbs/nbs.lock
     */
    public static final String LOCK_FILE = "nbs.lock";


    public static String getAvatarPath(){
        String fileSeparator = System.getProperty("file.separator");
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir")).append(fileSeparator);
        sb.append(NBS_ROOT).append(fileSeparator).append("cache").append(fileSeparator);
        sb.append("avatars");
        return sb.toString();
    }
}
