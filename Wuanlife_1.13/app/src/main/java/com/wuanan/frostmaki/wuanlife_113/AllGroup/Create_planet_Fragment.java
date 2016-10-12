package com.wuanan.frostmaki.wuanlife_113.AllGroup;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.renderscript.Type;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.qiniu.util.Auth;
import com.wuanan.frostmaki.wuanlife_113.R;
import com.wuanan.frostmaki.wuanlife_113.Utils.Http_Url;
import com.wuanan.frostmaki.wuanlife_113.Utils.ImageLoader;
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
import java.util.HashMap;


/**
 * Created by Frostmaki on 2016/7/16.
 */
public class Create_planet_Fragment extends Fragment{
    private Button create;
    private ImageButton selectImage;
    private EditText name;
    private EditText introduction;
    private Context mContext;
    private View view;
    private String g_imageUrl=null;
    private String data=null;

    private BtnThread btnThread;
private Thread1 t1;
    UploadManager uploadManager;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    Toast.makeText(mContext, "星球创建成功", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    break;
                case 1:
                    String failure=msg.obj.toString();
                    Toast.makeText(mContext, failure, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(mContext, "未登录", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            Log.e("创建星球","转到所有星球界面");

        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_creategroup, container, false);
        name = (EditText) view.findViewById(R.id.edit_groupName);
        introduction = (EditText) view.findViewById(R.id.edit_introduction);

        create = (Button) view.findViewById(R.id.button);
        mContext = getActivity().getApplicationContext();
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String GroupName = name.getText().toString();
                if (GroupName.length() > 0) {
                    Log.e("创建星球", "开始创建");
                    createDialog();


                } else Toast.makeText(mContext, "未输入星球名称", Toast.LENGTH_SHORT).show();
            }
        });
        uploadManager=new UploadManager();
        selectImage= (ImageButton) view.findViewById(R.id.create_planet_btn);
        selectImage.setColorFilter(Color.argb(255, 24, 165, 204));
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage.setColorFilter(Color.argb(255, 255, 255, 255));
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_REQUEST_CODE);
            }
        });
        return view;
    }

    private void createDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
        dialog.setTitle("");
        dialog.setMessage("确认创建星球");
        dialog.setCancelable(false);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                    btnThread=new BtnThread();
                    btnThread.start();

            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }


    class BtnThread extends Thread{
            public void run(){
                try {
                    t1.join();
                    BtnThread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                        Log.e("创建星球","开始线程");
                        if (MyApplication.getUserInfo() != null) {


                            String userID = MyApplication.getUserInfo().get(0).get("userID");
                            String ApiHost = MyApplication.getApiHost();

                            String Pre_URL = "http://" + ApiHost + "/?service=Group.Create&user_id=" + userID +
                                    "&name=" + name.getText().toString()
                                    +"&g_introduction="+introduction.getText().toString()
                                    +"&g_image="+g_imageUrl;
                            String resultData = Http_Url.getUrlReponse(Pre_URL);
                            try {
                                JSONObject jsonObject = new JSONObject(resultData);
                                JSONObject data = jsonObject.getJSONObject("data");
                                int code = data.getInt("code");
                                if (code == 1) {
                                    JSONObject info = data.getJSONObject("info");
                                    ArrayList<HashMap<String, String>> createGroupInfo = new ArrayList<HashMap<String, String>>();
                                    HashMap<String, String> map = new HashMap<String, String>();
                                    String group_base_id = "";//星球ID
                                    int user_base_id = 0;//用户ID
                                    String authorization = "";//用户权限 01表示创建者，02表示管理员，03表示会员
                                    String name = "";//星球名字
                                    String g_introduction = "";//星球简介
                                    String URL = "";//星球图片资源

                                    group_base_id = info.getString("group_base_id");
                                    user_base_id = info.getInt("user_base_id");
                                    authorization = info.getString("authorization");
                                    name = info.getString("name");
                                    g_introduction = info.getString("g_introduction");
                                    URL = info.getString("URL");

                                    map.put("group_base_id", group_base_id);
                                    map.put("user_base_id", user_base_id + "");
                                    map.put("authorization", authorization);
                                    map.put("name", name);
                                    map.put("g_introduction", g_introduction);
                                    map.put("URL", URL);
                                    createGroupInfo.add(map);

                                    Log.e("创建星球","创建成功");
                                    Message message=new Message();
                                    message.what=0;
                                    handler.sendMessage(message);

                                } else if (code==0){
                                    Log.e("创建星球","创建失败"+resultData);
                                    String msg=data.getString("msg");
                                    Message message=new Message();
                                    message.what=1;
                                    message.obj=msg;
                                    handler.sendMessage(message);

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("创建星球","异常"+e);
                            }

                        }else {
                            Log.e("创建星球","未登录");
                            Message message=new Message();
                            message.what=2;
                            handler.sendMessage(message);

                        }
                    }
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
                //Toast.makeText(mContext,uri.toString(),Toast.LENGTH_SHORT).show();
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
                //ImageView imageView= (ImageView) view.findViewById(R.id.imageView);
                saveBitmapAndGetPath(bitmap);
                selectImage.setColorFilter(Color.argb(0, 255, 255, 255));
                Log.e("加载图片1111","");

                selectImage.setImageBitmap(bitmap);


            }else {
                Log.e("weinnnno加载图片1111","");
            }
        }
    }
    public void saveBitmapAndGetPath(Bitmap bitmap) {
        Permission.verifyStoragePermissions(getActivity());
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
        Permission.verifyStoragePermissions(getActivity());
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

    String AccessKey=MyApplication.getAccessKey();
    String SecretKey=MyApplication.getSecretKey();
    String bucketname=MyApplication.getBucketname();

    private String getUptoken(){
        Auth auth= Auth.create(AccessKey,SecretKey);
        return auth.uploadToken(bucketname);
    }
    private void sendImage(String s) {
        //ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //bm.compress(Bitmap.CompressFormat.PNG, 60, stream);
        // byte[] bytes = stream.toByteArray();
        //byte[] data=Base64.encode(bytes, Base64.DEFAULT);
        data=s;
        t1=new Thread1();
        t1.start();

    }
    class Thread1 extends Thread {


        public void run () {
            //String data = s;
            Log.e("data--->", data + "");

            Log.e("qiniutest", "starting......");
            //String upkey = "uploadtest.png";

            String upkey = null;


            uploadManager.put(
                    data,
                    null, getUptoken(), new UpCompletionHandler() {
                        public void complete(String key, ResponseInfo rinfo, JSONObject response) {
                            String s = key + ", " + "\n" + rinfo + ", " + "\n" + response;
                            Log.e("qiniutest", s);
                            if (response != null) {
                                try {
                                    String responseKey = response.getString("key");
                                    Log.e("Url", "http://"+MyApplication.getYuMing()+"/" + responseKey);
                                    g_imageUrl = "http://"+MyApplication.getYuMing()+"/" + responseKey;
                                    //selectImage.setTag(g_imageUrl);
                                    //selectImage.setColorFilter(null);
                                    //new ImageLoader().showImageByThreadImageButton(selectImage,g_imageUrl);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.e("e", e + "");
                                }
                            }


                        }
                    }, new UploadOptions(null, "test-type", true, null, null));
        }
    }

}
