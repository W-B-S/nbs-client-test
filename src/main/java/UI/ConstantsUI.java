package UI;

import com.nbs.tools.PropertyUtil;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * @Package : UI
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/13-14:58
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ConstantsUI {
    /**
     * 软件名称、版本
     */
    public final static String APP_NAME = "NBS Chain";
    public final static String APP_VERSION = "v0.1";
    public final static String CRLF_PANEL = "\\r\\n";

    public final static String ENTER_CHARACTER = "\n";
    public final static String WSPACE_CHARACTER4 = "    ";

    /**
     * 窗口设置
     */
    public final static int MAIN_WINDOW_X = 240;
    public final static int MAIN_WINDOW_Y = 100;
    public final static int MAIN_WINDOW_WIDTH = 885;
    public final static int MAIN_WINDOW_HEIGHT = 636;

    /**
     * 文件选择框设置
     */
    public static final int FILE_CHOOSER_X = 300;
    public static final int FILE_CHOOSER_Y = 150;
    public static final int FILE_CHOOSER_WIDTH = 635;
    public static final int FILE_CHOOSER_HEIGHT = 500;

    /**
     * data 默认路径
     */
    public final static String NBS_PATH_DEF = "nbs";
    /**
     * 系统当前路径
     */
    public final static String CURRENT_DIR = System.getProperty("user.dir");

    public final static String IPFS_PATH = CURRENT_DIR + File.separator + ".nbs_local"+ File.separator;

    public final static String PROFILE_ROOT = CURRENT_DIR + File.separator + "profile"+ File.separator;

    /**
     * 图标路径
     */
    private static final String BUTTON_ICON_ROOT = "/icon/"+PropertyUtil.getProperty("nbs.button.dir","")+"/";

    /**
     * 主图标
     */
    public final static Image IMAGE_ICON = Toolkit.getDefaultToolkit()
            .getImage(AppMainWindow.class.getResource("/icon/nbs64.png"));
    /**
     * NBS logo
     */
    public final static ImageIcon NBS_ICON =  new ImageIcon(
            AppMainWindow.class.getResource("/icon/logo.png"));
    /**
     * 主窗口背景
     */
    public final static Color MAIN_BACK_COLOR = Color.WHITE;
    /**
     * 工具栏背景
     */
    public final static Color TOOL_BAR_BACK_COLOR = new Color(37, 174, 96);

    /**
     * 窗口Title 字体颜色
     */
    public final static Color PANEL_TITILE_COLOR = new Color(58,136,202);
    /**
     * 表格线条背景色
     */
    public final static Color TABLE_LINE_COLOR = new Color(48, 129, 129);

    /**
     * label 字体颜色
     */
    public final static Color NORMAL_FONT_COLOR = new Color(107,107,107);

    /**
     * 命令窗口背景色
     */
    public final static Color COMMAND_BACK_COLOR = new Color(25,25,25);
    /**
     * @Date    : 2018/6/13 15:19
     * @Author  : lanbery
     * @Decription :
     * <p>
     *     字体设置
     * </p>
     */
    public final static Font FONT_TITLE = new Font(PropertyUtil.getProperty("nbs.ui.font.family"),0,27);
    public final static Font FONT_SUB_TITLE = new Font(PropertyUtil.getProperty("nbs.ui.font.family"),0,20);
    public final static Font FONT_NORMAL = new Font(PropertyUtil.getProperty("nbs.ui.font.family"),0,13);
    public final static Font FONT_LABEL = new Font(PropertyUtil.getProperty("nbs.ui.font.family"),0,15);
    /**
     *
     */
    public final static Font FONT_CPR = new Font(PropertyUtil.getProperty("nbs.ui.font.family"),0,18);



    /**
     * 图标定义
     */
    //状态
    public final static ImageIcon ICON_STATUS = new ImageIcon(
            AppMainWindow.class.getResource("/icon/status.png"));
    public final static ImageIcon ICON_STATUS_ENABLED = new ImageIcon(
            AppMainWindow.class.getResource("/icon/status_enabled.png"));

    /**
     *
     */
    public final static ImageIcon ICON_TEST = new ImageIcon(
            AppMainWindow.class.getResource("/icon/test.png"));
    public final static ImageIcon ICON_FILE = new ImageIcon(
            AppMainWindow.class.getResource("/icon/data.png"));
    public final static ImageIcon ICON_FILE_ENABLED = new ImageIcon(
            AppMainWindow.class.getResource("/icon/data_enabled.png"));
    public final static ImageIcon ICON_FILE_LS = new ImageIcon(AppMainWindow.class.getResource(BUTTON_ICON_ROOT+"file_ls_normal.png"));
    public final static ImageIcon ICON_FILE_LS_ENABLED = new ImageIcon(AppMainWindow.class.getResource(BUTTON_ICON_ROOT+"file_ls_enabled.png"));
    public final static ImageIcon ICON_FILE_LS_DISABLED = new ImageIcon(AppMainWindow.class.getResource(BUTTON_ICON_ROOT+"file_ls_disabled.png"));

    public final static ImageIcon ICON_FILE_SHARE = new ImageIcon(AppMainWindow.class.getResource(BUTTON_ICON_ROOT+"file_share_normal.png"));
    public final static ImageIcon ICON_FILE_SHARE_ENABLED = new ImageIcon(AppMainWindow.class.getResource(BUTTON_ICON_ROOT+"file_share_enabled.png"));
    public final static ImageIcon ICON_FILE_SHARE_DISABLED = new ImageIcon(AppMainWindow.class.getResource(BUTTON_ICON_ROOT+"file_share_disabled.png"));

    public final static ImageIcon ICON_UPLOAD = new ImageIcon(AppMainWindow.class.getResource(BUTTON_ICON_ROOT+"upload_normal.png"));
    public final static ImageIcon ICON_UPLOAD_ENABLED = new ImageIcon(AppMainWindow.class.getResource(BUTTON_ICON_ROOT+"upload_enabled.png"));
    public final static ImageIcon ICON_UPLOAD_DISABLED = new ImageIcon(AppMainWindow.class.getResource(BUTTON_ICON_ROOT+"upload_disabled.png"));
    /**
     *
     */
    public final static ImageIcon ICON_IM = new ImageIcon(
            AppMainWindow.class.getResource("/icon/im.png"));
    public final static ImageIcon ICON_IM_ENABLED = new ImageIcon(
            AppMainWindow.class.getResource("/icon/im_enabled.png"));

    //设置
    public final static ImageIcon ICON_SETTING = new ImageIcon(
            AppMainWindow.class.getResource("/icon/setting.png"));
    public final static ImageIcon ICON_SETTING_ENABLED = new ImageIcon(
            AppMainWindow.class.getResource("/icon/setting_enabled.png"));
    public final static ImageIcon ICON_SETTING_DISABLED = new ImageIcon(
            AppMainWindow.class.getResource("/icon/setting.png"));

    //About
    public final static ImageIcon ICON_ABOUT = new ImageIcon(
            AppMainWindow.class.getResource("/icon/about.png"));
    public final static ImageIcon ICON_ABOUT_ENABLED = new ImageIcon(
            AppMainWindow.class.getResource("/icon/about_enabled.png"));

    //button icon
    public final static ImageIcon ICON_SEND = new ImageIcon(
            AppMainWindow.class.getResource(BUTTON_ICON_ROOT + "send.png"));
    public final static ImageIcon ICON_SEND_ENABLED = new ImageIcon(
            AppMainWindow.class.getResource(BUTTON_ICON_ROOT + "send_enabled.png"));
    public final static ImageIcon ICON_SEND_DISABLED = new ImageIcon(
            AppMainWindow.class.getResource(BUTTON_ICON_ROOT + "send_disabled.png"));

    /**
     * im
     */
    /**
     * check version
     */

    public final static ImageIcon ICON_CHECK = new ImageIcon(
            AppMainWindow.class.getResource("/icon/checkversion.png"));
    public final static ImageIcon ICON_CHECK_ENABLED = new ImageIcon(
            AppMainWindow.class.getResource("/icon/checkversion_enabled.png"));
    public final static ImageIcon ICON_CHECK_DISABLED = new ImageIcon(
            AppMainWindow.class.getResource("/icon/checkversion_disabled.png"));

    /**
     * 样式布局相关
     */
    // 主面板水平间隔
    public final static int MAIN_H_GAP = 25;
    public final static int PANEL_H_GAP10 = 15;

    // 主面板Label 大小
    public final static Dimension LABLE_SIZE = new Dimension(1300, 30);
    // Item Label 大小
    public final static Dimension LABLE_SIZE_ITEM = new Dimension(78, 30);
    // Item text field 大小
    public final static Dimension TEXT_FIELD_SIZE_ITEM = new Dimension(400, 24);
    // radio 大小
    public final static Dimension RADIO_SIZE = new Dimension(1300, 60);
    // 高级选项面板Item 大小
    public final static Dimension PANEL_ITEM_SIZE = new Dimension(1300, 40);
}
