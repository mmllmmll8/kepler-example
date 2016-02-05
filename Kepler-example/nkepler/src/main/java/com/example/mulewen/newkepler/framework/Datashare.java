package com.example.mulewen.newkepler.framework;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mulewen on 16/1/30.
 */
public class Datashare {

    //使用这个工具集保存一些简单的键值对
    private HashMap<String,SharedPreferences> shares = null;
    private SharedPreferences sharedPreferences = null;
    private SharedPreferences.Editor editor = null;

    public Datashare(ArrayList<String> names,Context context){//初始化
        shares = new HashMap<String, SharedPreferences>();
        for (String name:names
             ) {
            shares.put(name,context.getSharedPreferences(name,0));
        }
    }

    public boolean Savedata(ArrayList<HashMap> data,String sharename){
        editor = shares.get(sharename).edit();
        for (HashMap<String ,String> a: data
                ) {
            String name = a.get("name");
            String value = a.get("value");
            editor.putString(name,value);
        }
        return editor.commit();
    }

    public String getstring(String sharename,String name){
        SharedPreferences share = shares.get(sharename);
        return share.getString(name,"");
    }
}
