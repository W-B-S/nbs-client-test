package io.nbs.client.ui.panels.manage.body;

import com.nbs.ui.components.NbsBorder;
import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.ui.components.*;
import io.nbs.client.ui.components.forms.LCFormLabel;
import io.nbs.client.ui.components.forms.LCJTextArea;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.ui.panels.ParentAvailablePanel;
import io.nbs.client.ui.panels.manage.MMCubePanel;
import io.nbs.client.ui.panels.manage.listener.FillDetailInfoListener;
import io.nbs.client.vo.AttachmentDataDTO;
import io.nbs.commons.utils.DataSizeFormatUtil;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
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


    private Object holderObj;

    private LCAutoJTextPanel hashArea;//hash
    private LCAutoJTextPanel nameArea;//size blocks
    private LCAutoJTextPanel localFileArea;//localfile
    private LCJlabel hashLabel;
    private LCJlabel sizeLabel;
    private LCJlabel sizeVol;
    private LCJlabel precentLabel;
    private LCJlabel precentVol;
    private LCJlabel blockLabel;
    private LCJlabel blockVol;
    private JPanel operPanel;//lock,unlock ,view ,download button
    private boolean inLocal = false;
    private JPanel topPanel;
    private JPanel middlePanel;

    /**
     * construction
     */
    public MMRightPanel(JPanel parent) {
        super(parent);
        context = this;
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
        setBorder(new NbsBorder(NbsBorder.LEFT,ColorCnst.SHADOW));
        topPanel = new JPanel();
        middlePanel = new JPanel();

        nameArea = new LCAutoJTextPanel();
        nameArea.setForeground(ColorCnst.FONT_ABOUT_TITLE_BLUE);
        hashLabel = new LCJlabel("HASH:");
        hashArea = new LCAutoJTextPanel();
        localFileArea = new LCAutoJTextPanel();

        sizeLabel = new LCJlabel("Total Size：");
        blockLabel = new LCJlabel("Block");
        precentLabel = new LCJlabel("");

        sizeVol = new LCJlabel();
        blockVol = new LCJlabel();
        precentLabel = new LCJlabel();

        buildDataInFo();

    }

    private void buildDataInFo(){


    }



    /**
     * [initView description]
     *
     * @return {[type]} [description]
     */
    private void initView() {
        setLayout(new BorderLayout());

        middlePanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));

        topPanel.setLayout(new MigLayout("","[right]"));
        initViewTop();

        add(topPanel,BorderLayout.NORTH);
        add(middlePanel,BorderLayout.CENTER);
    }



    private void initViewTop(){
        JLabel generalLabel = new JLabel("General");
        JLabel detailLabel = new JLabel("Detail");
        //demoData();

        topPanel.add(nameArea,"growx, span ,gaptop 10,wrap");
        topPanel.add(generalLabel,"split, span, gaptop 10");
        topPanel.add(new JSeparator(),"growx, wrap, gaptop 10");
        topPanel.add(hashLabel,"gap 10");

        topPanel.add(hashArea,"span, growx ,wrap");
        topPanel.add(localFileArea,"growx,span,gaptop 4,wrap");

        /**
         * 明细开始
         */
        topPanel.add(detailLabel,"split, span, gaptop 10");
        topPanel.add(new JSeparator(),"growx, wrap,gaptop 10");


        topPanel.add(sizeLabel,"split,span ,gap top 10");
        topPanel.add(sizeVol,"span ,gap top 10");
        topPanel.add(blockLabel,"span ,gap top 10");
        topPanel.add(blockVol,"span ,gap top 10,wrap");



       for(int i = 0;i<30;i++){
            boolean pined = i%5==1 ? true : false;
            String hash="Hash_"+i;
            long ds = i*1000l;
            MMCubePanel cubePanel = new MMCubePanel(hash,ds,pined);
            middlePanel.add(cubePanel);
        }
    }

    private void demoData(){
        nameArea.setText("lanbery_skjhfskd.png");
        hashArea.setText("QmT7CXKZ8iy8nYEsUhteA1d9UKWT5CY796qqjvt5ExFFrF");
        sizeVol.setText("10.22KB");
        blockVol.setText("2");
        precentLabel.setText("20%");
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

    /**
     * 设置附件详细信息
     */
    public void setAttachmentDetailInfo(AttachmentDataDTO detailInfo){
        if(detailInfo==null)return;
        String fname = detailInfo.getFname()==null?detailInfo.getId():detailInfo.getFname();
        nameArea.setText(fname);
        hashArea.setText(detailInfo.getId());
        String sizeShow = DataSizeFormatUtil.formatDataSize(detailInfo.getFsize()==null?0:detailInfo.getFsize());
        sizeVol.setText(sizeShow);
        blockVol.setText("");
        if(StringUtils.isNotBlank(detailInfo.getCachedfile())){
            localFileArea.setText(detailInfo.getCachedfile());
        }
        context.updateUI();
    }


}