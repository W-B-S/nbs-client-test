package io.nbs.client.ui.panels.manage.body;

import com.alibaba.fastjson.JSON;
import com.nbs.ui.components.NbsBorder;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.beans.blk.BlockStat;
import io.ipfs.multihash.Multihash;
import io.nbs.client.Launcher;
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
import java.io.IOException;
import java.util.Map;

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

    private LCJlabel numLinksLable;
    private LCJlabel numLinksVol;

    private LCJlabel blockLabel;
    private LCJlabel blockVol;

    private LCJlabel linksSizeLabel;
    private LCJlabel linksSizeVol;

    private LCJlabel dataSizeLabel;
    private LCJlabel dataSizeVol;

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
        hashLabel = new LCJlabel("HASH:",14);
        hashArea = new LCAutoJTextPanel();
        localFileArea = new LCAutoJTextPanel();

        sizeLabel = new LCJlabel("Total Size：",11);
        numLinksLable = new LCJlabel("NumLinks:",11);

        blockLabel = new LCJlabel("BlockSize:",11);
        linksSizeLabel = new LCJlabel("LinksSize:",11);
        dataSizeLabel = new LCJlabel("DataSize:",11);


        sizeVol = new LCJlabel(10);
        numLinksVol = new LCJlabel(10);
        blockVol = new LCJlabel(10);
        linksSizeVol = new LCJlabel(10);
        dataSizeVol = new LCJlabel(10);

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
        topPanel.add(numLinksLable,"span ,gap top 10");
        topPanel.add(numLinksVol,"span ,gap top 10,wrap");


        topPanel.add(blockLabel,"split,span ,gap top 10");
        topPanel.add(blockVol,"span ,gap top 10");
        topPanel.add(linksSizeLabel,"span ,gap top 10");
        topPanel.add(linksSizeVol,"span ,gap top 10");
        topPanel.add(dataSizeLabel,"span ,gap top 10");
        topPanel.add(dataSizeVol,"span ,gap top 10,wrap");



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
        getBlockInfo(detailInfo);
        context.updateUI();
    }

    private void getBlockInfo(AttachmentDataDTO detailInfo){
        Multihash multihash = Multihash.fromBase58(detailInfo.getId());
        new Thread(()->{
            try {
                IPFS ipfs = Launcher.getContext().getIpfs();
                Map map = ipfs.object.stat(multihash);
                if(map!=null){
                    logger.info("stat:{}", JSON.toJSONString(map));
                    BlockStat stat = JSON.parseObject(JSON.toJSONString(map),BlockStat.class);
                    if(stat!=null){
                        numLinksVol.setText(""+stat.getNumLinks());
                        blockVol.setText(DataSizeFormatUtil.formatDataSize(stat.getBlockSize()==null?0:stat.getBlockSize()));
                        linksSizeVol.setText(DataSizeFormatUtil.formatDataSize(stat.getLinksSize()==null?0:stat.getLinksSize()));
                        dataSizeVol.setText(DataSizeFormatUtil.formatDataSize(stat.getDataSize()==null?0:stat.getDataSize()));
                    }
                }
                MerkleNode node = ipfs.object.links(multihash);
                logger.info("Node:{}", JSON.toJSONString(node));
                context.updateUI();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}