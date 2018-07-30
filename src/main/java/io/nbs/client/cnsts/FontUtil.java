package io.nbs.client.cnsts;

import io.nbs.client.cnsts.OSUtil;

import java.awt.*;

/**
 * @Package : com.nbs.ui.cnst
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/24-7:59
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class FontUtil {
    private static Font font;

    static
    {
        if (OSUtil.getOsType() == OSUtil.Windows)
        {
            font = new Font("微软雅黑", Font.PLAIN, 14);
        }
        else
        {
            font = new Font("PingFang SC", Font.PLAIN, 14);
        }
    }

    public static Font getDefaultFont()
    {
        return getDefaultFont(14, Font.PLAIN);
    }

    public static Font getDefaultFont(int size)
    {
        return getDefaultFont(size, Font.PLAIN);
    }

    public static Font getDefaultFont(int size, int style)
    {
        return font.deriveFont(style, size);
        //return new Font("YaHei Consolas Hybrid",  style, size);
        //return new Font("微软雅黑", style, size);
    }
}
