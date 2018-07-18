package io.nbs.client.cnsts;

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

    /**
     * 图标根
     */
    public static String TOOL_ICON_PATH = "/icons/tools/";

    /**
     * 临时文件目录
     */
    public static final String IPFS_BASE = "nbssvr";

    /**
     * 非NBS客户端区分
     */
    public static String NOTNBS_PEER_MIDDLE= "_NBSChain_";

    /**
     * 构造路径
     * @param agrs
     * @return
     */
    public static String consturactPath(String... agrs){
        StringBuilder sb = new StringBuilder();
        String fileSeparator = System.getProperty("file.separator");
        int len = agrs.length;
        for(int i=0;i<len;i++ ){
            if(i==(len-1)){
                sb.append(agrs[i]);
            }else {
                sb.append(agrs[i]).append(fileSeparator);
            }

        }
        return sb.toString();
    }
}
