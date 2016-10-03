package com.wuanan.frostmaki.wuanlife_113.LoginRegisterCancel;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wuanan.frostmaki.wuanlife_113.R;
import com.wuanan.frostmaki.wuanlife_113.Utils.Http_Url;
import com.wuanan.frostmaki.wuanlife_113.Utils.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Frostmaki on 2016/10/3.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener{

    private EditText mail;
    private EditText nickname;
    private EditText password;

    private Button register;

    private View view;
    private Context mContext;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0 :
                    //finish();
                    Toast.makeText(mContext,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    break;
                case 1://注册失败
                    Toast.makeText(mContext,msg.obj.toString(),Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_register,container,false);
        initView();
        initData();

        return view;
    }

    private void initData() {
        mContext=getActivity().getApplicationContext();
    }
    String resultData=null;
    private void getRes() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String ApiHost = MyApplication.getApiHost();
                String Pr_URL = "http://" + ApiHost + "/?service=User.Reg" + "&Email=" + mail.getText().toString()
                        + "&nickname=" + nickname.getText().toString()
                        + "&password=" + password.getText().toString();
                resultData = Http_Url.getUrlReponse(Pr_URL);
        if (resultData!=null){
                try {
                    String msg=null;
                    JSONObject jsonObject = new JSONObject(resultData);
                    JSONObject data=jsonObject.getJSONObject("data");

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

                        msg="注册成功";
                        Message message=new Message();
                        message.what=0;
                        message.obj=msg;
                        handler.sendMessage(message);
                    }else {
                        msg=data.getString("msg");
                        Message message=new Message();
                        message.what=1;
                        message.obj=msg;
                        handler.sendMessage(message);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            }
        }).start();

    }




    private void initView() {

        mail= (EditText)view. findViewById(R.id.mail);
        password= (EditText)view. findViewById(R.id.password);
        nickname= (EditText) view.findViewById(R.id.nickname);
        register= (Button)view. findViewById(R.id.register);

        register.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register :
                getRes();
                break;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    //这里处理返回事件
                    getActivity().finish();
                }
                return false;
            }
        });
    }


}
