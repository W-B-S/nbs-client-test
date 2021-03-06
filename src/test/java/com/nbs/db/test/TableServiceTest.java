package com.nbs.db.test;

import com.nbs.biz.model.ContactsEntity;
import com.nbs.biz.service.ContactsService;
import com.nbs.biz.service.TableService;
import io.nbs.commons.utils.DataBaseUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;

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
    SqlSession sqlSession = DataBaseUtil.getSqlSession();
    public static void main(String[] agrs){

        TableServiceTest tt = new TableServiceTest();

        //tt.createNbsContacts();
        tt.crateTest();
        //tt.save();

    }

    public void save(){
        ContactsService contactsService = new ContactsService(sqlSession);
        ContactsEntity entity = new ContactsEntity("QmSdodRYBStnt7L9MzVDG16xcoWEQLjz5j6Jr9Ez8DdfGU","lanbery");
        Date d = new Date();
        long l = d.getTime()/1000;

       // entity.setLmtime( );

        contactsService.insert(entity);
    }

    public void crateTest(){
        TableService service = new TableService(DataBaseUtil.getSqlSession());
        //service.initNBSMessage(true);
        service.initWorldMessages();
    }

    public void createNbsContacts(){
        TableService service = new TableService(DataBaseUtil.getSqlSession());
        service.initPeerMessages();
       // List<NBSTest> lst = service.getAll();
/*        if(lst!=null){
            String json = JSON.toJSONStringWithDateFormat(lst,"yyyy-MM-dd HH:mm:ss");
            System.out.println(json);
        }*/
    }

}
