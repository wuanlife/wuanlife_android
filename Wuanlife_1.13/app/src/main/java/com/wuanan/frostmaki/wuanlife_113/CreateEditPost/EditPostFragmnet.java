package com.wuanan.frostmaki.wuanlife_113.CreateEditPost;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wuanan.frostmaki.wuanlife_113.R;
import com.wuanan.frostmaki.wuanlife_113.Utils.Http_Url;
import com.wuanan.frostmaki.wuanlife_113.Utils.MyApplication;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Frostmaki on 2016/10/4.
 */
public class EditPostFragmnet extends Fragment implements View.OnClickListener{
    View mainview;


    private EditText edit_title;
    private EditText edit_text;
    private HorizontalScrollView horizontalScrollView;
    private LinearLayout image_linear;
    private ImageButton addImage;
    private ImageButton startselectImage;
    private ImageButton sendPost;

    private Context context;
    private int user_id;
    private int post_id;
private String title;
    private String text;



    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    Toast.makeText(context,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    break;
                case 1 :
                    Toast.makeText(context,msg.obj.toString(),Toast.LENGTH_SHORT).show();

                    getActivity().finish();
                    break;
                case 2:
                    break;

            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainview=inflater.inflate(R.layout.fragment_createeditpost,container,false);

        initData();
        initView();
        return mainview;
    }

    private void initData() {
        context=getActivity().getApplicationContext();

        post_id=getArguments().getInt("post_id");
        user_id= getArguments().getInt("user_id");
        title=getArguments().getString("title");
        text=getArguments().getString("text");

    }
    private void initView() {

        edit_title= (EditText) mainview.findViewById(R.id.createeditpost_title);
        edit_title.setText(title);

        edit_text= (EditText) mainview.findViewById(R.id.createeditpost_text);
        edit_text.setText(text);

        horizontalScrollView= (HorizontalScrollView) mainview.findViewById(R.id.horScroll);
        image_linear= (LinearLayout) mainview.findViewById(R.id.image_linear);
        startselectImage= (ImageButton) mainview.findViewById(R.id.startselectpicture);
        startselectImage.setVisibility(View.INVISIBLE);

        sendPost= (ImageButton)mainview. findViewById(R.id.send);

        sendPost.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.send :

                sendMethod();
                break;
        }
    }

    private void sendMethod() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String ApiHost= MyApplication.getApiHost();
                String Pre_URL="http://"+ApiHost+"/?service=post.editPost"
                        +"&user_id="+user_id
                        +"&&post_id="+post_id
                        +"&title="+edit_title.getText().toString()
                        +"&text="+edit_text.getText();
                String resultData= Http_Url.getUrlReponse(Pre_URL);
                String msg=null;
                try {
                    JSONObject jsonObject = new JSONObject(resultData);
                    JSONObject data = jsonObject.getJSONObject("data");

                    int code=data.getInt("code");
                    Message message=new Message();
                    if (code==1) {
                        message.what=1;
                    }else {
                        message.what=0;
                    }
                    message.obj=data.getString("msg");
                    handler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
