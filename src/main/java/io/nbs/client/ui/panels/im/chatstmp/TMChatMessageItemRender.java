package io.nbs.client.ui.panels.im.chatstmp;

import com.android.ninepatch.NinePatch;

import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.commons.helper.DateHelper;
import io.nbs.commons.utils.IconUtil;
import io.nbs.sdk.beans.IMMessageBean;
import io.nbs.sdk.beans.PeerInfo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
/**
 * @Package : io.nbs.client.ui.panels.im.chats
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/3-16:01
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class TMChatMessageItemRender extends JPanel implements TableCellRenderer {
    static int HEAD_ICON_WIDTH = 30, HEADER_ICON_HEIGHT = 30;
    static PeerInfo self;

    Holder holder = null;
    LeftViewHolder leftViewHolder = null;
    RightViewHolder rightViewHolder = null;
    ImageIcon _defIcon = null;

    public TMChatMessageItemRender(Dimension size) {
       setOpaque(true);
       self = MainFrame.getContext().getCurrentPeer();
       leftViewHolder = new LeftViewHolder(size);
       rightViewHolder = new RightViewHolder(size);
       ImageIcon icon = IconUtil.getIcon(this,"/icons/nbs.png");
       Image image = icon.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH);
       _defIcon = new ImageIcon(image);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Holder holder = null;

        IMMessageBean messageBean = (IMMessageBean) value;
        String msgSenderId = messageBean.getFrom();
        ImageIcon rightIcon = IconUtil.getIcon(this,"/icons/tools/lambor48.png");

        if(msgSenderId.equals(self.getId())){
            holder = rightViewHolder;
            holder._avatar.setIcon(_defIcon);
            holder._nick.setText(self.getNick());
        }else {
            holder = leftViewHolder;
            holder._nick.setText( messageBean.getFrom());
            holder._avatar.setIcon(_defIcon);
        }

        holder._messageText.setText(messageBean.getContent());
        String timeStr = DateHelper.currentTime();
        holder._time.setText(timeStr);

        int iHeight = holder._messageText.getPreferredSize().height + 10;
        iHeight = iHeight < 70 ? 70 : iHeight;
        int iH = table.getRowHeight(row);
        if (iH != iHeight) {
            table.setRowHeight(row, iHeight);
        }
        if (isSelected){
            holder.setBackground(Color.gray);
        }else{
            holder.setBackground(Color.white);
        }

        return holder;
    }

    private class Holder extends JPanel{
        JLabel              _nick               = new JLabel();
        JLabel              _avatar             = new JLabel();
        JLabel              _time               = new JLabel();
        JPanel              _contentTextArea    = new JPanel(new MigLayout("insets 0"));

        TMBubbleMessagePanel _messageText;
        NinePatch           _messageBack;
        Dimension           _cellSize;

        public Holder(Dimension size) {
            this._cellSize = size;
            setBackground(ColorCnst.WINDOW_BACKGROUND);
            this._contentTextArea.setBackground(ColorCnst.WINDOW_BACKGROUND);
        }
    }

    /**
     *
     */
    private class LeftViewHolder extends Holder{
        public LeftViewHolder(Dimension size) {
            super(size);
            _messageText = new TMBubbleMessagePanel((int)(MainFrame.RIGHT_EIDTH*0.65),true);

            //_messageBack = NinePatch.load(new URL("/icons/nbs128.png"),true);
            this.setLayout(new MigLayout("insets 0"));
            _nick.setHorizontalAlignment(JLabel.LEFT);
            _messageText.setBackGroundIcon(_messageBack);

            this.add(_contentTextArea,"dock east");
            this.add(_messageText,"dock east");
            this.add(_nick,"dock north, split 3");
            this.add(_avatar,"dock north");
            this.add(_time,"dock north");

            _contentTextArea.add(_messageText,"dock west, wrap");
        }
    }

    /**
     *
     */
    private class RightViewHolder extends Holder {
        public RightViewHolder(Dimension size) {
            super(size);
            _messageText = new TMBubbleMessagePanel((int)(this._cellSize.getWidth()*0.65),false);

            //TODO ninepatch
            this.setLayout(new MigLayout("ins 0, rtl","right"));
            _nick.setHorizontalAlignment(JLabel.RIGHT);
            _avatar.setHorizontalAlignment(JLabel.RIGHT);
           // _avatar.setIcon(_defIcon);
            _time.setHorizontalAlignment(JLabel.RIGHT);
            _messageText.setBackGroundIcon(_messageBack);

            this.add(_contentTextArea,"dock east, growy, pushy, wmin "+
                    this._cellSize.getWidth() * 0.65+"px");
            this.add(_nick, "dock north, split 3");
            this.add(_avatar, "dock north");
            this.add(_time, "dock north");

            _contentTextArea.add(_messageText, "dock east, wrap");
        }
    }

}
