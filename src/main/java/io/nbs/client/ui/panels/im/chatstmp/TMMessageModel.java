package io.nbs.client.ui.panels.im.chatstmp;

import io.nbs.sdk.beans.IMMessageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package : io.nbs.client.ui.panels.im.chats
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/3-15:28
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class TMMessageModel extends AbstractTableModel {
    static Logger logger = LoggerFactory.getLogger(TMMessageModel.class);

    List<IMMessageBean> messageList = new ArrayList<>();

    @Override
    public int getRowCount() {
        return messageList.size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return this.messageList.get(rowIndex);
    }

    @Override
    public Class<?> getColumnClass(int c) {
        return IMMessageBean.class;
    }

    public void setMessageList(List<IMMessageBean> messageList) {
        this.messageList = messageList;
    }

    public void addMessage(IMMessageBean bean){
        this.messageList.add(bean);
    }
}
