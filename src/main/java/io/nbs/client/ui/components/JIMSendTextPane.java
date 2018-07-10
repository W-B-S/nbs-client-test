package io.nbs.client.ui.components;

import javax.swing.*;
import javax.swing.text.*;

/**
 * @Package : com.nbs.ui.panels.message
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/26-20:33
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class JIMSendTextPane extends JTextPane {
    private Object tag;

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    /**
     * 用于实现自动强制折行
     */
    private class WarpEditorKit extends StyledEditorKit{
        private ViewFactory defaultFactory = new WarpColumnFactory();

        @Override
        public ViewFactory getViewFactory() {
            return defaultFactory;
        }
    }

    /**
     *
     */
    private class WarpColumnFactory implements ViewFactory{
        @Override
        public View create(Element elem) {
            String kind = elem.getName();
            if(kind != null){
                if(kind.equals(AbstractDocument.ContentElementName)){
                    return new WarpLabelView(elem);
                }else if(kind.equals(AbstractDocument.ParagraphElementName)){
                    return new ParagraphView(elem);
                }else if(kind.equals(AbstractDocument.SectionElementName)){
                    return new BoxView(elem,View.Y_AXIS);
                }else if(kind.equals(StyleConstants.ComponentElementName)){
                    return new ComponentView(elem);
                }else if(kind.equals(StyleConstants.IconElementName)){
                    return new WarpIconView(elem);
                }


            }
            //default view
            return new WarpLabelView(elem);
        }
    }

    private class WarpLabelView extends LabelView{
        public WarpLabelView(Element elem) {
            super(elem);
        }

        @Override
        public float getMinimumSpan(int axis) {
            switch (axis){
                case View.X_AXIS:
                    return 0;
                case View.Y_AXIS:
                    return super.getMinimumSpan(axis);
                default:
                    throw new IllegalArgumentException("Invalid axis : "+axis);
            }
        }
    }

    /**
     * 图片自动换行
     */
    private class WarpIconView extends IconView{
        public WarpIconView(Element elem) {
            super(elem);
        }

        @Override
        public float getMinimumSpan(int axis) {
            switch (axis){
                case View.X_AXIS:
                    return 0;
                case View.Y_AXIS:
                    return super.getMinimumSpan(axis);
                default:
                    throw new IllegalArgumentException("Invalid axis : "+axis);
            }
        }
    }

    public JIMSendTextPane() {
        super();
        this.setEditorKit(new WarpEditorKit());
    }
}
