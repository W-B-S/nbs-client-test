package io.ipfs.api.bitswap;

import java.util.List;

/**
 * @Package : io.ipfs.api.bitswap
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/10-22:53
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class BitSwap {
    private Long ProvideBufLen;
    private List<String> Wantlist;
    private List<String> Peers;

    private Integer BlocksReceived;
    private Long DataReceived;
    private Long DataSent;
    private Integer BlocksSent;

    private Integer DupBlksReceived;
    private Long DupDataReceived;

    public Long getProvideBufLen() {
        return ProvideBufLen;
    }

    public void setProvideBufLen(Long provideBufLen) {
        ProvideBufLen = provideBufLen;
    }

    public List<String> getWantlist() {
        return Wantlist;
    }

    public void setWantlist(List<String> wantlist) {
        Wantlist = wantlist;
    }

    public List<String> getPeers() {
        return Peers;
    }

    public void setPeers(List<String> peers) {
        Peers = peers;
    }

    public Integer getBlocksReceived() {
        return BlocksReceived;
    }

    public void setBlocksReceived(Integer blocksReceived) {
        BlocksReceived = blocksReceived;
    }

    public Long getDataReceived() {
        return DataReceived;
    }

    public void setDataReceived(Long dataReceived) {
        DataReceived = dataReceived;
    }

    public Long getDataSent() {
        return DataSent;
    }

    public void setDataSent(Long dataSent) {
        DataSent = dataSent;
    }

    public Integer getDupBlksReceived() {
        return DupBlksReceived;
    }

    public void setDupBlksReceived(Integer dupBlksReceived) {
        DupBlksReceived = dupBlksReceived;
    }

    public Long getDupDataReceived() {
        return DupDataReceived;
    }

    public void setDupDataReceived(Long dupDataReceived) {
        DupDataReceived = dupDataReceived;
    }

    public Integer getBlocksSent() {
        if(this.BlocksSent==null)BlocksSent=0;
        return BlocksSent;
    }

    public void setBlocksSent(Integer blocksSent) {
        BlocksSent = blocksSent;
    }

    public int getPeersSize(){
        if(this.Peers==null)return 0;
        return this.getPeers().size();
    }

    public int getWantSize(){
        if(this.getWantlist()==null)return 0;
        return this.getWantlist().size();
    }
}
