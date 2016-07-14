package com.wuanan.frostmaki.wuanlife_app;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class all_planet_Fragment extends Fragment{
    private Button mButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.all_planet,container,false);
        mButton= (Button) view.findViewById(R.id.mjp_btn);
        String text=getArguments().getString("text");
        mButton.setText(text);
        return view;
    }
}
