package com.way.tunnelvision.util;

/**
 * Created by pc on 2016/1/13.
 */
public class TextCustomUtil {
    public static String getInitialOfString(String nameStr){
        String initStr = "";
        if(nameStr == null || nameStr.length() <= 0){

        }else {
            initStr = nameStr.substring(0, 1);
        }
        return initStr;
    }
}
