package UI.panel.fm;

import UI.ConstantsUI;
import UI.panel.ContentJLabel;
import com.nbs.entity.NbsChainData;
import com.nbs.tools.PropertyUtil;
import com.nbs.tools.StringHelper;
import io.nbs.commons.helper.MerkleHashHelper;

import javax.swing.*;
import javax.swing.table.TableModel;



/**
 * @Package : UI.panel.fm
 * @Description :
 * <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/20-16:09
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class FileShowPanel extends JPanel {

    public static final String[] headers = new String[]{
            PropertyUtil.getProperty("nbs.ui.panel.file.grid.header-col0","Name"),
            PropertyUtil.getProperty("nbs.ui.panel.file.grid.header-col1","Hash"),
            PropertyUtil.getProperty("nbs.ui.panel.file.grid.header-col2","Type"),
            PropertyUtil.getProperty("nbs.ui.panel.file.grid.header-col3","Size")};

    private TableModel tableModel;
    private JScrollPane scrollPane;
    private JTable grid;

    private MerkleHashHelper hashHelper = MerkleHashHelper.getInstance();

    private NbsChainData chainData;

    public FileShowPanel() {
        super(true);
        //tableModel = getTableModel();
        //grid = new JTable(null,headers);

    }


    public void reload(NbsChainData chainData){
        this.removeAll();
        if(chainData==null){

            JLabel label = new ContentJLabel("没有查到！");
            this.add(label);
            this.validate();
            this.updateUI();
            return;
        }
        grid = new JTable(getGridData(chainData),headers);
        grid.validate();
        grid.updateUI();

        initSetGrid(grid);
        scrollPane = new JScrollPane(grid);
        scrollPane.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        this.add(scrollPane);
        this.updateUI();
    }

    private void initSetGrid(JTable table){
        if(table!=null){
            table.setFont(ConstantsUI.FONT_NORMAL);
            table.getTableHeader().setFont(ConstantsUI.FONT_NORMAL);
            table.getTableHeader().setBackground(ConstantsUI.TOOL_BAR_BACK_COLOR);
            table.setRowHeight(31);
            table.setGridColor(ConstantsUI.TABLE_LINE_COLOR);
            table.setSelectionBackground(ConstantsUI.TOOL_BAR_BACK_COLOR);


            //
            table.getColumnModel().getColumn(0).setPreferredWidth(120);
            table.getColumnModel().getColumn(0).setMaxWidth(220);

            table.getColumnModel().getColumn(2).setPreferredWidth(80);
            table.getColumnModel().getColumn(2).setMaxWidth(160);

            table.getColumnModel().getColumn(3).setPreferredWidth(80);
            table.getColumnModel().getColumn(3).setMaxWidth(150);
        }
    }



    /**
     *
     * @param nbsChainData
     * @return
     */
    public Object[][] getGridData(NbsChainData nbsChainData){
        int row = 0;
        if(nbsChainData!=null)row = 1;
        if(nbsChainData.getLinks()!=null&&nbsChainData.getLinks().size()>0){
            row = row+nbsChainData.getLinks().size();
        }
        Object[][] gridDatas = new Object[row][headers.length];
        Object[] self = buildRow(nbsChainData);
        if(self!=null)gridDatas[0] = self;
        if(nbsChainData!=null&&nbsChainData.getLinks()!=null&&nbsChainData.getLinks().size()>0){
            int i = 1;
            for(NbsChainData chainData : nbsChainData.getLinks()){
                Object[] sub = buildRow(chainData);
                if(sub!=null){
                    gridDatas[i] = sub;
                    i++;
                }
            }
        }
        return gridDatas;
    }

    /**
     *
     * @param data
     * @return
     */
    private Object[] buildRow(NbsChainData data){
        if(data==null||data.getHash()==null)return null;
        Object[] objects = new Object[4];
        objects[0] = data.getName()==null? hashHelper.getFileName( data.getHash()):data.getName();
        objects[1] = data.getHash();
        objects[2] = data.getType()==null ? "" : data.getType();
        objects[3] = StringHelper.getInstance().fileSizeConvert(data.getSize());
        return objects;
    }

    public NbsChainData getChainData() {
        return chainData;
    }

    public void setChainData(NbsChainData chainData) {
        this.chainData = chainData;
    }
}
