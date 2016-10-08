package com.wuanan.frostmaki.wuanlife_113.LoginRegisterCancel;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuanan.frostmaki.wuanlife_113.MyGroup.MyJoinCreateFragment;
import com.wuanan.frostmaki.wuanlife_113.R;

/**
 * Created by Frostmaki on 2016/9/24.
 */
public class BaseActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private static TextView toolbar_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initToolbar();
        int code=1;
        code=getIntent().getIntExtra("code",1);
        switch (code){
            case 1:
                toolbar_title.setText("登陆");
                Fragment fragment_login=new LoginFragment();
                FragmentManager fm=getFragmentManager();
                fm.beginTransaction().replace(R.id.content_frame, fragment_login).addToBackStack(null).commit();
                break;
            case 2:
                toolbar_title.setText("注册");
                Fragment fragment_register=new RegisterFragment();
                FragmentManager fm_2=getFragmentManager();
                fm_2.beginTransaction().replace(R.id.content_frame, fragment_register).addToBackStack(null).commit();
                break;
            case 3:
                toolbar_title.setText("星球列表");
                Fragment fragment_joincreateGroup=new MyJoinCreateFragment();
                FragmentManager fm_joincreateGroup=getFragmentManager();
                fm_joincreateGroup.beginTransaction().replace(R.id.content_frame,fragment_joincreateGroup).commit();
                break;
        }
    }

    private void initToolbar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar_title= (TextView) findViewById(R.id.toolbar_title);

        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_18dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                finish();
            }
        });
    }

    public static TextView getToolbar_title() {
        return toolbar_title;
    }


    public static void setToolbar_title(String title) {
        toolbar_title.setText(title);
    }
}
