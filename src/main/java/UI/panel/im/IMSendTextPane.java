package UI.panel.im;

import javax.swing.*;
import javax.swing.text.*;

/**
 * @Package : UI.panel.im
 * @Description :
 * <p>
 *     发送
 * </p>
 * @Author : lambor.c
 * @Date : 2018/6/16-22:01
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IMSendTextPane extends JTextPane {

    /**
     * @Date    : 2018/6/16 22:03
     * @Author  : lanbery
     * @Decription :
     * <p>内部类：自动换行</p>
     * @Param   :
     * @return
     * @throws
     */
    private class  WarpEditorKit extends StyledEditorKit{
        private ViewFactory defaultFactory = new WarpColumnFactory();

        @Override
        public ViewFactory getViewFactory() {
            return defaultFactory;
        }
    }

    /**
     *
     */
    public IMSendTextPane() {
        super();
        this.setEditorKit(new WarpEditorKit());
    }

    /**
     * ne
     */
    private class WarpColumnFactory implements ViewFactory{
        public View create(Element element){
            String kind = element.getName();

            if(kind != null){
                if(kind.equals(AbstractDocument.ContentElementName)){
                    return new WarpLabelView(element);
                }else if(kind.equals(AbstractDocument.ParagraphElementName)){
                    return new ParagraphView(element);
                }else if(kind.equals(AbstractDocument.SectionElementName)){
                    return new BoxView(element,View.X_AXIS);
                }else if(kind.equals(StyleConstants.ComponentElementName)){
                    return new ComponentView(element);
                }else if(kind.equals(StyleConstants.IconElementName)){
                    return new IconView(element);
                }
            }
            return new LabelView(element);
        }
    }

    /**
     *
     */
    private class WarpLabelView extends LabelView{
        public WarpLabelView(Element elem) {
            super(elem);
        }

        /**
         *
         * @param axis
         * @return
         */
        @Override
        public float getMinimumSpan(int axis) {
            switch (axis){
                case View.X_AXIS:
                    return 0;
                case View.Y_AXIS:
                    return super.getMinimumSpan(axis);
                default:
                    throw new IllegalArgumentException("Invalid axis :"+axis);
            }
        }
    }
}
