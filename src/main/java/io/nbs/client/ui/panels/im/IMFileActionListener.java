package io.nbs.client.ui.panels.im;

import UI.AppMainWindow;
import io.nbs.client.Launcher;
import io.nbs.client.listener.IPFSFileUploader;
import io.nbs.client.ui.frames.MainFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @Package : io.nbs.client.ui.panels.im
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/5-17:57
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IMFileActionListener implements ActionListener {
    private static Logger logger = LoggerFactory.getLogger(IMFileActionListener.class);

    private IPFSFileUploader fileUploader;

    private JFileChooser jFileChooser;
    public IMFileActionListener(IPFSFileUploader fileUploader,JFileChooser fileChooser) {
        this.fileUploader = fileUploader;
        this.jFileChooser = fileChooser;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        this.jFileChooser.showDialog(Launcher.getContext().getCurrentFrame(),"选择");
        File selection = jFileChooser.getSelectedFile();
        if(selection==null)return;
        logger.info(selection.getAbsolutePath());
        fileUploader.addFileToIPFS(selection);
    }
}
