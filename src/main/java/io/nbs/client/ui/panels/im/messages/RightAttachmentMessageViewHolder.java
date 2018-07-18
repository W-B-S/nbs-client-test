package io.nbs.client.ui.panels.im.messages;

import io.nbs.client.ui.components.GBC;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : io.nbs.client.ui.panels.im.messages
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/13-16:12
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class RightAttachmentMessageViewHolder extends IMAttachmentMessageViewHolder {

    /**
     *
     */
    public RightAttachmentMessageViewHolder() {
        initComponents();
        initView();
    }

    private void initComponents(){
        messageBubble = new JPanel();
    }

    /**
     *
     */
    private void initView(){
        /*------------------------------------------------------------------------- */
        /* timePanel                                                                */
        /* titleContent                                                     avatars */
        /*                                                                  avatars */
        /*                                                                  avatars */
        /* sizeLabel                            openBrowser addIPFS         avatars */
        /*------------------------------------------------------------------------- */
        setLayout(new BorderLayout());
        timePanel.add(time);

        attachmentPanel.setLayout(new GridBagLayout());

        attachmentPanel.add(attachmentIcon,
                new GBC(0,0).setWeight(1,1).setInsets(5,5,5,0));
        attachmentPanel.add(attachTitle,
                new GBC(1,0).setWeight(100,1).setAnchor(GBC.NORTH));
        attachmentPanel.add(sizeLabel,
                new GBC(1,1).setWeight(1,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.SOUTH).setInsets(0,8,5,5));
        messageBubble.add(attachmentPanel);
        JPanel wholeMsgPanel = new JPanel();
        wholeMsgPanel.setLayout(new BorderLayout());
        wholeMsgPanel.add(attachmentPanel,BorderLayout.CENTER);
        wholeMsgPanel.add(toolsPanel,BorderLayout.SOUTH);

        messageAvatarPanel.setLayout(new GridBagLayout());


        this.add(timePanel,BorderLayout.NORTH);
        this.add(messageAvatarPanel,BorderLayout.CENTER);
    }
}
