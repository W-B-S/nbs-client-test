package io.nbs.client.ui.panels.manage.body;

import io.nbs.client.ui.components.*;
import io.nbs.client.ui.components.forms.LCFormLabel;
import io.nbs.client.ui.components.forms.LCJTextArea;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.ui.panels.ParentAvailablePanel;
import io.nbs.client.ui.panels.manage.MMCubePanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : io.nbs.client.ui.panels.manage
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/11-15:04
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class MMRightPanel extends ParentAvailablePanel {
    private static MMRightPanel context;
    private static final int minWidth = 260;


    private LCJTextArea hashArea;//hash
    private LCJTextArea sizeArea;//size blocks
    private LCJTextArea fileArea;//localfile
    private LCJTextArea rateArea;
    private JPanel operPanel;//lock,unlock ,view ,download button
    private boolean inLocal = false;
    private JPanel topPanel;
    private JPanel middlePanel;

    /**
     * construction
     */
    public MMRightPanel(JPanel parent) {
        super(parent);
        initComponents();
        initView();
        setListeners();
    }

    /**
     * [initComponents description]
     *
     * @return {[type]} [description]
     */
    private void initComponents() {
        setBorder(MainFrame.redBorder);
        topPanel = new JPanel();
        middlePanel = new JPanel();
        buildDataInFo();

    }

    private void buildDataInFo(){
        hashArea = new LCJTextArea("QmTZCgednvfDkn3YH5R2c7APTtSHw6HaXRexyNnkqxqtuo");
        sizeArea = new LCJTextArea("SIZE ( BLOCKS ):","1291232","(","15",")");
        fileArea = new LCJTextArea("LOCAL FILE:");

    }



    /**
     * [initView description]
     *
     * @return {[type]} [description]
     */
    private void initView() {
        setLayout(new BorderLayout());

        middlePanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));

        initViewTop();
        add(topPanel,BorderLayout.NORTH);
        add(middlePanel,BorderLayout.CENTER);
    }

    private void initViewTop(){
        fileArea.appendText("c:/user/lanbery/.nbs/cache/20170829/jsjd.jpg");
        topPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP,10,5,true,false));
        LCFormLabel  hashForm = new LCFormLabel("HASH:");
        hashForm.setVolumeText("QmTZCgednvfDkn3YH5R2c7APTtSHw6HaXRexyNnkqxqtuo");
        LCFormLabel  sizeForm = new LCFormLabel("Size:");
        sizeForm.setVolumeText("121211224");



        topPanel.add(hashForm);
        topPanel.add(sizeForm);
                //new GBC(0,0).setWeight(1,1).setAnchor(GBC.EAST).setFill(GBC.HORIZONTAL).setInsets(0));

      /*  topPanel.add(hashArea,
                new GBC(1,0).setWeight(15,1).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL).setFill(0));*/

/*        topPanel.add(hashArea);
        topPanel.add(sizeArea);
        topPanel.add(fileArea);*/
       for(int i = 0;i<30;i++){
            boolean pined = i%5==1 ? true : false;
            String hash="Hash_"+i;
            long ds = i*1000l;
            MMCubePanel cubePanel = new MMCubePanel(hash,ds,pined);
            middlePanel.add(cubePanel);
        }
    }
    private void setListeners() {

    }

    /**
     * [getContext description]
     *
     * @return {[type]} [description]
     */
    public static MMRightPanel getContext() {
        return context;
    }
}