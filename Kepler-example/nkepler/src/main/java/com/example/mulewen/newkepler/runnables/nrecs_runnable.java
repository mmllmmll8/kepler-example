package com.example.mulewen.newkepler.runnables;

import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.content.SharedPreferences;
import android.content.Context;
import com.example.mulewen.newkepler.framework.Records_info_mid;
import com.example.mulewen.newkepler.object.REC_Info;

/**
 * Created by mulewen on 16/2/12.
 */
public class nrecs_runnable implements Runnable{

    Context context = null;
    String content = "";
    ArrayList<REC_Info> nrecinfos = null;

    public nrecs_runnable(Context context){
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
                formparams.add(new BasicNameValuePair("type", "record"));
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,"utf-8");
                entity.setContentType("application/x-www-form-urlencoded;charset=utf-8");
                post.setEntity(entity);
                HttpResponse response=httpClient.execute(post);
                if(response.getStatusLine().getStatusCode()==200)
                {
                    nrecinfos = Records_info_mid.getpoiinfomid(context).getNrecinfos();
                    nrecinfos = new ArrayList<REC_Info>();
                    SharedPreferences share = context.getSharedPreferences("exam",0);
                    SharedPreferences.Editor editer = share.edit();
                    editer.putString("nrecs", "");
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
