package io.ipfs.api.nbs.pubsub;

/**
 * @Package : io.ipfs.api.nbs.pubsub
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/26-15:39
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class PeersWrapper {
    private String[] Strings;

    public PeersWrapper(String[] strings) {
        Strings = strings;
    }

    public PeersWrapper() {
    }

    public String[] getStrings() {
        return Strings;
    }

    public void setStrings(String[] strings) {
        Strings = strings;
    }
}
