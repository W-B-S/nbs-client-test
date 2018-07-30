/*
 *   Copyright © 2018, lanbery,Lambor Co,. Ltd. All Rights Reserved.
 *
 *   Copyright Notice
 *   lanbery copyrights this specification. No part of this specification may be reproduced in any form or means, without the prior written consent of lanbery.
 *
 *   Disclaimer
 *   This specification is preliminary and is subject to change at any time without notice. Lambor assumes no responsibility for any errors contained herein.
 *
 */

package io.nbs.commons.helper;

import UI.ConstantsUI;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

public class MerkleHashHelper {
    private static final Logger logger = LoggerFactory.getLogger(MerkleHashHelper.class);

    private static final String MERKLE_FILE =".merkle";

    private static Properties MERKLE_HASH = new Properties();

    private MerkleHashHelper(){
        InputStream is = null;
        try{
            File f = new File(ConstantsUI.PROFILE_ROOT);
            if(!f.exists()){
                f.mkdirs();
            }
            File mfile = new File(ConstantsUI.PROFILE_ROOT,MERKLE_FILE);
            if(!mfile.exists()){
                mfile.createNewFile();
            }
            is = new BufferedInputStream(new FileInputStream(mfile));
            MERKLE_HASH.load(is);
            is.close();
        }catch (IOException ioe){
            logger.error("load merkle file.");
            if(is!=null){
                try {
                    is.close();
                }catch (IOException ioec){
                    logger.error("close io error.");
                }
            }
        }
    }

    private static class MerkleHolder {
        static MerkleHashHelper instance = new MerkleHashHelper();
    }

    /**
     *
     * @return
     */
    public static MerkleHashHelper getInstance(){
        return MerkleHolder.instance;
    }

    /**
     *
     * @return
     */
    public static Properties getMerkleHash(){
        return MERKLE_HASH;
    }

    /**
     *
     * @param key
     * @return
     */
    public String getFileName(String key){
        return MERKLE_HASH.getProperty(key);
    }

    /**
     *
     * @param hash
     * @param name
     * @param comments
     * @return
     * @throws IOException
     */
    public boolean storeMerkleHash(String hash,String name,String comments) throws FileNotFoundException {
        if(hash==null)return false;
        if(StringUtils.isBlank(name))name=hash;
        File merkleFile;
        FileOutputStream fos;
        try{
            MERKLE_HASH.setProperty(hash,name);
            merkleFile = new File(ConstantsUI.PROFILE_ROOT+MERKLE_FILE);
            fos = new FileOutputStream(merkleFile);
            if(StringUtils.isNotBlank(comments)){
                MERKLE_HASH.store(fos,comments);
            }else {
                MERKLE_HASH.store(fos,null);
            }
            fos.flush();
            fos.close();
            return true;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(MERKLE_FILE+" not found.");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
