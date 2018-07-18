package io.nbs.client.helper;

import io.ipfs.multihash.Multihash;
import io.nbs.commons.helper.ConfigurationHelper;

import java.awt.*;
import java.io.IOException;

/**
 * @Package : io.nbs.client.helper
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/13-14:28
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class BrowserOperationHelper {
    private static BrowserOperationHelper ourInstance ;
    private static java.awt.Desktop desktop;
    private static String GATEWAY_BASE;

    public static BrowserOperationHelper getInstance() {
        if(ourInstance==null)ourInstance = new BrowserOperationHelper();
        return ourInstance;
    }

    private BrowserOperationHelper() {
        desktop = java.awt.Desktop.getDesktop();
        GATEWAY_BASE = ConfigurationHelper.getInstance().getGateWayURL();
    }

    /**
     *
     * @param hash
     */
    public void openURL(String hash){
        if(hash==null)return;
        try {
            Multihash.fromBase58(hash);
        }catch (RuntimeException e){
            e.printStackTrace();
            return;
        }
        new Thread(()->{
            java.net.URI uri = java.net.URI.create(GATEWAY_BASE+hash);
            if(desktop.isSupported(Desktop.Action.BROWSE)){
                try {
                    desktop.browse(uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
