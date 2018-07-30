package io.nbs.client;



/**
 * @Package : UI
 * @Description :
 * <p>
 *     NBS Chain Client For Java
 * </p>
 * @Author : lambor.c
 * @Date : 2018/6/29-13:56
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class App {

    public static void main(String[] agrs){
      /*  ConfigurationHelper configHelper = ConfigurationHelper.getInstance();
        Properties cfg = configHelper.getCfgProps();*/
   /*     for(String k : cfg.stringPropertyNames()){
            System.out.println(k+"="+cfg.getProperty(k));
        }*/

        Launcher launcher = new Launcher();
        launcher.launch();

    }
}
