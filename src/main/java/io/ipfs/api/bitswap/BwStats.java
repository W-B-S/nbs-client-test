package io.ipfs.api.bitswap;

/**
 * @Package : io.ipfs.api.bitswap
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/11-0:49
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class BwStats {
    private Long TotalIn;
    private Long TotalOut;
    private Long RateIn;
    private Long RateOut;

    public Long getTotalIn() {
        return TotalIn;
    }

    public void setTotalIn(Long totalIn) {
        TotalIn = totalIn;
    }

    public Long getTotalOut() {
        return TotalOut;
    }

    public void setTotalOut(Long totalOut) {
        TotalOut = totalOut;
    }

    public Long getRateIn() {
        return RateIn;
    }

    public void setRateIn(Long rateIn) {
        RateIn = rateIn;
    }

    public Long getRateOut() {
        return RateOut;
    }

    public void setRateOut(Long rateOut) {
        RateOut = rateOut;
    }
}
