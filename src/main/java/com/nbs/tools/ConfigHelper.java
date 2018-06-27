package com.nbs.tools;

import UI.ConstantsUI;
import com.nbs.utils.FormatCnst;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
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
    /**
     * 系统当前路径
     */
    public final static String CURRENT_DIR = System.getProperty("user.dir");
    public static final String CONF_ROOT = CURRENT_DIR + File.separator+"config" + File.separator;
    public static final String PROFILE_ROOT = CURRENT_DIR + File.separator+"profile" + File.separator;
    public static final String NBS_FILES_ROOT_PATH = CURRENT_DIR + File.separator +"nbs" + File.separator;
    public static final String NBS_FILES_IPFS_ROOT = NBS_FILES_ROOT_PATH + "ipfs"+  File.separator;
    /**
     * 临时文件
     */
    public static final String NBS_TEMP_ROOT = NBS_FILES_ROOT_PATH + "_temp" + File.separator;
    public static final String NBS_CACHE_AVATAR_ROOT_PATH = NBS_FILES_ROOT_PATH +"cache"+ File.separator+ "avatar" + File.separator;
    private static final String CONF_FILE = "nbs-conf.properties";

    public static final String PK_CFG_IPFS_ADDR = "nbs.server.address";
    private static final String CLIENT_ADD_FILE_ROOT = "nbs.client.merkle.root";
    private static final String CLIENT_ADD_FILE_ROOT_HASH = "nbs.client.merkle.root.hash";
    private static final String IPFS_ADDR_DEFAULT = "/ip4/127.0.0.1/tcp/5001";

    public static String JSON_NICKNAME_KEY = "nickname";
    public static String JSON_AVATAR_KEY = "avatar";
    public static String JSON_AVATAR_NAME_KEY = "avatar-name";
    public static String JSON_AVATAR_SUFFIX_KEY = "suffix";


    private static Properties env = new Properties();
    static {
        InputStream is = null;
        try{
            is = new BufferedInputStream(new FileInputStream(
                    CnstTools.PROPS_ROOT_PATH + CONF_FILE));
            env.load(is);
            is.close();
        }catch (IOException ioe){
            logger.error("load config error.%s",ioe.getMessage());
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     *
     * @param key
     * @return
     */
    public static String getProperty(String key){
        if(key==null||"".equals(key.trim()))throw new IllegalArgumentException("key must a string");
        return env.getProperty(key);
    }

    /**
     *
     * @param key
     * @param defaultVal
     * @return
     */
    public static String getProperty(String key,String defaultVal){
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
    public static void setProperty(String key,String value ,String comments){
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
    public static void removeProperty(String key){
        env.remove(key);
    }

    /**
     * 获取Server Address
     * @return
     */
    public static String getIpfsAddress(){
        String v = getProperty(PK_CFG_IPFS_ADDR);
        if(StringUtils.isBlank(v))v= IPFS_ADDR_DEFAULT;
        return v;
    }

    /**
     *
     * @return
     */
    public static String getNbsFilesRoot(){
        return env.getProperty("nbs.client.files.root","nbs");
    }
    /**
     *
     * @return
     */
    public static Properties getEnv() {
        return env;
    }

    /**
     *
     * @return
     */
    public static String getClientAddRootNode(){
        String node = getProperty(CLIENT_ADD_FILE_ROOT);
        if(StringUtils.isBlank(node)){
            node = ".merkleroot";
            setProperty(CLIENT_ADD_FILE_ROOT,node,"initial the client add root node dest.");
        }
        return node;
    }

    public static String getClientAddFileRootHash(){
        return getProperty(CLIENT_ADD_FILE_ROOT_HASH);
    }

    /**
     * 更新 client root hash
     * @param hash
     * @param comments
     * @return
     * @throws IOException
     */
    public static boolean storeClientAddFileRootHash(String hash,String comments) throws IOException {
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

    public static String getAddLogFileName(){
        return env.getProperty("nbs.client.merkle.add.log.name","");
    }
}
