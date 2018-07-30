package UI.templete;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @Package : UI.templete
 * @Description :
 * <p>
 *     头像图标 100*100
 * </p>
 * @Author : lambor.c
 * @Date : 2018/6/19-15:47
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class AvatarComponent extends JComponent {
    private static final long serialVersionUID = 4590780102881395602L;

    private BufferedImage bufferedImage = null;
    private Graphics graphics = null;

    public AvatarComponent(BufferedImage bufferedImage, Graphics graphics) {
        this.bufferedImage = bufferedImage;
        this.graphics = graphics;
    }


    public AvatarComponent(){
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    /**
     *
     * @param avatarLocation
     * @throws IOException
     */
    public void loadImage(File avatarLocation) throws IOException {
        this.bufferedImage = ImageIO.read(avatarLocation);
        this.setBufferedImage(this.bufferedImage);
    }

    /**
     *
     * @param avatarLocation
     * @throws IOException
     */
    public void loadImage(URL avatarLocation) throws IOException {
        this.bufferedImage = ImageIO.read(avatarLocation);
        this.setBufferedImage(this.bufferedImage);
    }

    /**
     *
     * @param bufferedImage
     */
    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        if(this.bufferedImage == null){
            this.graphics=null;
            this.setBounds(0,0,0,0);
        }else {
            this.graphics = this.bufferedImage.createGraphics();
            this.setBounds(0,0,this.bufferedImage.getWidth(),this.bufferedImage.getHeight());
        }
    }

    @Override
    public Graphics getGraphics() {
        return graphics;
    }

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }
}
