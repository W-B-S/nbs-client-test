package io.nbs.client.ui.panels.im.views;

import io.nbs.client.ui.holders.AvatarViewHolder;

import javax.swing.*;

/**
 * @Package : com.nbs.ui.holder
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/24-8:14
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ContactsAvatarViewHolder extends AvatarViewHolder {
    private String letter;
    public JLabel letterLabel;

    public ContactsAvatarViewHolder(String ch)
    {
        this.letter = ch;
    }


    public String getLetter()
    {
        return letter;
    }
}
