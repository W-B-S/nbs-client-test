package io.nbs.client.cache;

import io.nbs.client.ui.components.adapters.MessageMouseListener;
import io.nbs.client.ui.panels.manage.holder.BLKAttachDataViewHolder;

import javax.swing.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package : io.nbs.client.cache
 * @Description :
 * <p>
 *     优化缓存
 * </p>
 * @Author : lambor.c
 * @Date : 2018/7/11-18:13
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class AttachmentsViewHolderCacheHelper {
    private final int CACHE_CAPACITY = 10;
    private List<BLKAttachDataViewHolder> attaHolders = new ArrayList<>();

    private int attPosition = 0;

    public AttachmentsViewHolderCacheHelper(){
        initialized();
    }

    private void initialized(){
        new Thread(()->{
            for(int i=0;i<CACHE_CAPACITY;i++){
                attaHolders.add(new BLKAttachDataViewHolder());
            }
        }).start();
    }

    /**
     * 从缓存中获取
     * @return
     */
    public synchronized BLKAttachDataViewHolder tryGetAttachmentBlockDataViewHolder(){
        BLKAttachDataViewHolder holder = null;
        if(attPosition<CACHE_CAPACITY&& attaHolders.size()>0){
            holder = attaHolders.get(attPosition);
            attPosition++;
        }
        return holder;
    }

    public synchronized void reset(){
        for(int i = 0;i<attPosition;i++){
            BLKAttachDataViewHolder holder = attaHolders.get(i);
            //TODO
            clearMouseListener(holder.attachmentPanel);
            clearMouseListener(holder.attachmentTitle);
        }

        attPosition = 0;
    }


    /**
     * 清空事件
     * @param component
     */
    private void clearMouseListener(JComponent component){
        for(MouseListener ml : component.getMouseListeners()){
            if(ml instanceof MessageMouseListener){
                component.removeMouseListener(ml);
            }
        }
    }
}
