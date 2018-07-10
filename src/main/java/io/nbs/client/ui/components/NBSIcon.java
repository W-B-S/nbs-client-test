package io.nbs.client.ui.components;

import javax.swing.*;
import java.net.URL;

/**
 * @Package : com.nbs.ui.icons
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/27-1:53
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class NBSIcon extends ImageIcon {
    private ImageIcon normal;
    private ImageIcon active;

    public NBSIcon(URL location) {
        super(location);
        this.normal = new ImageIcon(location);
        this.active = new ImageIcon(location);
    }

    public NBSIcon(URL normal,URL avtive) {
        super(normal);
        this.normal = new ImageIcon(normal);
        this.active = new ImageIcon(avtive);
    }

    public NBSIcon(URL normal,URL avtive,String tip) {
        super(normal,tip);
        this.normal = new ImageIcon(normal);
        this.active = new ImageIcon(avtive);
    }

    public ImageIcon getNormal() {
        return normal;
    }

    public void setNormal(ImageIcon normal) {
        this.normal = normal;
    }

    public ImageIcon getActive() {
        return active;
    }

    public void setActive(ImageIcon active) {
        this.active = active;
    }

    public NBSIcon(String filename, String description, ImageIcon active) {
        super(filename, description);
        this.normal = new ImageIcon(filename);
        this.active = active;
    }

    public void hover(){
        this.setImage(this.active.getImage());
    }

    public void normal(){
        this.setImage(this.normal.getImage());
    }
}
