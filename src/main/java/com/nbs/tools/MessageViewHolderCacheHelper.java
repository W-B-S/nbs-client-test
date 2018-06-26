package com.nbs.tools;

/**
 * @Package : com.nbs.tools
 * @Description :
 * <p>
 *     提供消息ViewHolder缓存,
 *     对消息的ViewHolder进入缓存能大大加速消息列表的加载速度，在刚进入房间时，默认先加载10条消息，
 * </p>
 * @Author : lambor.c
 * @Date : 2018/6/26-23:46
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class MessageViewHolderCacheHelper {
    private final int CACHE_CAPACITY = 10;
}
