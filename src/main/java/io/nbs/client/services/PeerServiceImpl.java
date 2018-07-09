package io.nbs.client.services;

import com.nbs.biz.data.entity.PeerContactsEntity;
import com.nbs.biz.service.PeerContactsService;
import io.nbs.client.cnsts.AppGlobalCnst;
import io.nbs.client.vo.ContactsItem;
import io.ipfs.api.IPFS;
import io.ipfs.api.JSONParser;
import io.nbs.client.Launcher;
import io.nbs.commons.helper.ConfigurationHelper;
import io.nbs.commons.helper.RadomCharactersHelper;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package : com.nbs.im.service
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/1-23:25
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class PeerServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(PeerServiceImpl.class);

    private IPFS ipfs;
    private PeerContactsService contactsService;
    private SqlSession sqlSession;

    public PeerServiceImpl(IPFS ipfs) {
        this.ipfs = ipfs;
        sqlSession = Launcher.getSqlSession();
        contactsService = new PeerContactsService(sqlSession);
    }

    /**
     * 开启客户端加载peers LIST
     * @param filterNoFrom 是否过滤信息不全的
     * @return
     */
    public List<ContactsItem> loadPeers(Boolean filterNoFrom){
        if(filterNoFrom==null)filterNoFrom = false;
        List<ContactsItem>  contactsItems = new ArrayList<>();
        /**
         * ipfs 获取
         */
        //TODO

        return mergeContacts();
        //return init4Test(25);
    }

    /**
     *
     * @return
     */
    private  List<ContactsItem> mergeContacts(){
        List<ContactsItem>  contactsItems = new ArrayList<>();
        List<String> onlinePeers = getOnlinePeers();
        List<PeerContactsEntity> contactsEntities = contactsService.findAll();

        if(onlinePeers==null||onlinePeers.size()==0)return contactsItems;

        if(contactsEntities!=null&&contactsEntities.size()>0){
            for(String peerId : onlinePeers){
                boolean notFind = true;
                for(PeerContactsEntity entity : contactsEntities){
                    if(entity.getId().equals(peerId)){
                        contactsItems.add(convert(entity));
                        notFind = false;
                    }
                }
                if(notFind){
                    contactsItems.add(constructionItemByPeerId(peerId));
                }
            }
        }else {
            for(String peerId : onlinePeers){
                contactsItems.add(constructionItemByPeerId(peerId));
            }
        }

        return contactsItems;
    }

    /**
     *
     * @param peerId
     * @return
     */
    private ContactsItem constructionItemByPeerId(String peerId){
        ContactsItem itemOnlyId = new ContactsItem();
        String nickTemp = peerId.substring(2,6)+AppGlobalCnst.NOTNBS_PEER_MIDDLE + peerId.substring(peerId.length()-4);
        itemOnlyId.setId(peerId);
        itemOnlyId.setOnline(1);
        itemOnlyId.setName(nickTemp);
        return itemOnlyId;
    }

    private ContactsItem convert(PeerContactsEntity entity){
        ContactsItem item = new ContactsItem();
        item.setOnline(1);
        item.setId(entity.getId());
        item.setName(entity.getNick());
        item.setAvatar(entity.getAvatar());
        item.setAvatarSuffix(entity.getAvatarSuffix());
        item.setFormid(entity.getFromid());
        item.setLocations(entity.getLocations());
        item.setIp(entity.getIp());
        return item;
    }


    /**
     *
     * @param
     * @return
     */
    public List<String> getOnlinePeers(){
        List<String> result = null;
        try {
            Object o = null;
            if(ConfigurationHelper.getInstance().subWorldPeers()){
                o = ipfs.pubsub.peers(IpfsMessageSender.NBSWORLD_IMS_TOPIC);
            }else {
                o = ipfs.pubsub.peers();
            }
            result = (List<String>)JSONParser.getValue(o,"Strings");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * TODO
     * @param needInserts
     */
    private void saveAndUpdatePeerContacts(  List<PeerContactsEntity> needInserts){

    }

    private List<ContactsItem> init4Test(int size){
        String nick = "";
        String hash = "QmaF4D3YrRiAhvjsN1hMWBTxSJ7e4aA3d1rs6XwXi94udN";

        List<ContactsItem>  contactsItems = new ArrayList<>();
        for(int i = 0 ;i<size;i++){
            ContactsItem item = new ContactsItem();
            item.setId(hash);
            item.setFormid("QmaF4D3YrRiAhvjsN1hMWBTxSJ7e4aA3d1rs6XwXi94udN");
            item.setName("Hlsa"+i);
            item.setAvatar("QmetTG1vuiEmuLdczNK7dgZAqvVgr4icZFJxSc62SBvJtn");
            item.setAvatarSuffix(".png");
            item.setOnline(1);
            contactsItems.add(item);
        }
        return contactsItems;
    }
}
