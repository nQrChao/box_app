package com.zqhy.app.network.utils;

import java.util.Comparator;

/**
 * @author Administrator
 */
public class MapKeyComparator implements Comparator<String>{
    @Override
    public int compare(String str1, String str2) {
        return str1.compareTo(str2);  
    }  
}  
