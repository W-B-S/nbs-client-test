package io.nbs.commons.helper;

import io.nbs.client.Launcher;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Package : io.ipfs.nbs.helper
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/29-14:24
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ConfigurationHelper {
    /**
     * 外部配置文件目录名
     */
    private static final String CONF_ROOT = "conf";
    public static final String CURRENT_DIR = System.getProperty("user.dir");
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String CONF_FILE = "nbs-conf.properties";
    private static final String I18N_FILE = "zh-cn.properties";

    public static final String PK_CFG_IPFS_ADDR = "nbs.server.address";
    private static final String IPFS_ADDR_DEFAULT = "/ip4/127.0.0.1/tcp/5001";


    /**
     * profiles
     */
    public static String JSON_NICKNAME_KEY = "nickname";
    public static String JSON_CFG_FROMID_KEY = "fromid";
    public static String JSON_AVATAR_KEY = "avatar";
    public static String JSON_AVATAR_NAME_KEY = "avatar-name";
    public static String JSON_AVATAR_SUFFIX_KEY = "suffix";




    private Properties cfgProps = new Properties();
    private Properties i18nProps = new Properties();
    private static int stats = 0;

    private ConfigurationHelper(){
        initLoadEnv();
        initLoadI18n();
    }

    private static class ConfigHolder{
        public static ConfigurationHelper instance = new ConfigurationHelper();
    }

    /**
     *
     * @param key
     * @param defVal
     * @return
     */
    public String getI18nProperty(String key,String defVal){
        return i18nProps.getProperty(key,defVal);
    }

    /**
     *
     * @param key
     * @return
     */
    public String getI18nProperty(String key){
        return i18nProps.getProperty(key);
    }
    /**
     *
     * @return
     */
    public static ConfigurationHelper getInstance(){
        return ConfigHolder.instance;
    }


    /**
     * 获取IPFS服务地址
     * @return
     */
    public String getIPFSAddress(){
        if(cfgProps!=null){
            return cfgProps.getProperty(PK_CFG_IPFS_ADDR,IPFS_ADDR_DEFAULT);
        }else {
            return IPFS_ADDR_DEFAULT;
        }
    }

    /**
     *
     */
    private void initLoadEnv(){
        InputStream is = null;
        try{
            is = new BufferedInputStream(new FileInputStream(
                    CONF_ROOT +Launcher.FILE_SEPARATOR + CONF_FILE));
            cfgProps.load(is);
            is.close();
            if(stats==2){
                stats=3;
            }else {
                stats=1;
            }
        }catch (IOException ioe){
            System.out.println("load config error." + ioe.getMessage());
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private void initLoadI18n(){
        InputStream is = null;
        try{
            is = new BufferedInputStream(new FileInputStream(
                    CONF_ROOT + Launcher.FILE_SEPARATOR + I18N_FILE));
            i18nProps.load(is);
            is.close();
            if(stats==1){
                stats=3;
            }else {
                stats=2;
            }
        }catch (IOException ioe){
            System.out.println("load i18n error."+ioe.getMessage());
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e){}
            }
        }
    }

    /**
     * 获取http IPFS URL
     * @return
     */
    public String getGateWayURL(){
        String gwUrl = cfgProps.getProperty("nbs.server.address.gateway-url","http://127.0.0.1:8080/ipfs/");
        if(StringUtils.isNotBlank(gwUrl))return gwUrl;
        if(!gwUrl.endsWith("/"))gwUrl = gwUrl+"/";
        return gwUrl;
    }

    /**
     * 返回组装好的
     * @param hash
     * @return
     */
    public String getGateWayURL(String hash){
        String gwUrl = getGateWayURL();
        return gwUrl+hash;
    }
}
