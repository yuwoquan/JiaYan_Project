package com.example.administrator.jiayan_project.vlayout.homepage;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 功能描述: 存储全局数据的基本类
 */
public class InflateConstants {

    /**
     * inflater 实例
     */
    private static LayoutInflater inflater;


    /**
     * view 实例
     *
     * @param context
     * @param parent
     * @param res
     * @return
     */
    public static View inflate(Context context, ViewGroup parent, int res) {
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        return inflater.inflate(res, parent, false);

    }

}
