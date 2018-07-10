package io.ipfs.api.bitswap;

import com.alibaba.fastjson.JSON;
import io.ipfs.api.ResData;
import io.ipfs.api.repo.RepoStat;
import io.nbs.commons.helper.ConfigurationHelper;
import io.nbs.commons.utils.HttpUtils;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

/**
 * @Package : io.ipfs.api.bitswap
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/10-22:59
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class BitSwapService {
    private static final Logger logger = LoggerFactory.getLogger(BitSwapService.class);
    private URL IPFS_URL;
    private static final String API_VER_SUFFIX = "/api/v0/";
    private static  String HOST = "127.0.0.1";
    private static  int PORT = 5001;
    private static final String PROTOCOL = "http";

    private BitSwapService() {
        HOST = ConfigurationHelper.getInstance().getIPFSServerHost();
        PORT = Integer.parseInt(ConfigurationHelper.getInstance().getIPFSApiPort());
    }

    public static BitSwapService getInstance(){
        return BitSwapServiceHolder.bitSwapService;
    }

    private static class BitSwapServiceHolder{
        protected static BitSwapService bitSwapService = new BitSwapService();
    }

    /**
     *
     * @return
     */
    public ResData<RepoStat> getRepoStat(){
        String apiURL = getApiUrl("repo/stat",null);
        ResData<RepoStat> resData = null;
        Response response = null;
        try {
            response =  HttpUtils.get(apiURL);
            if(response.code()==200){
                String resJsonStr = response.body().string();
                //logger.info(resJsonStr);
                RepoStat repoStat = JSON.parseObject(resJsonStr,RepoStat.class);
                resData = new ResData(repoStat);
            }else {
                resData = new ResData<>(1,"no get data");
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            resData = new ResData<>(1,e.getMessage());
        }
        return resData;
    }

    /**
     * bitswap/stat
     * @return
     */
    public ResData<BitSwap> getBitSwapStat(){
        String apiURL = getApiUrl("bitswap/stat",null);
        ResData<BitSwap> resData = null;
        Response response = null;
        try {
            response =  HttpUtils.get(apiURL);
            if(response.code()==200){
                String resJsonStr = response.body().string();
                //logger.info(resJsonStr);
                BitSwap bitSwap = JSON.parseObject(resJsonStr,BitSwap.class);
                resData = new ResData<>(bitSwap);
            }else {
                resData = new ResData<>(1,"no get data");
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            resData = new ResData<>(1,e.getMessage());
        }
        return resData;
    }

    /**
     *
     * @param subApi
     * @param argString
     * @return
     */
    private String getApiUrl(String subApi,String argString){
        StringBuilder sb = new StringBuilder();
        sb.append(PROTOCOL).append("://").append(HOST);
        sb.append(":").append(PORT).append(API_VER_SUFFIX);
        sb.append(subApi);
        if(StringUtils.isNotBlank(argString)){
            sb.append("/").append(argString);
        }
        return sb.toString();
    }
}
