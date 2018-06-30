package io.ipfs.nbs.utils;

import io.ipfs.nbs.ui.components.LamButtonIcon;
import io.ipfs.nbs.ui.components.NBSIconButton;

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

    public static NBSIconButton infoBTN =
            new NBSIconButton(
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/info_normal.png")),
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/info_active.png")),
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/info_normal.png")),
                    "PEER INFO"
            );
    public static NBSIconButton imBTN =
            new NBSIconButton(
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/chat_normal.png")),
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/chat_active.png")),
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/chat_normal.png")),
                    "聊天"
            );
    public static NBSIconButton dataBTN =
            new NBSIconButton(
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/chain_normal.png")),
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/chain_active.png")),
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/chain_normal.png")),
                    "链上数据"
            );
    public static NBSIconButton videoBTN =
            new NBSIconButton(
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/music_normal.png")),
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/music_active.png")),
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/music_normal.png")),
                    "视频"
            );

    public static NBSIconButton musicBTN =
            new NBSIconButton(
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/music_normal.png")),
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/music_active.png")),
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/music_normal.png")),
                    "音乐"
            );
    public static NBSIconButton confBTN;
    public static NBSIconButton aboutBTN =
            new NBSIconButton(
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/nbs40.png")),
                    new ImageIcon(LamButtonIcon.class.getResource("/icons/tools/nbs40_normal.png")),
                    "关于NBS"
            );

    private Properties i18nProps;
    private ButtonIconUtil(Properties i18nProps){

    }

    private NBSIconButton initNBSButton(){

        return null;
    }
}
