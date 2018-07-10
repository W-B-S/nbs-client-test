package io.nbs.commons.utils;

import java.util.UUID;

/**
 * @Package : io.nbs.commons.utils
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/4-11:06
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class UUIDGenerator {
    /**
     * UUID 36 e393c8ab-8f25-40da-90cf-007b5650af48
     */
    public static final String UUID_36 = "36";
    /**
     * UUID 32 LOWWER e393c8ab8f2540da90cf007b5650af48
     */
    public static final String UUID_LOWEER = "32l";

    private UUIDGenerator() {
    }

    /**
     *
     * @Author :wdcai
     * @Comment :
     * @param type
     *            :36 OR 32l
     * @return
     */
    public static String getUUID(String type) {
        if (type.equalsIgnoreCase(UUID_36)) {
            return UUID.randomUUID().toString();
        } else if (type.equalsIgnoreCase(UUID_LOWEER)) {
            return UUID.randomUUID().toString().replaceAll("-", "")
                    .toLowerCase();
        } else {
            return UUID.randomUUID().toString();
        }

    }

    /**
     * 32 UPPER :4B11CAA83B8741FC835C5440F68C2BEC
     *
     * @Author :wdcai
     * @Comment :
     * @return
     */
    public static String getUUID() {
        return getUUID(UUID_36).replaceAll("-", "").toUpperCase();
    }
}
