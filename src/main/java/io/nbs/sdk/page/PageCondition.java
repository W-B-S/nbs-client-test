package io.nbs.sdk.page;

/**
 * @Package : io.nbs.sdk.page
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/12-16:00
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class PageCondition {

    private int pageSize = 10;
    private int start = 0;

    private String sord = "ASC";
    private String sidx = null;
    private String searchStr;

    /**
     *
     * @param sidx
     * @param sord
     */
    public PageCondition(String sidx,String sord) {
        this.sidx = sidx;
        this.sord = sord;
    }

    /**
     *
     * @param sidx
     */
    public PageCondition(String sidx) {
        this.sidx = sidx;
    }

    public void setPageConition(int pageNo,int pageSize){
        this.pageSize = pageSize;
        this.start = pageNo<=1 ? 0 :(pageNo-1)*pageSize;
    }
    public void setPageConition(int pageNo){
        this.start = pageNo<=1 ? 0 :(pageNo-1)*pageSize;
    }


    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSearchStr() {
        return searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
