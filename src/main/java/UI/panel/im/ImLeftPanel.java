package UI.panel.im;

import com.nbs.ui.components.ColorCnst;
import com.nbs.ui.components.GBC;
import com.nbs.ui.panels.ListPanel;
import com.nbs.ui.panels.SearchPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : UI.panel.im
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/24-10:36
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ImLeftPanel extends JPanel {

    private SearchPanel searchPanel;

    /**
     *  contact list or other
     */
    private ListPanel listPanel;

    public ImLeftPanel() {
        initComponents();
        initView();
    }

    private void initComponents(){

        searchPanel = new SearchPanel(this);

        listPanel = new ListPanel(this);
        listPanel.setBackground(ColorCnst.FONT_GRAY);
    }

    private void initView(){
        this.setBackground(ColorCnst.FONT_GRAY);
        this.setLayout(new GridBagLayout());

        //add(searchPanel,new GBC(0,0).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setWeight(1,7));
        add(listPanel,new GBC(0,1).setAnchor(GBC.CENTER).setFill(GBC.BOTH).setWeight(1,2));
    }

    public ListPanel getListPanel() {
        return listPanel;
    }
}
