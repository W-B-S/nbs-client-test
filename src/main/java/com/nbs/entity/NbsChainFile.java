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

package com.nbs.entity;

import java.io.Serializable;
import java.util.Map;

public class NbsChainFile  implements Serializable {
    private Map Arguments;
    private Map<String,NbsChainData> Objects;

    public NbsChainFile(Map arguments, Map<String,NbsChainData> objects) {
        Arguments = arguments;
        Objects = objects;
    }

    public Map getArguments() {
        return Arguments;
    }

    public void setArguments(Map arguments) {
        Arguments = arguments;
    }

    public Map<String, NbsChainData> getObjects() {
        return Objects;
    }

    public void setObjects(Map<String, NbsChainData> objects) {
        Objects = objects;
    }

    public NbsChainData getMainNbsChainData(String hash58){
        if(hash58==null)return null;
        return this.Objects.get(hash58);

    }
}
