package com.wuanan.frostmaki.wuanlife_app.Login_Register;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.wuanan.frostmaki.wuanlife_app.Home.Home_Fragment;
import com.wuanan.frostmaki.wuanlife_app.MyApplication;
import com.wuanan.frostmaki.wuanlife_app.R;
import com.wuanan.frostmaki.wuanlife_app.Utils.Http_Url;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Frostmaki on 2016/7/18.
 */
public class Regis_http extends AsyncTask<String,String,String>{
    private String nickname;
    private String mail;
    private String password;
    private Context mContext;
    private Button login_third;
    private Button regis_third;
    private String ApiHost;
    private Activity activity;

    public Regis_http(Button button1,Button button2,Activity activity,Context context){
        login_third=button1;
        regis_third=button2;
        this.activity=activity;
        mContext=context;
        ApiHost= MyApplication.getUrl();
    }

    @Override
    protected String doInBackground(String... params) {
        nickname=params[0];
        mail=params[1];
        password=params[2];
        BufferedReader reader;
        String Pr_URL="http://"+ApiHost+"/?service=User.Reg";
        String Real_URL = Pr_URL
                + "&Email=" +mail
                + "&nickname="+nickname
                + "&password=" + password;
        Log.e( "注册URL ", Real_URL);
        String resultData= Http_Url.getUrlReponse(Real_URL);

        return resultData;
    }

    @Override
    protected void onPostExecute(String s) {
        String msg="";
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONObject data=jsonObject.getJSONObject("data");
            msg=data.getString("msg");
            int code=data.getInt("code");
            if (code==1){
                JSONObject info=data.getJSONObject("info");
                String nickname=info.getString("nickname");
                int userId=info.getInt("userID");
                String email=info.getString("Email");
                ArrayList<HashMap<String,String>> regisInfo=new ArrayList<HashMap<String,String>>();
                HashMap<String,String> map=new HashMap<String, String>();
                map.put("nickname",nickname);
                map.put("userID",userId+"");
                map.put("Email",email);
                regisInfo.add(map);
                MyApplication.setUserInfo(regisInfo);

                login_third.setText(nickname);

                regis_third.setText("注销");

                initContentView();
            }else if (code==0){

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
        }
    }
    private void initContentView(){
        Fragment fragment = new Home_Fragment();

        FragmentManager home_fm = activity.getFragmentManager();
        home_fm.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }
}
