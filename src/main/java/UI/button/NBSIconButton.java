package UI.button;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : UI.button
 * @Description : <p>
 *     自定义按钮
 * </p>
 * @Author : lambor.c
 * @Date : 2018/6/14-17:38
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class NBSIconButton extends JButton {
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private ImageIcon iconEnabled;
    private ImageIcon iconDisabled;
    /**
     *
     */
    private String tip;

    /**
     * 构造方法
     * @param normalIcon 正常
     * @param enabledIcon 可用
     * @param disabledIcon 不可用
     * @param tip
     */
    public NBSIconButton(ImageIcon normalIcon,ImageIcon enabledIcon,ImageIcon disabledIcon,String tip){
        super(normalIcon);
        this.iconEnabled = enabledIcon;
        this.iconDisabled = disabledIcon;
        this.tip = tip;

        initialize();
        setUp();
    }

    /**
     * 构造方法
     * @param normalIcon
     * @param enabledIcon
     * @param tip
     */
    public NBSIconButton (ImageIcon normalIcon,ImageIcon enabledIcon,String tip){
        super(normalIcon);
        this.iconEnabled = enabledIcon;
        this.iconDisabled = normalIcon;
        this.tip = tip;

        initialize();
        setUp();
    }

    /**
     * @Date    : 2018/6/15 10:24
     * @Author  : lanbery
     * @Decription :
     * <p>初始化设置：边距，边框 ed.</p>
     * @Param   :
     * @return
     * @throws
     */
    private void initialize(){
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
        this.setMargin(new Insets(0,0,0,0));
    }

    /**
     * 设置按钮样式：
     */
    private void setUp(){
        this.setRolloverIcon(iconEnabled);
        this.setPressedIcon(iconEnabled);
        this.setDisabledIcon(iconDisabled);

        if(!tip.equals("")){
            this.setToolTipText(tip);
        }
    }
}
