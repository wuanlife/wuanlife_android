package com.wuanan.frostmaki.wuanlife_app.Login_Register;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wuanan.frostmaki.wuanlife_app.Home.Home_Fragment;
import com.wuanan.frostmaki.wuanlife_app.MainActivity;
import com.wuanan.frostmaki.wuanlife_app.MyApplication;
import com.wuanan.frostmaki.wuanlife_app.R;
import com.wuanan.frostmaki.wuanlife_app.Utils.Http_Url;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;


public class Login_http extends AsyncTask<String,String,String>{
    private String mail;
    private String password;
    private Button login_Third;
    private Button Regis_Third;
    private Context mContext;
    private Activity activity;
    private String ApiHost;

    public Login_http(Button login_sec,Button regis_sec,Activity activity,Context context){
        login_Third=MainActivity.login_btn;
        Regis_Third=MainActivity.registered_btn;
        this.activity=activity;
        mContext=context;
        ApiHost= MyApplication.getUrl();
    }

    @Override
    protected String doInBackground(String... params) {
        mail=params[0];
        password=params[1];
        BufferedReader reader;
        String Pr_URL="http://"+ApiHost+"/?service=User.Login";

        String Real_URL = Pr_URL + "&Email=" +mail
                    + "&password=" + password;
        String resultData= Http_Url.getUrlReponse(Real_URL);

        return resultData;
    }

    @Override
    protected void onPostExecute(String resultData) {

        try {
            JSONObject jsonObject = new JSONObject(resultData);
            JSONObject data=jsonObject.getJSONObject("data");
            int code=data.getInt("code");
            if (code==1){
                JSONObject info=data.getJSONObject("info");
                String nickname=info.getString("nickname");
                int userID=info.getInt("userID");
                String email=info.getString("Email");
                ArrayList<HashMap<String,String>> loginInfo=new ArrayList<HashMap<String,String>>();
                HashMap<String,String> map=new HashMap<String, String>();
                map.put("nickname",nickname);
                map.put("userID",userID+"");
                map.put("Email",email);
                loginInfo.add(map);
                MyApplication.setUserInfo(loginInfo);
                login_Third.setText(nickname);

                Regis_Third.setText("注销");


                Toast.makeText(mContext,"登陆成功",Toast.LENGTH_SHORT).show();
                initContentView();
            }else if (code==0){
                Toast.makeText(mContext,"你输入的用户名或密码错误",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void initContentView(){
        Fragment fragment = new Home_Fragment();

        FragmentManager home_fm = activity.getFragmentManager();
        home_fm.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

}
