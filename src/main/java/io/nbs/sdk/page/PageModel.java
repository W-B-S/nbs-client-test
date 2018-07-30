package io.nbs.sdk.page;

import java.util.List;

/**
 * @Package : io.nbs.sdk.page
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/12-14:50
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class PageModel<E> {

    private List<E> list;
    /**
     * 总条数
     */
    private int totalRecords;
    private int pageSize = 10;
    /**
     * 当前页
     */
    private int pageNo;

    public PageModel(int pageSize, int pageNo) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
    }


    public PageModel() {
        this.pageSize = 10;
        this.pageNo = 1;
    }

    public PageModel(List<E> list, int totalRecords) {
        this.list = list;
        this.totalRecords = totalRecords;
    }

    /**
     *
     * @return
     */
    public int getTotalPages(){
        return (totalRecords+pageSize-1)/pageSize;
    }

    /**
     * 首页
     * @return
     */
    public int getTopPageNo(){
        return 1;
    }

    /**
     * 前一页
     * @return
     */
    private int getPrevousPageNo(){
        if(pageNo<=1)return 1;
        return pageNo-1;
    }

    /**
     * 最后一页
     * @return
     */
    public int getLastPageNo(){
        return getTotalPages();
    }

    /**
     * 下一页
     * @return
     */
    public int getNextPageNo(){
        if(pageNo>=getLastPageNo())return getLastPageNo();
        return pageNo+1;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    /**
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    public static int getOffest(int pageNo,int pageSize){
        if(pageNo<1)return 0;
        return (pageNo-1)*pageSize;
    }

    /**
     *
     * @param pageNo
     * @return
     */
    public static int getOffest(int pageNo){
        if(pageNo<1)return 0;
        return (pageNo-1)*10;
    }
}
