package com.ablingbling.app.tsmartrefresh;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xukui on 2018/5/8.
 */
public class DataHelper {

    public static List<String> getData(int size) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            list.add(System.currentTimeMillis() + "_" + i);
        }

        return list;
    }

}
