package com.wuanan.frostmaki.wuanlife_app.Utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Frostmaki on 2016/8/8.
 */
/*public class ImageUpLoad {
    private static int CAMERA_REQUEST_CODE = 1;
    private static int GALLERY_REQUEST_CODE = 2;
    private static int CROP_REQUEST_CODE = 3;

    public static String uptoken ="drhxTyPuxNKJJ4SuDUhxGb-Osh_q52icfG8xak06:Xdp34ylO7AN1KbJvaUqfcYcMQRk=:eyJzY29wZSI6ImV4YW1wbGUwMyIsImRlYWRsaW5lIjoxNDcwNjg0MDk5fQ==";

    UploadManager uploadManager;
    public URL GetImageUpLoadUrl(ImageView imageView){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CAMERA_REQUEST_CODE){
            if (data==null){
                return;
            }else {
                Bundle bundle=data.getExtras();
                if (bundle!=null){
                    Bitmap bitmap=bundle.getParcelable("data");
                    //ImageView imageView= (ImageView) findViewById(R.id.imageView);
                    //imageView.setImageBitmap(bitmap);
                    Uri uri=saveBitmap(bitmap);
                    startiImageZoom(uri);
                }
            }
        }else if (requestCode==GALLERY_REQUEST_CODE){
            if(data==null){
                return;
            }{
                Uri uri;
                uri=data.getData();
                Toast.makeText(MainActivity.this,uri.toString(),Toast.LENGTH_SHORT).show();
                Uri fileUri=convertUri(uri);
                startiImageZoom(fileUri);
            }
        }else if (requestCode==CROP_REQUEST_CODE){
            if (data==null){
                return;
            }
            Bundle bundle=data.getExtras();
            Bitmap bitmap=bundle.getParcelable("data");
            ImageView imageView= (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmap);
            saveBitmapAndGetPath(bitmap);

        }
    }
    private void saveBitmapAndGetPath(Bitmap bitmap) {
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
            sendImage(s);

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    private Uri convertUri(Uri uri) {
        InputStream is=null;
        try {
            is=getContentResolver().openInputStream(uri);
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
        uploadManager.put(data, null, uptoken, new UpCompletionHandler() {
            public void complete(String key, ResponseInfo rinfo, JSONObject response) {
                String s = key + ", "+ "\n"  + rinfo + ", " + "\n" + response;
                Log.e("qiniutest", s);
                if (response!=null) {
                    try {
                        String responseKey = response.getString("key");
                        Log.e("Url", "http://obkpv2vzz.bkt.clouddn.com/" + responseKey);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("e", e + "");
                    }
                }


            }
        }, new UploadOptions(null, "test-type", true, null, null));
    }
}*/

