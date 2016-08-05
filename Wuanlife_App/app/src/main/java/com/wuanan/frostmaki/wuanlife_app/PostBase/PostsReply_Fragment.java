package com.wuanan.frostmaki.wuanlife_app.PostBase;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wuanan.frostmaki.wuanlife_app.MyApplication;
import com.wuanan.frostmaki.wuanlife_app.R;
import com.wuanan.frostmaki.wuanlife_app.Utils.Http_Url;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Frostmaki on 2016/7/30.
 */
public class PostsReply_Fragment extends Fragment implements View.OnClickListener{
    private EditText replyText;
    private Button reply;

    private TextView title;
    private TextView nickname;
    private TextView GroupName;
    private TextView createTime;
    private TextView mainText;
    private ImageView imageView01;

    private Button editPost;
    private Button deletePost;
    private String BtnRight=0+"";
    ;


    private ListView PostReplyListview;
    private TextView replyCount;
    private Button pre;
    private Button next;
    private TextView currentPage;

    //private String user_id="";//回帖人ID
    private String text="";//内容
    private String posts_id="";//帖子ID
    private int pn=1;
    private Context mContext;
    private HashMap<String,String> hashMap01=null;
    private ArrayList<HashMap<String,String>> arrayList02=null;
    private HashMap<String,String> hashMap03=null;
    private GetPostReplyAdapter adapter;

    private int NoUserInfo=1;
    private int GetPostBaseInfo=2;
    private int GetPostReplyInfo=3;
    private  int PostReply=4;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    //z转到回复的帖子
                    break;
                case 1:
                    Toast.makeText(mContext,"未登录",Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    //kan帖子成功
                    title.setText(hashMap01.get("title"));
                    nickname.setText(hashMap01.get("nickName"));
                    GroupName.setText(hashMap01.get("groupName"));
                    createTime.setText(hashMap01.get("createTime"));
                    mainText.setText(hashMap01.get("text"));
                    //imageView01.setImageURI(hashMap01.get("image"));
                    if (hashMap01.get("editRight")==BtnRight){
                        editPost.setVisibility(View.VISIBLE);
                    }else if (hashMap01.get("deleteRight")==BtnRight){
                        deletePost.setVisibility(View.VISIBLE);
                    }
                    break;
                case 3:
                    //kan回帖成功
                    ArrayList<HashMap<String,String>> arrayList0202=null;
                    arrayList0202= (ArrayList<HashMap<String, String>>) msg.obj;
                    //String replyCountStr=arrayList0202.get(0).get("replyCount");
                    //replyCount.setText(replyCountStr+" 个回复");
                    //String currentPageStr=arrayList0202.get(0).get("currentPage");
                    //String pageCountStr=arrayList0202.get(0).get("pageCount");
                    //currentPage.setText(currentPageStr+" / " +pageCountStr);

                    adapter=new GetPostReplyAdapter(arrayList0202,mContext,replyCount,currentPage);
                    PostReplyListview.setAdapter(adapter);
                    setListViewHeightBasedOnChildren(PostReplyListview);
                    break;
                case 4:
                    getRes02();
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.postbase,container,false);
        mContext=getActivity().getApplicationContext();
        //user_id=//
         posts_id=getArguments().getString("postID");

        //ti帖子内容
title= (TextView) view.findViewById(R.id.postBase_title);
        nickname= (TextView) view.findViewById(R.id.postBase_nickname);
        GroupName= (TextView) view.findViewById(R.id.postBase_groupName);
        createTime= (TextView) view.findViewById(R.id.postBase_createTime);
        mainText= (TextView) view.findViewById(R.id.postBase_text);
        imageView01= (ImageView) view.findViewById(R.id.postBase_image);

        editPost= (Button) view.findViewById(R.id.edit);
        deletePost= (Button) view.findViewById(R.id.delete);

        editPost.setOnClickListener(this);
        deletePost.setOnClickListener(this);
        //回复ListView
        PostReplyListview= (ListView) view.findViewById(R.id.replyListView);
        replyCount= (TextView) view.findViewById(R.id.replyCount);
        pre= (Button) view.findViewById(R.id.pre);
        currentPage= (TextView) view.findViewById(R.id.currentPage);
        next= (Button) view.findViewById(R.id.next);

        pre.setOnClickListener(this);
        next.setOnClickListener(this);
        //di底部回复

        replyText= (EditText) view.findViewById(R.id.replyEdit);
        reply= (Button) view.findViewById(R.id.reply_btn);

        reply.setOnClickListener(this);

        getRes01();
        getRes02();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit:
                break;
            case R.id.delete:
                break;
            case R.id.pre:
                if (pn>1){
                    --pn;
                    getRes02();
                    adapter.notifyDataSetChanged();
                }else {
                    Log.e("pre","cuowu");
                }
                break;
            case R.id.next:
                if (pn<arrayList02.size()) {
                    ++pn;
                    getRes02();
                    adapter.notifyDataSetChanged();

                }else {
                    Log.e("next","cuowu");
                }
                break;
            case R.id.reply_btn:
                getRes03();
                break;

            default:
                break;
        }
    }

    private void getRes01(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    String ApiHost = MyApplication.getUrl();
                    if (MyApplication.getUserInfo() == null) {
                        Message message = new Message();
                        message.what = NoUserInfo;

                        handler.sendMessage(message);
                    } else {
                        String id = MyApplication.getUserInfo().get(0).get("userID");
                        String Pre_URL = "http://" + ApiHost + "/?service=Post.GetPostBase"
                                + "&post_id=" + posts_id
                                + "&id=" + id;
                        String resultData = Http_Url.getUrlReponse(Pre_URL);
                        hashMap01 = GetPostBase_JSON.getJSONParse(resultData);
                        if (hashMap01 == null) {
                            Log.e("kan帖子失败", "PostBase_Fragment  ");
                        } else {
                            Message message = new Message();
                            message.what = GetPostBaseInfo;
                            message.obj = hashMap01;
                            handler.sendMessage(message);
                        }

                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Log.e("PostsBase.Thread异常",e+"");
                }
            }
        }).start();
    }

    private void getRes02(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String ApiHost = MyApplication.getUrl();
                    String Pre_URL = "http://" + ApiHost + "/?service=Post.GetPostReply"
                            + "&post_id=" + posts_id
                            + "pn" + pn;
                    String resultData = Http_Url.getUrlReponse(Pre_URL);
                    arrayList02 = GetPostReply_JSON.getJSONParse(resultData);
                    if (arrayList02 == null) {
                        Log.e("huo获得回复帖子失败", "PostsReply_Fragment  ");
                    } else {
                        Log.e("获得回复帖子", "PostsReply_Fragment  "+arrayList02.size());
                        Message message = new Message();
                        message.what = GetPostReplyInfo;
                        message.obj = arrayList02;
                        handler.sendMessage(message);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void getRes03(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    text=replyText.getText().toString();
                    if (MyApplication.getUserInfo() == null) {
                        Message message = new Message();
                        message.what = NoUserInfo;

                        handler.sendMessage(message);
                    } else {
                        String user_id = MyApplication.getUserInfo().get(0).get("userID");
                        String ApiHost = MyApplication.getUrl();
                        String Pre_URL = "http://" + ApiHost + "/?service=Post.PostReply"
                                + "&post_id=" + posts_id
                                + "&text=" + text
                                + "&user_id=" + user_id;

                        String resultData = Http_Url.getUrlReponse(Pre_URL);
                        hashMap03 = PostsReply_JSON.getJSONParse(resultData);
                        if (hashMap03 == null) {
                            Log.e("hui帖子失败", "PostsReply_Fragment  ");
                        } else {
                            Message message = new Message();
                            message.what = 4;
                            message.obj = hashMap03;
                            handler.sendMessage(message);
                        }
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    Log.e("PostsReply.Thread异常",e+"");
                }
            }
        }).start();
    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}
