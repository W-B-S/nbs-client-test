package io.nbs.client.exceptions;

/**
 * @Package : io.nbs.client.exceptions
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/17-22:12
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class FileTooLargeException extends Exception {
    private static final String MSG = "文件太大，上传失败.";
    public FileTooLargeException() {
        super(MSG);
    }

    public FileTooLargeException(String message) {
        super(message);
    }

    public FileTooLargeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileTooLargeException(Throwable cause) {
        super(cause);
    }

    public FileTooLargeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
