package com.wuanan.frostmaki.wuanlife_app;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class My_Planet_Fragment extends Fragment{
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.my_planet,container,false);
        textView= (TextView) view.findViewById(R.id.textview1);
        String text=getArguments().getString("text");
        textView.setText("你好，这里是"+text);
        return view;
    }
}
