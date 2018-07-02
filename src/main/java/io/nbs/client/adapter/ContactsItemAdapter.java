package io.nbs.client.adapter;

import io.nbs.client.vo.ContactsItem;
import io.nbs.client.ui.holders.AvatarViewHolder;
import io.nbs.client.ui.holders.ContactsItemViewHolder;
import io.nbs.client.cnsts.ColorCnst;
import com.nbs.ui.components.NbsBorder;
import io.nbs.client.ui.holders.ContactsAvatarViewHolder;
import io.nbs.client.listener.AbstractMouseListener;
import io.nbs.commons.utils.AvatarUtil;
import io.nbs.commons.helper.CharacterParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

/**
 * @Package : com.nbs.ui.adapter
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/24-7:46
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ContactsItemAdapter extends BaseAdapter<ContactsItemViewHolder> {
    private List<ContactsItem> contactsItems;
    private List<ContactsItemViewHolder> viewHolders = new ArrayList<>();

    /**
     * 首拼音
     */
    Map<Integer, String> positionMap = new HashMap<>();
    /**
     * 选中的
     */
    private ContactsItemViewHolder selectedViewHolder;

    /**
     *
     * @param contactsItems
     */
    public ContactsItemAdapter(List<ContactsItem> contactsItems) {
        this.contactsItems = contactsItems;
        if(contactsItems != null){
            processData();
        }
    }


    @Override
    public int getCount() {
        return contactsItems.size();
    }

    @Override
    public ContactsItemViewHolder onCreateViewHolder(int viewType) {
        return new ContactsItemViewHolder();
    }

    @Override
    public AvatarViewHolder onCreateHeaderViewHolder(int viewType, int position) {
        for (int pos : positionMap.keySet())
        {
            if (pos == position)
            {
                String ch = positionMap.get(pos);
                return new ContactsAvatarViewHolder(ch.toUpperCase());
            }
        }

        return null;
    }

    /**
     * 字母
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindHeaderViewHolder(AvatarViewHolder viewHolder, int position) {
        ContactsAvatarViewHolder holder = (ContactsAvatarViewHolder) viewHolder;
        holder.setPreferredSize(new Dimension(100, 25));
        holder.setBackground(ColorCnst.ITEM_SELECTED_LIGHT);
        holder.setBorder(new NbsBorder(NbsBorder.BOTTOM));
        holder.setOpaque(true);

        holder.letterLabel = new JLabel();
        holder.letterLabel.setText(holder.getLetter());
        holder.letterLabel.setForeground(ColorCnst.FONT_GRAY);

        holder.setLayout(new BorderLayout());
        holder.add(holder.letterLabel, BorderLayout.WEST);
    }

    /**
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(ContactsItemViewHolder viewHolder, int position) {
        viewHolders.add(position, viewHolder);
        ContactsItem item = contactsItems.get(position);

        ImageIcon icon = new ImageIcon();
        icon.setImage(AvatarUtil.createOrLoadUserAvatar(item.getName())
                .getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        viewHolder.avatar.setIcon(icon);

        viewHolder.roomName.setText(item.getName());

        viewHolder.addMouseListener(new AbstractMouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                //JOptionPane.showMessageDialog(AppMainWindow.frame,item.getName());
                System.out.println(this.getClass().getName()+">>>>>>>>>>>>"+item.getName());

   //             IMPanel.getContext().contactsItemChanged(item);

                //TODO  右侧聊天面板加载切换
 /*               RightPanel.getContext().getUserInfoPanel().setUsername(item.getName());
                RightPanel.getContext().showPanel(RightPanel.USER_INFO);

                setBackground(viewHolder, Colors.ITEM_SELECTED);
                selectedViewHolder = viewHolder;

                for (ContactsItemViewHolder holder : viewHolders)
                {
                    if (holder != viewHolder)
                    {
                        setBackground(holder, Colors.DARK);
                    }
                }*/
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                if (selectedViewHolder != viewHolder)
                {
                    setBackground(viewHolder, ColorCnst.ITEM_SELECTED_LIGHT);
                }
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                if (selectedViewHolder != viewHolder)
                {
                    setBackground(viewHolder, ColorCnst.CONTACTS_ITEM_GRAY_MAIN);
                }
            }
        });
    }
    private void setBackground(ContactsItemViewHolder holder, Color color)
    {
        holder.setBackground(color);
    }

    public void processData()
    {
        Collections.sort(contactsItems);

        int index = 0;
        String lastChara = "";
        for (ContactsItem item : contactsItems)
        {
            String ch = CharacterParser.getSelling(item.getName()).substring(0, 1).toUpperCase();
            if (!ch.equals(lastChara))
            {
                lastChara = ch;
                positionMap.put(index, ch);
            }

            index++;
        }
    }
}
