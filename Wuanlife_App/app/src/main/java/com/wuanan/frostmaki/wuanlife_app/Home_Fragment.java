package com.wuanan.frostmaki.wuanlife_app;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Home_Fragment extends Fragment{
    private TextView mTextView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.home,container,false);
        mTextView= (TextView) view.findViewById(R.id.home_text);
        String text=getArguments().getString("text");
        mTextView.setText(text);
        return view;
    }
}
