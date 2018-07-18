package io.nbs.client.ui.components.common;

import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.cnsts.FontUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Package : io.nbs.client.ui.components.common
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/17-10:45
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class JImageBGPanel extends JPanel {
    private static final long serialVersionUID = 5456195237081251666L;
    /**
     * 居中
     */
    public static final int CENTER = 0;
    /**
     * 平铺
     */
    public static final int TILED = 1;
    /**
     * 拉伸
     */
    public static final int SCALED = 2;

    private int backgroundMode = 0;

    private Image backgroundImage;

    private JLabel text;

    public JImageBGPanel() {
        this(null,0);
    }

    /**
     *
     * @param backgroundImage
     * @param backgroundMode
     */
    public JImageBGPanel(Image backgroundImage,int backgroundMode) {
        super();
        this.backgroundMode = backgroundMode;
        this.backgroundImage = backgroundImage;
    }

    public JImageBGPanel(Image backgroundImage) {
        this(backgroundImage,0);
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
        this.repaint();
    }

    private void initComponets(){
        text = new JLabel("5");
        text.setOpaque(true);
        text.setForeground(ColorCnst.FONT_WHITE);
        text.setFont(FontUtil.getDefaultFont(10));
        text.setHorizontalAlignment(JLabel.CENTER);
    }

    public void setText(String textTip) {
        if(this.text!=null){
            this.text.setText(textTip);
            this.updateUI();
        }
    }

    public int getBackgroundMode() {
        return backgroundMode;
    }

    public void setBackgroundMode(int backgroundMode) {
        switch (backgroundMode){
            case 0:
            case 1:
            case 2:
                this.backgroundMode = backgroundMode;
                this.repaint();
                break;
            default:
                break;
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(backgroundImage!=null){
            int compWidth = this.getWidth();
            int compHeight = this.getHeight();
            int imgWidth = backgroundImage.getWidth(this);
            int imgHeight = backgroundImage.getHeight(this);

            switch (this.backgroundMode){
                case 0 :
                {
                    //float oScale = imgWidth*1.0F/imgWidth;
                   // int nImageW = imgWidth,nImageH = imgHeight;
                    BufferedImage bufImage = new BufferedImage(compWidth,compHeight,BufferedImage.TYPE_INT_RGB);
                    Graphics2D g2d = bufImage.createGraphics();
                    int x = (compWidth-imgWidth)/2;
                    int y = (compHeight-imgHeight)/2;

                    g2d.drawImage(backgroundImage,x,y,null);

                    g2d.dispose();

                    g.drawImage(bufImage,0,0,null);
                    break;
                }
                case 1:
                {
                    for(int ix=0;ix<compWidth;ix += imgWidth){
                        for(int iy=0;iy<compHeight;iy += imgHeight){
                            g.drawImage(backgroundImage,ix,iy,this);
                        }
                    }
                    break;
                }
                case 2:
                {
                    g.drawImage(backgroundImage,0,0,compWidth,compHeight,this);
                    break;
                }
                default:
                    break;
            }
        }
    }
}
