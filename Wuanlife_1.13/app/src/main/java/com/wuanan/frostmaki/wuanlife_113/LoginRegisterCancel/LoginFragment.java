package com.wuanan.frostmaki.wuanlife_113.LoginRegisterCancel;

import android.app.Fragment;
import android.app.FragmentManager;
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
public class LoginFragment extends Fragment implements View.OnClickListener{

    private EditText mail;
    private EditText password;
    private Button register;
    private Button forgetpassword;
    private Button login;
    private Boolean isLogin=false;

    private View view;
    private Context mContext;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0 :
                    Toast.makeText(mContext,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                   getActivity().finish();
                    break;
                case 1://登陆失败
                    Toast.makeText(mContext,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    isLogin=false;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_login,container,false);
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
                String Pr_URL = "http://" + ApiHost + "/?service=User.Login" + "&Email=" + mail.getText().toString()
                        + "&password=" + password.getText().toString();
                resultData = Http_Url.getUrlReponse(Pr_URL);


                if (resultData != null) {
                    JSONObject jsonObject = null;
                    try {
                        String msg = null;
                        jsonObject = new JSONObject(resultData);
                        JSONObject data = jsonObject.getJSONObject("data");
                        int code = data.getInt("code");
                        if (code == 1) {
                            JSONObject info = data.getJSONObject("info");
                            String nickname = info.getString("nickname");
                            int userID = info.getInt("userID");
                            String email = info.getString("Email");
                            ArrayList<HashMap<String, String>> loginInfo = new ArrayList<HashMap<String, String>>();
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("nickname", nickname);
                            map.put("userID", userID + "");
                            map.put("Email", email);
                            loginInfo.add(map);
                            MyApplication.setisLogin(true);
                            MyApplication.setUserInfo(loginInfo);

                            msg = "登陆成功";
                            Message message = new Message();
                            message.what = 0;
                            message.obj=msg;
                            handler.sendMessage(message);
                        } else {
                            msg = data.getString("msg");
                            Message message = new Message();
                            message.what = 1;
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

        mail= (EditText) view.findViewById(R.id.mail);
        password= (EditText)view. findViewById(R.id.password);
        register= (Button) view.findViewById(R.id.register);
        forgetpassword= (Button) view.findViewById(R.id.forgetpassword);
        login= (Button)view. findViewById(R.id.login);

        register.setOnClickListener(this);
        forgetpassword.setOnClickListener(this);
        login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register :
                /*
                转到注册
                 */
                BaseActivity.setToolbar_title("注册");
                Fragment fragment_register=new RegisterFragment();
                FragmentManager fm=getFragmentManager();
                fm.beginTransaction().replace(R.id.content_frame, fragment_register).addToBackStack(null).commit();
                break;
            case R.id.forgetpassword :
                Fragment fragment_repsw=new RepswFragment();
                FragmentManager fm_repsw=getFragmentManager();
                fm_repsw.beginTransaction().replace(R.id.content_frame,fragment_repsw).commit();
                break;
            case R.id.login :
                if (isLogin==false) {
                    getRes();
                }
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
