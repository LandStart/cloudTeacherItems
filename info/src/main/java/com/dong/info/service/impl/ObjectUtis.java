package com.dong.info.service.impl;

import org.springframework.util.CollectionUtils;

import java.util.Date;

public class ObjectUtis {

    /**功能1**/
    public static Date getTime(){
        return new Date();
    }

    /**功能1**/
    public static String getName(){
        return "Test";
    }


    public static void main(String[] args) {
        System.out.println(args.toString());
    }
}
