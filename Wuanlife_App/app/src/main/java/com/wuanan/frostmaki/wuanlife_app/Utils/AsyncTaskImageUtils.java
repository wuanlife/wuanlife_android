package com.wuanan.frostmaki.wuanlife_app.Utils;

/**
 * Created by Frostmaki on 2016/8/2.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

//因为是网络加载一张图片，所以传入参数为URL为String类型，并且返回一个Bitmap对象
public class AsyncTaskImageUtils extends AsyncTask<String, Void, Bitmap>{
    private Bitmap mBitmap;

    private ImageView imageView;
    private String urlTag;

    public AsyncTaskImageUtils(ImageView mImageView){
        imageView=mImageView;
    }
    @Override
    protected void onPreExecute() {//在执行异步任务之前调用，做一些初始化的操作
        super.onPreExecute();
    }
    @Override
    protected Bitmap doInBackground(String... params) {
        //写耗时的网络请求操作
        urlTag=params[0];//因为只传了一个URL参数
        if (urlTag!=null) {
            try {

                String str = URLEncoder.encode(urlTag, "UTF-8");
                String strURL = str.replaceAll("%3A", ":").replaceAll("%2F", "/");
                Log.e("11111111111111", strURL);

                URL mURL = new URL(strURL);
                HttpURLConnection conn = (HttpURLConnection) mURL.openConnection();
                //Thread.sleep(3000);//为了看到ProgressBar加载的效果，不会因为很快就加载完了，让它sleep 3秒
                InputStream is = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                mBitmap = BitmapFactory.decodeStream(bis);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mBitmap;
        }
        return null;
    }
    @Override
    protected void onPostExecute(Bitmap result) {
        if (result!=null) {
            if (imageView.getTag().equals(urlTag)) {
                imageView.setImageBitmap(result);
            }
        }
    }


}
