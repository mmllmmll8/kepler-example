package com.example.mulewen.newkepler.framework;

import android.content.Context;
import android.content.IntentSender;

import com.example.mulewen.newkepler.object.LBSInfo;
import com.example.mulewen.newkepler.object.POI_Info;
import com.example.mulewen.newkepler.object.REC_Info;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mulewen on 16/2/13.
 */
public class LBS_info_mid implements Middledata{

    Context context = null;
    ArrayList<LBSInfo> lbsInfos = null;
    boolean ischanged = false;

    private LBS_info_mid(Context context){
        this.context = context;
        lbsInfos = new ArrayList<LBSInfo>();
        ini();
    }

    @Override
    public void update() {
        if (ischanged){
            JSONArray jrecs = new JSONArray();
            for (LBSInfo lbs:lbsInfos
                    ) {
                JSONObject jrec = new JSONObject();
                JSONArray jpois = new JSONArray();
                JSONArray jlbss = new JSONArray();
                try {
                    jrec.put("userid",lbs.userid);
                    jrec.put("date",lbs.date);
                    jrec.put("lat",lbs.lat);
                    jrec.put("lng",lbs.lng);
                    jrec.put("accuracy",lbs.accuracy);
                    jrecs.put(jrec);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            ArrayList<HashMap> update = new ArrayList<HashMap>();
            HashMap<String,String> hasmap = new HashMap<String, String>();
            hasmap.put("name","lbss");
            hasmap.put("value",jrecs.toString());
            update.add(hasmap);
            Datacenter.getDatacenter(context).getshared().Savedata(update,"exam");
            ischanged = false;
        }
    }

    public void addlbss(LBSInfo nrec){
        ischanged = true;
        if(lbsInfos==null) {
            lbsInfos = new ArrayList<LBSInfo>();
        }
        lbsInfos.add(nrec);
    }

    @Override
    public void ini() {

    }

    private void inirecinfos(){
        Datacenter datacenter = Datacenter.getDatacenter(this.context);
        Datashare datashare = datacenter.getshared();
        String content = datashare.getstring("exam","lbss");
        JSONArray jlbsinfos = null;
        LBSInfo lbsinfo = null;

    }
}
