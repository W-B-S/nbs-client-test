package com.nbs.ui.panels;

import UI.AppMainWindow;
import UI.panel.im.IMPanel;
import com.alibaba.fastjson.JSON;
import com.nbs.biz.model.ContactsEntity;
import com.nbs.biz.service.ContactsService;
import io.nbs.client.vo.ContactsItem;
import com.nbs.entity.PeerBoradcastInfo;
import com.nbs.entity.PeerInfoBase;
import com.nbs.ipfs.IPFSHelper;
import com.nbs.ipfs.entity.IpfsMessage;
import io.nbs.commons.helper.ConfigurationHelper;
import io.nbs.commons.helper.DateHelper;
import io.nbs.client.adapter.ContactsItemAdapter;
import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.ui.components.GBC;
import io.nbs.client.ui.components.NbsListView;
import io.nbs.commons.utils.Base64CodecUtil;
import io.nbs.commons.utils.DbUtil;
import io.nbs.client.ui.panels.ParentAvailablePanel;
import io.nbs.commons.helper.RadomCharactersHelper;
import io.ipfs.api.IPFS;
import io.ipfs.api.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @Package : com.nbs.ui.panels
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/22-10:35
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ContactsPanel extends ParentAvailablePanel {
    private Logger logger = LoggerFactory.getLogger(ContactsPanel.class);

    /**
     *
     */
    private ContactsService service;
    /**
     *
     */
    private static ContactsPanel context;
    /**
     * peer 显示框
     */
    private NbsListView contactsListView;
    /**
     * 在线peer缓存
     */
    List<ContactsItem> peerItems = new ArrayList<>();
    /**
     * 当前聊天用户
     */
    private String currentName;

    //private IPFS ipfs = IPFSHelper.getInstance().getIpfs();

    /**
     *
     * @param parent
     */
    public ContactsPanel(JPanel parent) {
        super(parent);
        context = this;
        service = new ContactsService(DbUtil.getSqlSession());
        initComponent();
        initView();
        initData();
        contactsListView.setAdapter(new ContactsItemAdapter(peerItems));
        subWorld(200);
        //TODO 从服务器获取通讯录后，调用下面方法更新UI
        notifyDataSetChanged();
    }

    /**
     * 初始化组件
     */
    private void initComponent(){
        contactsListView = new NbsListView();
    }

    private void initView(){
        setLayout(new GridBagLayout());
        contactsListView.setContentPanelBackground(ColorCnst.DARKER);
        add(contactsListView,new GBC(0,0).setFill(GBC.BOTH).setWeight(1,1));
    }

    /**
     *
     */
    private void initData(){
        peerItems.clear();
        List<ContactsEntity> contacts = getContacts();
        for(ContactsEntity contactsUser : contacts){
            ContactsItem item = new ContactsItem(
                    contactsUser.getId(),
                    contactsUser.getNick(),
                    ContactsItem.TYPE.P.toString());
            peerItems.add(item);
        }
    }

    public void notifyDataSetChanged(){
        //initData();
        ((ContactsItemAdapter)contactsListView.getAdapter()).processData();
        contactsListView.notifyDataSetChanged(false);
        //更新头像
        getPeerAvatar();
    }

    /**
     *
     */
    private void getPeerAvatar(){
        IPFS ipfs = IPFSHelper.getInstance().getIpfs();
        if(ipfs==null){
            logger.warn("No IPFS Server Connection");
            return;
        }
    }


    /**
     * 初始化 TODO 数据库匹配补全信息
     * @return
     */
    private List<ContactsEntity> getContacts(){
        List<ContactsEntity> contacts = service.findAll();
        if(contacts==null)contacts = new ArrayList<>();
        RadomCharactersHelper charactersHelper = RadomCharactersHelper.getInstance();
        String hashPre = "hash.";
        if(AppMainWindow.currentPeerInfo()!=null){
            PeerInfoBase base = AppMainWindow.currentPeerInfo();
            ContactsEntity selfModel = new ContactsEntity(base.getPeerID(),base.getNick());
            selfModel.setAvatar(base.getAvatarHash());
            selfModel.setAvatarSuffix(base.getAvatarSuffix());
            contacts.add(selfModel);
        }

        IPFS ipfs = IPFSHelper.getInstance().getIpfs();

        try {
            Object obj = null;

            obj = ipfs.pubsub.peers();
            if(obj==null)return contacts;
            List<String> peers = (List<String>)JSONParser.getValue(obj,"Strings");
            if(peers!=null&&peers.size()>0){
                for(String peerId : peers){
                    StringBuilder nickSb = new StringBuilder();
                    nickSb.append(peerId.substring(0,5)).append("...")
                            .append(peerId.substring(peerId.length()-4));
                    ContactsEntity entity = new ContactsEntity(peerId,nickSb.toString());
                    contacts.add(entity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


/*        //虚拟6
        int count = 6;
        for(int i=0;i<count;i++){
            String id = charactersHelper.generated(hashPre,20);

            String name = charactersHelper.generated("",6);
            ContactsEntity model = new ContactsEntity(name,name);
            contacts.add(model);
        }*/
        return contacts;
    }



    /**
     * 发布消息
     * @param sleeps
     */
    private void subWorld(long sleeps){
        List<Map<String, Object>> resList = Collections.synchronizedList(new ArrayList<>());
        AtomicInteger size = new AtomicInteger(0);
        new Thread(()->{
            logger.warn(DateHelper.currentTime()+">>>>>>>启动订阅 NBSWorld..."+IPFSHelper.NBSWORLD_IMS_TOPIC);
            IPFS ipfs = new IPFS(ConfigurationHelper.getInstance().getIPFSAddress());

            while (true){
                try {
                    TimeUnit.MILLISECONDS.sleep(sleeps);
                    logger.info("Sleep>>>>>>>>>>>>>>>>>>>>>>>>"+sleeps);
                    ipfs.pubsub.sub(IPFSHelper.NBSWORLD_IMS_TOPIC,resList::add,t->t.printStackTrace());
                } catch (Exception e) {
                    logger.warn(e.getMessage());
                }
            }
        }).start();


        new Thread(()->{
            int i = 0;
            while(true){
                try {
                    TimeUnit.MILLISECONDS.sleep(2000);
                    logger.info("reader--->>>"+resList.size());
                    int diff = resList.size()-size.intValue();
                    int currSize = resList.size();
                    if( diff > 1){
                        for(int j =size.intValue() ;j<currSize;j++ ){
                            String json = JSONParser.toString(resList.get(j));
                            i++;
                            logger.info(i+">>收到世界消息："+json);
                            IpfsMessage imessage = JSON.parseObject(json,IpfsMessage.class);
                            if(imessage.getFrom().equals(AppMainWindow.currentPeerInfo().getFrom())){
                                logger.info("消息为自己所发消息："+json+">>>>>"+AppMainWindow.currentPeerInfo().getPeerID());
                                //不处理
                            }else {
                                imessage.setTime(DateHelper.currentTime());
                                //处理消息
                                proccessIpfsMessage(imessage);
                            }
                        }
                        size.set(currSize);
                    }else if(diff==1){
                        String json = JSONParser.toString(resList.get(currSize-1));
                        i++;
                        logger.info(i+">>收到世界消息："+json);
                        IpfsMessage imessage = JSON.parseObject(json,IpfsMessage.class);
                        if(imessage.getFrom().equals(AppMainWindow.currentPeerInfo().getFrom())){
                            logger.info("消息为自己所发消息："+json+">>>>>"+AppMainWindow.currentPeerInfo().getPeerID());
                            //不处理
                        }else {
                            imessage.setTime(DateHelper.currentTime());
                            //处理消息
                            proccessIpfsMessage(imessage);
                        }

                        size.set(currSize);
                    }else {

                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    /**
     * 处理消息
     * @param im
     */
    private void proccessIpfsMessage(IpfsMessage im){

  /*      if(im==null)return;
        Base64CodecUtil.CtrlTypes types =im.getTypes();
        if(types==null)types = Base64CodecUtil.CtrlTypes.unkonw;
        im = Base64CodecUtil.parseIpmsMessageCtrlType(im);
        switch (types){
            case online:

                *//**
                 * $ON.B64.J$eyJpZCI6IlFtU29BaURTR1g0dnFaZWcyS29ESHNKcEZTR1AyU21hRXQxM05CUFJGZ3BRZnkiLCJuaWNrIjoiTkJTQ2hhaW5fbGFuYmVyeSJ9$
                 * 解析更新列表
                 *//*
                PeerBoradcastInfo peerInfo = JSON.parseObject(im.getContents(),PeerBoradcastInfo.class);
                if(peerInfo.getId().equals(AppMainWindow.currentPeerInfo().getPeerID())){
                    logger.info("忽略自己上线");
                    return;
                }
                parseOnline(peerInfo,im.getFrom());
                break;
            case pctrl:
                break;
            case normal:
            case unkonw:
            default:
                im.setContents(Base64CodecUtil.decode(im.getContents()));
                IMPanel.appendMsgShow(peerItems,im);
                return;
        }*/
    }

    /**
     * 1.检查缓存存在？
     * 2.更新信息
     * 3.刷新UI
     * 4.显示
     * @param peerInfo
     */
    private void parseOnline(PeerBoradcastInfo peerInfo,String from){
        logger.info(peerInfo.getId()+"上线");
        boolean changed = checkContactsAndUpdate(peerInfo,from);
        //刷新UI

        //显示信息


    }


    public boolean checkContactsAndUpdate(PeerBoradcastInfo peerInfo,String from){
        if(peerInfo==null)return false;
        boolean changed = true;
        boolean exist = false;
        if(peerItems==null){
            peerItems = new ArrayList<>();
        }else {
            for(ContactsItem item : peerItems){
                if(item.getId().equals(peerInfo.getId())){//存在
                    item.setName(peerInfo.getNick());
                    item.setAvatar(peerInfo.getAvatarHash());
                    item.setAvatarSuffix(peerInfo.getAvatarSuffix());
                    exist = true;
                    break;
                }
            }
        }
        if(!exist){
            ContactsItem item = new ContactsItem(peerInfo.getId(),peerInfo.getNick());
            item.setFormid(from);
            item.setAvatar(peerInfo.getAvatarHash());
            item.setAvatarSuffix(peerInfo.getAvatarSuffix());
            peerItems.add(item);
        }
        return changed;
    }
}
