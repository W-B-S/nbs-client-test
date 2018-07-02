package io.nbs.client.ui.components;

import javax.swing.*;
import java.io.File;

/**
 * @Package : io.nbs.client.ui.components
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/2-13:51
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class EditableAvatarPanel extends JPanel {
    private static EditableAvatarPanel context;
    private static String text;

    private NBSButton editorBTN;
    private File selectFile;

    private int imageMaxW = 200;
    private int imageMaxH = 200;


    public static EditableAvatarPanel getContext() {
        return context;
    }

    /**
     * 自适应图片
     */
    class ImageAdjustLabel extends JLabel
    {

    }
}
