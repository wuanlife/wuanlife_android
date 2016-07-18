package com.wuanan.frostmaki.wuanlife_app.Login_Register;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.wuanan.frostmaki.wuanlife_app.MainActivity;
import com.wuanan.frostmaki.wuanlife_app.R;

/**
 * Created by Frostmaki on 2016/7/16.
 */
public class Login_Fragment extends Fragment{
    private EditText login_mail;
    private EditText login_password;
    private Button login_real;
    private View view;
    private String mail;
    private String password;
    private Login_http loginAsyncTask;
    private Button login_Sec;
    private MainActivity activity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.login,container,false);
        login_mail= (EditText) view.findViewById(R.id.login_mail);
        login_password= (EditText) view.findViewById(R.id.login_password);
        login_real= (Button) view.findViewById(R.id.login_real);
        activity=(MainActivity)getActivity();
        login_Sec =activity.login_btn;


        login_real.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mail=login_mail.getText().toString();
                password=login_password.getText().toString();

                loginAsyncTask=new Login_http(login_Sec,activity,getActivity().getApplicationContext());
                loginAsyncTask.execute(mail,password);
            }
        });
        return view;

    }

}
