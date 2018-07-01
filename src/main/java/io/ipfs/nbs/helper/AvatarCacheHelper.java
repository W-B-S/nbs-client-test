package io.ipfs.nbs.helper;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package : io.ipfs.nbs.helper
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/1-15:38
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class AvatarCacheHelper {
    private static AvatarCacheHelper ourInstance = new AvatarCacheHelper();

    public static AvatarCacheHelper getInstance() {
        return ourInstance;
    }
    private static Map<String,Image> avatarCacher = new HashMap<>();

    private AvatarCacheHelper() {
    }


}
