package io.nbs.service.ipfs;

import io.ipfs.api.bitswap.BitSwapService;

/**
 * @Package : io.nbs.service.ipfs
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/18-22:14
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class BitSwapServiceTest {

    private BitSwapService bitSwapService;
    public BitSwapServiceTest() {
        bitSwapService = BitSwapService.getInstance();
    }

    public static void main(String[] args){
        BitSwapServiceTest test = new BitSwapServiceTest();
    }
}
