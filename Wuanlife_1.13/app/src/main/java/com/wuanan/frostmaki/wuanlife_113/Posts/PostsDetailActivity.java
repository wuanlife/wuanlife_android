package com.wuanan.frostmaki.wuanlife_113.Posts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wuanan.frostmaki.wuanlife_113.CreateEditPost.CreatEditPostActivity;
import com.wuanan.frostmaki.wuanlife_113.MyGroup.MyGroupJson;
import com.wuanan.frostmaki.wuanlife_113.NewView.PostDetailsClass;
import com.wuanan.frostmaki.wuanlife_113.R;
import com.wuanan.frostmaki.wuanlife_113.Utils.Http_Url;
import com.wuanan.frostmaki.wuanlife_113.Utils.ImageLoader;
import com.wuanan.frostmaki.wuanlife_113.Utils.MyApplication;
import com.wuanan.frostmaki.wuanlife_113.Utils.Postlist;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Frostmaki on 2016/9/30.
 */
public class PostsDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;
    private TextView toolbar_title;
    private EditText replyText;
    private ImageButton reply;

    private TextView title;
    private TextView nickname;
    private TextView GroupName;
    private TextView createTime;
    private TextView mainText;
    private LinearLayout postDetail_image_linear;



    private int ItemRight=1;//1 有权限  0无权限
    private boolean isSticky=false;  //未置顶， 点击要求置顶
    private boolean isLogin=false;



    private ListView PostReplyListview;
    private TextView replyCount;
    private Button pre;
    private Button next;
    private TextView currentPage;



    //private String user_id="";//回帖人ID
    private String text="";//内容
    private int posts_id=0;//帖子ID
    private String groupName=null;
    private String id=null;

    private int pn=1;
    private Context mContext;
    private PostDetailsClass postDetail_Base=null;
    private ArrayList<HashMap<String,String>> arrayList02=null;
    private HashMap<String,String> hashMap03=null;
    private GetPostReplyAdapter adapter;

    private int TOASTMESSAGE=1;
    private int GetPostBaseInfo=2;
    private int GetPostReplyInfo=3;
    private  int PostReply=4;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //z转到回复的帖子
                    break;
                case 1:
                    Toast.makeText(mContext, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    //kan帖子成功
                    title.setText(postDetail_Base.getTitle());
                    nickname.setText(postDetail_Base.getNickname());
                    GroupName.setText(postDetail_Base.getGroupName());
                    createTime.setText(postDetail_Base.getCreateTime());
                    //mainText.setText(hashMap01.get("text"));

                    String source =postDetail_Base.getText();
                    handleText(source);

                    /*
                    处理Image
                     */
                 /*   if (postDetail_Base.getImage().size()>0){
                        for (int i=0;i<postDetail_Base.getImage().size();i++){
                            ImageView imageView=new ImageView(PostsDetailActivity.this);
                            String urlTag=postDetail_Base.getImage().get(i);
                            imageView.setTag(urlTag);
                            new ImageLoader().showImageByThread(imageView,urlTag);
                        }
                    }*/
                    break;
                case 3:
                    //kan回帖成功
                    ArrayList<HashMap<String,String>> arrayList0202=null;
                    arrayList0202= (ArrayList<HashMap<String, String>>) msg.obj;


                    adapter=new GetPostReplyAdapter(arrayList0202,mContext,replyCount,currentPage);
                    PostReplyListview.setAdapter(adapter);
                    setListViewHeightBasedOnChildren(PostReplyListview);
                    break;
                case 4:
                    //huitie
                    getPostReply();
                    adapter.notifyDataSetChanged();
                    break;
                case 5:
                    //删帖 置顶
                    Toast.makeText(mContext,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    if (msg.getData().getInt("code")==1) {
                        if (isSticky == false) {

                            isSticky = true;
                            toolbar.getMenu().findItem(R.id.Item_stick).setTitle("取消置顶");
                        } else {

                            isSticky = false;
                            toolbar.getMenu().findItem(R.id.Item_stick).setTitle("置顶");
                        }
                    }

                    break;
                case 6:
                    mainText.append((CharSequence) msg.obj);
                    break;
                case 7 :
                    Toast.makeText(mContext,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    if (msg.getData().getInt("code")==1) {
                        finish();
                    }
                    break;
            }
        }
    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.postsdetails_menu,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postbase);


        initView();
        initToolbar();

        getPostBase();
        getPostReply();

        setOverflowShowingAlways();
    }

    private void initToolbar() {
        toolbar_title.setText(groupName);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_18dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                xiao销毁当前活动，返回上一级活动
                 */
                finish();
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Item_delete :
                        /*
                        删除帖子
                         */
                        if (isLogin==true) {
                            delete_Post("Post.DeletePost");
                        }else {
                            Message message = new Message();
                            message.what = TOASTMESSAGE;
                            message.obj="请先登录";
                            handler.sendMessage(message);
                        }
                        break;
                    case R.id.Item_edit :
                        /*
                        跳转到发帖页面
                        1.传送————>标题  内容  用户ID  帖子ID
                         */
                        Intent intent=new Intent(PostsDetailActivity.this, CreatEditPostActivity.class);
                        intent.putExtra("code",1);//d代表是编辑
                        intent.putExtra("groupName",postDetail_Base.getGroupName());

                        intent.putExtra("post_id",postDetail_Base.getPostID());
                        intent.putExtra("user_id",postDetail_Base.getId());
                        intent.putExtra("title",postDetail_Base.getTitle());
                        intent.putExtra("text",postDetail_Base.getText());
                        startActivity(intent);
                        break;
                    case R.id.Item_stick :
                    if (isLogin==true){
                        if (isSticky==false){
                            //未置顶，点击要求置顶
                            // ————>成功后,isSticky=true;
                            // ——==>menu.findItem(R.id.Item_sticky).setTitle("取消置顶")
                            sticky_Post("Post.StickyPost");

                        }else {
                            //置顶，点击要求取消置顶
                            // ————>成功后,isSticky=false;
                            // ——==>menu.findItem(R.id.Item_sticky).setTitle("置顶")
                            sticky_Post("Post.UnStickyPost");

                        }
                }else {
                    Message message = new Message();
                    message.what = TOASTMESSAGE;
                    message.obj="请先登录";
                    handler.sendMessage(message);
                }
                        break;
                }
                return true;
            }
        });

    }

    private void delete_Post(String s) {
        final String link=s;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String userID = MyApplication.getUserInfo().get(0).get("userID");

                String ApiHost=MyApplication.getApiHost();
                String Pre_URL="http://"+ApiHost+"/?service="+link
                        +"&user_id="+userID
                        +"&post_id="+posts_id;
                String resultData= Http_Url.getUrlReponse(Pre_URL);
                try {
                    JSONObject jsonObject=new JSONObject(resultData);
                    int code=jsonObject.getJSONObject("data").getInt("code");
                    Message message = new Message();
                    message.what = 7;
                    message.obj=jsonObject.getJSONObject("data").getString("re");
                    if (code==1){
                        Bundle bundle=new Bundle();
                        bundle.putInt("code",1);
                        message.setData(bundle);
                    }else {
                        Bundle bundle=new Bundle();
                        bundle.putInt("code",0);
                        message.setData(bundle);
                    }
                    handler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void sticky_Post(String s) {
        final String link=s;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String userID = MyApplication.getUserInfo().get(0).get("userID");

                String ApiHost=MyApplication.getApiHost();
                String Pre_URL="http://"+ApiHost+"/?service="+link
                        +"&user_id="+userID
                        +"&post_id="+posts_id;
                String resultData= Http_Url.getUrlReponse(Pre_URL);
                try {
                    JSONObject jsonObject=new JSONObject(resultData);
                    int code=jsonObject.getJSONObject("data").getInt("code");
                    Message message = new Message();
                    message.what = 5;
                    message.obj=jsonObject.getJSONObject("data").getString("re");
                    if (code==1){
                        Bundle bundle=new Bundle();
                        bundle.putInt("code",1);
                        message.setData(bundle);
                    }else {
                        Bundle bundle=new Bundle();
                        bundle.putInt("code",0);
                        message.setData(bundle);
                    }
                    handler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /*
     *she设置overflow menu显示
     */
    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField!=null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        mContext=PostsDetailActivity.this;
        //user_id=//
        if (MyApplication.getUserInfo()!=null){
            id=MyApplication.getUserInfo().get(0).get("userID");
            isLogin=true;
        }
        posts_id=getIntent().getIntExtra("postID",0);//getArguments().getString("postID");
        groupName=getIntent().getStringExtra("groupName");

        //toolbar
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar_title= (TextView) findViewById(R.id.toolbar_title);

        //ti帖子内容
        title= (TextView) findViewById(R.id.postBase_title);
        nickname= (TextView) findViewById(R.id.postBase_nickname);
        GroupName= (TextView) findViewById(R.id.postBase_groupName);
        createTime= (TextView) findViewById(R.id.postBase_createTime);
        mainText= (TextView) findViewById(R.id.postBase_text);

        //回复ListView
        PostReplyListview= (ListView) findViewById(R.id.replyListView);

        replyCount= (TextView) findViewById(R.id.replyCount);
        pre= (Button) findViewById(R.id.pre);
        currentPage= (TextView) findViewById(R.id.currentPage);
        next= (Button) findViewById(R.id.next);

        pre.setOnClickListener(this);
        next.setOnClickListener(this);
        //di底部回复

        replyText= (EditText) findViewById(R.id.replyEdit);
        reply= (ImageButton) findViewById(R.id.reply_btn);

        reply.setOnClickListener(this);

        postDetail_image_linear= (LinearLayout) findViewById(R.id.postitemImage_linear);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.pre:
                if (pn>1){
                    --pn;
                    getPostReply();
                    adapter.notifyDataSetChanged();
                }else {
                    Log.e("pre","cuowu");
                }
                break;
            case R.id.next:
                if (pn<arrayList02.size()) {
                    ++pn;
                    getPostReply();
                    adapter.notifyDataSetChanged();

                }else {
                    Log.e("next","cuowu");
                }
                break;
            case R.id.reply_btn:

                if (isLogin==true) {
                    SendMyPostReply();
                }else {
                    Message message = new Message();
                    message.what = TOASTMESSAGE;
                    message.obj="请先登录";
                    handler.sendMessage(message);
                }
                break;

            default:
                break;
        }
    }

    private void getPostBase(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    String ApiHost = MyApplication.getApiHost();

                        String Pre_URL = "http://" + ApiHost + "/?service=Post.GetPostBase"
                                + "&post_id=" + posts_id
                                +"&id="+id;

                        //Log.e("PostsDetail.URL——————>",Pre_URL);
                        String resultData = Http_Url.getUrlReponse(Pre_URL);

                        postDetail_Base = GetBaseJson.getJSONParse(resultData);
                        if (postDetail_Base == null) {
                            Log.e("kan帖子失败", "PostBase_Fragment  ");
                        } else {

                            Message message = new Message();
                            message.what = GetPostBaseInfo;
                            message.obj = postDetail_Base;
                            handler.sendMessage(message);
                        }


                }catch (Exception e){
                    e.printStackTrace();
                    Log.e("PostsBase.Thread异常",e+"");
                }
            }
        }).start();
    }

    private void getPostReply(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String ApiHost = MyApplication.getApiHost();
                    String Pre_URL = "http://" + ApiHost + "/?service=Post.GetPostReply"
                            + "&post_id=" + posts_id
                            + "&pn=" + pn;
                    //Log.e("PostsDetails——reply.URL",Pre_URL);
                    String resultData = Http_Url.getUrlReponse(Pre_URL);
                    arrayList02 = GetPostReply_JSON.getJSONParse(resultData);
                    if (arrayList02 == null) {
                        Log.e("huo获得回复帖子失败", "Posts_Fragment  ");
                    } else {
                        //Log.e("获得回复帖子", "Posts_Fragment  "+arrayList02.size());
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

    private void SendMyPostReply(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    text=replyText.getText().toString();

                        String ApiHost = MyApplication.getApiHost();
                        String Pre_URL = "http://" + ApiHost + "/?service=Post.PostReply"
                                + "&post_id=" + posts_id
                                + "&text=" + text
                                + "&user_id=" + id;

                        String resultData = Http_Url.getUrlReponse(Pre_URL);
                        hashMap03 = PostsReply_JSON.getJSONParse(resultData);
                        if (hashMap03 == null) {
                            Log.e("hui帖子失败", "Posts_Fragment  ");
                        } else {
                            Message message = new Message();
                            message.what = PostReply;
                            message.obj = hashMap03;
                            handler.sendMessage(message);
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

    /*
    对Text进行处理
     */

    private void handleText(String s) {
        String source=s;
        String pattern = "<img\\ssrc=([^>]*)>";
        Pattern p = Pattern.compile(pattern);
        if (source!=null){
            Matcher m = p.matcher(source);
            Boolean real = source.matches(pattern);

            System.out.println(m.groupCount());

            int start = 0;
            int end = 0;
            getTextThread t2 = null;
            while (m.find()) {

                start = m.start();
                if ((start - end) == 0) {

                    end = m.end();
                    t2 = new getTextThread(m.group(1));
                    t2.start();

                } else {

                    String sub = null;
                    sub = source.substring(end, start);

                    if (t2 != null) {
                        try {

                            t2.join();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {

                    }

                    Message message = Message.obtain();
                    message.what = 6;
                    message.obj = sub;
                    handler.sendMessage(message);

                    end = m.end();

                    t2 = new getTextThread(m.group(1));
                    t2.start();
                }
            }

            if ((source.length()) > end) {

                if (t2 != null) {
                    try {

                        t2.join();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    //System.out.println(t2.isAlive());
                }

                String little = source.substring(end, source.length());
                Message message = Message.obtain();
                message.what = 6;
                message.obj = little;
                handler.sendMessage(message);

            }
        }
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
