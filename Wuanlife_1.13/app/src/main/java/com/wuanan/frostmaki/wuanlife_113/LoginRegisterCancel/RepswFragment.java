package com.wuanan.frostmaki.wuanlife_113.LoginRegisterCancel;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wuanan.frostmaki.wuanlife_113.R;
import com.wuanan.frostmaki.wuanlife_113.Utils.Http_Url;
import com.wuanan.frostmaki.wuanlife_113.Utils.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Frostmaki on 2016/10/6.
 */
public class RepswFragment extends Fragment implements View.OnClickListener{
    private EditText mail;
    private EditText password;
    private EditText psw;
    private EditText code;
    private Button sendMail;
    private Button send;
    View view;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    Toast.makeText(getActivity().getApplicationContext(),msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    getActivity().finish();
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_repsw,container,false);
        initView();
        return view;
    }

    private void initView() {
        mail= (EditText) view.findViewById(R.id.mail);
        password= (EditText) view.findViewById(R.id.password);
        psw= (EditText) view.findViewById(R.id.psw);
        code= (EditText) view.findViewById(R.id.code);
        sendMail= (Button) view.findViewById(R.id.sendMail);
        send= (Button) view.findViewById(R.id.send);

        send.setOnClickListener(this);
        sendMail.setOnClickListener(this);
        BaseActivity.setToolbar_title("忘记密码");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sendMail :
                getCodeMethod();
                break;
            case R.id.send :
                getRepswMethod();
                break;
        }
    }

    private void getCodeMethod(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String ApiHost= MyApplication.getApiHost();
                String Pr_URL = "http://" + ApiHost + "/?service=User.SendMail" + "&Email=" + mail.getText().toString()
                        ;
                String resultData = Http_Url.getUrlReponse(Pr_URL);
                if (resultData!=null){
                    try {
                        String msg=null;
                        JSONObject jsonobject=new JSONObject(resultData);
                        if (jsonobject.getInt("ret")==200){
                            int code=jsonobject.getJSONObject("data").getInt("code");
                            msg=jsonobject.getJSONObject("data").getString("msg");
                            if (code==1){

                            }
                        }else {
                            msg=jsonobject.getString("msg");
                        }
                        Message message=new Message();
                        message.obj=msg;
                        message.what=0;
                        handler.sendMessage(message);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    private void getRepswMethod(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String ApiHost= MyApplication.getApiHost();
                String Pr_URL = "http://" + ApiHost + "/?service=User.RePsw" + "&Email=" + mail.getText().toString()
                        +"&code="+code.getText().toString()
                        +"&password="+password.getText().toString()
                        +"&psw="+psw.getText().toString()
                        ;
                String resultData = Http_Url.getUrlReponse(Pr_URL);
                if (resultData!=null){
                    try {
                        String msg=null;
                        Message message=new Message();
                        message.obj=msg;
                        JSONObject jsonobject=new JSONObject(resultData);
                        if (jsonobject.getInt("ret")==200){
                            int code=jsonobject.getJSONObject("data").getInt("code");
                            msg=jsonobject.getJSONObject("data").getString("msg");
                            if (code==1){
                                message.obj=msg;
                                message.what=1;
                                handler.sendMessage(message);
                            }else {
                                message.obj=msg;
                                message.what=0;
                                handler.sendMessage(message);
                            }
                        }else {
                            msg=jsonobject.getString("msg");
                            message.what=0;
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
}
