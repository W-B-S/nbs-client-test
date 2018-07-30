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
    private static final int DEFAULT_PROFILE_AVATAR_SIZE = 200;
    private static final int DEFAULT_PROFILE_THUMB_SIZE = 48;
    private static final int DEFAULT_CONTACTS_THUMB_SIZE = 64;
    public static final String AVATAR_SUFFIX = ".png";
    private static AvatarImageHandler ourInstance = new AvatarImageHandler();

    public static AvatarImageHandler getInstance() {
        return ourInstance;
    }


    /**
     * 联系人头像 32 *32
     * 40*40
     * .nbs/cache/avatars/custom
     */
    private static String AVATAR_CUSTOM_HOME;
    /**
     *  用户配置头标
     * .nbs/profile/avatars/
     *  放置128*128 上传IPFS
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
        AVATAR_ORIGIN_HOME = AppGlobalCnst.consturactPath(Launcher.appBasePath,"cache","avatars");
        AVATAR_CUSTOM_HOME = AppGlobalCnst.consturactPath(Launcher.appBasePath,"cache","avatars","custom");
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

        destFile = new File(AVATAR_CUSTOM_HOME);
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
     *
     * @param filePath
     * @param size
     * @return
     */
    public ImageIcon getImageIconFromOrigin(String filePath,int size){
        File originFile = new File(filePath);
        return getImageIconFromOrigin(originFile,size);
    }

    /**
     * 获取头像，
     * @param originFile
     * @param size
     * @return
     */
    public ImageIcon getImageIconFromOrigin(File originFile,int size){
        if(originFile==null||originFile.isDirectory()||!originFile.exists())
            return IconUtil.getIcon(this,"/icons/nbs128.png",size,size);
        ImageIcon icon = null;
        try{
            icon = new ImageIcon(getAvatarScaleImage(originFile,size));
            return icon;
        }catch (IOException e){
            return IconUtil.getIcon(this,"/icons/nbs128.png",size,size);
        }
    }

    private Image getAvatarScaleImage(File file,int size) throws IOException {
        BufferedImage srcImage = ImageIO.read(file);
        if(size<=10)size=10;
        int oW =  srcImage.getWidth();
        int oH = srcImage.getHeight();
        float oScale = oW*10F/oH;
        int nW=10,nH = 10;float scale = 1.0F;
        if(oScale>=1.0F){
            nW = size;
            scale = size*1.0F/oW;
            nH = (int)(oH*scale);
        }else {
            nH = size;
            scale = size*1.0F/oH;
            nW = (int)(oW*scale);
        }
        return srcImage.getScaledInstance(nW,nH,BufferedImage.SCALE_SMOOTH);
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
     * 创建用户头像 所有 转换为png
     * 128,64
     * @param hashFileName
     * @throws Exception
     */
    public String createdAvatar4Profile(File srcFile,String hashFileName) throws Exception {
        if(!srcFile.exists())throw new Exception("图片源不存在.");
        String originName = StringUtils.isBlank(hashFileName) ? srcFile.getName() : hashFileName;
        /**
         * 128*128
         */
        originName = originName.substring(0,originName.lastIndexOf("."))+AVATAR_SUFFIX;
        String target128 = AppGlobalCnst.consturactPath(AVATAR_PROFILE_HOME,originName);
        File targetFile128 = new File(target128);
        generateThumbScale(srcFile,targetFile128,DEFAULT_PROFILE_AVATAR_SIZE);
        return originName;
    }

    /**
     * 40*40png
     * @param srcFile
     * @param hashFileName
     * @return
     */
    public ImageIcon createContactsAvatar(File srcFile,String hashFileName){
        if(srcFile==null||!srcFile.exists())return null;
        String targetPath = AppGlobalCnst.consturactPath(AVATAR_CUSTOM_HOME,hashFileName);
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
     * @param srcImg
     * @param destFile
     * @param w
     * @param h
     * @throws IOException
     */
    private void compressImage(Image srcImg,File destFile,int w,int h) throws IOException {
        if(!destFile.getParentFile().exists()){
            destFile.getParentFile().mkdirs();
        }
        if(!destFile.exists()){
            destFile.createNewFile();
        }

        //BufferedImage outBi = ImageIO.read(destFile);
        /**
         *
         */
        BufferedImage targetImg = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = targetImg.createGraphics();
        // 抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        targetImg = g2d.getDeviceConfiguration().createCompatibleImage(w,h,Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = targetImg.createGraphics();
        Image formatSrcImg = srcImg.getScaledInstance(w,h,Image.SCALE_AREA_AVERAGING);

        g2d.drawImage(formatSrcImg,0,0,null);
        g2d.dispose();
        String destName = destFile.getName();
        //destName = destName.substring(0,destName.lastIndexOf("."));
        String suffix = destName.substring(destName.lastIndexOf(".")+1);
        ImageIO.write(targetImg,suffix,destFile);
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
     * 获取联系人缓存目录
     * @return
     */
    public static String getAvatarCustomHome() {
        return AVATAR_CUSTOM_HOME;
    }

    /**
     *
     * @return
     */
    public static String getAvatarOriginHome() {
        return AVATAR_ORIGIN_HOME;
    }

    /**
     *
     * @param srcFile
     * @param size
     * @return
     */
    public ImageIcon getAvatarScaleIcon(File srcFile,int size){
        if(!srcFile.exists()||srcFile.isDirectory())return null;
        if(size<20)size=20;
        try {
            String fineName = srcFile.getName();
            BufferedImage image = ImageIO.read(srcFile);
            int oriWidth = image.getWidth();
            int oriHeight = image.getHeight();
            float oriScale = oriWidth*1.0F/oriHeight;
            int nW=32,nH=32;
            float zipScale = 1.0F;
            if(oriScale>=1.0F){
                //按宽压缩
                nW= size;
                zipScale = size*1.0F/oriWidth;
                nH = (int)(oriHeight*zipScale);
            }else{
                nH = size;
                zipScale = size*1.0F/oriHeight;
                nW = (int)(oriWidth*zipScale);
            }

            ImageIcon newIcon = new ImageIcon(AppGlobalCnst.consturactPath(AVATAR_CUSTOM_HOME,"f"+size,fineName));
            image.getScaledInstance(nW,nH,Image.SCALE_SMOOTH);
            newIcon.setImage(image);
            return newIcon;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
