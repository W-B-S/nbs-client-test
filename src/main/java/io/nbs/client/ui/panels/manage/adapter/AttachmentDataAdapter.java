package io.nbs.client.ui.panels.manage.adapter;

import io.nbs.client.adapter.BaseAdapter;
import io.nbs.client.cache.AttachmentsViewHolderCacheHelper;
import io.nbs.client.ui.components.NbsListView;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.ui.holders.AvatarViewHolder;
import io.nbs.client.ui.panels.manage.holder.BLKAttachDataViewHolder;
import io.nbs.client.ui.panels.manage.holder.AttachDataViewHolder;
import io.nbs.client.vo.AttachmentDataEntity;
import io.nbs.commons.utils.TimeUtil;
import io.nbs.sdk.beans.PeerInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Package : io.nbs.client.ui.panels.manage.adapter
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/11-16:58
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class AttachmentDataAdapter extends BaseAdapter<AttachDataViewHolder> {
    private List<AttachmentDataEntity> attaDatas;
    private NbsListView listView;
    private PeerInfo currentPeer;

    private AttachmentsViewHolderCacheHelper attachmentsViewHolderCacheHelper;


    /**
     * 选中的
     */
    private BLKAttachDataViewHolder viewHolder;

    public AttachmentDataAdapter(List<AttachmentDataEntity> attaDatas,NbsListView listView,AttachmentsViewHolderCacheHelper cacheHelper) {
        currentPeer = MainFrame.getContext().getCurrentPeer();
        this.attaDatas = attaDatas;
        this.attachmentsViewHolderCacheHelper = cacheHelper;
        this.listView = listView;
    }

    @Override
    public AttachDataViewHolder onCreateViewHolder(int viewType) {
        //TODO
        switch (viewType){
            case 0:
                //不存在
                break;
            case 1:
                //blk中
                break;
            case 2:
                //local file
                break;
        }
        return null ;
    }

    /**
     * 处理制定位置内容
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(AttachDataViewHolder viewHolder, int position) {
        if(viewHolder==null)return;

        final AttachmentDataEntity entity = attaDatas.get(position);

        AttachmentDataEntity preEntity = position == 0 ? null : attaDatas.get(position-1);
        // begin draw layout
        processOperIconAndTime(entity,preEntity,viewHolder);

    }

    /**
     * 处理时间与操作图片
     * @param entity
     */
    private void processOperIconAndTime(AttachmentDataEntity entity,AttachmentDataEntity preEntity,AttachDataViewHolder holder){
        if(preEntity != null){
            if(TimeUtil.inTheSameDate(entity.getCtime(),preEntity.getCtime())){
                holder.time.setVisible(false);
            }else {
                holder.time.setVisible(true);
                holder.time.setText(TimeUtil.formatMonthDay(entity.getCtime()));
            }
        }else {
            holder.time.setVisible(true);
            holder.time.setText(TimeUtil.formatMonthDay(entity.getCtime()));
        }
        //处理图标
    }

    @Override
    public AvatarViewHolder onCreateHeaderViewHolder(int viewType, int position) {
        return super.onCreateHeaderViewHolder(viewType, position);
    }

    @Override
    public int getCount() {
        return attaDatas.size();
    }
}
