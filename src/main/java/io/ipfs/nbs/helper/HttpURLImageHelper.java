package io.ipfs.nbs.helper;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import io.ipfs.nbs.Launcher;
import io.ipfs.nbs.cnsts.AppGlobalCnst;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Package : io.ipfs.nbs.utils
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/30-10:33
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class HttpURLImageHelper {
    private static String IPFS_PREFFIX ;
    private static final String DEFAULT_IPFS_PRESUFFIX = "http://127.0.0.1:8080/ipfs/";
    private static final int DEFAULT_THUMB_SIZE = 128;


    private HttpURLImageHelper() {
    }

    private static class HttpURLImageToolHolder{
        private static HttpURLImageHelper instance = new HttpURLImageHelper();
    }

    /**
     *
     * @param ipfsPreffix
     * @return
     */
    public static HttpURLImageHelper getInstance(String ipfsPreffix){
        return  HttpURLImageToolHolder.instance.initIpfsPreffix(ipfsPreffix);
    }

    /**
     *
     * @return
     */
    public static HttpURLImageHelper getInstance(){
        return HttpURLImageToolHolder.instance.initIpfsPreffix(DEFAULT_IPFS_PRESUFFIX);
    }

    private HttpURLImageHelper initIpfsPreffix(String preffix){
        if(StringUtils.isNotBlank(preffix)){
            IPFS_PREFFIX = preffix;
        }else {
            IPFS_PREFFIX = DEFAULT_IPFS_PRESUFFIX;
        }
        return this;
    }

    /**
     *
     * @param url
     * @param fileName
     * @return
     */
    public File saveFileFromUrl(URL url,String fileName) throws Exception {
        String avatarSavePath = AppGlobalCnst.consturactPath(Launcher.appBasePath,"cache","avatars",fileName);
        File checkFile = new File(avatarSavePath);
        if(checkFile.isDirectory())throw new Exception(StringUtils.join("文件路径不正确:",fileName));
        System.out.println(checkFile.getAbsolutePath());
        try {
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            DataInputStream in = new DataInputStream(conn.getInputStream());
            FileImageOutputStream out = new FileImageOutputStream(checkFile);
            byte[] buffer = new byte[4096];
            int count = 0;
            while ((count = in.read(buffer))>0){
                out.write(buffer,0,count);
            }
            out.close();
            in.close();
            return checkFile;
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("获取文件错误",e.getCause());
        }
    }

    private void saveURL(URL url,File saveFile){

    }

    public ImageIcon generateThumbScale(Image image,String identifyName,int size){
        if(size<32)size = DEFAULT_THUMB_SIZE;
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        int w=128,h=128;
        float scale = width*1.0F/height;
        String path = AppGlobalCnst.consturactPath(Launcher.appBasePath,"cache","thumbs",identifyName);
        File destFile = new File(path);
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
            compressImage(image,destFile,w,h);
            if(destFile.exists()){
                ImageIcon iconThumb = new ImageIcon(path);
                return iconThumb;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void compressImage(Image img,File destFile,int w,int h) throws IOException {
        if(!destFile.getParentFile().exists())destFile.getParentFile().mkdirs();
        BufferedImage image = new BufferedImage(w,h,BufferedImage.SCALE_SMOOTH);
        image.getGraphics().drawImage(img,0,0,w,h,null);
        FileOutputStream out = new FileOutputStream(destFile);
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        encoder.encode(image);
        out.close();

    }

    public Image generateThumb(Image image,String identify,int size){
        if(size<32)size = DEFAULT_THUMB_SIZE;

        try {
            int[] imageSize = getImageSize(image);
            int oriW = imageSize[0];
            int oriH = imageSize[1];
            float scale = imageSize[0]*1.0F/imageSize[1];

            if(oriW > oriH && oriW>size){
                oriW = size;
                oriH = (int)(oriH/scale);
            }else if(oriW<oriH&&oriH>size){
                oriH = size;
                oriW = (int)(oriH*scale);
            }
            BufferedImage temBugImage = new BufferedImage(oriW,oriW,BufferedImage.TYPE_INT_RGB);
            temBugImage.getGraphics().drawImage(image.getScaledInstance(oriW,oriH,Image.SCALE_SMOOTH),0,0,null);
            File cacheFile = new File(AppGlobalCnst.consturactPath(Launcher.appBasePath,"cache","avatars",identify+"_thumb"));
            System.out.println(cacheFile.getAbsolutePath());
            FileOutputStream out = new FileOutputStream(cacheFile);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(temBugImage);
            out.close();
            return temBugImage;
        }catch (IOException e){

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private int[] getImageSize(Image image) throws Exception {
        if(image==null)return new int[]{16,16};
        int[] sizes = {1,1};
        try {
            sizes[0] = image.getWidth(null);
            sizes[1] = image.getHeight(null);
        }catch (Exception e){
            throw new Exception("获取Image错误",e.getCause());
        }
        return sizes;
    }
}
