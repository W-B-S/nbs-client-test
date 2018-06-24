package com.nbs.ui.panels;

import com.nbs.biz.model.ContactsModel;
import com.nbs.entity.ContactsItem;
import com.nbs.ipfs.IPFSHelper;
import com.nbs.ui.adapter.ContactsItemAdapter;
import com.nbs.ui.components.ColorCnst;
import com.nbs.ui.components.GBC;
import com.nbs.ui.components.NbsListView;
import com.nbs.utils.RadomCharactersHelper;
import io.ipfs.api.IPFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

    /**
     *
     * @param parent
     */
    public ContactsPanel(JPanel parent) {
        super(parent);
        context = this;
        initComponent();
        initView();
        initData();
        contactsListView.setAdapter(new ContactsItemAdapter(peerItems));
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
        contactsListView.setContentPanelBackground(ColorCnst.MAIN_COLOR);
        add(contactsListView,new GBC(0,0).setFill(GBC.BOTH).setWeight(1,1));
    }

    /**
     *
     */
    private void initData(){
        peerItems.clear();
        List<ContactsModel> contacts = getContacts();
        for(ContactsModel contactsUser : contacts){
            ContactsItem item = new ContactsItem(
                    contactsUser.getId(),
                    contactsUser.getNick(),
                    ContactsItem.TYPE.P.toString());
            peerItems.add(item);
        }
    }

    public void notifyDataSetChanged(){
        initData();
        ((ContactsItemAdapter)contactsListView.getAdapter()).processData();
        contactsListView.notifyDataSetChanged(false);
        //更新头像
        getPeerAvatar();
    }

    private void getPeerAvatar(){
        IPFS ipfs = IPFSHelper.getInstance().getIpfs();
        if(ipfs==null){
            logger.warn("No IPFS Server Connection");
            return;
        }
    }


    /**
     * 初始化Demo
     * @return
     */
    private List<ContactsModel> getContacts(){
        List<ContactsModel> contacts = new ArrayList<>();
        RadomCharactersHelper charactersHelper = RadomCharactersHelper.getInstance();
        String hashPre = "hash.";
        int count = 20;

        for(int i=0;i<count;i++){
            String id = charactersHelper.generated(hashPre,20);

            String name = charactersHelper.generated("NBS_",6);
            ContactsModel model = new ContactsModel(id,name);

            contacts.add(model);
        }
        return contacts;
    }

}
