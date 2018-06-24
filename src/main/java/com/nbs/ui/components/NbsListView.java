package com.nbs.ui.components;

import com.nbs.ui.AvatarViewHolder;
import com.nbs.ui.ViewHolder;
import com.nbs.ui.adapter.BaseAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package : com.nbs.ui.components
 * @Description :
 * <p>RCListView</p>
 * @Author : lambor.c
 * @Date : 2018/6/22-10:41
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class NbsListView extends JScrollPane {

    /**
     *
     */
    private BaseAdapter adapter;
    private JPanel contentPanel;

    private int vGap;
    private int hGap;


    private java.util.List<Rectangle> rectangleList = new ArrayList<>();
    boolean scrollToBottom = false;
    //自适应
    private AdjustmentListener adjustmentListener;

    private MouseAdapter mouseAdapter;
    /**
     * 滚动条
     */
    private ScrollUI scrollUI;


    /**
     * 顶部监听事件
     */
    private ScrollToTopListener scrollToTopListener;
    private boolean scrollBarPressed = false;
    private int lastScrollValue = -1;

    //鼠标滚动监听
    private static int lastItemCount = 0;
    private MouseAdapter scrollMouseListener;
    private boolean scrollAttachMouseListener = false;
    private boolean messageLoading = false;
    private long lastWeelTime = 0;

    public NbsListView() {
        this(0,0);
    }

    public NbsListView(int vGap, int hGap) {
        this.vGap = vGap;
        this.hGap = hGap;
        //
        initComponent();
        setListeners();
    }

    /**
     * 鼠标离开
     * @param jComponent
     */
    public void setScrollHiddenOnMouseLeave(JComponent jComponent){
        if(scrollMouseListener==null){
            scrollMouseListener = new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setScrollBarColor(ColorCnst.SCROLL_BAR_THUMB,ColorCnst.WINDOW_BACKGROUND);
                    getVerticalScrollBar().repaint();
                    super.mouseEntered(e);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setScrollBarColor(ColorCnst.WINDOW_BACKGROUND,ColorCnst.WINDOW_BACKGROUND);
                    getVerticalScrollBar().repaint();
                    super.mouseExited(e);
                }
            };
        }
        if(!scrollAttachMouseListener){
            getVerticalScrollBar().addMouseListener(scrollMouseListener);
            scrollAttachMouseListener = true;
        }

        jComponent.addMouseListener(scrollMouseListener);
    }

    /**
     * 设置滚动条的颜色 TODO
     * @param thumbColor
     * @param trackColor
     */
    public void setScrollBarColor(Color thumbColor,Color trackColor){
        if(scrollUI == null){
            scrollUI = new ScrollUI(thumbColor,trackColor);
            this.getVerticalScrollBar().setUI(scrollUI);
        }else {
            scrollUI.setThumbColor(thumbColor);
            scrollUI.setTrackColor(trackColor);
        }
    }

    /**
     * 初始化控件
     */
    private void initComponent(){
        contentPanel = new JPanel();
        contentPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP,hGap,vGap,true,true));
        contentPanel.setBackground(ColorCnst.WINDOW_BACKGROUND);

        this.setViewportView(contentPanel);
        this.setBorder(null);
        this.getVerticalScrollBar().setUnitIncrement(25);
        this.getVerticalScrollBar().setUI(new ScrollUI());
    }

    /**
     * 设置监听
     */
    private void setListeners(){
        adjustmentListener = new AdjustmentListener() {
            /**
             *
             * @param e
             */
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if(e.getValue() == 0 && e.getValue() != lastScrollValue
                        && scrollToTopListener != null && !scrollBarPressed
                        && !scrollToBottom){
                    messageLoading = true;
                    scrollToTopListener.onScrollToTop();
                }

                if(e.getAdjustmentType() == AdjustmentEvent.TRACK && scrollToBottom){
                    getVerticalScrollBar().setValue(
                            getVerticalScrollBar().getModel().getMaximum() - getVerticalScrollBar().getModel().getExtent());

                }

                lastScrollValue = e.getValue();
            }
        };

        //鼠标适配
        mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                scrollToBottom = false;
                scrollBarPressed = true;
                super.mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                scrollBarPressed =false;
                super.mouseReleased(e);
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if(System.currentTimeMillis()-lastWeelTime <1000){
                    lastWeelTime = System.currentTimeMillis();
                    return;
                }

                if(getVerticalScrollBar().getValue() == 0){
                    if(messageLoading){
                        messageLoading=false;
                    }else {
                        //鼠标滚轮到顶，自动加载
                        if(scrollToTopListener!=null){
                            scrollToTopListener.onScrollToTop();
                        }
                    }
                }

                scrollToBottom = false;

                lastWeelTime = System.currentTimeMillis();

                super.mouseWheelMoved(e);
            }
        };

        getVerticalScrollBar().addAdjustmentListener(adjustmentListener);
        getVerticalScrollBar().addMouseListener(mouseAdapter);
        addMouseListener(mouseAdapter);
        addMouseListener(mouseAdapter);

    }

    public void setAutoScrollToBottom(){
        scrollToBottom = true;
    }

    public void setAutoScroll2Top(){
        scrollToBottom = false;
        getVerticalScrollBar().setValue(1);
    }

    /**
     * 填充组件
     */
    public void fillComponents(){
        if(adapter == null){
            return;
        }
        lastItemCount = adapter.getCount();
        for(int i = 0 ;i<adapter.getCount();i++){
            int viewType = adapter.getItemViewType(i);
            AvatarViewHolder avatarViewHolder = adapter.onCreateHeaderViewHolder(viewType,i);
            if(avatarViewHolder != null){
                adapter.onBindHeaderViewHolder(avatarViewHolder,i);
                contentPanel.add(avatarViewHolder);
                rectangleList.add(avatarViewHolder.getBounds());
            }

            ViewHolder holder = adapter.onCreateViewHolder(viewType);
            adapter.onBindViewHolder(holder,i);
            contentPanel.add(holder);
        }
    }

    public BaseAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
        fillComponents();
    }

    public  void setContentPanelBackground(Color color){
        contentPanel.setOpaque(true);
        contentPanel.setBackground(color);
    }

    public void scrollToPosition(int position){

    }

    /**
     * 获取滚动条在底部时显示的条目数
     * @return
     */
    private  int getLastVisibleItemCount(){
        int height = getHeight();

        int elemHeight = 0;
        int count = 0;
        for (int i = contentPanel.getComponentCount() - 1; i >= 0; i--)
        {
            count++;
            int h = contentPanel.getComponent(i).getHeight() + 20;
            elemHeight += h;

            if (elemHeight >= height)
            {
                break;
            }
        }

        return count;
    }

    /**
     * 重绘整个listView
     */
    public void notifyDataSetChanged(boolean keepSize)
    {
        if (keepSize)
        {
            if (lastItemCount == adapter.getCount())
            {
                System.out.println("数量相同");
                // 保持原来内容面板的宽高，避免滚动条长度改变或可见状态改变时闪屏
                contentPanel.setPreferredSize(new Dimension(contentPanel.getWidth(), contentPanel.getHeight()));
            }
        }

        contentPanel.removeAll();
        contentPanel.repaint();
        fillComponents();
        contentPanel.revalidate();
    }

    /**
     * 重绘指定区间内的元素
     *
     * @param startPosition
     * @param count
     */
    public void notifyItemRangeInserted(int startPosition, int count)
    {
        /*for (int i = startPosition; i < count; i++)
        {
            int viewType = adapter.getItemViewType(i);
            ViewHolder holder = adapter.onCreateViewHolder(viewType);
            adapter.onBindViewHolder(holder, i);
            contentPanel.add(holder, startPosition);
        }*/

        for (int i = count - 1; i >= startPosition; i--)
        {
            int viewType = adapter.getItemViewType(i);
            ViewHolder holder = adapter.onCreateViewHolder(viewType);
            adapter.onBindViewHolder(holder, i);
            contentPanel.add(holder, startPosition);
        }
    }

    /**
     * 重绘指定位置的元素
     *
     * @param position
     */
    public void notifyItemChanged(int position)
    {
        //contentPanel.remove(position);
        //int viewType = adapter.getItemViewType(position);
        //ViewHolder holder = adapter.onCreateViewHolder(viewType);
        ViewHolder holder = (ViewHolder) getItem(position);
        adapter.onBindViewHolder(holder, position);
        //contentPanel.revalidate();
        holder.repaint();

        /*contentPanel.getComponent(position).setBackground(Color.red);
        contentPanel.getComponent(position).revalidate();*/
    }

    public Component getItem(int n)
    {
        return contentPanel.getComponent(n);
    }

    public JPanel getContentPanel()
    {
        return contentPanel;
    }

    public void setScrollToTopListener(ScrollToTopListener listener)
    {
        this.scrollToTopListener = listener;
    }

    /**
     *
     * @param position
     * @param end
     */
    public void notifyItemInserted(int position, boolean end)
    {
        int viewType = adapter.getItemViewType(position);
        ViewHolder holder = adapter.onCreateViewHolder(viewType);
        adapter.onBindViewHolder(holder, position);

        position = end ? -1 : position;
        contentPanel.add(holder, position);
        contentPanel.revalidate();
    }

    public void notifyItemRemoved(int position)
    {
        contentPanel.remove(position);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /**
     * 获取列表中所有的ViewHolder项目，不包括AvatarViewHolder
     * @return
     */
    public java.util.List<Component> getItems()
    {
        Component[] components = contentPanel.getComponents();
        List<Component> viewHolders = new ArrayList<>();
        for (Component com : components)
        {
            if (!(com instanceof AvatarViewHolder))
            {
                viewHolders.add(com);
            }
        }

        return viewHolders;
    }

    public interface ScrollToTopListener
    {
        void onScrollToTop();
    }
}
