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
     * 从URL获取图标
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







    /**
     * 生成头像图标
     * @param srcFile
     * @param identify
     * @param size
     * @return
     */
    public Image generateThumb(File srcFile,String identify,int size){
        if(size<32)size = DEFAULT_THUMB_SIZE;
        try {
            Image image = ImageIO.read(srcFile);
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
