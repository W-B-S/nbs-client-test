package io.nbs.client.ui.panels.manage.body;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.beans.blk.BlockStat;
import io.ipfs.multihash.Multihash;
import io.nbs.client.Launcher;
import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.helper.BrowserOperationHelper;
import io.nbs.client.ui.components.LCJlabel;
import io.nbs.client.ui.components.NBSButton;
import io.nbs.client.ui.components.VerticalFlowLayout;
import io.nbs.client.ui.components.forms.LCFormLabel;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.ui.panels.ParentAvailablePanel;
import io.nbs.commons.helper.DateHelper;
import io.nbs.commons.utils.DataSizeFormatUtil;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @Package : io.nbs.client.ui.panels.manage.body
 * @Description :
 * <p>
 *     不在本地的hash
 * </p>
 * @Author : lambor.c
 * @Date : 2018/7/13-2:26
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class TipResultHashPanel extends ParentAvailablePanel {
    private static TipResultHashPanel context;
    private static String PRE_NUMLINKS ="NumLinks :";
    private static String PRE_BLKSIZE ="BlockSize :";
    private static String PRE_LSIZE ="LinksSize :";
    private static String PRE_DSIZE ="DataSize :";
    private static String PRE_SUMSIZE ="CumulativeSize :";

    private JPanel contentPanel = new JPanel();

    private LCJlabel hashLabel;
    private LCJlabel hashVol;
    private JLabel loading;

    private LCJlabel numLinks;
    private LCJlabel blockSize;
    private LCJlabel linksSize;
    private LCJlabel dataSize;
    private LCJlabel cumulativeSize;

    private LCJlabel numLinksVol;
    private LCJlabel blockSizeVol;
    private LCJlabel linksSizeVol;
    private LCJlabel dataSizeVol;
    private LCJlabel cumulativeSizeVol;

    private LCJlabel errorLabel;
    private LCFormLabel searchUsed;

    private JPanel operPanel;
    private String hash58;
    private NBSButton openBtn;
    private NBSButton addBtn;

    private BlockStat stat;

    private MMMonitPanel monitPanel;




    private IPFS ipfs;
    /**
     * construction
     */
    public TipResultHashPanel(JPanel parent) {
        super(parent);
        context = this;
        ipfs = Launcher.getContext().getIpfs();
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
        setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP,10,20,true,false));
        hashLabel = new LCJlabel(15);
        hashLabel.setHorizontalAlignment(JLabel.CENTER);

        errorLabel = new LCJlabel(15);
        errorLabel.setForeground(ColorCnst.RED);
        errorLabel.setHorizontalAlignment(JLabel.CENTER);
        errorLabel.setVisible(false);

        loading = new JLabel();
        loading.setHorizontalAlignment(JLabel.CENTER);
        loading.setVisible(false);

        numLinks = new LCJlabel(PRE_NUMLINKS,14);
        blockSize = new LCJlabel(PRE_BLKSIZE,14);
        linksSize = new LCJlabel(PRE_LSIZE,14);
        dataSize = new LCJlabel(PRE_DSIZE,14);
        cumulativeSize = new LCJlabel(PRE_SUMSIZE,14);

        numLinksVol = new LCJlabel(13);
        blockSizeVol = new LCJlabel(13);
        linksSizeVol = new LCJlabel(13);
        dataSizeVol = new LCJlabel(13);
        cumulativeSizeVol = new LCJlabel(13);

        /**
         *
         */
        searchUsed = new LCFormLabel("搜索到文件用时:");
        searchUsed.setHorizontalAlignment(JLabel.LEFT);
        operPanel = new JPanel();
        operPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,20,5));
        operPanel.setVisible(false);
        operPanel.add(searchUsed);
        openBtn = new NBSButton("浏览器打开",ColorCnst.CONTACTS_ITEM_GRAY,ColorCnst.WINDOW_BACKGROUND_LIGHT);
        openBtn.setForeground(ColorCnst.FONT_ABOUT_TITLE_BLUE);
        addBtn = new NBSButton("下载到本地",ColorCnst.CONTACTS_ITEM_GRAY,ColorCnst.WINDOW_BACKGROUND_LIGHT);
        addBtn.setVisible(false);//暂未开发完成
        addBtn.setForeground(ColorCnst.FONT_ABOUT_TITLE_BLUE);

        operPanel.add(openBtn);
        operPanel.add(addBtn);


        this.add(errorLabel);

        errorLabel.setText("lanbery");

        monitPanel = new MMMonitPanel();

        this.add(contentPanel);
        this.add(operPanel);
        this.add(monitPanel);
    }

    public void searchingFromIntelnet(){
        clearVol();
        errorLabel.setForeground(ColorCnst.FONT_ABOUT_TITLE_BLUE);
        errorLabel.setText("正在努力查找中...");
        errorLabel.setVisible(true);
        operPanel.setVisible(false);
    }

    /**
     * [initView description]
     *
     * @return {[type]} [description]
     */
    private void initView() {
        contentPanel.setLayout(new MigLayout("","[left]"));
        contentPanel.add(hashLabel,"span,growx ,gaptop 5,wrap");
        contentPanel.add(cumulativeSize,"split,span,growx ,gaptop 5");
        contentPanel.add(cumulativeSizeVol,"span 3,growx ,gaptop 5,wrap");
        contentPanel.add(blockSize,"split,span,growx ,gaptop 5,gapright 10");
        contentPanel.add(blockSizeVol,"span ,growx ,gaptop 5,gapright 10");
        contentPanel.add(linksSize,"span,growx ,gaptop 5,gapright 10");
        contentPanel.add(linksSizeVol,"span,growx ,gaptop 5,wrap");
        contentPanel.add(dataSize,"split,span ,growx ,gaptop 5");
        contentPanel.add(dataSizeVol,"span ,growx ,gaptop 5");
        contentPanel.add(numLinks,"span ,growx ,gaptop 5");
        contentPanel.add(numLinksVol,"span ,growx ,gaptop 5,wrap");
        contentPanel.add(errorLabel,"split ,span 1 ,wrap,grow");
    }

    private void setListeners() {
        openBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                monitPanel.monitorList();
                logger.info("客户端IP:{}打开浏览器时间:{},浏览HASH:{}",MainFrame.getContext().getCurrentPeer().getIp(),DateHelper.currentTime(),hash58);
                BrowserOperationHelper.getInstance().openURL(hash58);
            }
        });

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void setHash(String hash){
        this.hash58 = hash;
        hashLabel.setText(hash);
        this.updateUI();
    }



    public void setBlkStat(BlockStat stat,String errorMSG,long usedSecd){
        String timeUsed = DateHelper.calcUsedTime(usedSecd);
        searchUsed.setVolumeText(timeUsed);
        searchUsed.getTextArea().setBackground(ColorCnst.WINDOW_BACKGROUND);
        if(errorMSG==null&&stat!=null){
            this.stat = stat;
            errorLabel.setVisible(false);
            operPanel.setVisible(true);
            Long totalSize = stat.getCumulativeSize()==null?0:stat.getCumulativeSize();
            Long blkSize = stat.getBlockSize();
            cumulativeSizeVol.setText(DataSizeFormatUtil.formatDataSize(totalSize));
            blockSizeVol.setText(DataSizeFormatUtil.formatDataSize(blkSize));
            linksSizeVol.setText(DataSizeFormatUtil.formatDataSize(stat.getLinksSize()));
            dataSizeVol.setText(DataSizeFormatUtil.formatDataSize(stat.getDataSize()));
            String numlks = stat.getNumLinks()==null?"0" : ""+stat.getNumLinks();
            numLinksVol.setText(numlks);
            monitPanel.startMonitor(stat.getHash(),stat);
        }else {
            clearVol();
            errorLabel.setText(errorMSG);
            errorLabel.setForeground(ColorCnst.RED);
            errorLabel.setVisible(true);
            operPanel.setVisible(false);
        }

        this.updateUI();
    }

    /**
     *
     */
    public void clearVol(){
        cumulativeSizeVol.setText("");
        blockSizeVol.setText("");
        linksSizeVol.setText("");
        dataSizeVol.setText("");
        numLinksVol.setText("");
    }

    /**
     * [getContext description]
     *
     * @return {[type]} [description]
     */
    public static TipResultHashPanel getContext() {
        return context;
    }

    /**
     * 获取前一次
     * @return
     */
    public String prevousHash(){
        return this.hash58==null?"":this.hash58;
    }

    private void pinedHash(){
        Multihash multihash = Multihash.fromBase58(hash58);
        /**
         *
         */
        new Thread(()->{
            try {
                ipfs.pin.add(multihash);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        java.util.List<MerkleNode> allNodes = new ArrayList<>();
    }


    public MerkleNode getLinks() {
        return null;
    }

    public MMMonitPanel getMonitPanel() {
        return monitPanel;
    }

    public void hideMMMonitor(){
        monitPanel.setVisible(false);
    }
}