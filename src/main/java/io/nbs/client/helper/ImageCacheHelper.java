package io.nbs.client.helper;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import io.ipfs.api.IPFS;
import io.ipfs.multihash.Multihash;
import io.nbs.client.Launcher;
import io.nbs.client.cnsts.AppGlobalCnst;
import io.nbs.commons.helper.ConfigurationHelper;
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
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Package : io.nbs.client.helper
 * @Description :
 * <p>
 *     图片缓存处理
 *     ImageCache
 * </p>
 * @Author : lambor.c
 * @Date : 2018/7/2-21:51
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ImageCacheHelper {
    private static Logger logger = LoggerFactory.getLogger(ImageCacheHelper.class);
    //缩略图
    public static final int THUMB = 0;
    //原图
    public static final int ORIGINAL = 1;

    /**
     * .nbs/cache/image/
     */
    public String IMAGE_CACHE_ROOT_PATH;


    public ImageCacheHelper( ) {
        this.IMAGE_CACHE_ROOT_PATH =
                AppGlobalCnst.consturactPath(Launcher.appBasePath,"cache","image");
        //.nbs/cache/image

        File cacheFile = new File(IMAGE_CACHE_ROOT_PATH);
        if(!cacheFile.exists()){
            cacheFile.mkdirs();
            logger.info("创建image缓存目录:{}",IMAGE_CACHE_ROOT_PATH);
        }
    }

    /**
     * 获取缩略图缓存
     * 存在返回ImageIcon 不存在返回null
     * @param identify
     * @return
     */
    public ImageIcon tryGetThumbCache(String identify){
        File cacheFile = new File(AppGlobalCnst.consturactPath(IMAGE_CACHE_ROOT_PATH,identify+"_thumb"));
        if(cacheFile.exists()){
            ImageIcon icon = new ImageIcon(cacheFile.getAbsolutePath());
            return icon;
        }
        return null;
    }

    /**
     * 异步获取缩略图
     * http://127.0.0.1:8080/ipfs/hash
     * @param identify  文件名hash
     * @param suffix
     * @param listener
     */
    public void getThumbAsynchronously(String identify,String suffix,ImageCacheRequestListener listener){

    }

    /**
     *
     * @param identity
     * @param suffix
     * @param listener
     */
    public void getOriginAsynchronously(String identity,String suffix,ImageCacheRequestListener listener){
        getImage(ORIGINAL,identity,suffix,listener);
    }

    /**
     * 获取图片
     * http://127.0.0.1:8080/ipfs/hash
     * @param reqType
     * @param identify
     * @param suffix
     * @param listener
     */
    private void getImage(int reqType,String identify,String suffix ,ImageCacheRequestListener listener){
        new Thread(()->{
            File cacheFile ;
            if(reqType==THUMB){
                cacheFile = new File(AppGlobalCnst.consturactPath(IMAGE_CACHE_ROOT_PATH , identify +"_thumb"));
            }else {
                cacheFile = new File(AppGlobalCnst.consturactPath(IMAGE_CACHE_ROOT_PATH,identify+suffix));
            }

            if(cacheFile.exists()){
                logger.info("本地缓存图片:{}",cacheFile.getAbsolutePath());
                ImageIcon icon = new ImageIcon(cacheFile.getAbsolutePath());
                listener.onSuccess(icon,cacheFile.getAbsolutePath());
            }else {
                String IPFS_HTTP_URL = ConfigurationHelper.getInstance().getGateWayURL();
                URL url = null;
                File originFile = new File(AppGlobalCnst.consturactPath(IMAGE_CACHE_ROOT_PATH,identify+suffix));
                try {
                    url = new URL(AppGlobalCnst.consturactPath(IPFS_HTTP_URL,identify));
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    DataInputStream in = new DataInputStream(conn.getInputStream());
                    FileImageOutputStream out = new FileImageOutputStream(originFile);
                    byte[] buffer = new byte[4096];
                    int count = 0;
                    while ((count = in.read(buffer))>0){
                        out.write(buffer,0,count);
                    }
                    out.close();
                    in.close();
                    Image image = ImageIO.read(originFile);
                    createThumb(image,identify);
                    ImageIcon newIcon;
                    if(reqType == THUMB){
                        newIcon = new ImageIcon(image);
                    }else {
                        newIcon = new ImageIcon(originFile.getAbsolutePath());
                    }
                    listener.onSuccess(newIcon,cacheFile.getAbsolutePath());
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    listener.onFail("图片不存在.");
                }

            }
        }).start();
    }

    /**
     * 生成图片缩略图
     *
     * @param image
     * @param identify
     */
    public void createThumb(Image image, String identify)
    {
        try
        {
            int[] imageSize = getImageSize(image);
            int destWidth = imageSize[0];
            int destHeight = imageSize[1];

            float scale = imageSize[0] * 1.0F / imageSize[1];

            if (imageSize[0] > imageSize[1] && imageSize[0] > 200)
            {
                destWidth = 200;
                destHeight = (int) (destWidth / scale);
            }
            else if (imageSize[0] < imageSize[1] && imageSize[1] > 200)
            {
                destHeight = 200;
                destWidth = (int) (destHeight * scale);
            }

            // 开始读取文件并进行压缩
            BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);

            tag.getGraphics().drawImage(image.getScaledInstance(destWidth, destHeight, Image.SCALE_SMOOTH), 0, 0, null);

            File cacheFile = new File(AppGlobalCnst.consturactPath(IMAGE_CACHE_ROOT_PATH,identify + "_thumb") );
            FileOutputStream out = new FileOutputStream(cacheFile);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(tag);
            out.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public static int[] getImageSize(Image image)
    {

        if (image == null)
        {
            return new int[]{10, 10};
        }
        int result[] = {0, 0};
        try
        {
            result[0] = image.getWidth(null); // 得到源图宽
            result[1] = image.getHeight(null); // 得到源图高
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public interface ImageCacheRequestListener{
        /**
         *
         * @param icon
         * @param path
         */
        void onSuccess(ImageIcon icon,String path);

        /**
         *
         * @param reason
         */
        void onFail(String reason);
    }
}
