package io.nbs.client.helper;

import io.nbs.client.Launcher;
import io.nbs.client.cnsts.AppGlobalCnst;
import io.nbs.commons.utils.IconUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Package : io.ipfs.nbs.helper
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/1-13:12
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class AvatarImageHandler {
    private static Logger logger = LoggerFactory.getLogger(AvatarImageHandler.class);
    private static final int DEFAULT_THUMB_SIZE = 128;
    private static final int DEFAULT_PROFILE_AVATAR_SIZE = 128;
    private static final int DEFAULT_PROFILE_THUMB_SIZE = 48;
    private static final int DEFAULT_CONTACTS_THUMB_SIZE = 40;
    private static AvatarImageHandler ourInstance = new AvatarImageHandler();

    public static AvatarImageHandler getInstance() {
        return ourInstance;
    }


    /**
     * 联系人头像 32 *32
     * 40*40
     * .nbs/cache/avatars/thumbs
     */
    private static String AVATAR_THUMB_HOME;
    /**
     * 用户配置头标
     * .nbs/profile/avatars/
     * 放置128*128 上传IPFS
     * .nbs/profile/avatars/thumbs
     * 64*64 hashFileName
     */
    private static String AVATAR_PROFILE_HOME;

    /**
     *  原图路径
     *  .nbs/profile/origin
     */
    private static String AVATAR_ORIGIN_HOME;

    private AvatarImageHandler() {
        AVATAR_PROFILE_HOME = AppGlobalCnst.consturactPath(Launcher.appBasePath,"profile","avatars");
        AVATAR_ORIGIN_HOME = AppGlobalCnst.consturactPath(Launcher.appBasePath,"profile","origin");
        AVATAR_THUMB_HOME = AppGlobalCnst.consturactPath(Launcher.appBasePath,"cache","avatars","thumbs");
    }

    /**
     * 初始化头标目录
     */
    public void initAvatarLocalDir(){
        File destFile;
        /**
         * profile/avatars/thumbs
         */
        destFile = new File(AppGlobalCnst.consturactPath(AVATAR_PROFILE_HOME,"thumbs"));
        if(!destFile.exists())destFile.mkdirs();

        /**
         *
         */
        destFile = new File(AVATAR_ORIGIN_HOME);
        if(!destFile.exists())destFile.mkdirs();

        destFile = new File(AVATAR_THUMB_HOME);
        if(!destFile.exists())destFile.mkdirs();
    }

    /**
     * 获取用户头像
     * @param hashFileName
     * @return
     */
    public ImageIcon getAvatar128(String hashFileName){
        if(StringUtils.isBlank(hashFileName))return IconUtil.getIcon(this,"/icons/nbs128.png");
        File iconFile = new File(AppGlobalCnst.consturactPath());
        if(!iconFile.exists())return IconUtil.getIcon(this,"/icons/nbs128.png");
        ImageIcon icon = new ImageIcon(AppGlobalCnst.consturactPath(AVATAR_PROFILE_HOME,hashFileName));
        return icon;
    }

    /**
     * 下载文件
     * @param url
     * @param orifile
     * @throws Exception
     */
    public boolean getFileFromIPFS(URL url,File orifile) throws Exception {
        try {
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            DataInputStream in = new DataInputStream(conn.getInputStream());
            FileImageOutputStream out = new FileImageOutputStream(orifile);
            byte[] buffer = new byte[4096];
            int count = 0;
            while ((count = in.read(buffer))>0){
                out.write(buffer,0,count);
            }
            out.close();
            in.close();
            return true;
        } catch (IOException e) {
            logger.warn(e.getMessage(),e.getCause());
            throw new Exception("获取文件错误",e.getCause());
        }
    }

    /**
     * 上传时
     * 创建用户头像
     * 128,64
     * @param hashFileName
     * @throws Exception
     */
    public void createdAvatar4Profile(File srcFile,String hashFileName) throws Exception {
        if(!srcFile.exists())throw new Exception("图片源不存在.");
        String originName = StringUtils.isBlank(hashFileName) ? srcFile.getName() : hashFileName;
        /**
         * 128*128
         */
        String target128 = AppGlobalCnst.consturactPath(AVATAR_PROFILE_HOME,originName);
        File targetFile128 = new File(target128);
        generateThumbScale(srcFile,targetFile128,DEFAULT_PROFILE_AVATAR_SIZE);

        File target64 = new File(AppGlobalCnst.consturactPath(AVATAR_PROFILE_HOME,"thumbs",originName));
        generateThumbScale(srcFile,target64,DEFAULT_PROFILE_THUMB_SIZE);
    }

    /**
     * 40
     * @param srcFile
     * @param hashFileName
     * @return
     */
    public ImageIcon createContactsAvatar(File srcFile,String hashFileName){
        if(srcFile==null||!srcFile.exists())return null;
        String targetPath = AppGlobalCnst.consturactPath(AVATAR_THUMB_HOME,hashFileName);
        File thumbs4Contacts = new File(targetPath);
        try {
            generateThumbScale(srcFile,thumbs4Contacts,DEFAULT_CONTACTS_THUMB_SIZE);
            return new ImageIcon(targetPath);
        } catch (IOException e) {
            logger.warn(e.getMessage());
            return null;
        }
    }

    /**
     * 压缩
     * @param srcFile 原图
     * @param targetFile 保存的文件
     * @param size 大小
     * @return
     */
    private boolean generateThumbScale(File srcFile, File targetFile, int size) throws IOException {
        Image image = ImageIO.read(srcFile);
        if(size<32)size = DEFAULT_THUMB_SIZE;
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        int w=128,h=128;
        float scale = width*1.0F/height;
        float compScale = 1.0F;
        if(scale>=1.0F&& width>size){//按宽压缩
            compScale = size*1.0F/width;
            w = size;
            h = (int)(height*compScale);
        }else if(scale<1.0F && height>size) {
            h =  size;
            compScale = size*1.0F/height;
            w = (int)(width*compScale);
        }
        try {
            compressImage(image,targetFile,w,h);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @param img
     * @param destFile
     * @param w
     * @param h
     * @throws IOException
     */
    private void compressImage(Image img,File destFile,int w,int h) throws IOException {
        if(!destFile.getParentFile().exists())destFile.getParentFile().mkdirs();
        BufferedImage image = new BufferedImage(w,h,BufferedImage.SCALE_SMOOTH);
        image.getGraphics().drawImage(img,0,0,w,h,null);
        FileOutputStream out = new FileOutputStream(destFile);
        String destName = destFile.getName();
        String suffix = destName.substring(destName.lastIndexOf(".")+1);
        ImageIO.write(image,suffix,destFile);
        out.close();
    }

    /**
     * 联系人头像 32 *32
     * 40*40
     * .nbs/cache/avatars/thumbs
     */
    public static String getAvatarThumbHome() {
        return AVATAR_THUMB_HOME;
    }

    /**
     * 用户配置头标
     * .nbs/profile/avatars/
     * 放置128*128 上传IPFS
     * .nbs/profile/avatars/thumbs
     * 64*64 hashFileName
     */
    public static String getAvatarProfileHome() {
        return AVATAR_PROFILE_HOME;
    }

    /**
     *
     * @return
     */
    public static String getAvatarOriginHome() {
        return AVATAR_ORIGIN_HOME;
    }
}
