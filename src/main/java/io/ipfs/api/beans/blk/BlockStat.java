package io.ipfs.api.beans.blk;

/**
 * @Package : io.ipfs.api.beans.blk
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/13-10:02
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class BlockStat {
    private String hash;
    private Integer numLinks;
    private Long BlockSize;
    private Long LinksSize;
    private Long DataSize;
    private Long CumulativeSize;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getNumLinks() {
        return numLinks;
    }

    public void setNumLinks(Integer numLinks) {
        this.numLinks = numLinks;
    }

    public Long getBlockSize() {
        return BlockSize;
    }

    public void setBlockSize(Long blockSize) {
        BlockSize = blockSize;
    }

    public Long getLinksSize() {
        return LinksSize;
    }

    public void setLinksSize(Long linksSize) {
        LinksSize = linksSize;
    }

    public Long getDataSize() {
        return DataSize;
    }

    public void setDataSize(Long dataSize) {
        DataSize = dataSize;
    }

    public Long getCumulativeSize() {
        return CumulativeSize;
    }

    public void setCumulativeSize(Long cumulativeSize) {
        CumulativeSize = cumulativeSize;
    }
}
