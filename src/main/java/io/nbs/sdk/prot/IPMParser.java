package io.nbs.sdk.prot;

/**
 * @Package : io.nbs.sdk.prot
 * @Description :
 * <p>
 *     the ipfs message protocol parse util
 * </p>
 * @Author : lambor.c
 * @Date : 2018/7/2-16:44
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IPMParser {

    public static String encode(){
        return "";
    }

    public static enum  IPMTypes{
        /**
         * $ON.B64.J$xxxxxsssds$
         */
        online("ON.B64.J","$"),
        /**
         * $PC.B64.J$xxxxxxxx$
         */
        pctrl("PC.B64.J","$"),
        /**
         * $PC.B64.J$xxxxxxxx$
         */
        nomarl("IMN.URLEN.S","$"),
        /**
         *
         */
        image("FIM.N.S","$"),
        /**
         *
         */
        unkonw("","");
        private String separator;
        private String tag;

        IPMTypes(String tag,String separator) {
            this.separator = separator;
            this.tag = tag;
        }

        public String getPrefix(){
            return this.separator+this.tag+this.separator;
        }

        public int getPrefixLength(){
            return getPrefix().length();
        }
    }
}
