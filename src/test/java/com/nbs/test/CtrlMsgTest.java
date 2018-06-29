package com.nbs.test;

import com.alibaba.fastjson.JSON;
import com.nbs.entity.PeerBoradcastInfo;
import com.nbs.utils.Base64CodecUtil;
import io.ipfs.nbs.utils.RadomCharactersHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Package : com.nbs.test
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/24-23:40
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class CtrlMsgTest {

    private static Logger log = LoggerFactory.getLogger(CtrlMsgTest.class);

   public static void main(String[] agrs){
       CtrlMsgTest cmt = new CtrlMsgTest();
       /*long l = System.currentTimeMillis()/1000;
       System.out.println(">>>"+l);*/
       //cmt.GetCtrlOnline();

       String name = "kasdfsadf.txt";
       System.out.println(name.substring(name.lastIndexOf(".")));
   }

   private void GetCtrlOnline(){
       String id = "QmNLiRSpcxTvaQKh53paJixMEs7FKMxTbGLNq7vzt8noos";
       String avatar = "Qmd1noRjxovS5LMRbG34wJGEZF6eQmeKgtbiFgbezHuzwP";
       String suffix = ".png";
       String name = RadomCharactersHelper.getInstance().generated("SChain_",4);
       PeerBoradcastInfo peerInfo = new PeerBoradcastInfo(id,name,avatar,suffix);

       String json = JSON.toJSONString(peerInfo);
       log.info(json);
       String encode = Base64CodecUtil.encodeCtrlMsg(peerInfo,Base64CodecUtil.CtrlTypes.online);
       log.info(encode);
   }
}
