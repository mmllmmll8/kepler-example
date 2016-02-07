package com.example.mulewen.newkepler.framework;

import android.content.Context;
import com.example.mulewen.newkepler.object.LBSInfo;
import com.example.mulewen.newkepler.object.POI_Info;
import com.example.mulewen.newkepler.object.REC_Info;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mulewen on 16/2/3.
 */
public class Records_info_mid implements Middledata{

    ArrayList<REC_Info> recinfos = null;
    ArrayList<REC_Info> nrecinfos = null;
    private static Records_info_mid records_info_mid = null;
    private Context context = null;
    private boolean rechaschange = false;
    private boolean nrechaschange = false;

    public static Records_info_mid getpoiinfomid(Context context){
        if(records_info_mid==null){
            return new Records_info_mid(context);
        }else {
            return records_info_mid;
        }
    }

    private Records_info_mid(Context context){
        this.context = context;
        recinfos = new ArrayList<REC_Info>();
        nrecinfos = new ArrayList<REC_Info>();
        ini();
    }

    public ArrayList<REC_Info> getRecinfos(){
        return recinfos;
    }

    public ArrayList<REC_Info> getNrecinfos(){
        return nrecinfos;
    }

    @Override
    public void update() {

        if (rechaschange==true){
            //新增rec原生数据存入sharepreference
            JSONArray jrecs = new JSONArray();
            for (REC_Info rec:recinfos
                 ) {
                JSONObject jrec = new JSONObject();
                JSONArray jpois = new JSONArray();
                JSONArray jlbss = new JSONArray();
                try {
                    for (POI_Info poi:rec.pois
                         ) {
                        JSONObject jpoi = new JSONObject();
                        jpoi.put("type",poi.type);
                        jpoi.put("id",poi.id);
                        jpoi.put("lat",poi.lat);
                        jpoi.put("lng",poi.lng);
                        jpoi.put("name",poi.name);
                        jpois.put(jpoi);
                    }
                    for (LBSInfo lbs:rec.lbss
                         ) {
                        JSONObject jlbs = new JSONObject();
                        jlbs.put("lng",lbs.lng);
                        jlbs.put("lat",lbs.lat);
                        jlbss.put(jlbs);
                    }
                    jrec.put("lbsinfo",jlbss);
                    jrec.put("poiinfo",jpois);
                    jrec.put("userid",rec.userid);
                    jrec.put("date",rec.date);
                    jrecs.put(jrec);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            ArrayList<HashMap> update = new ArrayList<HashMap>();
            HashMap<String,String> hasmap = new HashMap<String, String>();
            hasmap.put("name","records");
            hasmap.put("value",jrecs.toString());
            update.add(hasmap);
            Datacenter.getDatacenter(context).getshared().Savedata(update,"exam");
            rechaschange = false;
        }
        if(nrechaschange==true){
            //新增nrec原生数据存入
            JSONArray njrecs = new JSONArray();
            for (REC_Info rec:nrecinfos
                    ) {
                JSONObject jrec = new JSONObject();
                JSONArray jarray = new JSONArray();
                try {
                    for (POI_Info poi:rec.pois
                            ) {
                        JSONObject jpoi = new JSONObject();
                        jpoi.put("type",poi.type);
                        jpoi.put("id",poi.id);
                        jpoi.put("lat",poi.lat);
                        jpoi.put("lng",poi.lng);
                        jpoi.put("name",poi.name);
                        jarray.put(jpoi);
                    }
                    jrec.put("poiinfo",jarray);
                    jrec.put("userid",rec.userid);
                    jrec.put("date",rec.date);
                    njrecs.put(jrec);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            ArrayList<HashMap> update = new ArrayList<HashMap>();
            HashMap<String,String> hasmap = new HashMap<String, String>();
            hasmap.put("name","nrecs");
            hasmap.put("value",njrecs.toString());
            update.add(hasmap);
            Datacenter.getDatacenter(context).getshared().Savedata(update,"exam");
            nrechaschange = false;
        }
    }

    public void addnrecs(REC_Info nrec){
        nrechaschange = true;
        if(nrecinfos==null) {
            nrecinfos = new ArrayList<REC_Info>();
        }
        nrecinfos.add(nrec);
    }

    public void addrecs(REC_Info rec){
        rechaschange = true;
        if(recinfos==null) {
            recinfos = new ArrayList<REC_Info>();
        }
        recinfos.add(rec);
    }

    @Override
    public void ini() {
        inirecinfos("records");
        inirecinfos("nrecs");
    }

    private void inirecinfos(String valuename){
        Datacenter datacenter = Datacenter.getDatacenter(this.context);
        Datashare datashare = datacenter.getshared();
        String records = datashare.getstring("exam",valuename);
        JSONArray jrecords = null;
        REC_Info recinfo = null;
        try {
            if(!records.equalsIgnoreCase("")){
                jrecords = new JSONArray(records);
                for(int i = 0;i<jrecords.length();i++){
                    recinfo = new REC_Info();
                    JSONObject jobject = jrecords.getJSONObject(i);
                    ArrayList<POI_Info> pois = new ArrayList<POI_Info>();
                    ArrayList<LBSInfo> lbss = new ArrayList<LBSInfo>();
                    recinfo.date = jobject.getString("date");
                    recinfo.userid = jobject.getString("userid");
                    JSONArray jpois = jobject.getJSONArray("poiinfo");
                    JSONArray jlbss = jobject.getJSONArray("lbsinfo");
                    for (int z = 0;z<jlbss.length();z++){
                        LBSInfo lbs = new LBSInfo();
                        JSONObject jlbs = jlbss.getJSONObject(z);
                        lbs.lat = jlbs.getDouble("lat");
                        lbs.lng = jlbs.getDouble("lng");
                        lbss.add(lbs);
                    }
                    recinfo.lbss = lbss;

                    for (int z = 0;z<jpois.length();z++){
                        POI_Info poi = new POI_Info();
                        JSONObject jpoi = jpois.getJSONObject(z);
                        poi.id = jpoi.getString("id");
                        poi.lat = jpoi.getDouble("lat");
                        poi.lng = jpoi.getDouble("lng");
                        poi.type = jpoi.getString("type");
                        poi.name = jpoi.getString("name");
                        pois.add(poi);
                    }
                    recinfo.pois = pois;
                    if(valuename=="records"){
                        recinfos.add(recinfo);
                    }else {
                        nrecinfos.add(recinfo);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
