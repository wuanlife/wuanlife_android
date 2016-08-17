package com.wuanan.frostmaki.wuanlife_app.Post;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
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
import com.wuanan.frostmaki.wuanlife_app.Post.PostEdit.EditPosts_Fragment;
import com.wuanan.frostmaki.wuanlife_app.R;
import com.wuanan.frostmaki.wuanlife_app.Utils.Http_Url;
import com.wuanan.frostmaki.wuanlife_app.Utils.ImageLoader;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Frostmaki on 2016/7/30.
 */
public class Posts_Fragment extends Fragment implements View.OnClickListener{
    private EditText replyText;
    private Button reply;

    private TextView title;
    private TextView nickname;
    private TextView GroupName;
    private TextView createTime;
    private TextView mainText;
    private ImageView imageView01;
    private ImageView imageView02;
    private ImageView imageView03;
    private String image01=null;
    private String image02=null;
    private String image03=null;

    private Button stickyPost;
    private Button editPost;
    private Button deletePost;
    private int BtnRight=1;//youquanxian
    private boolean isSticky=false;



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
                    //mainText.setText(hashMap01.get("text"));
                    String source=hashMap01.get("text");
                    //Log.e("source",source);
                    //String pattern="<img[^>|/s]?src[=][^>]*[>]";
                    //String pattern="<img.*src=((.*?)[^>]*?)>";
                    String pattern="<img\\ssrc=([^>]*)>";
                    Pattern p=Pattern.compile(pattern);
                    Matcher m = p.matcher(source);
                    Boolean real=source.matches(pattern);

                    System.out.println(m.groupCount());

                    int start=0;
                    int end=0;
                    getTextThread t2=null;
                    while (m.find()){
                        //Log.e("group(0)",m.group(0));
                        //Log.e("group(1)",m.group(1));
                        //Log.e("start()", String.valueOf(m.start()));

                        //Log.e("end()", String.valueOf(m.end()));
                        start=m.start();
                        if ((start-end)==0){

                            end=m.end();
                            t2=new getTextThread(m.group(1));
                            t2.start();
                            //Log.e("无前方","00");

                        }else {

                            String sub=null;
                            sub = source.substring(end, start );

                            if (t2!=null) {
                                try {
                                    //System.out.println(t2.isAlive());
                                    t2.join();
                                    //System.out.println(t2.isAlive());
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }else {

                            }
                            Log.e("sub",sub);
                            Message message=Message.obtain();
                            message.what=6;
                            message.obj=sub;
                            handler.sendMessage(message);
                            //editText2.append(sub);
                            //Log.e("you前方","qianfang");
                            end=m.end();

                            t2=new getTextThread(m.group(1));
                            t2.start();

                            //Log.e("有前方","后方");
                        }

                        //editText2.setText(m.group(1));
                    }
                    //Log.e("reault end", String.valueOf(end));
                    //Log.e("source.length()", String.valueOf(source.length()));
                    if ((source.length())>end){

                        if (t2!=null) {
                            try {
                                //System.out.println(t2.isAlive());
                                t2.join();
                                //System.out.println(t2.isAlive());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }else {
                            //System.out.println(t2.isAlive());
                        }
                        //Log.e("最后","后方");
                        String little=source.substring(end,source.length());
                        Message message=Message.obtain();
                        message.what=6;
                        message.obj=little;
                        handler.sendMessage(message);
                        //editText2.append(little);
                    }
                    //imageView01.setImageURI(hashMap01.get("image"));
                    if (Integer.parseInt(hashMap01.get("editRight"))==BtnRight){
                        editPost.setVisibility(View.VISIBLE);
                    }
                    if (Integer.parseInt(hashMap01.get("deleteRight"))==BtnRight){
                        deletePost.setVisibility(View.VISIBLE);
                    }
                    if (Integer.parseInt(hashMap01.get("stickyRight"))==BtnRight){
                        stickyPost.setVisibility(View.VISIBLE);
                        if (Integer.parseInt(hashMap01.get("sticky"))==BtnRight){
                            isSticky=true;
                            stickyPost.setText("取消置顶");
                        }
                    }

                    if (hashMap01.get("image01")!=null){

                        imageView01.setVisibility(View.VISIBLE);
                        image01=hashMap01.get("image01");
                        imageView01.setTag(image01);
                        new ImageLoader().showImageByThread(imageView01, image01);
                    }
                    if (hashMap01.get("image02")!=null){
                        imageView02.setVisibility(View.VISIBLE);
                        image02=hashMap01.get("image01");
                        imageView02.setTag(image02);
                        new ImageLoader().showImageByThread(imageView02, image02);
                    }
                    if (hashMap01.get("image03")!=null){
                        imageView03.setVisibility(View.VISIBLE);
                        image02=hashMap01.get("image03");
                        imageView03.setTag(image03);
                        new ImageLoader().showImageByThread(imageView03, image03);
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
                    //huitie
                    getRes02();
                    adapter.notifyDataSetChanged();
                    break;
                case 5:
                    //删帖 置顶
                    Toast.makeText(mContext,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                    break;
                case 6:
                    mainText.append((CharSequence) msg.obj);
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
        imageView01= (ImageView) view.findViewById(R.id.postBase_image01);
        imageView02= (ImageView) view.findViewById(R.id.postBase_image02);
        imageView03= (ImageView) view.findViewById(R.id.postBase_image03);

        stickyPost= (Button) view.findViewById(R.id.sticky);
        editPost= (Button) view.findViewById(R.id.edit);
        deletePost= (Button) view.findViewById(R.id.delete);

        stickyPost.setOnClickListener(this);
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
            case R.id.sticky:
                if (isSticky==false){
                    //置顶成功
                    stickyPost();
                    isSticky=true;
                    stickyPost.setText("取消置顶");
                }else {
                    //取消置顶成功
                    UnstickyPost();
                    isSticky=false;
                    stickyPost.setText("置顶");
                }
                break;
            case R.id.edit:
                Bundle bundle=new Bundle();
                bundle.putString("postID",posts_id);
                Log.e("postID",posts_id+"");
                bundle.putString("title",title.getText().toString());
                bundle.putString("text",mainText.getText().toString());
                EditPosts_Fragment editPosts_fragment=new EditPosts_Fragment();
                editPosts_fragment.setArguments(bundle);
                FragmentManager fm=getFragmentManager();
                fm.beginTransaction().replace(R.id.content_frame,editPosts_fragment).addToBackStack(null).commit();
                break;
            case R.id.delete:
                deletePost();
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
                            + "&pn=" + pn;
                    String resultData = Http_Url.getUrlReponse(Pre_URL);
                    arrayList02 = GetPostReply_JSON.getJSONParse(resultData);
                    if (arrayList02 == null) {
                        Log.e("huo获得回复帖子失败", "Posts_Fragment  ");
                    } else {
                        Log.e("获得回复帖子", "Posts_Fragment  "+arrayList02.size());
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
                            Log.e("hui帖子失败", "Posts_Fragment  ");
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

    private void deletePost(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String userID = MyApplication.getUserInfo().get(0).get("userID");

                String ApiHost=MyApplication.getUrl();
                String Pre_URL="http://"+ApiHost+"/?service=Post.DeletePost"
                        +"&user_id="+userID
                        +"&post_id="+posts_id;
                String resultData= Http_Url.getUrlReponse(Pre_URL);
                try {
                    JSONObject jsonObject=new JSONObject(resultData);
                    int code=jsonObject.getJSONObject("data").getInt("code");
                    if (code==1){
                        Message message = new Message();
                        message.what = 5;
                        message.obj="删帖成功";
                        handler.sendMessage(message);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void stickyPost(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String userID = MyApplication.getUserInfo().get(0).get("userID");

                String ApiHost=MyApplication.getUrl();
                String Pre_URL="http://"+ApiHost+"/?service=Post.StickyPost"
                        +"&user_id="+userID
                        +"&post_id="+posts_id;
                String resultData= Http_Url.getUrlReponse(Pre_URL);
                try {
                    JSONObject jsonObject=new JSONObject(resultData);
                    int code=jsonObject.getJSONObject("data").getInt("code");
                    if (code==1){
                        Message message = new Message();
                        message.what = 5;
                        message.obj="z置顶成功";
                        handler.sendMessage(message);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void UnstickyPost(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String userID = MyApplication.getUserInfo().get(0).get("userID");

                String ApiHost=MyApplication.getUrl();
                String Pre_URL="http://"+ApiHost+"/?service=Post.UnStickyPost"
                        +"&user_id="+userID
                        +"&post_id="+posts_id;
                String resultData= Http_Url.getUrlReponse(Pre_URL);
                try {
                    JSONObject jsonObject=new JSONObject(resultData);
                    int code=jsonObject.getJSONObject("data").getInt("code");
                    if (code==1){
                        Message message = new Message();
                        message.what = 5;
                        message.obj="z取消置顶成功";
                        handler.sendMessage(message);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    class getTextThread extends Thread {
        String str = null;

        public getTextThread(String s) {
            str = s;
        }

        @Override
        public void run() {
            //String s="http://b.hiphotos.baidu.com/image/h%3D360/sign=8918c5efbe3eb1355bc7b1bd961ea8cb/7a899e510fb30f244bb50504ca95d143ad4b038d.jpg";
            try {
                URL url = new URL(str);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                SpannableString spannableString = new SpannableString("<img src=" + str + ">");

                ImageSpan imageSpan = new ImageSpan(mContext,
                        bitmap);

                spannableString.setSpan(imageSpan, 0, spannableString.length(), SpannableString.SPAN_MARK_MARK);


                Message message = new Message();
                message.what = 6;
                message.obj = spannableString;
                handler.sendMessage(message);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
