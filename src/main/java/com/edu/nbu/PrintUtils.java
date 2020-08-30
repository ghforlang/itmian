package com.edu.nbu;

import com.alibaba.fastjson.JSON;

public class PrintUtils {

    public static void print(Object obj){
        System.out.println(JSON.toJSONString(obj));
    }


    public static String toStr(Object obj){
        return JSON.toJSONString(obj);
    }
}
