package com.example.mulewen.newkepler.LBS;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.amap.api.services.poisearch.PoiSearch.Query;
import com.amap.api.services.poisearch.PoiSearch.SearchBound;
import com.example.mulewen.newkepler.framework.Datacenter;
import com.example.mulewen.newkepler.framework.Datashare;
import com.example.mulewen.newkepler.framework.Records_info_mid;
import com.example.mulewen.newkepler.object.LBSInfo;
import com.example.mulewen.newkepler.object.POI_Info;
import com.example.mulewen.newkepler.object.REC_Info;
import android.content.Context;
import android.util.Log;

public class getpoi {
	public static void start(
			final String date,
			double error,
			LatLonPoint point ,
			String city,
			final Context context){
		Query query = new Query("",
				"050000|060000|070000|080000|090000|100000|120200|190400|" +
				"110000|120000|130000|140000|150000|200000|190000|141200|" +
				"050100|050200|050300|050400|050500|050600|050700|140500|" +
				"050800|050900|060100|060200|060300|060400|060600|060700|190403|" +
				"060800|080100|080200|080300|080500|080600|090100|120300|120303", city);
		Log.e("getpoi","start");
		query.setPageSize(30);
		PoiSearch poisearch = new PoiSearch(context, query);
		poisearch.setOnPoiSearchListener(new OnPoiSearchListener(){
			@Override
			public void onPoiSearched(PoiResult arg0, int arg1) {
				// TODO Auto-generated method stub
				if(arg1 == 0){
					if (arg0 != null&&arg0.getQuery()!=null) {
						List<PoiItem> poiItems = arg0.getPois();
						REC_Info rec_info = new REC_Info();
						Datacenter datacenter = Datacenter.getDatacenter(context);
						Datashare share = datacenter.getshared();
						if(poiItems!=null ){
							//生成预存的rec数据
							ArrayList<POI_Info> pois = new ArrayList<POI_Info>();
							Iterator itr = poiItems.iterator();
							//poiinfo
							//填充poi信息
							while (itr.hasNext()) {
								POI_Info poi = new POI_Info();
								PoiItem nextObj = (PoiItem) itr.next();
								poi.lat = nextObj.getLatLonPoint().getLatitude();
								poi.lng = nextObj.getLatLonPoint().getLongitude();
								poi.id = String.valueOf(nextObj.getPoiId());
								try {
									poi.name = URLEncoder.encode(String.valueOf(nextObj.getTitle()),"utf-8");
									poi.type = URLEncoder.encode(String.valueOf(nextObj.getTypeDes()),"utf-8");
								} catch (UnsupportedEncodingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								pois.add(poi);
							}
							rec_info.pois = pois;
							//lbsinfo
							//填充lbs信息
							ArrayList<LBSInfo> lbss = new ArrayList<LBSInfo>();
							ArrayList<String> names = new ArrayList<String>();

							names.add("gaode");
							names.add("baidu");
							names.add("tencent");

							for (String name : names ){
								String lbsinfo = share.getstring("exam", name);
								if(!lbsinfo.equalsIgnoreCase("")){
									LBSInfo lbs = new LBSInfo();
									try {
										lbs.lat = Double.valueOf(new JSONObject(lbsinfo).getString("lat"));
										lbs.lng = Double.valueOf(new JSONObject(lbsinfo).getString("lng"));
										lbss.add(lbs);
									} catch (JSONException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
							}
							rec_info.lbss = lbss;
						}
						if(rec_info.pois.size()!=0){
							rec_info.date = date;
							try {
								rec_info.userid =URLEncoder.encode(share.getstring("exam", "id"),"utf-8");
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						Records_info_mid rec_mid = Records_info_mid.getpoiinfomid(context);
						rec_mid.getRecinfos();
						rec_mid.addrecs(rec_info);
						rec_mid.addnrecs(rec_info);
						rec_mid.update();
					}
				}
			}
		});
		poisearch.setBound(new SearchBound(point, (int) error));
		poisearch.searchPOIAsyn();
	}

}
