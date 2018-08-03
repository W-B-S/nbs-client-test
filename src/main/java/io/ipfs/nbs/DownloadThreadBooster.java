package io.ipfs.nbs;

import com.nbs.entity.IPFSFileEntity;
import com.sun.xml.internal.bind.v2.util.ByteArrayOutputStreamEx;
import io.ipfs.api.IPFS;
import io.ipfs.multihash.Multihash;
import io.nbs.client.Launcher;
import io.nbs.client.cnsts.AppGlobalCnst;
import io.nbs.commons.helper.ConfigurationHelper;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Package : io.ipfs.nbs
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/8/2-15:10
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class DownloadThreadBooster {
    public static final int MAX_SIZE = 10;
    private BlockingQueue<IPFSFileEntity> queue ;
    private String DEF_PATH;
    private static String savePath;
    private boolean running =false;
    private DWConsumer dwConsumer;

    private IPFS ipfs1;

    private DownloadThreadBooster(){
        DEF_PATH = AppGlobalCnst.consturactPath(Launcher.appBasePath,"download");
        savePath = DEF_PATH;
        File desk = new File(savePath);
        if(!desk.exists())desk.mkdirs();
        dwConsumer = new DWConsumer();
        queue = new ArrayBlockingQueue<>(MAX_SIZE);
    }

    private static class ThreadBoosterHold {
        private static DownloadThreadBooster dtb = new DownloadThreadBooster();
    }

    public static DownloadThreadBooster getInstance(){
        return ThreadBoosterHold.dtb;
    }

    public BlockingQueue<IPFSFileEntity> getQueue() {
        return queue;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    /**
     *
     */
    class DWConsumer extends Thread{
        @Override
        public void run() {
            download();
        }

        /**
         * 下载
         */
        private void download(){
            while (true){
                try {
                    IPFSFileEntity entity = queue.take();
                    IPFS ipfs = getIpfs();
                    String hash58 = entity.getHash();
                    String fileName = StringUtils.isBlank(entity.getSaveName()) ? entity.getHash()+(entity.getSuffix()==null?"":entity.getSuffix()) : entity.getSaveName();
                    ByteArrayOutputStream baos=null;
                    FileOutputStream fos = null;
                    try {
                        Multihash multihash = Multihash.fromBase58(hash58);
                        byte[] datas = ipfs.get(multihash);
                        long ts = System.currentTimeMillis();
                        String tmpFileName = AppGlobalCnst.consturactPath(savePath,fileName);
                        File tmpFile = new File(tmpFileName);
                        if(tmpFile.exists()){
                            tmpFile.delete();
                        }
                        tmpFile.createNewFile();
                        baos = new ByteArrayOutputStream();
                        baos.write(datas);

                        fos = new FileOutputStream(tmpFile);

                        baos.writeTo(fos);
                       // DataOutputStream dos = new DataOutputStream(new FileOutputStream(tmpFile));
                        fos.flush();
                        File saveFile = new File(AppGlobalCnst.consturactPath(savePath,fileName+".mp4"));
                        if(saveFile.exists())saveFile.delete();
                        tmpFile.renameTo(saveFile);
                        System.out.println("success download...");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            if(baos!=null)baos.close();
                            if(fos!=null)fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e1) {
                }
            }
        }
    }

    /**
     * 添加下载队列
     * @param entity
     * @return true add success false
     */
    public boolean addDownloadTask(IPFSFileEntity entity){
        boolean b = queue.offer(entity);
        return b;
    }

    /**
     * 启动下载
     */
    public void start(){
        if(!running){
            running = true;
            dwConsumer.start();
        }
    }

    /**
     *
     * @return
     */
    private IPFS getIpfs(){
        if(ipfs1==null){
            String url = ConfigurationHelper.getInstance().getIPFSAddress();
            ipfs1 = new IPFS(url);
        }
        return ipfs1;
    }

}
