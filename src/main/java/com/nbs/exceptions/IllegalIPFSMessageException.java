package com.nbs.exceptions;

/**
 * @Package : com.nbs.exceptions
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/26-10:17
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IllegalIPFSMessageException extends Exception {
    private static final String MSG = "ipfs message formatter error.";

    public IllegalIPFSMessageException() {
        super(MSG);
    }

    public IllegalIPFSMessageException(String message) {
        super(message);
    }
}
