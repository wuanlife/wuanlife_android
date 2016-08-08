package com.wuanan.frostmaki.wuanlife_app.CreatePosts;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.wuanan.frostmaki.wuanlife_app.MyApplication;
import com.wuanan.frostmaki.wuanlife_app.R;
import com.wuanan.frostmaki.wuanlife_app.Utils.Http_Url;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by Frostmaki on 2016/7/29.
 */
public class CreatePosts_Fragment extends Fragment implements View.OnClickListener{
    private EditText title;
    private EditText text;
    private Button determine;
    private Button selectImage;
    private ImageView imageView;

    UploadManager uploadManager;
    private View view;

    private Context mContext;
    private String userID="";
    private String GroupID="";
    private String Title="";
    private String Text="";
    private String p_image=null;
    private HashMap<String,String> hashMap=null;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    Toast.makeText(mContext,"fa帖子成功",Toast.LENGTH_SHORT).show();
                    //zhuan到页面
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.createposts,container,false);
        title= (EditText) view.findViewById(R.id.title);
        text= (EditText) view.findViewById(R.id.text);
        determine= (Button) view.findViewById(R.id.determine);
        selectImage= (Button) view.findViewById(R.id.button2);

        mContext=getActivity().getApplicationContext();

        determine.setOnClickListener(this);
        selectImage.setOnClickListener(this);

        uploadManager=new UploadManager();

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

            case R.id.button2:
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_REQUEST_CODE);
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

                    GroupID = getArguments().getString("groupId");
                    Title=title.getText().toString();
                    Text=text.getText().toString();
                    String ApiHost=MyApplication.getUrl();
                    String Pre_URL="http://"+ApiHost+"/?service=Group.Posts"
                            +"&user_id="+userID
                            +"&group_base_id="+GroupID
                            +"&title="+Title
                            +"&text="+Text
                            +"&p_image="+p_image;

                    String resultData= Http_Url.getUrlReponse(Pre_URL);
                    hashMap=CreatePosts_JSON.getJSONParse(resultData);
                    if (hashMap==null){
                        Log.e("fa帖子失败","createPosts_Fragment  ");
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

    private static int CAMERA_REQUEST_CODE = 1;
    private static int GALLERY_REQUEST_CODE = 2;
    private static int CROP_REQUEST_CODE = 3;

    //public static String uptoken ="drhxTyPuxNKJJ4SuDUhxGb-Osh_q52icfG8xak06:Xdp34ylO7AN1KbJvaUqfcYcMQRk=:eyJzY29wZSI6ImV4YW1wbGUwMyIsImRlYWRsaW5lIjoxNDcwNjg0MDk5fQ==";
private String uptoken=MyApplication.getUptoken();

    /*public URL GetImageUpLoadUrl(ImageView imageView){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
        return null;
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CAMERA_REQUEST_CODE){
            if (data==null){
                return;
            }else {
                Bundle bundle=data.getExtras();

                Bitmap bitmap=bundle.getParcelable("data");
                //ImageView imageView= (ImageView) findViewById(R.id.imageView);
                //imageView.setImageBitmap(bitmap);
                Uri uri=saveBitmap(bitmap);
                if (uri!=null) {
                    startiImageZoom(uri);
                }

            }
        }else if (requestCode==GALLERY_REQUEST_CODE){
            if(data==null){
                return;
            }{
                Uri uri;
                uri=data.getData();
                Toast.makeText(mContext,uri.toString(),Toast.LENGTH_SHORT).show();
                Uri fileUri=convertUri(uri);
                startiImageZoom(fileUri);
            }
        }else if (requestCode==CROP_REQUEST_CODE){
            if (data==null){
                return;
            }
            Bundle bundle=data.getExtras();
            Bitmap bitmap=bundle.getParcelable("data");
            if (bitmap!=null){
                ImageView imageView= (ImageView) view.findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);
                saveBitmapAndGetPath(bitmap);
            }
        }
    }
    public void saveBitmapAndGetPath(Bitmap bitmap) {
        File tmpDir=new File(Environment.getExternalStorageDirectory()+"/com.wuanlife/");
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
    }
    private Uri convertUri(Uri uri) {
        InputStream is=null;
        try {
            is=mContext.getContentResolver().openInputStream(uri);

            Bitmap bitmap= BitmapFactory.decodeStream(is);
            is.close();
            return saveBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }

    private Uri saveBitmap(Bitmap bitmap){
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
            //String s=tmpDir.getAbsolutePath()+"avater.png";
            //sendImage(s);
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void startiImageZoom(Uri uri){
        Intent intent=new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        intent.putExtra("crop","true");
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        intent.putExtra("outputX",150);
        intent.putExtra("outputY",150);
        intent.putExtra("return-data",true);
        startActivityForResult(intent,CROP_REQUEST_CODE);

    }

    private void sendImage(String s) {
        //ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //bm.compress(Bitmap.CompressFormat.PNG, 60, stream);
        // byte[] bytes = stream.toByteArray();
        //byte[] data=Base64.encode(bytes, Base64.DEFAULT);

        String data=s;
        Log.e("data--->",data+"");

        Log.e("qiniutest", "starting......");
        //String upkey = "uploadtest.png";
        String upkey=null;
        uploadManager.put(
                data,
                null, uptoken, new UpCompletionHandler() {
                    public void complete(String key, ResponseInfo rinfo, JSONObject response) {
                        String s = key + ", "+ "\n"  + rinfo + ", " + "\n" + response;
                        Log.e("qiniutest", s);
                        if (response!=null) {
                            try {
                                String responseKey = response.getString("key");
                                Log.e("Url", "http://obkpv2vzz.bkt.clouddn.com/" + responseKey);
                                p_image="http://obkpv2vzz.bkt.clouddn.com/" + responseKey;
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("e", e + "");
                            }
                        }


                    }
                }, new UploadOptions(null, "test-type", true, null, null));
    }
}