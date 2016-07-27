package com.wuanan.frostmaki.wuanlife_app.Nothing;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuanan.frostmaki.wuanlife_app.R;

/**
 * Created by Frostmaki on 2016/7/25.
 */
public class Nothing_Fragment extends Fragment {
    private TextView name;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.nothing,container,false);
        name= (TextView) view.findViewById(R.id.name);
        String Name=getArguments().getString("Name");
        name.setText(Name);
        return view;
    }
}
