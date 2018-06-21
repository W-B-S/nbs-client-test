package com.nbs.tools;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Package : com.nbs.tools
 * @Description : <p>
 *     Props 工具类
 * </p>
 * @Author : lambor.c
 * @Date : 2018/6/13-15:21
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class PropertyUtil {

    /**
     * @Date    : 2018/6/13 15:49
     * @Author  : lanbery
     * @Decription :
     * <p></p>
     * @Param   :
     * @return 
     * @throws
     */
    public static String getProperty(String key){
        Properties props = new Properties();
        try{
            InputStream in = new BufferedInputStream(
                    new FileInputStream(CnstTools.PROPS_ROOT_PATH+"zh-cn.properties"));
            props.load(in);
            String v = props.getProperty(key);
            return v;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param key
     * @param defValue
     * @return
     */
    public static String getProperty(String key,String defValue){
        String v = getProperty(key);
        return v == null ? defValue : v;
    }

    /**
     * 配置参数
     */

    public static final String PKUI_PANEL_ABOUT_LABEL = "nbs.ui.panel.about.label";

}
