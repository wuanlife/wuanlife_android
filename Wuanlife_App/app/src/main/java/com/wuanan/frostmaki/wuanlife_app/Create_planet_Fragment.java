package com.wuanan.frostmaki.wuanlife_app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wuanan.frostmaki.wuanlife_app.All_planet.All_planet_Fragment;

/**
 * Created by Frostmaki on 2016/7/16.
 */
public class Create_planet_Fragment extends Fragment{
    private Button button;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.create_planet,container,false);
        button= (Button) view.findViewById(R.id.create_planet_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment all_planet_Fragment = new All_planet_Fragment();
                FragmentManager all_planet_fm = getFragmentManager();
                all_planet_fm.beginTransaction().replace(R.id.content_frame, all_planet_Fragment).commit();
            }
        });
        return view;
    }
}
