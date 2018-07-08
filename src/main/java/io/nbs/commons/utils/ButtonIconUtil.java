package io.nbs.commons.utils;

import io.nbs.client.ui.components.LamButtonIcon;
import io.nbs.client.ui.components.NBSIconButton;

import javax.swing.*;
import java.util.Properties;

/**
 * @Package : io.ipfs.nbs.utils
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/30-19:52
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ButtonIconUtil {

    private static final String BUTTON_PREFFIX = "/icons/tools/";

    public static final ImageIcon CHAT_NORMAL = new ImageIcon("");

    public static final NBSIconButton searchBtn =
            new NBSIconButton(
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/search.png")),
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/search_pre.png")),
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/search.png")),
                    "搜索"
            );

    /**
     * 信息
     */
    public static NBSIconButton infoBTN =
            new NBSIconButton(
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/info.png")),
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/info_pre.png")),
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/info_dis.png")),
                    "PEER INFO"
            );
    public static NBSIconButton imBTN =
            new NBSIconButton(
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/chat.png")),
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/chat_pre.png")),
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/chat_dis.png")),
                    "聊天"
            );
    public static NBSIconButton dataBTN =
            new NBSIconButton(
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/manage.png")),
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/manage_pre.png")),
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/manage_dis.png")),
                    "链上数据"
            );
    public static NBSIconButton videoBTN =
            new NBSIconButton(
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/media.png")),
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/media_pre.png")),
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/media_dis.png")),
                    "视频"
            );

    public static NBSIconButton musicBTN =
            new NBSIconButton(
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/media.png")),
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/media_pre.png")),
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/media_dis.png")),
                    "音乐"
            );
    public static NBSIconButton confBTN;
    public static NBSIconButton aboutBTN =
            new NBSIconButton(
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/NBS_ic_dis.png")),
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/NBS_ic_pre.png")),
                    "关于NBS"
            );

    private Properties i18nProps;
    private ButtonIconUtil(Properties i18nProps){

    }

    private NBSIconButton initNBSButton(){

        return null;
    }
}
