package com.wuanan.frostmaki.wuanlife_app.Post.PostEdit;

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
import android.widget.Toast;

import com.wuanan.frostmaki.wuanlife_app.CreatePosts.CreatePosts_JSON;
import com.wuanan.frostmaki.wuanlife_app.MyApplication;
import com.wuanan.frostmaki.wuanlife_app.R;
import com.wuanan.frostmaki.wuanlife_app.Utils.Http_Url;

import java.util.HashMap;

/**
 * Created by Frostmaki on 2016/7/29.
 */
public class EditPosts_Fragment extends Fragment implements View.OnClickListener{
    private EditText title;
    private EditText text;
    private Button determine;

    private Context mContext;
    private String userID="";
    private String PostID="";
    private String Title="";
    private String Text="";
    private HashMap<String,String> hashMap=null;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    Toast.makeText(mContext,"编辑成功",Toast.LENGTH_SHORT).show();
                    //zhuan到页面
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.createposts,container,false);
        title= (EditText) view.findViewById(R.id.title);
        title.setText(getArguments().getString("title"));
        text= (EditText) view.findViewById(R.id.text);
        text.setText(getArguments().getString("text"));
        determine= (Button) view.findViewById(R.id.determine);

        mContext=getActivity().getApplicationContext();

        determine.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.determine:
                Log.e("determine","click");
                if (title.getText().toString()==null){
                    Toast.makeText(mContext,"没写标题",Toast.LENGTH_SHORT).show();
                }else if (text.getText().toString()==null){
                    Toast.makeText(mContext,"没写内容",Toast.LENGTH_SHORT).show();
                }else {
                    getRes();
                    getFragmentManager().popBackStack();
                }
                break;

            default:
                break;
        }

    }

    private void getRes(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    userID = MyApplication.getUserInfo().get(0).get("userID");

                    PostID = getArguments().getString("postID");
                    Title=title.getText().toString();
                    Text=text.getText().toString();
                    String ApiHost=MyApplication.getUrl();
                    String Pre_URL="http://"+ApiHost+"/?service=Post.editPost"
                            +"&user_id="+userID
                            +"&post_id="+PostID
                            +"&title="+Title
                            +"&text="+Text;

                    String resultData= Http_Url.getUrlReponse(Pre_URL);
                    hashMap=CreatePosts_JSON.getJSONParse(resultData);
                    if (hashMap==null){
                        Log.e("编辑帖子失败","EditPosts_Fragment  ");
                    }else {
                        Message message=new Message();
                        message.what=0;
                        message.obj=hashMap;
                        handler.sendMessage(message);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    Log.e("createPosts.Thread异常",e+"");
                }
            }
        }).start();
    }
}
