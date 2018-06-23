package com.nbs.ui.adapter;

import com.nbs.ui.AvatarViewHolder;
import com.nbs.ui.ViewHolder;

/**
 * @Package : com.nbs.ui.adapter
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/23-23:17
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public abstract class BaseAdapter<T extends ViewHolder> {
    /**
     * 返回数量
     * @return
     */
    public int getCount(){return 0;}

    public abstract T onCreateViewHolder(int viewType);


    public AvatarViewHolder onCreateHeaderViewHolder(int viewType,int postion){
        return null;
    }

    public int getItemViewType(int position){
        return 0;
    }

    public abstract void onBindViewHolder(T viewHolder,int position);

    public void onBindHeaderViewHolder(AvatarViewHolder viewHolder,int position){

    }
}
