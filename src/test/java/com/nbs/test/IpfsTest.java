package com.nbs.test;

import UI.AppMainWindow;
import UI.ConstantsUI;
import UI.panel.ContentJLabel;
import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.TypeReference;
import com.nbs.entity.NbsChainFile;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.cid.Cid;
import io.ipfs.multiaddr.MultiAddress;
import io.ipfs.multihash.Multihash;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @Package : com.nbs.test
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/19-13:05
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IpfsTest {

    private String address = "/ip4/127.0.0.1/tcp/5001";
    private IPFS ipfs = new IPFS(address);
    public IpfsTest(){

    }

    public static void main(String[] args){
        IpfsTest it = new IpfsTest();
        it.getConfig();
       // it.test1();
       // it.listAddr();
       // it.getIDmap();
       // it.pubsubTest();
        //it.fileTest();
        //it.addFile();
        //it.fileLs();
        it.addDirFile();
    }

    private void getConfig(){
        String key = "Gateway";
        try {
            Object s= ipfs.config.get(key);
            String json = io.ipfs.api.JSONParser.toString(s);
            System.out.println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fileLs(){
        String hash58 = "Qmaj6Kz4eqheWwgKfKoZdtHVHzeDE3mZchPrXWUKnraCcg";
        try {
            Multihash multihash = Multihash.fromBase58(hash58);
            Map lsMap = ipfs.file.ls(multihash);
            Cid cid = Cid.buildCidV0(multihash);

            byte[] bytes = ipfs.dag.get(cid);

            String s = new String(bytes);
            show(s);



            for(Object k : lsMap.values()){
                show(""+(k instanceof MerkleNode));
                show(String.valueOf(k));
            }
            show("++++++++++++++++++++====================================================+++++++++++++++++");
            String json = JSON.toJSONString(lsMap,true);
            show(json);
            NbsChainFile chainFile = JSON.parseObject(JSON.toJSONString(lsMap),new TypeReference<NbsChainFile>(){});

           // JSONArray chainFile = JSON.parseArray(json);
            if(chainFile!=null&&chainFile.getObjects().size()>0){
                show(chainFile.getObjects().size()+"");
                show(chainFile.getMainNbsChainData(hash58).getType());
            }

            //MerkleNode.fromJSON(json);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pub(){

    }

    private void show(String s){
        System.out.println(s);
    }

    public void test1(){
        try {
            Map map =ipfs.id();
            Object o = map.get("ID");
            if(o!=null)show(o.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listAddr(){
        try {
            List<MultiAddress> bts = ipfs.bootstrap();
            for(MultiAddress addr : bts){
                show(addr.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void temp(){

    }

    public void  pubsubTest(){
        try {
            Object o = ipfs.pubsub.ls();
            Object o1 = ipfs.pubsub.peers();
            String pubsubPeers = JSON.toJSONString(o1,true);
            show(pubsubPeers);
            ipfs.pubsub.pub("NBS","NBS4");
            //Stream<Map<String, Object>> stream=  ipfs.pubsub.sub("NBS");
            //long l = stream.count();
            show("lanbery");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void subTest(){
        try {
            Stream<Map<String,Object>> subStream = ipfs.pubsub.sub("Lanbery");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fileTest(){
        try {
            NamedStreamable.ByteArrayWrapper byteArrayWrapper = new NamedStreamable.ByteArrayWrapper("nbsTest.txt","NBS add File".getBytes());
            List<MerkleNode> l = ipfs.add(byteArrayWrapper);
            for(MerkleNode n : l){
                show(n.hash.toBase58());
                String json = JSON.toJSONString(n);
                show(json);
            }

        } catch (IOException e) {e.printStackTrace();

        }
    }

    public void addFile(){
        try {
            String filePath = ConstantsUI.PROFILE_ROOT+"nbs.info";
            File file = new File(filePath);
            NamedStreamable.FileWrapper fileWrapper = new NamedStreamable.FileWrapper(file);
            List<MerkleNode> merkleNodes = ipfs.add(fileWrapper);
            for(MerkleNode n : merkleNodes){
                String s = n.hash.toBase58()+":"+n.name;
                show(s);
                String json = n.toJSONString();
                show(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addDirFile(){
        try {
            File dir = new File("E:/tmp");
            NamedStreamable.FileWrapper wrapper = new NamedStreamable.FileWrapper(dir);
            List<MerkleNode> nodes = ipfs.add(wrapper,false,true);
            String json = JSON.toJSONString(nodes,true);
            show(json);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void getIDmap(){
        try {
            Map map = ipfs.id("QmZUaCzkMhSStKV65zGmkUkus8NQ2jaTmFWb7hEwSRt4SZ");

            if(map!=null){
                show(map.size()+"");
                String peerIDJson = JSON.toJSONString(map,true);
                show(peerIDJson);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
