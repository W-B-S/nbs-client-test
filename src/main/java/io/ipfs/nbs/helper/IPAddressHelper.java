package io.ipfs.nbs.helper;

import com.alibaba.fastjson.JSON;
import io.nbs.client.Launcher;
import io.nbs.client.cnsts.AppGlobalCnst;
import io.nbs.commons.helper.ConfigurationHelper;
import io.nbs.commons.utils.HttpUtils;
import io.nbs.sdk.beans.TaobaoResult;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @Package : io.ipfs.nbs.helper
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/9-16:05
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IPAddressHelper {
    private static final Logger logger = LoggerFactory.getLogger(IPAddressHelper.class);

    private static String IP = null;
    private static IPAddressHelper helper = null;
    private static String HTML_TEMP;
    public static final Pattern IPV4_PATTERN = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
    public static final Pattern IPV6_SID_REGEX = Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");
    public static final Pattern IPV6_COMPRESSED_REGEX = Pattern.compile("^^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");
    private static String CHECKED_URL;
    private static String CHECKED_LOCATION_URL;


    private IPAddressHelper() {
        HTML_TEMP = AppGlobalCnst.consturactPath(Launcher.CURRENT_DIR,"TEMP","html");
        File dirFile = new File(HTML_TEMP);
        if(dirFile.exists())dirFile.delete();
        dirFile.mkdirs();
        CHECKED_URL = ConfigurationHelper.getInstance()
                .getCfgProps().getProperty("nbs.server.ip.checked-url","http://www.net.cn/static/customercare/yourip.asp");
        CHECKED_LOCATION_URL = ConfigurationHelper.getInstance()
                .getCfgProps().getProperty("nbs.server.ip.checked-locations-url","http://ip.taobao.com/service/getIpInfo.php?ip=");
    }

    public static IPAddressHelper getInstance(){
        if(helper==null)helper= new IPAddressHelper();
        return helper;
    }


    public String getRealIP(){
        logger.info(CHECKED_URL);
        Document document = null;
        try {
            document = Jsoup.connect(CHECKED_URL).get();
            Elements h2s = document.getElementsByTag("h2");
            for(Element h2:h2s){
                String content = h2.text();
                logger.info(content);
                if(isIP(content)){
                    return content;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getLocations(String ip){
        String url = CHECKED_LOCATION_URL + ip;
        String res = "";
        Response response = null;

        try {
            response = HttpUtils.get(url);
            String tempResp = response.body().string();
            logger.info(tempResp);
            TaobaoResult result = JSON.parseObject(tempResp,TaobaoResult.class);
            if(result!=null&&result.getData()!=null){
                return result.getData().getLocations();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return res;
    }

/*    public static void main(String[] args){
        String ip="169.254.204.10";
        boolean b = IPAddressHelper.getInstance().isIP(ip);
        System.out.println(">>>"+b);
    }*/

    public boolean isIPV4(final String inStr){
        return IPV4_PATTERN.matcher(inStr).matches();
    }

    public boolean isIPV6(final String inStr){
        return IPV6_COMPRESSED_REGEX.matcher(inStr).matches()||IPV6_SID_REGEX.matcher(inStr).matches();
    }

    /**
     *
     * @param inAddress
     * @return
     */
    public boolean isIP(final String inAddress){
        return isIPV4(inAddress)||isIPV6(inAddress);
    }
}
