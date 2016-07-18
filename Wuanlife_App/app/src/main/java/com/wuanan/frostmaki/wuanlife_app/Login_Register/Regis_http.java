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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Frostmaki on 2016/7/18.
 */
public class Regis_http extends AsyncTask<String,String,String>{
    private String nickname;
    private String mail;
    private String password;
    private Context mContext;
    private Button login_third;
    private String ApiHost;
    private Activity activity;

    public Regis_http(Button button,Activity activity,Context context){
        login_third=button;
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
        String Pr_URL=ApiHost+"=User.Reg";
        try {
            String Real_URL = Pr_URL
                    + "&Email=" +mail
                    + "&nickname="+nickname
                    + "&password=" + password;
            Log.e( "doInBackground: ", Real_URL);

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
                login_third.setText(nickname);

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
