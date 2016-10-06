package com.wuanan.frostmaki.wuanlife_113.CreateEditPost;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.wuanan.frostmaki.wuanlife_113.Posts.PostsDetailActivity;
import com.wuanan.frostmaki.wuanlife_113.R;
import com.wuanan.frostmaki.wuanlife_113.Utils.Http_Url;
import com.wuanan.frostmaki.wuanlife_113.Utils.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Frostmaki on 2016/10/1.
 */
public class CreatEditPostActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView toolbar_title;

    private String title;
    private String text;
    private int post_id;
    private String groupName=null;
    private int user_id;
    private int group_id;
    private int code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initView();
        initData();
        initToolbar();
        switch (code){
            case 0 :
                finish();
                break;
            case 1 ://edit
                /*

                 */
                Fragment edit=new EditPostFragmnet();
                FragmentManager fm_edit=getFragmentManager();
                Bundle args=new Bundle();
                post_id=getIntent().getIntExtra("post_id",0);
                title=getIntent().getStringExtra("title");
                text=getIntent().getStringExtra("text");

                args.putInt("group_id",post_id);
                args.putString("title",title);
                args.putString("text",text);
                args.putInt("user_id",user_id);
                edit.setArguments(args);
                fm_edit.beginTransaction().replace(R.id.content_frame,edit).commit();
                break;
            case 2 ://create
                Fragment create=new CreatePostFragment();
                FragmentManager fm_create=getFragmentManager();
                Bundle bundle=new Bundle();
                group_id=getIntent().getIntExtra("group_id",0);
                bundle.putInt("group_id",group_id);
                bundle.putInt("user_id",user_id);
                create.setArguments(bundle);
                fm_create.beginTransaction().replace(R.id.content_frame,create).commit();
        }
    }

    private void initData() {
        code=getIntent().getIntExtra("code",0);
        groupName=getIntent().getStringExtra("groupName");
        //group_id=getIntent().getIntExtra("group_id",0);
        user_id= getIntent().getIntExtra("user_id",0);

    }

    private void initToolbar() {
        toolbar_title.setText(groupName);
        Log.e("groupName",groupName);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_18dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initView() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar_title= (TextView) findViewById(R.id.toolbar_title);

    }


}
