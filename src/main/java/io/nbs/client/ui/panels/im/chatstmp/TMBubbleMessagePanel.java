package io.nbs.client.ui.panels.im.chatstmp;

import com.android.ninepatch.NinePatch;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : io.nbs.client.ui.panels.im.chats
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/3-16:07
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class TMBubbleMessagePanel extends JTextArea {
    private static final long serialVersionUID = 8619514461892835444L;
    private NinePatch _backGroudnIcon;
    private int _preferredWidth;
    private int _widthGap;
    private Dimension _originalSize;
    private boolean _isLeftItem;

    private  static final int _insetLeft = 15, _insetRight = 8;

    public TMBubbleMessagePanel(int preferredWidth, boolean isLeft) {
        setOpaque(false);
        setLineWrap(true);
        setWrapStyleWord(true);

        this._preferredWidth = preferredWidth;

        this._widthGap = _insetLeft + _insetRight;
        _originalSize = this.getPreferredScrollableViewportSize();
        this.setSize(new Dimension(this._preferredWidth,this._originalSize.height));

        this._isLeftItem = isLeft;
    }

    /**
     *
     * @param icon
     */
    public void setBackGroundIcon(NinePatch icon) {
        this._backGroudnIcon = icon;
    }

    @Override
    protected void paintComponent(Graphics g) {
        String text = getText();
        int totalLen = g.getFontMetrics().stringWidth(text);
        int width = this._originalSize.width;
        int heigh = this._originalSize.height;
        int offset_x = 0;

        if((totalLen + this._widthGap) > this._preferredWidth){
            width = this._preferredWidth;
            heigh = (totalLen/(this._preferredWidth - this._widthGap)+1)*this._originalSize.height;
            this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        }else {
            if(this._isLeftItem){
                width = totalLen + this._widthGap;
                heigh = this._originalSize.height;
                offset_x = 0;
                this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            }else{
                width = totalLen + this._widthGap;
                heigh = this._originalSize.height;
                offset_x = this._preferredWidth -width;
                this.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            }
        }
        if(_backGroudnIcon != null){
            _backGroudnIcon.draw((Graphics2D)g,offset_x,0,width,heigh);
            //_backGroudnIcon
        }
        super.paintComponent(g);
    }

    @Override
    public Insets getInsets() {
        if(this._isLeftItem){
            return new Insets(10,_insetLeft,10,_insetRight);
        }else {
            return new Insets(10,_insetRight,10,_insetLeft);
        }
    }
}
