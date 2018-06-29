package UI.button;

import UI.ConstantsUI;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : UI.button
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/19-16:14
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class AvatarIconButton extends JButton {
    private String nickname;

    public AvatarIconButton(String nickname) {
        super(ConstantsUI.NBS_ICON);
        this.nickname = nickname;
        initialize();
        setTip();

    }

    /**
     *
     */
    public AvatarIconButton() {
        super(ConstantsUI.NBS_ICON);
        initialize();
        setTip();
    }

    /**
     *
     * @param icon
     */
    public AvatarIconButton(Icon icon) {
        super(icon);
        if(icon==null)this.setIcon(ConstantsUI.NBS_ICON);
        initialize();
        setTip();
    }

    /**
     *
     * @param icon
     * @param nickname
     */
    public AvatarIconButton(Icon icon, String nickname) {
        super(icon);
        if(icon==null)this.setIcon(ConstantsUI.NBS_ICON);
        this.nickname = nickname;
        initialize();
        setTip();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     *
     */
    private void initialize(){
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
        this.setMargin(new Insets(0,0,0,0));
    }

    private void setTip(){
        if(StringUtils.isNoneBlank(nickname))this.setToolTipText(nickname);
    }
}
