package com.example.mulewen.newkepler.runnables;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.example.mulewen.newkepler.framework.LBS_info_mid;
import com.example.mulewen.newkepler.object.LBSInfo;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mulewen on 16/2/16.
 */
public class lbss_runnable implements Runnable{
    Context context = null;
    String content = "";
    ArrayList<LBSInfo> lbsinfos = null;

    public lbss_runnable(Context context){
        this.context = context;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        String content = context.getSharedPreferences("exam",0).getString("nrecs","");

        if(content==""){
            Log.e("records", "void");
        }else{
            Log.e("records", "sending");
            try {
                HttpClient httpClient=new DefaultHttpClient();
                HttpPost post = new HttpPost("http://121.42.136.178:8080/kepler-server/SendData");
                List<NameValuePair> formparams = new ArrayList<NameValuePair>();
                formparams.add(new BasicNameValuePair("data", content));
                formparams.add(new BasicNameValuePair("type", "lbs"));
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,"utf-8");
                entity.setContentType("application/x-www-form-urlencoded;charset=utf-8");
                post.setEntity(entity);
                HttpResponse response=httpClient.execute(post);
                if(response.getStatusLine().getStatusCode()==200)
                {
                    lbsinfos = LBS_info_mid.getlbsinfomid(context).getLbsInfos();
                    lbsinfos = new ArrayList<LBSInfo>();
                    SharedPreferences share = context.getSharedPreferences("exam",0);
                    SharedPreferences.Editor editer = share.edit();
                    editer.putString("lbss", "");
                    editer.commit();
                }
                Log.e("haha",String.valueOf(response.getStatusLine().getStatusCode()));
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                Log.e("haha", e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
