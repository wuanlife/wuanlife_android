package com.wuanan.frostmaki.wuanlife_113.CreateEditPost;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.wuanan.frostmaki.wuanlife_113.R;
import com.wuanan.frostmaki.wuanlife_113.Utils.Http_Url;
import com.wuanan.frostmaki.wuanlife_113.Utils.MyApplication;
import com.wuanan.frostmaki.wuanlife_113.Utils.Permission;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Frostmaki on 2016/10/4.
 */
public class CreatePostFragment extends Fragment implements View.OnClickListener{
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
    private int group_id;
    private Uri imagecontentpath=null;
    private String imagefilepath=null;
    private String imageNetpath=null;

    private static int CAMERA_REQUEST_CODE = 1;
    private static int GALLERY_REQUEST_CODE = 2;
    private static int CROP_REQUEST_CODE = 3;
    private String uptoken=null;
    private UploadManager uploadManager;


    private View view;
    private static int IMAGE_LINEAR_CHILDCOUNT=10;

    private int id_index=0;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:

                    String netpath=msg.getData().getString("netpath");
                    InputStream is=null;
                    try {
                        is = context.getContentResolver().openInputStream(imagecontentpath);

                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        addImageMethod(bitmap,netpath);
                        is.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case 1 :
                    Toast.makeText(context,"发帖成功",Toast.LENGTH_SHORT).show();

                    getActivity().finish();
                    break;
                case 2:
                    for (int i=0;i<image_linear.getChildCount();i++){
                        if (msg.getData().getInt("id")==image_linear.getChildAt(i).getId()){
                            image_linear.removeView(image_linear.getChildAt(i));
                        }
                    }
                    break;
                case 3:
                    Toast.makeText(context,msg.obj.toString(),Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainview=inflater.inflate(R.layout.fragment_createeditpost,container,false);
        initView();
        initData();
        return mainview;
    }

    private void initData() {
        context=getActivity().getApplicationContext();

        group_id=getArguments().getInt("group_id");
        user_id= getArguments().getInt("user_id");

        uptoken= MyApplication.getUptoken();
        uploadManager=new UploadManager();

        //btnThread=new BtnThread();
    }
    private void initView() {

        edit_title= (EditText) mainview.findViewById(R.id.createeditpost_title);
        edit_text= (EditText) mainview.findViewById(R.id.createeditpost_text);
        horizontalScrollView= (HorizontalScrollView) mainview.findViewById(R.id.horScroll);
        image_linear= (LinearLayout) mainview.findViewById(R.id.image_linear);
        addImage= (ImageButton) mainview.findViewById(R.id.addImage);
        startselectImage= (ImageButton) mainview.findViewById(R.id.startselectpicture);
        sendPost= (ImageButton)mainview. findViewById(R.id.send);


        startselectImage.setOnClickListener(this);
        sendPost.setOnClickListener(this);
        addImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startselectpicture :
                if (horizontalScrollView.getVisibility()==View.VISIBLE){

                    horizontalScrollView.setVisibility(View.GONE);

                }else if (horizontalScrollView.getVisibility()==View.GONE){

                    horizontalScrollView.setVisibility(View.VISIBLE);

                }
                break;
            case R.id.addImage :
                if (image_linear.getChildCount()<=IMAGE_LINEAR_CHILDCOUNT) {
                    startselectpicture();
                } else{
                    Toast.makeText(context,"图片最多10个",Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.send :

                sendMethod();
                break;
        }
    }

    private void sendMethod() {
        String text=edit_title.getText().toString();
        ArrayList<String> arraylist=new ArrayList<String>();
        for (int i=1;i<image_linear.getChildCount();i++){
            //Log.e("image.url---->", (String) image_linear.getChildAt(i).getTag());
            arraylist.add((String) image_linear.getChildAt(i).getTag());
            text=text+(String) image_linear.getChildAt(i).getTag();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                String ApiHost= MyApplication.getApiHost();
                String Pre_URL="http://"+ApiHost+"/?service=Group.Posts"
                        +"&user_id="+user_id
                        +"&group_base_id="+group_id
                        +"&title="+edit_title.getText().toString()
                        +"&text="+edit_text.getText();// +"&p_image="+p_image
                String resultData= Http_Url.getUrlReponse(Pre_URL);
                String msg=null;
                try {

                    JSONObject jsonObject = new JSONObject(resultData);
                    int ret=jsonObject.getInt("ret");
                    Message message = new Message();
                    if (ret==200){
                        JSONObject data = jsonObject.getJSONObject("data");

                        int code = data.getInt("code");


                        if (code == 1) {
                            message.what = 1;
                            JSONObject info = data.getJSONObject("info");
                            String post_id = info.getString("post_base_id");
                            message.obj = post_id;
                        /*
                        跳转到详情帖子
                         */

                        } else {
                            message.what = 3;
                            msg = data.getString("msg");
                            message.obj = msg;

                        }
                    }else {
                        message.what = 3;
                        msg = jsonObject.getString("msg");
                        message.obj = msg;
                    }
                    handler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /*
    打开相册
    选择图片
    剪辑，到零时文件夹
    将路径传递过来
    设置给新的ImageView
    addView；
     */
    private void startselectpicture() {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }

    private void addImageMethod(Bitmap bitmap,String s){


        view= LayoutInflater.from(context).inflate(R.layout.imageitem,image_linear,false);
        final FrameLayout frame= (FrameLayout) view.findViewById(R.id.frame);
        if (frame.getParent()!=null){
            ((ViewGroup)frame.getParent()).removeView(frame);

        }
        frame.setId(id_index);
        ImageView imageView_1= (ImageView) frame.getChildAt(0);
        imageView_1.setImageBitmap(bitmap);
        frame.setTag(s);

        final ImageView imageView_2= (ImageView) frame.getChildAt(1);
        imageView_2.setId(id_index);
        imageView_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"111",Toast.LENGTH_SHORT).show();
            }
        });
        imageView_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Message message=new Message();
                message.what=2;
                Bundle bundle=new Bundle();
                bundle.putInt("id",imageView_2.getId());
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
        image_linear.addView(frame);
        id_index++;
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GALLERY_REQUEST_CODE){
            if(data==null){
                return;
            }else {
                Uri uri;
                uri=data.getData();
                imagecontentpath=uri;
                Uri fileUri=convertUri(uri);

            }
        }
    }


    private Uri convertUri(Uri uri) {
        InputStream is=null;
        try {
            is=context.getContentResolver().openInputStream(uri);

            Bitmap bitmap= BitmapFactory.decodeStream(is);
            is.close();

            return saveBitmapAndGetPath(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Uri saveBitmapAndGetPath(Bitmap bitmap) {
        Permission.verifyStoragePermissions(getActivity());
        File tmpDir=new File(Environment.getExternalStorageDirectory()+"/com.wuanlife_113/");
        if (!tmpDir.exists()){
            tmpDir.mkdir();
        }
        File img=new File(tmpDir.getAbsolutePath()+"really.png");

        try {
            FileOutputStream fos=new FileOutputStream(img);
            bitmap.compress(Bitmap.CompressFormat.PNG,85,fos);
            fos.flush();
            fos.close();

            String s=tmpDir.getAbsolutePath()+"really.png";
            if (s!=null) {
                sendImage(s);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("e",e+"");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("e",e+"");
        }
        return Uri.fromFile(img);
    }

  /*  private Uri saveBitmap(Bitmap bitmap){
        Bitmap old=bitmap;
        File tmpDir=new File(Environment.getExternalStorageDirectory()+"/com.wuanlife/");
        if (!tmpDir.exists()){
            tmpDir.mkdir();
        }
        File img=new File(tmpDir.getAbsolutePath()+"avater.png");

        try {
            FileOutputStream fos=new FileOutputStream(img);
            bitmap.compress(Bitmap.CompressFormat.PNG,85,fos);
            fos.flush();
            fos.close();
            String s=tmpDir.getAbsolutePath()+"avater.png";
            //sendImage(s);

            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }*/

   /* private void startiImageZoom(Uri uri){
        Intent intent=new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        intent.putExtra("crop","true");
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        intent.putExtra("outputX",150);
        intent.putExtra("outputY",150);
        intent.putExtra("return-data",true);
        startActivityForResult(intent,CROP_REQUEST_CODE);

    }*/

    private void sendImage(String s) {

        imagefilepath=s;
        sendQiNiu Thread_sendQiNiu=new sendQiNiu();

        Thread_sendQiNiu.start();

    }


    class sendQiNiu extends Thread{

        @Override
        public void run() {

            //Log.e("data--->",imagefilepath+"");

            //Log.e("qiniutest", "starting......");
            //String upkey = "uploadtest.png";
            String upkey=null;
            uploadManager.put(
                    imagefilepath,
                    null, uptoken, new UpCompletionHandler() {
                        public void complete(String key, ResponseInfo rinfo, JSONObject response) {
                            String s = key + ", "+ "\n"  + rinfo + ", " + "\n" + response;
                            Log.e("qiniutest", s);
                            if (response!=null) {
                                try {
                                    String responseKey = response.getString("key");
                                    Log.e("Url", "http://"+MyApplication.getYuMing()+"/" + responseKey);
                                    imageNetpath="<img src="+"http://"+MyApplication.getYuMing()+"/"+ responseKey+">";

                                    Message message=new Message();
                                    message.what=0;
                                    Bundle bundle=new Bundle();
                                    bundle.putString("netpath",imageNetpath);

                                    message.setData(bundle);
                                    handler.sendMessage(message);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.e("e", e + "");
                                }
                            }


                        }
                    }, new UploadOptions(null, "test-type", true, null, null));
        }
    }


  /*  class getImageFromNet extends Thread{
            String str=null;
        public getImageFromNet(String s){
            str=s;
        }
        @Override
        public void run() {
            //String s="http://b.hiphotos.baidu.com/image/h%3D360/sign=8918c5efbe3eb1355bc7b1bd961ea8cb/7a899e510fb30f244bb50504ca95d143ad4b038d.jpg";
            try {
                URL url=new URL(str);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                Bitmap bitmap=BitmapFactory.decodeStream(inputStream);



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }*/
}
