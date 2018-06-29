package io.ipfs.nbs.ui.frames;

import io.ipfs.api.IPFS;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;


/**
 * @Package : io.ipfs.nbs.ui.frames
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/29-18:34
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class InitialFrame extends JFrame {

    private static final Logger logger = LoggerFactory.getLogger(InitialFrame.class);

    private IPFS ipfs;
    private static Point origin = new Point();

    private SqlSession sqlSession;

    public InitialFrame(IPFS ipfs){
        this.ipfs = ipfs;
    }
}
