package com.wuanan.frostmaki.wuanlife_113.MyGroup;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.wuanan.frostmaki.wuanlife_113.AllGroup.GroupListPosts.GroupPostsActivity;
import com.wuanan.frostmaki.wuanlife_113.R;
import com.wuanan.frostmaki.wuanlife_113.Utils.MyApplication;
import com.wuanan.frostmaki.wuanlife_113.Utils.MyGridView;

/**
 * Created by Frostmaki on 2016/10/7.
 */
public class MyJoinCreateFragment extends Fragment implements AdapterView.OnItemClickListener{
    private MyGridView join;
    private MyJoinCreateGroupAdapter joinAdapter;
    private MyJoinCreateGroupAdapter createAdapter;
    private MyGridView create;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_myjoinandcreate,container,false);
        initView();
        return view;
    }

    private void initView() {
        //headview=LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.fragment_myjoinandcreate,null);

        join= (MyGridView)view.findViewById(R.id.gridview_join);
        create= (MyGridView) view.findViewById(R.id.gridview_create);


        joinAdapter=new MyJoinCreateGroupAdapter(getActivity().getApplicationContext(), MyApplication.getJoinGroupArrayList());
        createAdapter=new MyJoinCreateGroupAdapter(getActivity().getApplicationContext(), MyApplication.getCreateGroupArrayList());

        join.setOnItemClickListener(this);
        create.setOnItemClickListener(this);
        join.setAdapter(joinAdapter);
        create.setAdapter(createAdapter);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("parent",(parent.getId())+"");
        Log.e("parent","111111111");
        switch (parent.getId()){
            case R.id.gridview_join :
                int join_id=MyApplication.getJoinGroupArrayList().get(position).getId();
                String join_name=MyApplication.getJoinGroupArrayList().get(position).getName();
                openThisGroup(join_id,join_name);
                break;
            case R.id.gridview_create :
                int create_id=MyApplication.getCreateGroupArrayList().get(position).getId();
                String create_name=MyApplication.getCreateGroupArrayList().get(position).getName();
                openThisGroup(create_id,create_name);
                break;
        }
    }

    private void openThisGroup(int id,String name) {
        Intent intent=new Intent(getActivity(), GroupPostsActivity.class);
        intent.putExtra("group_id",id);
        intent.putExtra("group_name",name);

        startActivity(intent);
    }
}
