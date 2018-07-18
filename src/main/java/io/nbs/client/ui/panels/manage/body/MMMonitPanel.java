package io.nbs.client.ui.panels.manage.body;

import com.alibaba.fastjson.JSON;
import io.ipfs.api.*;
import io.ipfs.api.beans.blk.BlockStat;
import io.ipfs.api.beans.bw.BitSwap;
import io.ipfs.api.bitswap.BitSwapService;
import io.ipfs.multihash.Multihash;
import io.nbs.client.Launcher;
import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.cnsts.FontUtil;
import io.nbs.client.ui.components.LCJlabel;
import io.nbs.client.ui.components.SizeAutoAdjustTextArea;
import io.nbs.client.ui.components.VerticalFlowLayout;
import io.nbs.client.ui.components.forms.LCFormLabel;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.ui.panels.ParentAvailablePanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Package : io.nbs.client.ui.panels.manage.body
 * @Description :
 * <p>
 *     任务监控
 * </p>
 * @Author : lambor.c
 * @Date : 2018/7/18-20:39
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class MMMonitPanel extends JPanel {
    private static final Logger logger = LoggerFactory.getLogger(MMMonitPanel.class);
    private static MMMonitPanel context;
    private JLabel statusLabel = new JLabel();
    private int seconds = 0;//用时
    private Color wantHashColor;


    //private SizeAutoAdjustTextArea textArea;
    private JPanel wantListPanel;

    public static boolean CtrlSign = false;
    private long fsize = 0l;
    private String rhash;
    private IPFS ipfs;
    private Map<String,Long> blkmap = new HashMap();
    private AtomicLong completeSize = new AtomicLong();

    private BitSwapService bitSwapService;
    private JPanel middlePanel ;
    private LCJlabel timelabel;
    /**
     *
     */
    private BlockingQueue<Multihash> pinedBooster = new ArrayBlockingQueue<>(20);
    /**
     * construction
     */
    public MMMonitPanel() {
        context = this;
        ipfs = Launcher.getContext().getIpfs();
        bitSwapService = BitSwapService.getInstance();
        wantHashColor = ColorCnst.MAIN_COLOR_DARKER;
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

       // int maxWidth = (int)(MainFrame.getContext().currentWindowWidth*0.375);
       // textArea = new SizeAutoAdjustTextArea(maxWidth);
        wantListPanel = new JPanel();
        middlePanel = new JPanel();
        timelabel = new LCJlabel();

    }

    /**
     * [initView description]
     *
     * @return {[type]} [description]
     */
    private void initView() {
        this.setLayout(new BorderLayout());

        middlePanel.setLayout(new FlowLayout(FlowLayout.RIGHT,5,0));
        statusLabel.setForeground(ColorCnst.FONT_ABOUT_TITLE_BLUE);
        statusLabel.setFont(FontUtil.getDefaultFont(16));
        statusLabel.setHorizontalAlignment(JLabel.LEFT);

        timelabel = new LCJlabel(wantHashColor);
        timelabel.setHorizontalAlignment(JLabel.LEFT);
        middlePanel.add(statusLabel);
        middlePanel.add(timelabel);


       // textArea.setEditable(false);
        wantListPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP,10,0,true,false));

        this.add(middlePanel,BorderLayout.NORTH);
        this.add(wantListPanel,BorderLayout.CENTER);


        this.setVisible(false);
    }

    private void setListeners() {
/*        MouseListener[] listeners = textArea.getMouseListeners();
        for(MouseListener mouseListener : listeners){
            textArea.removeMouseListener(mouseListener);
        }*/

    }

    /**
     * [getContext description]
     *
     * @return {[type]} [description]
     */
    public static MMMonitPanel getContext() {
        return context;
    }

    public static void setCtrlSign(boolean ctrlSign) {
        CtrlSign = ctrlSign;
    }

    /**
     * 启动浏览
     * @param rhash
     * @param stat
     */
    public void startMonitor(String rhash,BlockStat stat){
        this.rhash = rhash;
        this.fsize = stat.getCumulativeSize();
        Multihash multihash = Multihash.fromBase58(stat.getHash());


        new Thread(()->{
            int sec = 0;
            while (completeSize.get()<fsize){
                sec++;
                try {
                    TimeUnit.SECONDS.sleep(1);
                    timelabel.setText(sec+"s");
                    timelabel.updateUI();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        blkmap.clear();
        //start
        new Thread(()->{
            generateHash(multihash);
        }).start();

        //monitorList();
        boosterPined();
        this.setVisible(true);
    }


    private void generateHash(Multihash multihash){
        try {
           MerkleNode node = ipfs.object.links(multihash);
           if(node!=null){
               if(node.links==null||node.links.size()==0){
                   pinedBooster.put(node.hash);
               }else {
                   for(MerkleNode n : node.links){
                        generateHash(n.hash);
                   }
               }
           }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void boosterPined(){
        if(CtrlSign)return;
        new Thread(()->{
            while (completeSize.get()<fsize){
                CtrlSign = true;
                try {
                    Multihash multihash = pinedBooster.take();

                    Object o =ipfs.object.stat(multihash);
                    String json = JSONParser.toString(o);
                    BlockStat stat = JSON.parseObject(json,BlockStat.class);
                    blkmap.put(multihash.toBase58(),stat.getCumulativeSize());
                    long nl = completeSize.longValue()+stat.getCumulativeSize();
                    completeSize.addAndGet(nl);
                    ipfs.pin.add(multihash);
                    byte[] data =ipfs.object.data(multihash);
                    NamedStreamable.ByteArrayWrapper byteArrayWrapper = new NamedStreamable.ByteArrayWrapper(data);
                    ipfs.add(byteArrayWrapper);
                    Map blkMap = ipfs.block.stat(multihash);
                    logger.info("{}>>block :{}",multihash.toBase58(),JSON.toJSON(blkMap));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            CtrlSign = false;
        }).start();
    }

    /**
     *
     */
    public void monitorList(){

        new Thread(()->{
            while (!CtrlSign){
                ResData<BitSwap> resData = bitSwapService.getBitSwapStat();
                if(resData.getCode()==0){
                    statusLabel.setText("浏览器打开，正在加载数据...");
                    BitSwap bitSwap = resData.getData();
                    if(bitSwap!=null&&bitSwap.getWantlist().size()>0){
                        wantListPanel.removeAll();
                        for(String hash58 : bitSwap.getWantlist()){
                            wantListPanel.add(new LCJlabel(hash58,wantHashColor){
                                @Override
                                public void setToolTipText(String text) {
                                    super.setToolTipText(hash58);
                                }
                                @Override
                                public synchronized void addMouseListener(MouseListener l) {
                                    MouseAdapter adapter = new MouseAdapter() {
                                        @Override
                                        public void mouseEntered(MouseEvent e) {
                                            e.getComponent().setCursor(MainFrame.handCursor);
                                        }
                                    };
                                    super.addMouseListener(adapter);
                                }
                            });
                            wantListPanel.updateUI();
                        }

                        try {
                            TimeUnit.SECONDS.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

    }


}