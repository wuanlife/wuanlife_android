package com.wuanan.frostmaki.wuanlife_app.Login_Register;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.wuanan.frostmaki.wuanlife_app.MainActivity;
import com.wuanan.frostmaki.wuanlife_app.R;

/**
 * Created by Frostmaki on 2016/7/16.
 */
public class Registered_Fragment extends Fragment {
    private EditText nickname;
    private EditText Email;
    private EditText password;
    private Button regis_real;
    private MainActivity activity;
    private Button login_Sec;
    private Button regis_Sec;
    private Regis_http regis_http;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.registered,container,false);
        nickname= (EditText) view.findViewById(R.id.regis_nickname);
        Email= (EditText) view.findViewById(R.id.regis_mail);
        password= (EditText) view.findViewById(R.id.regis_password);
        regis_real= (Button) view.findViewById(R.id.regis_real);
        activity=(MainActivity)getActivity();
        login_Sec=activity.login_btn;
        regis_Sec=activity.registered_btn;

        regis_real.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String nicknameText = nickname.getText().toString();
                    String EmailText = Email.getText().toString();
                    String passwordText = password.getText().toString();
                    regis_http = new Regis_http(login_Sec, regis_Sec, activity, getActivity().getApplicationContext());
                    regis_http.execute(nicknameText, EmailText, passwordText);

            }
        });
        return view;
    }
}
