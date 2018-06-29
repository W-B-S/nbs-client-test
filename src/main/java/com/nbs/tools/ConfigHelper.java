package com.nbs.tools;

import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * @Package : com.nbs.tools
 * @Description :
 * <p>
 *     NBS配置类
 * </p>
 * @Author : lambor.c
 * @Date : 2018/6/19-8:35
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ConfigHelper {
    private static final Logger logger = LoggerFactory.getLogger(ConfigHelper.class);
    private int stats = 0;
    /**
     * 系统当前路径
     */
    public final static String CURRENT_DIR = System.getProperty("user.dir");
    public final static String OS_FILE_SEPARATOR = File.separator;
    public static final String CONF_ROOT = File.separator+"conf" + File.separator;
    private static final String CONF_FILE = "nbs-conf.properties";
    private static final String I18N_FILE = "zh-cn.properties";
    public static final String PROFILE_ROOT = CURRENT_DIR + File.separator+"profile" + File.separator;
    public static final String NBS_FILES_ROOT_PATH = CURRENT_DIR + File.separator +"nbs" + File.separator;
    public static final String NBS_FILES_IPFS_ROOT = NBS_FILES_ROOT_PATH + "ipfs"+  File.separator;
    /**
     * 临时文件
     */
    public static final String NBS_TEMP_ROOT = NBS_FILES_ROOT_PATH + "_temp" + File.separator;
    public static final String NBS_CACHE_AVATAR_ROOT_PATH = NBS_FILES_ROOT_PATH +"cache"+ File.separator+ "avatar" + File.separator;

    public static final String PK_CFG_IPFS_ADDR = "nbs.server.address";
    private static final String CLIENT_ADD_FILE_ROOT = "nbs.client.merkle.root";
    private static final String CLIENT_ADD_FILE_ROOT_HASH = "nbs.client.merkle.root.hash";
    private static final String IPFS_ADDR_DEFAULT = "/ip4/127.0.0.1/tcp/5001";

    public static String JSON_NICKNAME_KEY = "nickname";
    public static String JSON_AVATAR_KEY = "avatar";
    public static String JSON_AVATAR_NAME_KEY = "avatar-name";
    public static String JSON_AVATAR_SUFFIX_KEY = "suffix";

    private static Properties env = new Properties();
    private static Properties i18nProps = new Properties();

    private ConfigHelper(){
        initLoadEnv();
        /**
         * i18n
         */
        initLoadI18n();
    }

    /**
     *
     */
    private void initLoadEnv(){
        InputStream is = null;
        try{
            is = new BufferedInputStream(new FileInputStream(
                    CONF_ROOT + CONF_FILE));
            env.load(is);
            is.close();
            if(stats==2){
                stats=3;
            }else {
                stats=1;
            }
        }catch (IOException ioe){
            logger.error("load config error.{}",ioe.getMessage());
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
                    CONF_ROOT + I18N_FILE));
            i18nProps.load(is);
            is.close();
            if(stats==1){
                stats=3;
            }else {
                stats=2;
            }
        }catch (IOException ioe){
            logger.error("load i18n error.{}",ioe.getMessage());
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 重新加载配置
     * @return
     */
    public boolean reload(){
        if(stats==3)return true;
        switch (stats){
            case 1:
                initLoadI18n();
                break;
            case 2:
                initLoadEnv();
                break;
            case 0:
            default:
                initLoadEnv();
                initLoadI18n();
        }
        return 3==stats;
    }

    private static class ConfigHolder {
        public static ConfigHelper instance = new ConfigHelper();
    }

    public static ConfigHelper getInstance(){
        return ConfigHolder.instance;
    }

    /**
     *
     * @param key
     * @return
     */
    public String getProperty(String key){
        return env.getProperty(key);
    }

    /**
     *
     * @param key
     * @param defaultVal
     * @return
     */
    public String getProperty(String key,String defaultVal){
        return env.getProperty(key,defaultVal);
    }

    /**
     *
     * @param key
     * @param value
     */
    public void setProperty(String key,String value){
        setProperty(key,value,null);
    }

    /**
     * 设置配置，并保存至config
     * @param key
     * @param value
     * @param comments
     */
    public void setProperty(String key,String value ,String comments){
        if(StringUtils.isBlank(key))throw  new IllegalArgumentException("key must a string");
        boolean oldhas = env.containsKey(key);
        if(value==null)value = "";
        File configFile;
        FileOutputStream fos;
        try{
            env.setProperty(key,value);
            configFile = new File(CONF_ROOT + CONF_FILE);
            fos = new FileOutputStream(configFile);
            env.store(fos,comments);
        } catch (IOException e) {
            e.printStackTrace();
            if(!oldhas)env.remove(key);//如果不存在原始，则删除新的
            logger.error("NBS config File not find.",e.getMessage());
        }
    }

    /**
     * 移除
     * @param key
     */
    public void removeProperty(String key){
        env.remove(key);
    }

    /**
     * 获取Server Address
     * @return
     */
    public String getIpfsAddress(){
        String v = getProperty(PK_CFG_IPFS_ADDR);
        if(StringUtils.isBlank(v))v= IPFS_ADDR_DEFAULT;
        return v;
    }

    /**
     *
     * @return
     */
    public String getNbsFilesRoot(){
        return env.getProperty("nbs.client.files.root","nbs");
    }
    /**
     *
     * @return
     */
    public Properties getEnv() {
        return env;
    }

    /**
     *
     * @return
     */
    public String getClientAddRootNode(){
        String node = getProperty(CLIENT_ADD_FILE_ROOT);
        if(StringUtils.isBlank(node)){
            node = ".merkleroot";
            setProperty(CLIENT_ADD_FILE_ROOT,node,"initial the client add root node dest.");
        }
        return node;
    }

    public String getClientAddFileRootHash(){
        return getProperty(CLIENT_ADD_FILE_ROOT_HASH);
    }

    /**
     * 更新 client root hash
     * @param hash
     * @param comments
     * @return
     * @throws IOException
     */
    public boolean storeClientAddFileRootHash(String hash,String comments) throws IOException {
        if(hash==null)return false;
        if(comments==null)comments = "";
        comments = System.getenv("user.name")+" : " + comments;
        File configFile;
        FileOutputStream fos;

        env.setProperty(CLIENT_ADD_FILE_ROOT_HASH,hash);
        configFile = new File(CONF_ROOT + CONF_FILE);
        fos = new FileOutputStream(configFile);
        env.store(fos,comments);
        return true;
    }

    public String getAddLogFileName(){
        return env.getProperty("nbs.client.merkle.add.log.name","");
    }

    /**
     * 控制显示会员
     * @return
     */
    public boolean subWorldPeers(){
        String stats = env.getProperty("nbs.client.im.topic.subworld","enabled");
        return(stats.equalsIgnoreCase("enabled")
                || stats.equalsIgnoreCase("true")
                || stats.equalsIgnoreCase("1")
        )  ? true : false;
    }

    public String getI18nResourceHome(){
        String i18nHome = env.getProperty("nbs.client.i18n.home","zh_cn");
        return OS_FILE_SEPARATOR+"icon"+OS_FILE_SEPARATOR+i18nHome+OS_FILE_SEPARATOR;
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
     * @param key
     * @param defaultVal
     * @return
     */
    public String getI18nProperty(String key,String defaultVal){
        return i18nProps.getProperty(key,defaultVal);
    }
}
