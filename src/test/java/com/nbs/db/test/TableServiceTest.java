package com.nbs.db.test;

import com.alibaba.fastjson.JSON;
import com.nbs.biz.model.NBSTest;
import com.nbs.biz.service.TableService;
import com.nbs.utils.DbUtil;

import java.util.List;

/**
 * @Package : com.nbs.db.test
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/23-21:58
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class TableServiceTest {

    public TableServiceTest() {

    }

    public static void main(String[] agrs){

        TableServiceTest tt = new TableServiceTest();
        //tt.createNbsContacts();
        tt.crateTest();

    }

    public void crateTest(){
        TableService service = new TableService(DbUtil.getSqlSession());
        service.initNBSMessage(true);
    }

    public void createNbsContacts(){
        TableService service = new TableService(DbUtil.getSqlSession());
        service.initNbsContacts();
        List<NBSTest> lst = service.getAll();
        if(lst!=null){
            String json = JSON.toJSONStringWithDateFormat(lst,"yyyy-MM-dd HH:mm:ss");
            System.out.println(json);
        }
    }

}
