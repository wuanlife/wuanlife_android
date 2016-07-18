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
import com.wuanan.frostmaki.wuanlife_app.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class Login_http extends AsyncTask<String,String,String>{
    private String mail;
    private String password;
    public Button login_Third;
    private Context mContext;
    private Activity activity;


    public Login_http(Button login_sec,Activity activity,Context context){
        login_Third=login_sec;
        this.activity=activity;
        mContext=context;
    }
    String Pr_URL="http://dev.wuanlife.com:800/?service=User.Login";
    @Override
    protected String doInBackground(String... params) {
        mail=params[0];
        password=params[1];
        BufferedReader reader;
        try {
            String Real_URL = Pr_URL + "&Email=" +mail
                    + "&password=" + password;
            Log.e("Real_URL", 1111+"");

            URL url=new URL(Real_URL);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(8000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "plain/text; charset=UTF-8");
            connection.setRequestMethod("POST");


            InputStream in=connection.getInputStream();
            Log.e( "doInBackground: ",in.toString() );
            reader=new BufferedReader(new InputStreamReader(in));
            Log.e("reader",reader.toString());

            int responseCode=connection.getResponseCode();
            if(responseCode==200) {
                String inputline = null;
                String resultData = null;

                while ((inputline = reader.readLine()) != null) {
                    resultData = inputline + "\n";
                }
                Log.e("result",resultData);
                return resultData;
            }else{
                Log.e("responseCode","ww"+responseCode);
            }

        }catch (Exception e){
            e.printStackTrace();
            Log.e("e",e+"");
        }
        return null;
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
                login_Third.setText(nickname);

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
