package io.nbs.client.ui.panels.manage.adapter;

import io.nbs.client.adapter.BaseAdapter;
import io.nbs.client.cache.AttachmentsViewHolderCacheHelper;
import io.nbs.client.ui.components.NbsListView;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.ui.holders.AvatarViewHolder;
import io.nbs.client.ui.holders.ViewHolder;
import io.nbs.client.ui.panels.manage.holder.BLKAttachDataViewHolder;
import io.nbs.client.ui.panels.manage.holder.AttachDataViewHolder;
import io.nbs.client.ui.panels.manage.listener.FillDetailInfoListener;
import io.nbs.client.vo.AttachmentDataDTO;
import io.nbs.commons.utils.DataSizeFormatUtil;
import io.nbs.commons.utils.IconUtil;
import io.nbs.commons.utils.TimeUtil;
import io.nbs.sdk.beans.OnlineMessage;
import io.nbs.sdk.beans.PeerInfo;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package : io.nbs.client.ui.panels.manage.adapter
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/11-16:58
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class AttachmentDataAdapter extends BaseAdapter<AttachDataViewHolder> {
    private List<AttachmentDataDTO> attaDatas;
    private NbsListView listView;
    private PeerInfo currentPeer;

    private AttachmentsViewHolderCacheHelper attachmentsViewHolderCacheHelper;
    private FillDetailInfoListener listener;

    /**
     * 选中的
     */
    private BLKAttachDataViewHolder viewHolder;

    public AttachmentDataAdapter(
            List<AttachmentDataDTO> attaDatas,
            NbsListView listView,
            AttachmentsViewHolderCacheHelper cacheHelper,
            FillDetailInfoListener listener) {
        currentPeer = MainFrame.getContext().getCurrentPeer();
        this.listener = listener;
        this.attaDatas = attaDatas;
        this.attachmentsViewHolderCacheHelper = cacheHelper;
        this.listView = listView;
    }

    /**
     * 创建展示条目
     * @param viewType
     * @return
     */
    @Override
    public AttachDataViewHolder onCreateViewHolder(int viewType) {
        //TODO
        switch (viewType){
            case 0:
                //不存在
            case 1:
            case 2:
                //local file
                BLKAttachDataViewHolder holder = attachmentsViewHolderCacheHelper.tryGetAttachmentBlockDataViewHolder();
                if(holder==null){
                    holder = new BLKAttachDataViewHolder();
                    holder.setFillDetailListener(listener);
                }
                return holder;
        }
        return new BLKAttachDataViewHolder(listener) ;
    }

    /**
     * 处理制定位置内容
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(AttachDataViewHolder viewHolder, int position) {
        if(viewHolder==null)return;

        final AttachmentDataDTO entity = attaDatas.get(position);

        AttachmentDataDTO preEntity = position == 0 ? null : attaDatas.get(position-1);
        // begin draw layout
        processOperIconAndTime(entity,preEntity,viewHolder);

        processBLKAttachView(viewHolder,entity);
    }

    /**
     * 处理时间与操作图片
     * @param entity
     */
    private void processOperIconAndTime(AttachmentDataDTO entity, AttachmentDataDTO preEntity, AttachDataViewHolder holder)
    {
        if(preEntity != null){
            if(TimeUtil.inTheSameDate(entity.getCtime(),preEntity.getCtime())){
                holder.time.setVisible(false);
            }else {
                holder.time.setVisible(true);
                holder.time.setText(entity.getCtime());
            }
        }else {
            holder.time.setVisible(true);
            holder.time.setText(entity.getCtime());
        }
        //处理图标
    }

    /**
     * 设置值
     * @param viewHolder
     * @param dataDTO
     */
    private void processBLKAttachView(AttachDataViewHolder viewHolder,AttachmentDataDTO dataDTO)
    {
        String title = StringUtils.isNotBlank(dataDTO.getFname()) ? dataDTO.getFname() : dataDTO.getId();
        BLKAttachDataViewHolder holder = (BLKAttachDataViewHolder)viewHolder;
        holder.attachmentTitle.setText(title);

        Map<String,Object> map = new HashMap<>();
        map.put("attachmentHash",dataDTO.getId());
        map.put("fname",dataDTO.getCachedfile());
        map.put("inlocal",dataDTO.getInlocal());
        map.put("messageId",dataDTO.getSeqno());//messageId uuid

        holder.attachmentPanel.setTag(dataDTO);
        ImageIcon icon = IconUtil.getIcon(this,"/icons/data-works.png",80,80);
        holder.attachIcon.setIcon(icon);
        holder.srcPanel.setText(dataDTO.getPeername()==null ? "" : dataDTO.getPeername());
        holder.hashTitle.setText(dataDTO.getId());

        String sizeShow = DataSizeFormatUtil.formatDataSize(dataDTO.getFsize());
        holder.sizeLabel.setText(sizeShow);


        //lanbery setAttachmentShow
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
