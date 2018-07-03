package io.nbs.client.ui.panels.im.chatstmp;

import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.ui.components.ScrollUI;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.ui.panels.ParentAvailablePanel;
import io.nbs.sdk.beans.IMMessageBean;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package : io.nbs.client.ui.panels.im.chats
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/3-15:25
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class TMChatShowPanel extends ParentAvailablePanel {
    /**
     * 滚动窗口
     */
    private JScrollPane scrolView;
    private TMMessageModel imMessageModel;

    private JTable hisMessageTable;

    private Dimension currentSize;

    public TMChatShowPanel(JPanel parent) {
        super(parent);
        currentSize = new Dimension(MainFrame.RIGHT_EIDTH,300);
        logger.info(">>>>>>>>>>>>>>>>>>."+currentSize.width);
        initComponents();
        initView();
        loadData();
    }

    private void initComponents(){
        imMessageModel = new TMMessageModel();

        hisMessageTable = new JTable(imMessageModel);

        scrolView = new JScrollPane(hisMessageTable);

       // Dimension tableSize = new Dimension(currentSize.width,currentSize.height);
        //scrolView.setPreferredSize(currentSize);
        scrolView.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrolView.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrolView.getVerticalScrollBar().setUI(new ScrollUI());
        scrolView.getVerticalScrollBar().setUnitIncrement(25);

        TMChatMessageItemRender itemRender = new TMChatMessageItemRender(currentSize);

        hisMessageTable.setDefaultRenderer(IMMessageBean.class,itemRender);
        hisMessageTable.setBackground(ColorCnst.MAIN_COLOR);
        hisMessageTable.setOpaque(true);


        hisMessageTable.setBorder(null);
        scrolView.setBorder(null);
        setBorder(null);
/*        hisMessageTable.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
            }
        });*/

    }

    private void initView(){
        hisMessageTable.setTableHeader(null);
        add(scrolView);
    }

    private void loadData(){
        imMessageModel.setMessageList(initDemo(20));
    }

    /**
     *
     * @param size
     * @return
     */
    private List<IMMessageBean> initDemo(int size){
        List<IMMessageBean> list = new ArrayList<>();
        for(int i=0;i<size;i++){
            IMMessageBean bean;
            if(i%3==0){
                bean = new IMMessageBean("LWW"+1,"你好！+"+i);
                bean.setSeqno("lsdfsdf"+i);
                bean.setFrom("QmaF4D3YrRiAhvjsN1hMWBTxSJ7e4aA3d1rs6XwXi94udN");
            }else {
                bean = new IMMessageBean("CCC"+1,"你好！+"+i);
                bean.setSeqno("lsdfsdf"+i);
                bean.setFrom("NBSChain_"+i);
            }
            list.add(bean);
        }
        return list;
    }
}
